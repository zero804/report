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
 
 
package net.datenwerke.rs.core.client.reportexporter.rpc;

import java.util.List;

import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;

import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownUser;

public interface ReportExporterRpcServiceAsync {

	Request storeInSessionForExport(ReportDto reportDto, String executorToken,
			String format, List<ReportExecutionConfigDto> configs,
			AsyncCallback<Void> callback);

	void exportViaMail(ReportDto reportDto, String executorToke, String format,
			List<ReportExecutionConfigDto> configs, String subject,
			String message, List<StrippedDownUser> recipients,
			AsyncCallback<Void> callback);
	
	void getExportDefaultSettingsAsJSON(String identifier, AsyncCallback<String> callback);
	
	void getExportDefaultCharset(AsyncCallback<String> callback);

	Request exportSkipDownload(ReportDto reportDto, String executorToken, String format, AsyncCallback<Void> callback);
}
