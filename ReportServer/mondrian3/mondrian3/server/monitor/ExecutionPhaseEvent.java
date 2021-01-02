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
 * Event signalling the start of a phase of executing an MDX statement.
 *
 * <p>A phase begins when Mondrian has tried to execute a statement and has
 * determined that it needs cell values in order to give the complete, correct
 * result. It generates one or more SQL statements to load those cells, and
 * starts a new phase. Most MDX statements can be completed in 3 or fewer
 * phases.</p>
 */
public class ExecutionPhaseEvent extends ExecutionEvent {
    public final int phase;
    public final int hitCount;
    public final int missCount;
    public final int pendingCount;

    /**
     * Creates an ExecutionPhaseEvent.
     *
     * @param timestamp Timestamp
     * @param serverId Server id
     * @param connectionId Connection id
     * @param statementId Statement id
     * @param executionId Execution id
     * @param phase Phase
     * @param hitCount Cache hits this phase
     * @param missCount Cache misses this phase
     * @param pendingCount Cache entries hit but not ready this phase
     */
    public ExecutionPhaseEvent(
        long timestamp,
        int serverId,
        int connectionId,
        long statementId,
        long executionId,
        int phase,
        int hitCount,
        int missCount,
        int pendingCount)
    {
        super(timestamp, serverId, connectionId, statementId, executionId);
        this.phase = phase;
        this.hitCount = hitCount;
        this.missCount = missCount;
        this.pendingCount = pendingCount;
    }

    @Override
    public String toString() {
        return "ExecutionPhaseEvent(" + executionId + ", " + phase + ")";
    }

    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}

// End ExecutionPhaseEvent.java
