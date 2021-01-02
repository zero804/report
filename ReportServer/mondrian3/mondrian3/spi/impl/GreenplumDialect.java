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
// Copyright (C) 2008-2009 Millersoft
// Copyright (C) 2011-2013 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.spi.impl;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of {@link mondrian3.spi.Dialect} for the GreenplumSQL database.
 *
 * @author Millersoft
 * @since Dec 23, 2009
 */
public class GreenplumDialect extends PostgreSqlDialect {

    /**
     * Creates a GreenplumDialect.
     *
     * @param connection Connection
     */
    public GreenplumDialect(Connection connection) throws SQLException {
        super(connection);
    }


    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            GreenplumDialect.class,
            // While we're choosing dialects, this still looks like a Postgres
            // connection.
            DatabaseProduct.POSTGRESQL)
        {
            protected boolean acceptsConnection(Connection connection) {
                return super.acceptsConnection(connection)
                   && isDatabase(DatabaseProduct.GREENPLUM, connection);
            }
        };

    public boolean supportsGroupingSets() {
        return true;
    }

    public boolean requiresGroupByAlias() {
        return true;
    }

    public boolean requiresAliasForFromQuery() {
        return false;
    }

    public boolean allowsCountDistinct() {
        return true;
    }

    public boolean allowsFromQuery() {
        return false;
    }

    public DatabaseProduct getDatabaseProduct() {
        return DatabaseProduct.GREENPLUM;
    }

    public String generateCountExpression(String exp) {
        return caseWhenElse(exp + " ISNULL", "'0'", "TEXT(" + exp + ")");
    }

    public boolean allowsRegularExpressionInWhereClause() {
        // Support for regexp was added in GP 3.2+
        if (productVersion.compareTo("3.2") >= 0) {
            return true;
        } else {
            return false;
        }
    }
}

// End GreenplumDialect.java
