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
 
 
package net.datenwerke.rs.authenticator.cr.service.pam;

import javax.persistence.NoResultException;

import net.datenwerke.rs.authenticator.client.login.dto.ChallengeResponseAuthToken;
import net.datenwerke.rs.authenticator.client.login.dto.ChallengeResponseContainer;
import net.datenwerke.rs.authenticator.cr.client.pam.ChallengeResponseClientPam;
import net.datenwerke.rs.authenticator.cr.service.ChallengeResponseService;
import net.datenwerke.security.client.login.AuthToken;
import net.datenwerke.security.service.authenticator.AuthenticationResult;
import net.datenwerke.security.service.authenticator.ReportServerPAM;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;

public class ChallengeResponsePAM implements ReportServerPAM {

	private static final String CLIENT_MODULE_NAME = ChallengeResponseClientPam.class.getName();
	private final UserManagerService userManagerService;
	private final ChallengeResponseService challengeResponseService;
	
	@Inject
	public ChallengeResponsePAM(
			UserManagerService userManagerService, 
			ChallengeResponseService challengeResponseService
	) {
		this.userManagerService = userManagerService;
		this.challengeResponseService = challengeResponseService;
	}

	@Override
	
	
	public AuthenticationResult authenticate(AuthToken[] tokens) {
		for(Object token : tokens){
			if(token instanceof ChallengeResponseAuthToken){
				ChallengeResponseAuthToken crToken = (ChallengeResponseAuthToken) token;
				User u = authenticate(crToken.getUsername(), crToken.getChallengeResponse());
				if(null != u){
					return new AuthenticationResult(true, u);
				}else{
					User usr = getUserOrNull(crToken.getUsername());
					AuthenticationResult result = new AuthenticationResult(false, usr);
					return result;
				}
			}
		}

		return new AuthenticationResult(false, null);
	}

	private User authenticate(String username, ChallengeResponseContainer container) {
		User user = getUserOrNull(username);

		if(null == user)
			return null;

		boolean res = challengeResponseService.validateResponse(container, user.getPassword());

		if(res){
			return user;
		}else
			return null;
	}

	private User getUserOrNull(String username){
		try{
			return userManagerService.getUserByName(username);
		}catch(NoResultException ex){
			return null;
		}	
	}
	
	@Override
	public String getClientModuleName() {
		return CLIENT_MODULE_NAME;
	}
}
