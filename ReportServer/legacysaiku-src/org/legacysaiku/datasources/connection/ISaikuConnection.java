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

import java.util.Properties;

/**
 * @author pmac
 *
 */
public interface ISaikuConnection {
	
    public static final String OLAP_DATASOURCE = "OLAP"; //$NON-NLS-1$
    public static final String NAME_KEY = "name"; //$NON-NLS-1$
    public static final String DRIVER_KEY = "driver"; //$NON-NLS-1$
    public static final String URL_KEY = "location"; //$NON-NLS-1$
    public static final String USERNAME_KEY = "username"; //$NON-NLS-1$
    public static final String PASSWORD_KEY = "password"; //$NON-NLS-1$
    public static final String SECURITY_ENABLED_KEY = "security.enabled"; //$NON-NLS-1$
    public static final String SECURITY_TYPE_KEY = "security.type"; //$NON-NLS-1$
    public static final String SECURITY_TYPE_SPRING2MONDRIAN_VALUE = "one2one"; //$NON-NLS-1$
    public static final String SECURITY_TYPE_SPRINGLOOKUPMONDRIAN_VALUE = "lookup"; //$NON-NLS-1$
    public static final String SECURITY_TYPE_PASSTHROUGH_VALUE = "passthrough"; //$NON-NLS-1$
    public static final String SECURITY_LOOKUP_KEY = "security.mapping"; //$NON-NLS-1$
    public static final String DATASOURCE_PROCESSORS = "datasource.processors"; //$NON-NLS-1$
    public static final String CONNECTION_PROCESSORS = "connection.processors"; //$NON-NLS-1$
    

    public static final String[] KEYS = new String[] { NAME_KEY, DRIVER_KEY, URL_KEY,
	      USERNAME_KEY, PASSWORD_KEY, SECURITY_ENABLED_KEY, SECURITY_TYPE_KEY, SECURITY_TYPE_PASSTHROUGH_VALUE,
	      SECURITY_TYPE_SPRING2MONDRIAN_VALUE, SECURITY_TYPE_SPRINGLOOKUPMONDRIAN_VALUE, DATASOURCE_PROCESSORS,
	      CONNECTION_PROCESSORS };

    public static final String[] DATASOURCES = new String[] { OLAP_DATASOURCE };

	  /**
	   * Sets the properties to be used when the connection is made. The standard 
	   * keys for the properties are defined in this interface
	   * @param props
	   */
	  void setProperties( Properties props );
	  

	  /**
	   * @return the last resultset from the last query executed
	   *
	  IUnifiedResultSet getResultSet();
	  
	  /**
	   * Connects to the data source using the supplied properties.
	   * @param props Datasource connection properties
	   * @return true if the connection was successful
	   */
	  boolean connect(Properties props) throws Exception;
	  
	  boolean connect() throws Exception;
	  
	  boolean clearCache() throws Exception;

	  
	  /**
	   * @return true if the connection has been properly initialized.
	   */
	  public boolean initialized();

	  /**
	   * returns the type of connection
	   * @return
	   */
	  public String getDatasourceType();
	  
	  public Object getConnection();
	  	  
	  public String getName();

}
