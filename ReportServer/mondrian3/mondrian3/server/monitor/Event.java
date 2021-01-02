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
// Copyright (C) 2011-2013 Pentaho
// All Rights Reserved.
*/
package mondrian3.server.monitor;

import mondrian3.rolap.RolapUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Base class for an event of interest.
 *
 * <p>This class, and subclasses, is an immutable but serializable.</p>
 */
public abstract class Event implements Message {
    /**
     * When the event occurred. Milliseconds since the epoch UTC, just like
     * {@link System#currentTimeMillis()}.
     */
    public final long timestamp;

    /**
     * When {@link RolapUtil#MONITOR_LOGGER} is set to TRACE,
     * this field will contain the stack of the code which
     * created this event.
     */
    public final String stack;

    /**
     * Creates an Event.
     *
     * @param timestamp Timestamp
     *
     */
    public Event(
        long timestamp)
    {
        this.timestamp = timestamp;
        if (RolapUtil.MONITOR_LOGGER.isTraceEnabled()) {
            try {
                throw new Exception();
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw, true));
                this.stack = sw.toString();
            }
        } else {
            this.stack = null;
        }
    }
}

// End Event.java
