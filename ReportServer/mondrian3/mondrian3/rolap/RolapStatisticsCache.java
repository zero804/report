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
package mondrian3.rolap;

import mondrian3.olap.MondrianDef;
import mondrian3.rolap.sql.SqlQuery;
import mondrian3.server.Execution;
import mondrian3.spi.Dialect;
import mondrian3.spi.StatisticsProvider;

import java.util.*;
import javax.sql.DataSource;

/**
 * Provides and caches statistics.
 *
 * <p>Wrapper around a chain of {@link mondrian3.spi.StatisticsProvider}s,
 * followed by a cache to store the results.</p>
 */
public class RolapStatisticsCache {
    private final RolapStar star;
    private final Map<List, Integer> columnMap = new HashMap<List, Integer>();
    private final Map<List, Integer> tableMap = new HashMap<List, Integer>();
    private final Map<String, Integer> queryMap =
        new HashMap<String, Integer>();

    public RolapStatisticsCache(RolapStar star) {
        this.star = star;
    }

    public int getRelationCardinality(
        MondrianDef.Relation relation,
        String alias,
        int approxRowCount)
    {
        if (approxRowCount >= 0) {
            return approxRowCount;
        }
        if (relation instanceof MondrianDef.Table) {
            final MondrianDef.Table table = (MondrianDef.Table) relation;
            return getTableCardinality(
                null, table.schema, table.name);
        } else {
            final SqlQuery sqlQuery = star.getSqlQuery();
            sqlQuery.addSelect("*", null);
            sqlQuery.addFrom(relation, null, true);
            return getQueryCardinality(sqlQuery.toString());
        }
    }

    private int getTableCardinality(
        String catalog,
        String schema,
        String table)
    {
        final List<String> key = Arrays.asList(catalog, schema, table);
        int rowCount = -1;
        if (tableMap.containsKey(key)) {
            rowCount = tableMap.get(key);
        } else {
            final Dialect dialect = star.getSqlQueryDialect();
            final List<StatisticsProvider> statisticsProviders =
                dialect.getStatisticsProviders();
            final Execution execution =
                new Execution(
                    star.getSchema().getInternalConnection()
                        .getInternalStatement(),
                    0);
            for (StatisticsProvider statisticsProvider : statisticsProviders) {
                rowCount = statisticsProvider.getTableCardinality(
                    dialect,
                    star.getDataSource(),
                    catalog,
                    schema,
                    table,
                    execution);
                if (rowCount >= 0) {
                    break;
                }
            }

            // Note: If all providers fail, we put -1 into the cache, to ensure
            // that we won't try again.
            tableMap.put(key, rowCount);
        }
        return rowCount;
    }

    private int getQueryCardinality(String sql) {
        int rowCount = -1;
        if (queryMap.containsKey(sql)) {
            rowCount = queryMap.get(sql);
        } else {
            final Dialect dialect = star.getSqlQueryDialect();
            final List<StatisticsProvider> statisticsProviders =
                dialect.getStatisticsProviders();
            final Execution execution =
                new Execution(
                    star.getSchema().getInternalConnection()
                        .getInternalStatement(),
                    0);
            for (StatisticsProvider statisticsProvider : statisticsProviders) {
                rowCount = statisticsProvider.getQueryCardinality(
                    dialect, star.getDataSource(), sql, execution);
                if (rowCount >= 0) {
                    break;
                }
            }

            // Note: If all providers fail, we put -1 into the cache, to ensure
            // that we won't try again.
            queryMap.put(sql, rowCount);
        }
        return rowCount;
    }

    public int getColumnCardinality(
        MondrianDef.Relation relation,
        MondrianDef.Expression expression,
        int approxCardinality)
    {
        if (approxCardinality >= 0) {
            return approxCardinality;
        }
        if (relation instanceof MondrianDef.Table
            && expression instanceof MondrianDef.Column)
        {
            final MondrianDef.Table table = (MondrianDef.Table) relation;
            final MondrianDef.Column column = (MondrianDef.Column) expression;
            return getColumnCardinality(
                null,
                table.schema,
                table.name,
                column.name);
        } else {
            final SqlQuery sqlQuery = star.getSqlQuery();
            sqlQuery.setDistinct(true);
            sqlQuery.addSelect(expression.getExpression(sqlQuery), null);
            sqlQuery.addFrom(relation, null, true);
            return getQueryCardinality(sqlQuery.toString());
        }
    }

    private int getColumnCardinality(
        String catalog,
        String schema,
        String table,
        String column)
    {
        final List<String> key = Arrays.asList(catalog, schema, table, column);
        int rowCount = -1;
        if (columnMap.containsKey(key)) {
            rowCount = columnMap.get(key);
        } else {
            final Dialect dialect = star.getSqlQueryDialect();
            final List<StatisticsProvider> statisticsProviders =
                dialect.getStatisticsProviders();
            final Execution execution =
                new Execution(
                    star.getSchema().getInternalConnection()
                        .getInternalStatement(),
                    0);
            for (StatisticsProvider statisticsProvider : statisticsProviders) {
                rowCount = statisticsProvider.getColumnCardinality(
                    dialect,
                    star.getDataSource(),
                    catalog,
                    schema,
                    table,
                    column,
                    execution);
                if (rowCount >= 0) {
                    break;
                }
            }

            // Note: If all providers fail, we put -1 into the cache, to ensure
            // that we won't try again.
            columnMap.put(key, rowCount);
        }
        return rowCount;
    }

    public int getColumnCardinality2(
        DataSource dataSource,
        Dialect dialect,
        String catalog,
        String schema,
        String table,
        String column)
    {
        return -1;
    }
}

// End RolapStatisticsCache.java
