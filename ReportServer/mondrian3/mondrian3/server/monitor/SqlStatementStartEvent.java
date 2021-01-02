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

import mondrian3.server.Locus;

/**
 * Event created just before Mondrian starts to execute a SQL statement.
 */
public class SqlStatementStartEvent extends SqlStatementEvent {
    public final int cellRequestCount;

    /**
     * Creates a SqlStatementStartEvent.
     *
     * @param timestamp Timestamp
     * @param sqlStatementId SQL Statement id
     * @param locus Locus of event
     * @param sql SQL
     * @param purpose Why Mondrian is executing this statement
     * @param cellRequestCount Number of missed cells that led to this request
     */
    public SqlStatementStartEvent(
        long timestamp,
        long sqlStatementId,
        Locus locus,
        String sql,
        Purpose purpose,
        int cellRequestCount)
    {
        super(timestamp, sqlStatementId, locus, sql, purpose);
        this.cellRequestCount = cellRequestCount;
    }

    public String toString() {
        return "SqlStatementStartEvent(" + sqlStatementId + ")";
    }

    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}

// End SqlStatementStartEvent.java
