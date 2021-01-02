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
 
 
package net.datenwerke.gf.client.theme;

import net.datenwerke.gf.client.theme.dto.ThemeUiConfig;
import net.datenwerke.gf.client.theme.rpc.ThemeRpcServiceAsync;
import net.datenwerke.gxtdto.client.dtomanager.Dao;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class ThemeDao extends Dao {
	
	private final ThemeRpcServiceAsync rpcService;

	@Inject
	public ThemeDao(ThemeRpcServiceAsync rpcService) {
		this.rpcService = rpcService;
	}
	
	public void loadUiTheme(AsyncCallback<ThemeUiConfig> callback){
		rpcService.loadUiConfig(callback);
	}
	
	

}
