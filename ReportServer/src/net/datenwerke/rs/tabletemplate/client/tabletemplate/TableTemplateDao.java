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
 
 
package net.datenwerke.rs.tabletemplate.client.tabletemplate;

import java.util.Collection;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.tabletemplate.client.tabletemplate.dto.TableReportTemplateDto;
import net.datenwerke.rs.tabletemplate.client.tabletemplate.rpc.TableTemplateRpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class TableTemplateDao extends Dao {

	private final TableTemplateRpcServiceAsync rpcService;

	@Inject
	public TableTemplateDao(TableTemplateRpcServiceAsync rpcService) {
		this.rpcService = rpcService;
	}
	
	public void removeTemplates(ReportDto report, String executeToken, Collection<TableReportTemplateDto> templates, AsyncCallback<Void> callback){
		rpcService.removeTemplates(report, executeToken, templates, transformAndKeepCallback(callback));
	}
	
	public void loadTemplates(ReportDto report, String executeToken,
			AsyncCallback<List<TableReportTemplateDto>> callback){
		rpcService.loadTemplates(report, executeToken, transformAndKeepCallback(callback));
	}
	
}
