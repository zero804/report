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
// Copyright (C) 2013 Pentaho
// All Rights Reserved.
*/
package mondrian3.spi.impl;

import mondrian3.olap.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * User: cboyden
 * Date: 2/8/13
 */
public class RedshiftDialect extends PostgreSqlDialect {
    /**
     * Creates a RedshiftDialect.
     *
     * @param connection Connection
     */
    public RedshiftDialect(Connection connection) throws SQLException {
        super(connection);
    }

    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            RedshiftDialect.class,
            DatabaseProduct.POSTGRESQL)
        {
            protected boolean acceptsConnection(Connection connection) {
                return super.acceptsConnection(connection)
                    && isDatabase(DatabaseProduct.REDSHIFT, connection);
            }
        };

    public DatabaseProduct getDatabaseProduct() {
        return DatabaseProduct.REDSHIFT;
    }

    @Override
    public String generateInline(
        List<String> columnNames,
        List<String> columnTypes,
        List<String[]> valueList)
    {
        return generateInlineGeneric(
            columnNames, columnTypes, valueList, null, false);
    }

    @Override
    public void quoteStringLiteral(
        StringBuilder buf,
        String value)
    {
        // '\' to '\\'
        Util.singleQuoteString(value.replaceAll("\\\\", "\\\\\\\\"), buf);
    }

    @Override
    public boolean allowsRegularExpressionInWhereClause() {
        return false;
    }

    @Override
    public String generateRegularExpression(
        String source,
        String javaRegExp)
    {
        return null;
    }
}

// End RedshiftDialect.java