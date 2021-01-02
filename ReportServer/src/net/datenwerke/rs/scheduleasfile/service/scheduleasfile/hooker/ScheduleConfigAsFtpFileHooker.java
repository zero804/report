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
 
 
package net.datenwerke.rs.scheduleasfile.service.scheduleasfile.hooker;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.dto.ScheduleAsFtpFileInformation;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.action.ScheduleAsFtpFileAction;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinition;
import net.datenwerke.rs.scheduler.service.scheduler.exceptions.InvalidConfigurationException;
import net.datenwerke.rs.scheduler.service.scheduler.hooks.ScheduleConfigProviderHook;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob;
import net.datenwerke.scheduler.service.scheduler.exceptions.ActionNotSupportedException;

public class ScheduleConfigAsFtpFileHooker implements ScheduleConfigProviderHook {

	private final Provider<ScheduleAsFtpFileAction> actionProvider;
	
	@Inject
	public ScheduleConfigAsFtpFileHooker(
		DtoService dtoService,
		Provider<ScheduleAsFtpFileAction> actionProvider
		) {
		
		/* store objects */
		this.actionProvider = actionProvider;
	}



	@Override
	public void adaptJob(ReportExecuteJob job,
			ReportScheduleDefinition scheduleDTO)
			throws InvalidConfigurationException {
		ScheduleAsFtpFileInformation info = scheduleDTO.getAdditionalInfo(ScheduleAsFtpFileInformation.class);
		if(null == info)
			return;
		if(null == info.getName() || info.getName().trim().isEmpty())
			throw new InvalidConfigurationException("No name specified");
		
		if(null == info.getFolder() || info.getFolder().trim().isEmpty())
			throw new InvalidConfigurationException("No folder specified");
		
		ScheduleAsFtpFileAction action = actionProvider.get();
		
		action.setName(info.getName());
		action.setFolder(info.getFolder());
		
		try {
			job.addAction(action);
		} catch (ActionNotSupportedException e) {
			throw new InvalidConfigurationException(e);
		}
	}



	@Override
	public void adaptScheduleDefinition(ReportScheduleDefinition rsd,
			ReportExecuteJob job) throws ExpectedException {
		ScheduleAsFtpFileAction action = job.getAction(ScheduleAsFtpFileAction.class);
		if(null == action)
			return;
		
		ScheduleAsFtpFileInformation info = new ScheduleAsFtpFileInformation();
		
		info.setName(action.getName());
		info.setFolder(action.getFolder());
		
		if(null == info.getName() || info.getName().trim().isEmpty())
			throw new IllegalArgumentException("No name specified");
		
		if(null == info.getFolder() || info.getFolder().trim().isEmpty())
			throw new IllegalArgumentException("No folder specified");
		
		rsd.addAdditionalInfo(info);
	}

}
