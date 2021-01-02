/*
 *  ReportServer
 *  Copyright (c) 2007 - 2020 InfoFabrik GmbH
 *  http://reportserver.net/
 *
 *
 * This file is part of ReportServer.
 *
 * ReportServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package net.datenwerke.rs.passwordpolicy.service.lostpassword;

import java.util.Set;

import javax.persistence.NoResultException;

import net.datenwerke.rs.authenticator.client.login.dto.ChallengeResponseAuthToken;
import net.datenwerke.rs.authenticator.client.login.dto.ChallengeResponseContainer;
import net.datenwerke.rs.authenticator.client.login.dto.NamedUserAuthToken;
import net.datenwerke.rs.authenticator.client.login.dto.UserPasswordAuthToken;
import net.datenwerke.rs.authenticator.cr.service.ChallengeResponseService;
import net.datenwerke.rs.authenticator.cr.service.SessionChallengeContainer;
import net.datenwerke.rs.passwordpolicy.service.BsiPasswordPolicyService;
import net.datenwerke.rs.passwordpolicy.service.BsiPasswordPolicyUserMetadata;
import net.datenwerke.rs.utils.crypto.PasswordHasher;
import net.datenwerke.security.client.login.AuthToken;
import net.datenwerke.security.service.authenticator.hooks.PreAuthenticateHook;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.UserPropertiesService;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.security.service.usermanager.entities.UserProperty;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Tests whether a user tries to authenticate with a temporary password, 
 * assigned by the lostPassword module and if so performs necessary tasks
 * to ensure a new temporary password gets assigned
 * 
 *
 */
public class LostPasswordPreAuthenticateHook implements PreAuthenticateHook {

	private final UserManagerService userManagerService;
	private final  PasswordHasher passwordHasher;
	private final ChallengeResponseService challengeResponseService;
	private final UserPropertiesService userPropertiesService;
	private final BsiPasswordPolicyService bsiPasswordPolicyService;
	private final Provider<SessionChallengeContainer> challengeContainerProvider;

	@Inject
	public LostPasswordPreAuthenticateHook(
		UserManagerService userManagerService, 
		PasswordHasher passwordHasher,
		ChallengeResponseService challengeResponseService, 
		UserPropertiesService userPropertiesService, 
		BsiPasswordPolicyService bsiPasswordPolicyService,
		Provider<SessionChallengeContainer> challengeContainerProvider
		) {
		
		this.userManagerService = userManagerService;
		this.passwordHasher = passwordHasher;
		this.challengeResponseService = challengeResponseService;
		this.userPropertiesService = userPropertiesService;
		this.bsiPasswordPolicyService = bsiPasswordPolicyService;
		this.challengeContainerProvider = challengeContainerProvider;
	}
	
	@Override
	public void authenticating(AuthToken[] tokens) {
		for(AuthToken token : tokens){
			if(token instanceof NamedUserAuthToken){
				NamedUserAuthToken nameUserToken = (NamedUserAuthToken) token;

				/* get user */
				User u = null;
				try{
					u = userManagerService.getUserByName(nameUserToken.getUsername());
					if(null == u)
						continue;
				}catch(NoResultException e){ 
					continue;
				}
				
				UserProperty tmpPasswdProp = userPropertiesService.getProperty(u, LostPasswordModule.USER_PROPERTY_TMP_PASSWORD);
				UserProperty tmpPasswdDateProp = userPropertiesService.getProperty(u, LostPasswordModule.USER_PROPERTY_TMP_PASSWORD_DATE);
				
				if(null == tmpPasswdDateProp || null == tmpPasswdProp)
					continue;
					
				long createDate = Long.valueOf(tmpPasswdDateProp.getValue());

				/* abort if tmp password has expired */
				if(System.currentTimeMillis() - createDate < 60 * 60 * 1000){

					/* set the temporary password as the real password and enforce password change */
					if(validatePassword(token, tmpPasswdProp)){
						/* password is already hashed */
						u.setPassword(tmpPasswdProp.getValue(), new PasswordHasher() {
							@Override
							public String hashPassword(String password) {
								return password;
							}
							
							@Override
							public String getHmacPassphrase() {
								return null;
							}

							@Override
							public boolean validatePassword(
									String hashedPassword,
									String cleartextPassword) {
								return false;
							}

							@Override
							public String hashPassword(String password, String salt) {
								return password;
							}
						});
						
						BsiPasswordPolicyUserMetadata userMetadata = bsiPasswordPolicyService.getUserMetadata(u);
						userMetadata.enforcePasswordChangeOnNextLogin();
						bsiPasswordPolicyService.updateUserMetadata(u, userMetadata);
					}
				}						
			
				Set<UserProperty> properties = u.getProperties();
				tmpPasswdDateProp.setValue("0");
				properties.remove(tmpPasswdDateProp);
				properties.remove(tmpPasswdProp);
				u.setProperties(properties);
				
				userManagerService.merge(u);
				
				
				return;
			}
		}
	}

	private boolean validatePassword(AuthToken token, UserProperty tmpPasswdProp) {
		if(token instanceof UserPasswordAuthToken){
			return passwordHasher.validatePassword(tmpPasswdProp.getValue(), ((UserPasswordAuthToken)token).getPassword());
		} else if(token instanceof ChallengeResponseAuthToken){
			ChallengeResponseContainer challengeResponse = ((ChallengeResponseAuthToken)token).getChallengeResponse();
			boolean validationResult = challengeResponseService.validateResponse(challengeResponse, tmpPasswdProp.getValue());
			challengeContainerProvider.get().setContainer(challengeResponse);
			return validationResult;
		}
	
		return false;
	}

}
