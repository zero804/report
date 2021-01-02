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
 
 
package net.datenwerke.rs.computedcolumns.client.computedcolumns;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.computedcolumns.client.computedcolumns.dto.ComputedColumnDto;
import net.datenwerke.rs.computedcolumns.client.computedcolumns.rpc.ComputedColumnsRpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class ComputedColumnsDao extends Dao {

	private final ComputedColumnsRpcServiceAsync rpcService;

	@Inject
	public ComputedColumnsDao(ComputedColumnsRpcServiceAsync rpcService) {
		super();
		this.rpcService = rpcService;
	}

	public void getColumnType(TableReportDto report, ComputedColumnDto oldColumn, ComputedColumnDto newColumn,
			AsyncCallback<Integer> callback){
		rpcService.getColumnType(report, oldColumn, newColumn, transformAndKeepCallback(callback));
	}
	
	public void getColumnType(TableReportDto report, ComputedColumnDto column,
			AsyncCallback<Integer> callback){
		rpcService.getColumnType(report, column, transformAndKeepCallback(callback));
	}
	
}
