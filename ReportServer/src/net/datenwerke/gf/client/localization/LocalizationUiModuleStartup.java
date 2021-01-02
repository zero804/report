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
 
 
package net.datenwerke.gf.client.localization;

import java.util.Date;

import com.google.gwt.user.client.Cookies;
import com.google.inject.Inject;

import net.datenwerke.gf.client.localization.hooker.AuthenticatorWindowExtraOptionHooker;
import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.utilityservices.UtilsUIService;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.security.client.login.hooks.AuthenticatorWindowExtraOptionHook;

public class LocalizationUiModuleStartup {

	@Inject
	public LocalizationUiModuleStartup(
			HookHandlerService hookHandlerService, 
			final WaitOnEventUIService waitOnEventService,
			final LocalizationDao localizationDao,
			AuthenticatorWindowExtraOptionHooker extraOptionHooker, 
			final LoginService loginService,
			final UtilsUIService utilsUIService
		) {
		hookHandlerService.attachHooker(AuthenticatorWindowExtraOptionHook.class, extraOptionHooker, 100);
		
		SynchronousCallbackOnEventTrigger localeCallback = new SynchronousCallbackOnEventTrigger() {
			@Override
			public void execute(final WaitOnEventTicket ticket) {
				waitOnEventService.signalProcessingDone(ticket);
				
				String locale = localizationDao.getCurrentClientLocale();
				
				if(loginService.isAuthenticated() && null == locale)
					locale = "en";
				
				if(null != locale ){
					Date exp = new Date(System.currentTimeMillis() + 157784630000L);
					Cookies.setCookie("locale", locale, exp);
					localizationDao.setUserLocale(locale, new RsAsyncCallback<Void>());
				}
				
				if(loginService.isAuthenticated())
					localizationDao.setUserTimezone(utilsUIService.guessUserTimezone(), new RsAsyncCallback<Void>());
			}
		};
		
		/* load generic rights after login */
		waitOnEventService.callbackOnEvent(LoginService.REPORTSERVER_EVENT_AFTER_ANY_LOGIN, localeCallback);
		
		/* load generic rights after login */
		waitOnEventService.callbackOnEvent(LoginService.REPORTSERVER_EVENT_BEFORE_AUTH_WINDOW_LOAD, localeCallback);
	}

}
