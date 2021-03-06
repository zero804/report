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
 
 
package net.datenwerke.rs.scheduleasfile.client.scheduleasfile.rpc;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.StorageType;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;


public interface ScheduleAsFileRpcServiceAsync  {

	void exportIntoTeamSpace(ReportDto reportDto, String executorToke,
			String format, List<ReportExecutionConfigDto> configs,
			AbstractTsDiskNodeDto folder, String name, String description,
			AsyncCallback<Void> callback);
	
	void exportIntoFtp(ReportDto reportDto, String executorToken,
			String format, List<ReportExecutionConfigDto> configs, String name, String folder, AsyncCallback<Void> callback);
			
	void getFtpDefaultFolder(AsyncCallback<String> callback);

	void getStorageEnabledConfigs(AsyncCallback<Map<StorageType, Boolean>> transformAndKeepCallback);
}
