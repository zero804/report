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
 
 
package net.datenwerke.rs.scheduleasfile.service.scheduleasfile;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.hooker.ScheduleAsFileEmailNotificationHooker;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.hooker.ScheduleAsFtpFileEmailNotificationHooker;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.hooker.ScheduleConfigAsFileHooker;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.hooker.ScheduleConfigAsFtpFileHooker;
import net.datenwerke.rs.scheduler.service.scheduler.hooks.ScheduleConfigProviderHook;
import net.datenwerke.scheduler.service.scheduler.hooks.SchedulerExecutionHook;

public class ScheduleAsFileStartup {

	@Inject
	public ScheduleAsFileStartup(
		HookHandlerService hookHandler,
		
		Provider<ScheduleConfigAsFileHooker> scheduleAsFileConfigHooker, 
		Provider<ScheduleConfigAsFtpFileHooker> scheduleAsFtpFileConfigHooker, 
		Provider<ScheduleAsFileEmailNotificationHooker> emailNotificationHooker,
		Provider<ScheduleAsFtpFileEmailNotificationHooker> emailFtpNotificationHooker
		
	){
		
		hookHandler.attachHooker(ScheduleConfigProviderHook.class, scheduleAsFileConfigHooker);
		hookHandler.attachHooker(ScheduleConfigProviderHook.class, scheduleAsFtpFileConfigHooker);
		
		hookHandler.attachHooker(SchedulerExecutionHook.class, emailNotificationHooker);
		hookHandler.attachHooker(SchedulerExecutionHook.class, emailFtpNotificationHooker);
	}
}
