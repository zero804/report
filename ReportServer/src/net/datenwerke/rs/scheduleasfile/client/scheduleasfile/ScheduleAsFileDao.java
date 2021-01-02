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
 
 
package net.datenwerke.rs.scheduleasfile.client.scheduleasfile;

import java.util.List;
import java.util.Map;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.rpc.ScheduleAsFileRpcServiceAsync;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class ScheduleAsFileDao extends Dao {

	private final ScheduleAsFileRpcServiceAsync rpcService;
	
	@Inject
	public ScheduleAsFileDao(ScheduleAsFileRpcServiceAsync rpcService) {
		this.rpcService = rpcService;
	}

	public void exportIntoTeamSpace(ReportDto reportDto, String executorToken,
			String format, List<ReportExecutionConfigDto> configs, AbstractTsDiskNodeDto folder, String name,
			String description, AsyncCallback<Void> callback){
		rpcService.exportIntoTeamSpace(reportDto, executorToken, format, configs, folder, name, description, transformAndKeepCallback(callback));
	}
	
	public void exportIntoFtp(ReportDto reportDto, String executorToken,
			String format, List<ReportExecutionConfigDto> configs, String name,
			String folder, AsyncCallback<Void> callback
			) {
		rpcService.exportIntoFtp(reportDto, executorToken, format, configs, name, folder, transformAndKeepCallback(callback));
	}
	
	public void getFtpDefaultFolder(AsyncCallback<String> callback) {
		rpcService.getFtpDefaultFolder(transformAndKeepCallback(callback));
	}
	
	public void getStorageEnabledConfigs(AsyncCallback<Map<StorageType,Boolean>> callback) {
		rpcService.getStorageEnabledConfigs(transformAndKeepCallback(callback));
	}
}
