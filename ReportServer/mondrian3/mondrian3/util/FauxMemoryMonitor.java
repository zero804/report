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
// Copyright (C) 2007-2007 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.util;

/**
 * The <code>FauxMemoryMonitor</code> implements the <code>MemoryMonitor</code>
 * interface but does nothing: all methods are empty.
 *
 * @author <a>Richard M. Emberson</a>
 * @since Feb 03 2007
 */
public class FauxMemoryMonitor implements MemoryMonitor {
    FauxMemoryMonitor() {
    }

    public boolean addListener(Listener listener, int thresholdPercentage) {
        return true;
    }

    public boolean addListener(final Listener listener) {
        return true;
    }

    public void updateListenerThreshold(Listener listener, int percentage) {
        // empty
    }

    public boolean removeListener(Listener listener) {
        return true;
    }
    public void removeAllListener() {
        // empty
    }
    public long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }
    public long getUsedMemory() {
        return Runtime.getRuntime().freeMemory();
    }
}

// End FauxMemoryMonitor.java
