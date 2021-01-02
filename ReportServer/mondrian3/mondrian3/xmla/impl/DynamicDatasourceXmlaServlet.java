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
// Copyright (C) 2006-2013 Pentaho
// All Rights Reserved.
*/
package mondrian3.xmla.impl;

import mondrian3.server.DynamicContentFinder;
import mondrian3.server.RepositoryContentFinder;

import java.util.HashMap;
import java.util.Map;

/**
 * Extends DefaultXmlaServlet to add dynamic datasource loading capability.
 *
 * @author Thiyagu Ajit
 * @author Luc Boudreau
 */
public class DynamicDatasourceXmlaServlet extends MondrianXmlaServlet {
    private static final long serialVersionUID = 1L;

    /**
     * A map of datasources definitions to {@link DynamicContentFinder}
     * instances.
     */
    private final Map<String, DynamicContentFinder> finders =
        new HashMap<String, DynamicContentFinder>();

    @Override
    protected RepositoryContentFinder makeContentFinder(String dataSources) {
        if (!finders.containsKey(dataSources)) {
            finders.put(dataSources, new DynamicContentFinder(dataSources));
        }
        return finders.get(dataSources);
    }
    @Override
    public void destroy() {
        for (DynamicContentFinder finder : finders.values()) {
            finder.shutdown();
        }
        super.destroy();
    }
}

// End DynamicDatasourceXmlaServlet.java
