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
 
 
package net.datenwerke.rs.core.client.reportproperties;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportStringPropertyDto;
import net.datenwerke.rs.core.client.reportproperties.rpc.ReportPropertiesRpcServiceAsync;

public class ReportPropertiesDao extends Dao{

	private final ReportPropertiesRpcServiceAsync rpcService;
	
	@Inject
	public ReportPropertiesDao(
			ReportPropertiesRpcServiceAsync rpcService	
		){
	
		/* store objects */
		this.rpcService = rpcService;
	}
	
	public void getPropertyKeys(ReportDto reportDto, AsyncCallback<List<String>> callback){
		rpcService.getPropertyKeys(reportDto, transformAndKeepCallback(callback));
	}

	public void getSupportedPropertyKeys(ReportDto reportDto, AsyncCallback<List<String>> callback){
		rpcService.getSupportedPropertyKeys(reportDto, transformAndKeepCallback(callback));
	}

	public void updateProperties(ReportDto report, List<ReportStringPropertyDto> addedProperties,
			List<ReportStringPropertyDto> modifiedProperties, List<ReportStringPropertyDto> removedProperties,
			AsyncCallback<ReportDto> callback) {
		rpcService.updateProperties(report, addedProperties, modifiedProperties, removedProperties, transformAndKeepCallback(callback));
	}
}
