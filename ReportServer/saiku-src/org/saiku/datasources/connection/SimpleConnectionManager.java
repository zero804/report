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
 
 
package org.saiku.datasources.connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.saiku.datasources.datasource.SaikuDatasource;
import org.saiku.olap.util.exception.SaikuOlapException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleConnectionManager extends AbstractConnectionManager {
    private Map<String, ISaikuConnection> connections = new HashMap<>();
    private final List<String> errorConnections = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(SimpleConnectionManager.class);
 
    
    @Override
    public void init() throws SaikuOlapException {
    	this.connections = getAllConnections();
    }

    @Override
    protected ISaikuConnection getInternalConnection(String name, SaikuDatasource datasource)
      throws SaikuOlapException {

        ISaikuConnection con;

        if (!connections.containsKey(name)) {
            con =  connect(name, datasource);
            if (con != null) {
                connections.put(name, con);
            } else {
                if (!errorConnections.contains(name)) {
                    errorConnections.add(name);
                }
            }

        } else {
            con = connections.get(name);
        }
        return con;
    }

    @Override
    protected ISaikuConnection refreshInternalConnection(String name, SaikuDatasource datasource) {
		try {
            ISaikuConnection con = connections.remove(name);
			if (con != null) {
				con.clearCache();
			}
            return getInternalConnection(name, datasource);
		}
		catch (Exception e) {
			log.error("Could not get internal connection", e);
		}
		return null;
    }

    private ISaikuConnection connect(String name, SaikuDatasource datasource) throws SaikuOlapException {
      if ( datasource != null ) {


        try {
          ISaikuConnection con = SaikuConnectionFactory.getConnection( datasource );
          if ( con.initialized() ) {
            return con;
          }
        } catch ( Exception e ) {
          log.error("Could not get connection", e);
        }

        return null;
      }

    throw new SaikuOlapException(  "Cannot find connection: (" + name + ")"  );
  }
}
