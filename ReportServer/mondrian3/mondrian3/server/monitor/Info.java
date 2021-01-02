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

import mondrian3.util.BeanMap;

/**
 * Abstract base class for objects returned from polling the
 * {@link mondrian3.server.monitor.Monitor} for server state.
 */
abstract class Info {

    /**
     * A printout of the stack trace which represents the code stack
     * when the event was created. Useful for debugging purposes and
     * identifying orphaned connections and statements.
     */
    public final String stack;

    protected Info(String stack) {
        this.stack = stack;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + new BeanMap(this);
    }
}

// End Info.java
