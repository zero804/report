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
 
 
package net.datenwerke.gf.server.localization;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.inject.Provider;

import net.datenwerke.gf.client.localization.rpc.LocalizationRpcService;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.rs.utils.localization.annotations.DefaultLocale;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.annotation.SecurityChecked;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class LocalizationRpcServiceImpl extends SecuredRemoteServiceServlet implements LocalizationRpcService {
	
	private static final long serialVersionUID = -7452594860448525628L;
	private Provider<String> defaultLocale;

	@Inject
	public LocalizationRpcServiceImpl(@DefaultLocale Provider<String> defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	@Override
	@SecurityChecked(loginRequired=false)
	public Map<String, String> getLanguageSelectorConfiguration() throws ServerCallFailedException {
		Collection<String> availableLocales = LocalizationServiceImpl.getAvailableLocales();
		
		String defaultKey = defaultLocale.get();
		String defaultLbl = Locale.forLanguageTag(defaultKey).getDisplayName(Locale.forLanguageTag(defaultKey));
		if(null != defaultLbl && ! defaultLbl.isEmpty()){
			defaultLbl = Character.toUpperCase(defaultLbl.charAt(0)) + defaultLbl.substring(1); 
		}
		
		Map<String, String> tmpMap = new TreeMap<>();
		for(String lCode : availableLocales){
			String lName = Locale.forLanguageTag(lCode).getDisplayName(Locale.forLanguageTag(lCode));
			if(null != lName && !lName.isEmpty()){
				lName = Character.toUpperCase(lName.charAt(0)) + lName.substring(1); 
				tmpMap.put(lName, lCode);
			}
		}

		Map<String, String> res = new LinkedHashMap<>();
		res.put(defaultKey, defaultLbl);
		for(Map.Entry<String, String> e : tmpMap.entrySet()){
			res.put(e.getValue(), e.getKey());
		}
	
		return res;
	}

	@Override
	@SecurityChecked(loginRequired=false)
	@Transactional(rollbackOn=Exception.class)
	public void setUserLocale(String locale) throws ServerCallFailedException {
		LocalizationServiceImpl.setUserLocal(Locale.forLanguageTag(locale));
	}

	@Override
	@SecurityChecked(loginRequired=false)
	public void setUserTimezone(String timezone) {
		LocalizationServiceImpl.setUserTimezone(timezone);
	}
	
}
