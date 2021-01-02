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
// Copyright (C) 2011-2012 Pentaho
// All Rights Reserved.
*/
package mondrian3.server.monitor;

/**
 * Deletion of a segment from the cell cache.
 */
public class CellCacheSegmentDeleteEvent extends CellCacheEvent {

    public final int coordinateCount;

    /**
     * Creates a CellCacheSegmentCreateEvent.
     *
     * @param timestamp Timestamp
     * @param serverId ID of the server from which the event originates.
     * @param connectionId ID of the connection from which the event
     * originates.
     * @param statementId ID of the statement from which the event originates.
     * @param executionId ID of the execution from which the event originates.
     * @param coordinateCount Number of coordinates of segment header
     * @param source Source of segment
     */
    public CellCacheSegmentDeleteEvent(
        long timestamp,
        int serverId,
        int connectionId,
        long statementId,
        long executionId,
        int coordinateCount,
        Source source)
    {
        super(
            timestamp, serverId, connectionId,
            statementId, executionId, source);
        this.coordinateCount = coordinateCount;
    }

    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}

// End CellCacheSegmentDeleteEvent.java
