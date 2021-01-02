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
 
 
package net.datenwerke.rs.base.client.reportengines.jasper;

import java.util.List;

import net.datenwerke.gf.client.upload.dto.FileToUpload;
import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.base.client.reportengines.jasper.dto.JasperReportDto;
import net.datenwerke.rs.base.client.reportengines.jasper.dto.JasperReportJRXMLFileDto;
import net.datenwerke.rs.base.client.reportengines.jasper.rpc.JasperReportRpcServiceAsync;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class JasperReportDao extends Dao {


	private final JasperReportRpcServiceAsync rpcService;
	
	@Inject
	public JasperReportDao(JasperReportRpcServiceAsync treeManager){
		this.rpcService = treeManager;
	}
	
	public void updateJRXMLFile(JasperReportJRXMLFileDto file,
			AsyncCallback<AbstractNodeDto> callback){
		rpcService.updateJRXMLFile(file, transformDtoCallback(callback));
	}

	public void removeJRXMLFile(JasperReportJRXMLFileDto file,
			AsyncCallback<AbstractNodeDto> callback){
		rpcService.removeJRXMLFile(file, transformDtoCallback(callback));
	}

	public void removeAllSubReports(JasperReportDto report,
			AsyncCallback<AbstractNodeDto> callback) {
		rpcService.removeAllSubReports(report, transformDtoCallback(callback));
	}
	
	public void uploadJRXMLFiles(JasperReportDto report, List<FileToUpload> files, AsyncCallback<JasperReportDto> callback){
		rpcService.uploadJRXMLFiles(report, files, transformDtoCallback(callback));
	}
}
