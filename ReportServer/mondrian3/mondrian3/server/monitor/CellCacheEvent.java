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

import mondrian3.olap.CacheControl;

/**
 * Event concerning the cell cache.
 */
public abstract class CellCacheEvent extends ExecutionEvent {

    public final Source source;

    /**
     * Creates a CellCacheEvent.
     * @param timestamp Timestamp of the event.
     * @param serverId Server ID from which originated the event.
     * @param connectionId Connection ID from which originated the event.
     * @param statementId Statement ID from which originated the event.
     * @param executionId Execution ID from which originated the event.
     * @param source The source of the event, being a value of Source.
     */
    public CellCacheEvent(
        long timestamp,
        int serverId,
        int connectionId,
        long statementId,
        long executionId,
        Source source)
    {
        super(timestamp, serverId, connectionId, statementId, executionId);
        this.source = source;
    }

    /**
     * Enumeration of sources of a cell cache segment.
     */
    public enum Source {
        /**
         * A segment that is placed into the cache by an external cache.
         *
         * <p>Some caches (e.g. memcached) never generate this kind of
         * event.</p>
         *
         * <p>In JBoss Infinispan, one scenario that causes this kind of event
         * is as follows. A user issues an MDX query against a different
         * Mondrian node in the same Infinispan cluster. To resolve missing
         * cells, that node issues a SQL statement to load a segment. Infinispan
         * propagates that segment to its peers, and each peer is notified that
         * an "external segment" is now in the cache.</p>
         */
        EXTERNAL,

        /**
         * A segment that has been loaded in response to a user query,
         * and populated by generating and executing a SQL statement.
         */
        SQL,

        /**
         * a segment that has been loaded in response to a user query,
         * and populated by rolling up existing cache segments.
         */
        ROLLUP,

        /**
         * a segment that has been deleted by a call through
         * the {@link CacheControl} API.
         */
        CACHE_CONTROL,
    }
}

// End CellCacheEvent.java
