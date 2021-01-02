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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.olap4j.OlapConnection;
import org.olap4j.OlapException;
import org.olap4j.impl.Olap4jUtil;
import org.olap4j.metadata.Database;
import org.saiku.datasources.connection.IConnectionManager;
import org.saiku.olap.util.exception.SaikuOlapException;

import mondrian.xmla.XmlaHandler;
import mondrian.xmla.XmlaHandler.ConnectionFactory;
import mondrian.xmla.XmlaHandler.Request;
import mondrian.xmla.XmlaHandler.XmlaExtra;
import mondrian.xmla.XmlaRequest;
import mondrian.xmla.impl.Olap4jXmlaServlet;

/**
 * Created by bugg on 30/03/15.
 */
public class SaikuXmlaServlet extends Olap4jXmlaServlet {

  private static IConnectionManager connections;


  public SaikuXmlaServlet() {
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
  protected ConnectionFactory createConnectionFactory(final ServletConfig servletConfig) throws ServletException {
    return new ConnectionFactory() {
      private final XmlaHandler.XmlaExtra extra =
          new SaikuXmlaExtraImpl();
      public OlapConnection getConnection(String s, String s1, String s2, Properties properties) throws SQLException {
        try {
          //connections.refreshAllConnections();
          if(s!=null) {
            for(Map.Entry<String, OlapConnection> entry : connections.getAllOlapConnections().entrySet()) {
              if(entry.getKey().toLowerCase().equals(s.toLowerCase())) {
                return entry.getValue();
              }
            }
            return connections.getOlapConnection(s);
          }
          else{
            for(Map.Entry<String, OlapConnection> entry : connections.getAllOlapConnections().entrySet()) {
              return entry.getValue();
            }
          }


        } catch (SaikuOlapException e) {
          e.printStackTrace();
        }
        return null;
      }

      public Map<String, Object> getPreConfiguredDiscoverDatasourcesResponse() {

      return null;//getDataSources();
      }

      public Request startRequest(XmlaRequest xmlaRequest, OlapConnection olapConnection) {
        return null;
      }

      public void endRequest(Request request) {

      }

      public XmlaExtra getExtra() {
        return extra;
      }
    };
  }

  private Map<String, Object> getDataSources() {
    List<Map<String,Object>> lret = new ArrayList<>();
    try {
      Map<String, OlapConnection> conns = connections.getAllOlapConnections();
      for (Map.Entry<String, OlapConnection> c : conns.entrySet()) {
        Database olapDb = c.getValue().getOlapDatabase();
        final String modes = createCsv(olapDb.getAuthenticationModes());
        final String providerTypes = createCsv(olapDb.getProviderTypes());
        List<Map<String, Object>> l = Collections.singletonList(
            Olap4jUtil.mapOf(
                "DataSourceName", (Object) c.getKey(),
                "DataSourceDescription", olapDb.getDescription(),
                "URL", olapDb.getURL(),
                "DataSourceInfo", olapDb.getDataSourceInfo(),
                "ProviderName", olapDb.getProviderName(),
                "ProviderType", providerTypes,
                "AuthenticationMode", modes));
        return l.get(0);
        //lret.addAll(l);
      }

    }
    catch (Exception e){

    }
    return null;
      //return lret;
  }

  private static class SaikuXmlaExtraImpl extends XmlaHandler.XmlaExtraImpl {

    @Override
      public List<Map<String, Object>> getDataSources(
          OlapConnection connection) throws OlapException
      {
        List<Map<String,Object>> lret = new ArrayList<>();
        try {
          Map<String, OlapConnection> conns = connections.getAllOlapConnections();
          for(Map.Entry<String, OlapConnection> c : conns.entrySet()){
            Database olapDb = c.getValue().getOlapDatabase();
            final String modes = createCsv(olapDb.getAuthenticationModes());
            final String providerTypes = createCsv(olapDb.getProviderTypes());
            List<Map<String, Object>> l = Collections.singletonList(
                Olap4jUtil.mapOf(
                    "DataSourceName", (Object) c.getKey(),
                    "DataSourceDescription", olapDb.getDescription(),
                    "URL", olapDb.getURL(),
                    "DataSourceInfo", olapDb.getDataSourceInfo(),
                    "ProviderName", olapDb.getProviderName(),
                    "ProviderType", providerTypes,
                    "AuthenticationMode", modes));
            lret.addAll(l);
          }


          return lret;
        } catch (SaikuOlapException e) {
          e.printStackTrace();
        }

return null;
      }
    }
  private static String createCsv(Iterable<? extends Object> iterable) {
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (Object o : iterable) {
      if (!first) {
        sb.append(',');
      }
      sb.append(o);
      first = false;
    }
    return sb.toString();
  }

}

