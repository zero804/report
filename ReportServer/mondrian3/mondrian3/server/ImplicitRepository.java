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
// Copyright (C) 2010-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.server;

import mondrian3.olap.MondrianServer;
import mondrian3.rolap.RolapConnection;
import mondrian3.rolap.RolapSchema;

import org.olap4j.OlapConnection;
import org.olap4j.impl.Olap4jUtil;

import java.util.*;

/**
 * Implementation of {@link Repository} for
 * a server that doesn't have a repository: each connection in the server
 * has its own catalog (specified in the connect string) and therefore the
 * catalog and schema metadata will be whatever pertains to that connection.
 * (That's why the methods have a connection parameter.)
 *
 * @author Julian Hyde
 */
public class ImplicitRepository implements Repository {
    public ImplicitRepository() {
        super();
    }

    public List<String> getCatalogNames(
        RolapConnection connection,
        String databaseName)
    {
        // In an implicit repository, we assume that there is a single
        // database, a single catalog and a single schema.
        return
            Collections.singletonList(
                connection.getSchema().getName());
    }

    public List<String> getDatabaseNames(RolapConnection connection)
    {
        // In an implicit repository, we assume that there is a single
        // database, a single catalog and a single schema.
        return
            Collections.singletonList(
                connection.getSchema().getName());
    }

    public Map<String, RolapSchema> getRolapSchemas(
        RolapConnection connection,
        String databaseName,
        String catalogName)
    {
        final RolapSchema schema = connection.getSchema();
        assert schema.getName().equals(catalogName);
        return Collections.singletonMap(schema.getName(), schema);
    }

    public OlapConnection getConnection(
        MondrianServer server,
        String databaseName,
        String catalogName,
        String roleName,
        Properties props)
    {
        // This method does not make sense in an ImplicitRepository. The
        // catalog and schema are gleaned from the connection, not vice
        // versa.
        throw new UnsupportedOperationException();
    }

    public List<Map<String, Object>> getDatabases(
        RolapConnection connection)
    {
        return Collections.singletonList(
            Olap4jUtil.<String, Object>mapOf(
                "DataSourceName", connection.getSchema().getName(),
                "DataSourceDescription", null,
                "URL", null,
                "DataSourceInfo", connection.getSchema().getName(),
                "ProviderName", "Mondrian",
                "ProviderType", "MDP",
                "AuthenticationMode", "Unauthenticated"));
    }

    public void shutdown() {
        // ignore.
    }
}

// End ImplicitRepository.java
