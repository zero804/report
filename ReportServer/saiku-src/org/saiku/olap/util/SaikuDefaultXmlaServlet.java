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
 * Copyright 2014 OSBI Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.saiku.olap.util;

import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.olap4j.OlapConnection;
import org.saiku.datasources.connection.IConnectionManager;
import org.saiku.olap.util.exception.SaikuOlapException;

import mondrian.xmla.XmlaHandler;
import mondrian.xmla.XmlaRequest;
import mondrian.xmla.impl.DefaultXmlaServlet;

/**
 * Created by bugg on 30/03/15.
 */
public class SaikuDefaultXmlaServlet extends DefaultXmlaServlet{

  private static IConnectionManager connections;

  public SaikuDefaultXmlaServlet() {
    super();


  }
  @Override
  public void init(ServletConfig config) throws ServletException{
    super.init(config);

    ServletContext context = getServletContext();

//    WebApplicationContext applicationContext =
//        WebApplicationContextUtils
//            .getWebApplicationContext(context);
//    connections = (IConnectionManager) applicationContext.getBean("connectionManager");
    
    throw new RuntimeException("not implemented");
    //TODO:SAIKU
  }
  @Override
  protected XmlaHandler.ConnectionFactory createConnectionFactory(ServletConfig servletConfig) throws ServletException {
    return new XmlaHandler.ConnectionFactory() {
      private final XmlaHandler.XmlaExtra extra =
          new XmlaHandler.XmlaExtraImpl();
      public OlapConnection getConnection(String s, String s1, String s2, Properties properties) throws SQLException {
        try {
          connections.refreshAllConnections();
          return connections.getOlapConnection(System.getProperty("xmla_datasource"));
        } catch (SaikuOlapException e) {
          e.printStackTrace();
        }
        return null;
      }

      public Map<String, Object> getPreConfiguredDiscoverDatasourcesResponse() {

        return null; //getDiscoverDatasourcesPreConfiguredResponse(servletConfig);
      }

      public XmlaHandler.Request startRequest(XmlaRequest xmlaRequest, OlapConnection olapConnection) {
        return null;
      }

      public void endRequest(XmlaHandler.Request request) {

      }

      public XmlaHandler.XmlaExtra getExtra() {
        return extra;
      }
    };
  }
}
