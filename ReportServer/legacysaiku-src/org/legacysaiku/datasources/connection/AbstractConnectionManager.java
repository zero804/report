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
package org.legacysaiku.datasources.connection;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.olap4j.OlapConnection;
import org.legacysaiku.datasources.datasource.SaikuDatasource;
import org.legacysaiku.service.datasource.IDatasourceManager;
import org.legacysaiku.service.datasource.IDatasourceProcessor;
import org.legacysaiku.service.util.exception.SaikuServiceException;

public abstract class AbstractConnectionManager implements IConnectionManager {


	private IDatasourceManager ds;

	public void setDataSourceManager(IDatasourceManager ds) {
		this.ds = ds;
	}

	public IDatasourceManager getDataSourceManager() {
		return ds;
	}

	public abstract void init();

	private SaikuDatasource preProcess(SaikuDatasource datasource) {
		if (datasource.getProperties().containsKey(ISaikuConnection.DATASOURCE_PROCESSORS)) {
			datasource = datasource.clone();
			String[] processors = datasource.getProperties().getProperty(ISaikuConnection.DATASOURCE_PROCESSORS).split(",");
			for (String processor : processors) {
				try {
				@SuppressWarnings("unchecked")
				final Class<IDatasourceProcessor> clazz =
					(Class<IDatasourceProcessor>)
					Class.forName(processor);
				final Constructor<IDatasourceProcessor> ctor =
					clazz.getConstructor();
				final IDatasourceProcessor dsProcessor = ctor.newInstance();
				datasource = dsProcessor.process(datasource);
				}
				catch (Exception e) {
					throw new SaikuServiceException("Error applying DatasourceProcessor \"" + processor + "\"", e);
				}
			}
		}
		return datasource;
	}
	
	private ISaikuConnection postProcess(SaikuDatasource datasource, ISaikuConnection con) {
		if (datasource.getProperties().containsKey(ISaikuConnection.CONNECTION_PROCESSORS)) {
			datasource = datasource.clone();
			String[] processors = datasource.getProperties().getProperty(ISaikuConnection.CONNECTION_PROCESSORS).split(",");
			for (String processor : processors) {
				try {
				@SuppressWarnings("unchecked")
				final Class<IConnectionProcessor> clazz =
					(Class<IConnectionProcessor>)
					Class.forName(processor);
				final Constructor<IConnectionProcessor> ctor =
					clazz.getConstructor();
				final IConnectionProcessor conProcessor = ctor.newInstance();
				return conProcessor.process(con);
				}
				catch (Exception e) {
					throw new SaikuServiceException("Error applying ConnectionProcessor \"" + processor + "\"", e);
				}
			}
		}
		return con;
	}

	public ISaikuConnection getConnection(String name) {
		SaikuDatasource datasource = ds.getDatasource(name);
		datasource = preProcess(datasource);
		ISaikuConnection con = getInternalConnection(name, datasource);
		con = postProcess(datasource, con);
		return con;
	}

	protected abstract ISaikuConnection getInternalConnection(String name, SaikuDatasource datasource);
	
	protected abstract ISaikuConnection refreshInternalConnection(String name, SaikuDatasource datasource);

	public void refreshAllConnections() {
		ds.load();
		for (String name : ds.getDatasources().keySet()) {
			refreshConnection(name);
		}
	}

	public void refreshConnection(String name) {
		SaikuDatasource datasource = ds.getDatasource(name);
		datasource = preProcess(datasource);
		ISaikuConnection con = refreshInternalConnection(name, datasource);
		con = postProcess(datasource, con);
	}

	public Map<String, ISaikuConnection> getAllConnections() {
		Map<String, ISaikuConnection> resultDs = new HashMap<String, ISaikuConnection>();
		for (String name : ds.getDatasources().keySet()) {
			ISaikuConnection con = getConnection(name);
			if (con != null) {
				resultDs.put(name,con);
			}
		}
		return resultDs;
	}

	public OlapConnection getOlapConnection(String name) {
		ISaikuConnection con = getConnection(name);
		if (con != null) {
			Object o = con.getConnection();
			if (o != null && o instanceof OlapConnection) {
				return (OlapConnection) o;
			}
		}
		return null;
	}

	public Map<String, OlapConnection> getAllOlapConnections() {
		Map<String, ISaikuConnection> connections = getAllConnections();
		Map<String, OlapConnection> ocons = new HashMap<String, OlapConnection>();
		for (ISaikuConnection con : connections.values()) {
			Object o = con.getConnection();
			if (o != null && o instanceof OlapConnection) {
				ocons.put(con.getName(), (OlapConnection) o);
			}
		}

		return ocons;
	}

	public boolean isDatasourceSecurity(SaikuDatasource datasource, String value) {
		if (datasource != null && value != null) {
			Properties props = datasource.getProperties();
			if (props != null && isDatasourceSecurityEnabled(datasource)) {
				if (props.containsKey(ISaikuConnection.SECURITY_TYPE_KEY)) {
					return props.getProperty(ISaikuConnection.SECURITY_TYPE_KEY).equals(value);
				}
			}
		}
		return false;
	}

	public boolean isDatasourceSecurityEnabled(SaikuDatasource datasource) {
		if (datasource != null) {
			Properties props = datasource.getProperties();
			if (props != null && props.containsKey(ISaikuConnection.SECURITY_ENABLED_KEY)) {
				String enabled = props.getProperty(ISaikuConnection.SECURITY_ENABLED_KEY, "false");
				boolean isSecurity = Boolean.parseBoolean(enabled);
				return isSecurity;
			}
		}
		return false;
	}
}
