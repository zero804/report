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
package mondrian3.spi;

import mondrian3.server.Execution;

import javax.sql.DataSource;

/**
 * Provides estimates of the number of rows in a database.
 *
 * <p>Mondrian generally finds statistics providers via the
 * {@link Dialect#getStatisticsProviders} method on the dialect object for the
 * current connection. The default implementation of that method looks first at
 * the "mondrian3.statistics.providers.DATABASE" property (substituting the
 * current database name, e.g. MYSQL or ORACLE, for <i>DATABASE</i>), then at
 * the {@link mondrian3.olap.MondrianProperties#StatisticsProviders "mondrian3.statistics.providers"}
 * property.</p>
 *
 * @see mondrian3.spi.impl.JdbcStatisticsProvider
 * @see mondrian3.spi.impl.SqlStatisticsProvider
 *
 */
public interface StatisticsProvider {
    /**
     * Returns an estimate of the number of rows in a table.
     *
     * @param dialect Dialect
     * @param dataSource Data source
     * @param catalog Catalog name
     * @param schema Schema name
     * @param table Table name
     * @param execution Execution
     *
     * @return Estimated number of rows in table, or -1 if there
     * is no estimate
     */
    int getTableCardinality(
        Dialect dialect,
        DataSource dataSource,
        String catalog,
        String schema,
        String table,
        Execution execution);

    /**
     * Returns an estimate of the number of rows returned by a query.
     *
     * @param dialect Dialect
     * @param dataSource Data source
     * @param sql Query, e.g. "select * from customers where age < 20"
     * @param execution Execution
     *
     * @return Estimated number of rows returned by query, or -1 if there
     * is no estimate
     */
    int getQueryCardinality(
        Dialect dialect,
        DataSource dataSource,
        String sql,
        Execution execution);

    /**
     * Returns an estimate of the number of rows in a table.
     *
     * @param dialect Dialect
     * @param dataSource Data source
     * @param catalog Catalog name
     * @param schema Schema name
     * @param table Table name
     * @param column Column name
     * @param execution Execution
     *
     * @return Estimated number of rows in table, or -1 if there
     * is no estimate
     */
    int getColumnCardinality(
        Dialect dialect,
        DataSource dataSource,
        String catalog,
        String schema,
        String table,
        String column,
        Execution execution);
}

// End StatisticsProvider.java
