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
// Copyright (C) 2011-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.server.monitor;

/**
 * Information about the execution of an MDX statement.
 */
public class ExecutionInfo extends Info {
    public final long executionId;
    public final int phaseCount;
    public final long cellCacheRequestCount;
    public final long cellCacheHitCount;
    public final long cellCacheMissCount;
    public final long cellCachePendingCount;
    public final int sqlStatementStartCount;
    public final int sqlStatementExecuteCount;
    public final int sqlStatementEndCount;
    public final long sqlStatementRowFetchCount;
    public final long sqlStatementExecuteNanos;
    public final int cellRequestCount;

    public ExecutionInfo(
        String stack,
        long executionId,
        int phaseCount,
        long cellCacheRequestCount,
        long cellCacheHitCount,
        long cellCacheMissCount,
        long cellCachePendingCount,
        int sqlStatementStartCount,
        int sqlStatementExecuteCount,
        int sqlStatementEndCount,
        long sqlStatementRowFetchCount,
        long sqlStatementExecuteNanos,
        int cellRequestCount)
    {
        super(stack);
        this.executionId = executionId;
        this.phaseCount = phaseCount;
        this.cellCacheRequestCount = cellCacheRequestCount;
        this.cellCacheHitCount = cellCacheHitCount;
        this.cellCacheMissCount = cellCacheMissCount;
        this.cellCachePendingCount = cellCachePendingCount;
        this.sqlStatementStartCount = sqlStatementStartCount;
        this.sqlStatementExecuteCount = sqlStatementExecuteCount;
        this.sqlStatementEndCount = sqlStatementEndCount;
        this.sqlStatementRowFetchCount = sqlStatementRowFetchCount;
        this.sqlStatementExecuteNanos = sqlStatementExecuteNanos;
        this.cellRequestCount = cellRequestCount;
        assert cellCacheRequestCount
               == cellCacheHitCount
                  + cellCacheMissCount
                  + cellCachePendingCount;
    }
}

// End ExecutionInfo.java
