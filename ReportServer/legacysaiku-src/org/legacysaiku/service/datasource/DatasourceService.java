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
 
 
/*  
 *   Copyright 2012 OSBI Ltd
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.legacysaiku.service.datasource;

import java.io.Serializable;
import java.util.Map;

import org.legacysaiku.datasources.connection.IConnectionManager;
import org.legacysaiku.datasources.datasource.SaikuDatasource;

public class DatasourceService implements Serializable {

	/**
	 * Unique serialization UID 
	 */
	private static final long serialVersionUID = -4407446633148181669L;

	private IDatasourceManager datasources;
	
	private IConnectionManager connectionManager;
	
	public void setConnectionManager(IConnectionManager ic) {
		connectionManager = ic;
		datasources = ic.getDataSourceManager();
	}

	public IConnectionManager getConnectionManager() {
		return connectionManager;
	}
	
	public void addDatasource(SaikuDatasource datasource) {
		datasources.addDatasource(datasource);
	}
	
	public void setDatasource(SaikuDatasource datasource) {
		datasources.setDatasource(datasource);
	}
	
	public void removeDatasource(String datasourceName) {
		datasources.removeDatasource(datasourceName);
	}
	
	public SaikuDatasource getDatasource(String datasourceName) {
		return datasources.getDatasource(datasourceName);
	}
	
	public Map<String,SaikuDatasource> getDatasources() {
		return datasources.getDatasources();
	}

}
