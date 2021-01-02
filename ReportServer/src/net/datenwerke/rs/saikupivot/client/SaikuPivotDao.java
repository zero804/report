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
 
 
package net.datenwerke.rs.saikupivot.client;

import javax.inject.Inject;

import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;

public class SaikuPivotDao extends Dao {
	
	private final SaikuPivotRpcServiceAsync rpcService;
	
	@Inject
	public SaikuPivotDao(SaikuPivotRpcServiceAsync rpcService) {
		this.rpcService = rpcService;
	}
	
	public Request stashReport(String token, TableReportDto report, AsyncCallback<Void> callback){
		return rpcService.stashReport(token, report,  transformAndKeepCallback(callback));
	}

	public void cubeExport(String token, TableReportDto report, AsyncCallback<String> callback) {
		rpcService.cubeExport(token, report, transformAndKeepCallback(callback));
	}
	
	public void cubeExportMondrian3(String token, TableReportDto report, AsyncCallback<String> callback) {
		rpcService.cubeExportMondrian3(token, report, transformAndKeepCallback(callback));
	}
	
}
