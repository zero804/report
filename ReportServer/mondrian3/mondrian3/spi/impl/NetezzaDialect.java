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
// Copyright (C) 2008-2009 Jaspersoft
// Copyright (C) 2009-2013 Pentaho
// All Rights Reserved.
*/
package mondrian3.spi.impl;

import mondrian3.rolap.SqlStatement;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Implementation of {@link mondrian3.spi.Dialect} for the Netezza database.
 *
 * @author swood
 * @since April 17, 2009
 */
public class NetezzaDialect extends PostgreSqlDialect {

    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            NetezzaDialect.class,
            // Netezza behaves the same as PostGres but doesn't use the
            // postgres driver, so we setup the factory to NETEZZA.
            DatabaseProduct.NETEZZA)
        {
            protected boolean acceptsConnection(Connection connection) {
                return isDatabase(DatabaseProduct.NETEZZA, connection);
            }
        };

    /**
     * Creates a NetezzaDialect.
     *
     * @param connection Connection
     */
    public NetezzaDialect(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    public DatabaseProduct getDatabaseProduct() {
        return DatabaseProduct.NETEZZA;
    }

    @Override
    public boolean allowsRegularExpressionInWhereClause() {
        return false;
    }

    @Override
    public String generateRegularExpression(String source, String javaRegex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlStatement.Type getType(
        ResultSetMetaData metaData, int columnIndex)
        throws SQLException
    {
        final int precision = metaData.getPrecision(columnIndex + 1);
        final int scale = metaData.getScale(columnIndex + 1);
        final int columnType = metaData.getColumnType(columnIndex + 1);

        if (columnType == Types.NUMERIC || columnType == Types.DECIMAL
            && (scale == 0 && precision == 38))
        {
            // Netezza marks longs as scale 0 and precision 38.
            // An int would overflow.
            logTypeInfo(metaData, columnIndex, SqlStatement.Type.DOUBLE);
            return SqlStatement.Type.DOUBLE;
        }
        return super.getType(metaData, columnIndex);
    }
}

// End NetezzaDialect.java
