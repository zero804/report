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
 
 
package net.datenwerke.rs.passwordpolicy.service;

import groovy.lang.Singleton;
import net.datenwerke.rs.configservice.service.configservice.hooks.ReloadConfigNotificationHook;
import net.datenwerke.rs.utils.config.ConfigFileNotFoundException;
import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.security.service.eventlogger.events.InvalidConfigEvent;
import net.datenwerke.security.service.usermanager.UserPropertiesService;
import net.datenwerke.security.service.usermanager.entities.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

@Singleton
public class BsiPasswordPolicyServiceImpl implements BsiPasswordPolicyService, ReloadConfigNotificationHook {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private final UserPropertiesService userPropertiesService;
	private final Provider<BsiPasswordPolicy> policyProvider;
	private final EventBus eventBus;
	
	private BsiPasswordPolicy bsiPasswordPolicy;

	@Inject
	public BsiPasswordPolicyServiceImpl(
		UserPropertiesService userPropertiesService,
		Provider<BsiPasswordPolicy> policyProvider,
		EventBus eventBus) {
		
		this.userPropertiesService = userPropertiesService;
		this.policyProvider = policyProvider;
		this.eventBus = eventBus;
	}
	
	@Override
	public BsiPasswordPolicyUserMetadata getUserMetadata(User user) {
		BsiPasswordPolicyUserMetadata bsiPasswordPolicyUserMetadata = new BsiPasswordPolicyUserMetadata();
		bsiPasswordPolicyUserMetadata.loadfromUser(user, userPropertiesService);
		
		return bsiPasswordPolicyUserMetadata;
	}

	@Override
	public void updateUserMetadata(User user, BsiPasswordPolicyUserMetadata userMetadata) {
		userMetadata.updateUser(user, userPropertiesService);
	}

	@Override
	public boolean isActive() {
		BsiPasswordPolicy bsiPasswordPolicy = getPolicy();
		return null != bsiPasswordPolicy && bsiPasswordPolicy.isValid();
	}

	@Override
	public BsiPasswordPolicy getPolicy() {
		if(null == bsiPasswordPolicy){
			bsiPasswordPolicy = policyProvider.get();
			try{
				bsiPasswordPolicy.loadConfig();
				logger.info("password policy loaded. policy is: " + (bsiPasswordPolicy.isValid() ? " valid" : "invalid"));
			} catch(Exception e){
				if(e instanceof ConfigFileNotFoundException)
					logger.info("Password policy not active: " + e.getMessage());
				else
					logger.warn("Could not load password policy: ", e);
				eventBus.fireEvent(new InvalidConfigEvent("password policy", e.getMessage()));
			}
		}
		
		return bsiPasswordPolicy;
	}

	@Override
	public void reloadConfig() {
		bsiPasswordPolicy = null;
	}

	@Override
	public void reloadConfig(String identifier) {
		if(PasswordPolicyModule.CONFIG_FILE.equals(identifier))
			reloadConfig();
	}

	
	

}
