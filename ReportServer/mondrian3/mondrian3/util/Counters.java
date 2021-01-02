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
// Copyright (C) 2011-2012 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.util;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A collection of counters. Used internally for logging and
 * consistency-checking purposes. Should not be relied upon by applications.
 */
public abstract class Counters {
    /** Number of times {@code SqlStatement.execute} has completed
     * successfully. */
    public static final AtomicLong SQL_STATEMENT_EXECUTE_COUNT =
        new AtomicLong();

    /** Number of times {@code SqlStatement.close} has been called. */
    public static final AtomicLong SQL_STATEMENT_CLOSE_COUNT = new AtomicLong();

    /** Ids of all {@code SqlStatement} instances that are executing. */
    public static final Set<Long> SQL_STATEMENT_EXECUTING_IDS =
        Collections.synchronizedSet(new HashSet<Long>());
}

// End Counters.java
