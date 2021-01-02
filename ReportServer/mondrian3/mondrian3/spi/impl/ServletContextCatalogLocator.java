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
// Copyright (C) 2005-2006 Pentaho
// All Rights Reserved.
*/
package mondrian3.spi.impl;

import mondrian3.spi.CatalogLocator;

import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletContext;

/**
 * Locates a catalog based upon a {@link ServletContext}.<p/>
 *
 * If the catalog URI is an absolute path, it refers to a resource inside our
 * WAR file, so replace the URL.
 *
 * @author Gang Chen, jhyde
 * @since December, 2005
 */
public class ServletContextCatalogLocator implements CatalogLocator {
    private ServletContext servletContext;

    public ServletContextCatalogLocator(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String locate(String catalogPath) {
        // If the catalog is an absolute path, it refers to a resource inside
        // our WAR file, so replace the URL.
        if (catalogPath != null && catalogPath.startsWith("/")) {
            try {
                URL url = servletContext.getResource(catalogPath);
                if (url == null) {
                    // The catalogPath does not exist, but construct a feasible
                    // URL so that the error message makes sense.
                    url = servletContext.getResource("/");
                    url = new URL(
                        url.getProtocol(),
                        url.getHost(),
                        url.getPort(),
                        url.getFile() + catalogPath.substring(1));
                }
                catalogPath = url.toString();
            } catch (MalformedURLException ignored) {
            }
        }
        return catalogPath;
    }
}

// End ServletContextCatalogLocator.java
