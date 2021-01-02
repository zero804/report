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
// Copyright (C) 2011-2011 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap.agg;

/**
 * Signals that there are enough outstanding cell requests that it is
 * worth terminating this phase of execution and asking the segment cache
 * for all of the cells that have been asked for.
 *
 * <p>Not really an exception, just a way of aborting a process so that we can
 * do some work and restart the process. Any code that handles this exception
 * is typically in a loop that calls {@link mondrian3.rolap.RolapResult#phase}.
 * </p>
 *
 * <p>There are several advantages to this:</p>
 * <ul>
 *     <li>If the query has been run before, the cells will be in the
 *     cache already, and this is an opportunity to copy them into the
 *     local cache.</li>
 *     <li>If cell requests are for the same or similar cells, it gives
 *     opportunity to fetch these cells. Then the requests can be answered
 *     from local cache, and we don't need to bother the cache manager with
 *     similar requests.</li>
 *     <li>Prevents memory from filling up with cell requests.</li>
 * </ul>
 */
public final class CellRequestQuantumExceededException
    extends RuntimeException
{
    public static final CellRequestQuantumExceededException INSTANCE =
        new CellRequestQuantumExceededException();

    private CellRequestQuantumExceededException() {
    }
}

// End CellRequestQuantumExceededException.java
