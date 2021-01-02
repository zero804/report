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

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.rs.passwordpolicy.service.BsiPasswordPolicy;
import net.datenwerke.rs.passwordpolicy.service.BsiPasswordPolicyService;
import net.datenwerke.rs.passwordpolicy.service.BsiPasswordPolicyUserMetadata;
import net.datenwerke.rs.utils.crypto.PasswordHasher;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.rs.utils.misc.DateUtils;
import net.datenwerke.security.service.security.locale.SecurityMessages;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.security.service.usermanager.hooks.ChangePasswordHook;

import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;

public class BsiPasswordPolicyChangePasswordHook implements ChangePasswordHook{

	private final static SecurityMessages messages = LocalizationServiceImpl.getMessages(SecurityMessages.class);
	
	private final PasswordHasher passwordHasher;
	
	private final BsiPasswordPolicyService bsiPasswordPolicyService;
	
	@Inject
	public BsiPasswordPolicyChangePasswordHook(
			PasswordHasher passwordHasher,
			BsiPasswordPolicyService bsiPasswordPolicyService) {
		this.passwordHasher = passwordHasher;
		this.bsiPasswordPolicyService = bsiPasswordPolicyService;
	}
	
	@Override
	public void afterPasswordChanged(User user) {
		if(!bsiPasswordPolicyService.isActive())
			return;
		
		BsiPasswordPolicy policy = bsiPasswordPolicyService.getPolicy();
		
		BsiPasswordPolicyUserMetadata data = bsiPasswordPolicyService.getUserMetadata(user);
		
		data.addRecentPassword(user.getPassword(), policy.getHistorySize());
		data.setLastChangedPassword(new Date());
	
		bsiPasswordPolicyService.updateUserMetadata(user, data);
	}
	
	@Override
	public void beforePasswordChanged(User user, String newPassword) throws ExpectedException {
		if(!bsiPasswordPolicyService.isActive())
			return;
		
		BsiPasswordPolicy policy = bsiPasswordPolicyService.getPolicy();
		BsiPasswordPolicyUserMetadata data = bsiPasswordPolicyService.getUserMetadata(user);
		
		/* check minimum password age */
		if(null != data.getLastChangedPassword()){
			int passwordAge = DateUtils.getDeltaDays(data.getLastChangedPassword(), new Date());
			if(passwordAge < policy.getPasswordMinAge()){
				throw new ExpectedException(messages.changePasswordOnceInDays(policy.getPasswordMinAge()));
			}
		}
		
		/* check password history */
		if(data.recentPasswordsContain(newPassword, policy.getHistorySize(), passwordHasher)){
			throw new ExpectedException(messages.changePasswordHistoryFail(policy.getHistorySize()));
		}
		
		/* check password complexity */
		if(!policy.getPasswordComplexitySpecification().isSatisfiedBy(newPassword)){
			throw new ExpectedException(messages.changePasswordComplexityFail(StringUtils.join(policy.getPasswordComplexitySpecification().getErrorCause(newPassword), "\r\n")));
		}

		
	};
}
