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
 
 
package net.datenwerke.rs.dsbundle.client.dsbundle;

import java.util.List;
import java.util.Map;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.dsbundle.client.dsbundle.rpc.DatasourceBundleRpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class DatasourceBundleDao extends Dao {

	private final DatasourceBundleRpcServiceAsync rpcService;

	@Inject
	public DatasourceBundleDao(DatasourceBundleRpcServiceAsync rpcService) {
		this.rpcService = rpcService;
	}
	
	public void getAvailableBundleKeys(AsyncCallback<List<String>> callback){
		rpcService.getAvailableBundleKeys(transformAndKeepCallback(callback));
	}
	
	public void getBundleSelectorConfiguration(AsyncCallback<Map<String, String>> callback){
		rpcService.getBundleSelectorConfiguration(callback);
	}
	
	public void setSelectedBundle(String bundleKey, AsyncCallback<Void> callback){
		rpcService.setSelectedBundle(bundleKey, callback);
	}
}
