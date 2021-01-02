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
// Copyright (C) 2012-2012 Pentaho
// All Rights Reserved.
*/
package mondrian3.spi.impl;

import mondrian3.olap.Util;
import mondrian3.server.Execution;
import mondrian3.spi.Dialect;
import mondrian3.spi.StatisticsProvider;

import org.apache.log4j.Logger;

import java.sql.*;
import javax.sql.DataSource;

/**
 * Implementation of {@link mondrian3.spi.StatisticsProvider} that uses JDBC
 * metadata calls to count rows and distinct values.
 */
public class JdbcStatisticsProvider implements StatisticsProvider {
    private static final Logger LOG =
        Logger.getLogger(JdbcStatisticsProvider.class);
    public int getTableCardinality(
        Dialect dialect,
        DataSource dataSource,
        String catalog,
        String schema,
        String table,
        Execution execution)
    {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            resultSet =
                connection
                    .getMetaData()
                    .getIndexInfo(catalog, schema, table, false, true);
            int maxNonUnique = -1;
            while (resultSet.next()) {
                final int type = resultSet.getInt(7); // "TYPE" column
                final int cardinality = resultSet.getInt(11);
                final boolean unique =
                    !resultSet.getBoolean(4); // "NON_UNIQUE" column
                switch (type) {
                case DatabaseMetaData.tableIndexStatistic:
                    return cardinality; // "CARDINALITY" column
                }
                if (!unique) {
                    maxNonUnique = Math.max(maxNonUnique, cardinality);
                }
            }
            // The cardinality of each non-unique index will be the number of
            // non-NULL values in that index. Unless we're unlucky, one of those
            // columns will cover most of the table.
            return maxNonUnique;
        } catch (SQLException e) {
            // We will have to try a count() operation or some other
            // statistics provider in the chain.
            if (LOG.isDebugEnabled()) {
                LOG.debug(
                    "JdbcStatisticsProvider failed to get the cardinality of the table "
                        + table,
                    e);
            }
            return -1;
        } finally {
            Util.close(resultSet, null, connection);
        }
    }

    public int getQueryCardinality(
        Dialect dialect,
        DataSource dataSource,
        String sql,
        Execution execution)
    {
        // JDBC cannot help with this. Defer to another statistics provider.
        return -1;
    }

    public int getColumnCardinality(
        Dialect dialect,
        DataSource dataSource,
        String catalog,
        String schema,
        String table,
        String column,
        Execution execution)
    {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            resultSet =
                connection
                    .getMetaData()
                    .getIndexInfo(catalog, schema, table, false, true);
            while (resultSet.next()) {
                int type = resultSet.getInt(7); // "TYPE" column
                switch (type) {
                case DatabaseMetaData.tableIndexStatistic:
                    continue;
                default:
                    String columnName = resultSet.getString(9); //COLUMN_NAME
                    if (columnName != null && columnName.equals(column)) {
                        return resultSet.getInt(11); // "CARDINALITY" column
                    }
                }
            }
            return -1; // information not available, apparently
        } catch (SQLException e) {
            // We will have to try a count() operation or some other
            // statistics provider in the chain.
            if (LOG.isDebugEnabled()) {
                LOG.debug(
                    "JdbcStatisticsProvider failed to get the cardinality of the table "
                        + table,
                    e);
            }
            return -1;
        } finally {
            Util.close(resultSet, null, connection);
        }
    }
}

// End JdbcStatisticsProvider.java
