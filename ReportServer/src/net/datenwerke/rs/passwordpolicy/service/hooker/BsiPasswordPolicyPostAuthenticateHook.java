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
 
 
package net.datenwerke.rs.passwordpolicy.service.hooker;

import java.util.Date;

import net.datenwerke.async.DwAsyncService;
import net.datenwerke.async.helpers.TransactionalRunnableFactory;
import net.datenwerke.rs.passwordpolicy.client.PasswordExpiredAuthenticationResultInfo;
import net.datenwerke.rs.passwordpolicy.service.BsiPasswordPolicy;
import net.datenwerke.rs.passwordpolicy.service.BsiPasswordPolicyService;
import net.datenwerke.rs.passwordpolicy.service.BsiPasswordPolicyUserMetadata;
import net.datenwerke.rs.utils.misc.DateUtils;
import net.datenwerke.security.client.login.resultinfos.AccountLockedAuthenticateResultInfo;
import net.datenwerke.security.service.authenticator.AuthenticationResult;
import net.datenwerke.security.service.authenticator.hooks.PostAuthenticateHook;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;

public class BsiPasswordPolicyPostAuthenticateHook implements PostAuthenticateHook{

	private final static int MILLISECONDS_PER_MINUTE = 60 * 1000;

	private final UserManagerService userManagerService;
	private final DwAsyncService dwAsyncService;
	private final TransactionalRunnableFactory trFactory;

	private final BsiPasswordPolicyService bsiPasswordPolicyService;

	@Inject
	public BsiPasswordPolicyPostAuthenticateHook(
			UserManagerService userManagerService, 
			DwAsyncService dwAsyncService,
			TransactionalRunnableFactory trFactory, 
			BsiPasswordPolicyService bsiPasswordPolicyService
			) {
		this.userManagerService = userManagerService;
		this.dwAsyncService = dwAsyncService;
		this.trFactory = trFactory;
		this.bsiPasswordPolicyService = bsiPasswordPolicyService;
	}

	@Override
	public void authenticated(final AuthenticationResult authRes) {
		if(!bsiPasswordPolicyService.isActive())
			return;

		User user = authRes.getUser();

		if(null != user){
			final BsiPasswordPolicyUserMetadata data = bsiPasswordPolicyService.getUserMetadata(user);
			
			BsiPasswordPolicy bsiPasswordPolicy = bsiPasswordPolicyService.getPolicy();
			
			/* Check if there are failed login attempts to reset */
			if(null != data.getLastFailedLogin()){
				long sinceLastFailure = (System.currentTimeMillis() - data.getLastFailedLogin().getTime())/MILLISECONDS_PER_MINUTE;
				if(sinceLastFailure > bsiPasswordPolicy.getAccountLockoutAutoResetTimeout()){
					data.setFailedLoginCount(0);
					data.setLastFailedLogin(null);
				}
			}
			
			/* Check if account is locked */
			if(data.getFailedLoginCount() > bsiPasswordPolicy.getAccountLockoutThreshold()){
				authRes.setAllowed(false);
				authRes.addInfo(new AccountLockedAuthenticateResultInfo(new Date((long)data.getLastFailedLogin().getTime() + ((long)bsiPasswordPolicy.getAccountLockoutAutoResetTimeout() * (long)MILLISECONDS_PER_MINUTE))));
				return;
			}
			
			if(authRes.isAllowed()){
				/* process successful login */
				data.registerSuccessfulLogin();

				/* Check if password has expired */
				if(bsiPasswordPolicy.getPasswordMaxAge() > 0){
					if(null != user.getPassword()){
						int expiresIn = 0;

						if(null != data.getLastChangedPassword()){

							expiresIn = bsiPasswordPolicy.getPasswordMaxAge() - DateUtils.getDeltaDays(data.getLastChangedPassword(), new Date());
						}

						if(expiresIn <= 5){
							authRes.addInfo(new PasswordExpiredAuthenticationResultInfo(user.getUsername(), expiresIn));
						}

						if(expiresIn <= 0){
							authRes.setAllowed(false);
						}
					}
				}

				/* Check if password change is enforced */
				if(data.isEnforcePasswordChange()){
					authRes.addInfo(new PasswordExpiredAuthenticationResultInfo(user.getUsername(), 0));
					authRes.setAllowed(false);
				}

			}else{
				/* process failed login */
				data.registerFailedLogin();
			}


			final long userId = authRes.getUser().getId();
			Runnable updateUserProperties = trFactory.create(new Runnable() {

				@Override
				public void run() {
					User user = (User) userManagerService.getNodeById(userId);
					bsiPasswordPolicyService.updateUserMetadata(user, data);
					userManagerService.merge(user);

				}
			});

			dwAsyncService.submit(updateUserProperties);	
		}

	}


}