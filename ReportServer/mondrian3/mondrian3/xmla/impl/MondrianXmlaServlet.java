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
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2011-2012 Pentaho
// All Rights Reserved.
*/
package mondrian3.xmla.impl;

import mondrian3.olap.MondrianServer;
import mondrian3.olap.Util;
import mondrian3.server.RepositoryContentFinder;
import mondrian3.server.UrlRepositoryContentFinder;
import mondrian3.spi.CatalogLocator;
import mondrian3.spi.impl.ServletContextCatalogLocator;
import mondrian3.xmla.XmlaHandler;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.*;

/**
 * Extension to {@link mondrian3.xmla.XmlaServlet} that instantiates a
 * Mondrian engine.
 *
 * @author jhyde
 */
public class MondrianXmlaServlet extends DefaultXmlaServlet {
    public static final String DEFAULT_DATASOURCE_FILE = "datasources.xml";

    protected MondrianServer server;

    @Override
    protected XmlaHandler.ConnectionFactory createConnectionFactory(
        ServletConfig servletConfig)
        throws ServletException
    {
        if (server == null) {
            // A derived class can alter how the calalog locator object is
            // created.
            CatalogLocator catalogLocator = makeCatalogLocator(servletConfig);

            String dataSources = makeDataSourcesUrl(servletConfig);
            RepositoryContentFinder contentFinder =
                makeContentFinder(dataSources);
            server =
                MondrianServer.createWithRepository(
                    contentFinder, catalogLocator);
        }
        return (XmlaHandler.ConnectionFactory) server;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (server != null) {
            server.shutdown();
            server = null;
        }
    }

    /**
     * Creates a callback for reading the repository. Derived classes may
     * override.
     *
     * @param dataSources Data sources
     * @return Callback for reading repository
     */
    protected RepositoryContentFinder makeContentFinder(String dataSources) {
        return new UrlRepositoryContentFinder(dataSources);
    }

    /**
     * Make catalog locator.  Derived classes can roll their own.
     *
     * @param servletConfig Servlet configuration info
     * @return Catalog locator
     */
    protected CatalogLocator makeCatalogLocator(ServletConfig servletConfig) {
        ServletContext servletContext = servletConfig.getServletContext();
        return new ServletContextCatalogLocator(servletContext);
    }

    /**
     * Creates the URL where the data sources file is to be found.
     *
     * <p>Derived classes can roll their own.
     *
     * <p>If there is an initParameter called "DataSourcesConfig"
     * get its value, replace any "${key}" content with "value" where
     * "key/value" are System properties, and try to create a URL
     * instance out of it. If that fails, then assume its a
     * real filepath and if the file exists then create a URL from it
     * (but only if the file exists).
     * If there is no initParameter with that name, then attempt to
     * find the file called "datasources.xml"  under "WEB-INF/"
     * and if it exists, use it.
     *
     * @param servletConfig Servlet config
     * @return URL where data sources are to be found
     */
    protected String makeDataSourcesUrl(ServletConfig servletConfig)
    {
        String paramValue =
                servletConfig.getInitParameter(PARAM_DATASOURCES_CONFIG);
        // if false, then do not throw exception if the file/url
        // can not be found
        boolean optional =
            getBooleanInitParameter(
                servletConfig, PARAM_OPTIONAL_DATASOURCE_CONFIG);

        URL dataSourcesConfigUrl = null;
        try {
            if (paramValue == null) {
                // fallback to default
                String defaultDS = "WEB-INF/" + DEFAULT_DATASOURCE_FILE;
                ServletContext servletContext =
                    servletConfig.getServletContext();
                File realPath = new File(servletContext.getRealPath(defaultDS));
                if (realPath.exists()) {
                    // only if it exists
                    dataSourcesConfigUrl = realPath.toURL();
                    return dataSourcesConfigUrl.toString();
                } else {
                    return null;
                }
            } else if (paramValue.startsWith("inline:")) {
                return paramValue;
            } else {
                paramValue = Util.replaceProperties(
                    paramValue,
                    Util.toMap(System.getProperties()));
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(
                        "XmlaServlet.makeDataSources: paramValue="
                        + paramValue);
                }
                // is the parameter a valid URL
                MalformedURLException mue = null;
                try {
                    dataSourcesConfigUrl = new URL(paramValue);
                } catch (MalformedURLException e) {
                    // not a valid url
                    mue = e;
                }
                if (dataSourcesConfigUrl == null) {
                    // see if its a full valid file path
                    File f = new File(paramValue);
                    if (f.exists()) {
                        // yes, a real file path
                        dataSourcesConfigUrl = f.toURL();
                    } else if (mue != null) {
                        // neither url or file,
                        // is it not optional
                        if (! optional) {
                            throw mue;
                        }
                    }
                    return null;
                }
                return dataSourcesConfigUrl.toString();
            }
        } catch (MalformedURLException mue) {
            throw Util.newError(mue, "invalid URL path '" + paramValue + "'");
        }
    }
}

// End MondrianXmlaServlet.java
