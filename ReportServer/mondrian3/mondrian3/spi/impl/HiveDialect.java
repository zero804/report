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
// Copyright (C) 2011-2013 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.spi.impl;

import mondrian3.olap.Util;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Implementation of {@link mondrian3.spi.Dialect} for the Hive database.
 *
 * @author Hongwei Fu
 * @since Jan 10, 2011
 */
public class HiveDialect extends JdbcDialectImpl {
    private static final int MAX_COLUMN_NAME_LENGTH = 128;

    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            HiveDialect.class,
            DatabaseProduct.HIVE)
        {
            protected boolean acceptsConnection(Connection connection) {
                return super.acceptsConnection(connection)
                    && !isDatabase(DatabaseProduct.IMPALA, connection);
            }
        };

    /**
     * Creates a HiveDialect.
     *
     * @param connection Connection
     *
     * @throws SQLException on error
     */
    public HiveDialect(Connection connection) throws SQLException {
        super(connection);
    }

    protected String deduceIdentifierQuoteString(
        DatabaseMetaData databaseMetaData)
    {
        return null;
    }

    protected Set<List<Integer>> deduceSupportedResultSetStyles(
        DatabaseMetaData databaseMetaData)
    {
        // Hive don't support this, so just return an empty set.
        return Collections.emptySet();
    }

    protected boolean deduceReadOnly(DatabaseMetaData databaseMetaData) {
        try {
            return databaseMetaData.isReadOnly();
        } catch (SQLException e) {
            // Hive is read only (as of release 0.7)
            return true;
        }
    }

    protected int deduceMaxColumnNameLength(DatabaseMetaData databaseMetaData) {
        try {
            return databaseMetaData.getMaxColumnNameLength();
        } catch (SQLException e) {
            return MAX_COLUMN_NAME_LENGTH;
        }
    }

    public boolean allowsCompoundCountDistinct() {
        return true;
    }

    public boolean requiresAliasForFromQuery() {
        return true;
    }

    @Override
    public boolean requiresOrderByAlias() {
        return true;
    }

    @Override
    public boolean allowsOrderByAlias() {
        return true;
    }

    @Override
    public boolean requiresGroupByAlias() {
        return false;
    }

    public boolean requiresUnionOrderByExprToBeInSelectClause() {
        return false;
    }

    public boolean requiresUnionOrderByOrdinal() {
        return false;
    }

    public String generateInline(
        List<String> columnNames,
        List<String> columnTypes,
        List<String[]> valueList)
    {
        return "select * from ("
            + generateInlineGeneric(
                columnNames, columnTypes, valueList, " from dual", false)
            + ") x limit " + valueList.size();
    }

    protected void quoteDateLiteral(
        StringBuilder buf,
        String value,
        Date date)
    {
        // Hive doesn't support Date type; treat date as a string '2008-01-23'
        Util.singleQuoteString(value, buf);
    }

    @Override
    protected String generateOrderByNulls(
        String expr,
        boolean ascending,
        boolean collateNullsLast)
    {
        // In Hive, Null values are worth negative infinity.
        if (collateNullsLast) {
            if (ascending) {
                return "ISNULL(" + expr + ") ASC, " + expr + " ASC";
            } else {
                return expr + " DESC";
            }
        } else {
            if (ascending) {
                return expr + " ASC";
            } else {
                return "ISNULL(" + expr + ") DESC, " + expr + " DESC";
            }
        }
    }

    public boolean allowsAs() {
        return false;
    }

    public boolean allowsJoinOn() {
        return false;
    }
}

// End HiveDialect.java
