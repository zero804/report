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
 
 
package net.datenwerke.gf.client.config;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import net.datenwerke.gf.client.config.rpc.ClientConfigRpcServiceAsync;
import net.datenwerke.gxtdto.client.dtomanager.Dao;

public class ClientConfigDao extends Dao {
	
	private final ClientConfigRpcServiceAsync rpcService;

	@Inject
	public ClientConfigDao(ClientConfigRpcServiceAsync rpcService) {
		this.rpcService = rpcService;
	}
	
	public void getClientConfig(String configfile, AsyncCallback<String> callback){
		rpcService.getConfigFile(configfile, callback);
	}
	
	public void getConfigProperties(String identifier, AsyncCallback<HashMap<String,String>> callback){
		rpcService.getConfigProperties(identifier, callback);
	}
}
