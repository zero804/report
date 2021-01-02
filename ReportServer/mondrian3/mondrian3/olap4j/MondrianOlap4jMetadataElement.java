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
// Copyright (C) 2012-2012 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap4j;

import mondrian3.olap.OlapElement;

import org.olap4j.OlapWrapper;

import java.sql.SQLException;

/**
 * Basic features of metadata elements in Mondrian's olap4j driver.
 *
 * @author jhyde
 */
abstract class MondrianOlap4jMetadataElement
    implements OlapWrapper
{
    /**
     * Helper for {@link #unwrap(Class)} and {@link #isWrapperFor(Class)}.
     *
     * @param iface Desired interface
     * @param <T> Type
     * @return This as desired interface, or null
     */
    protected <T> T unwrapImpl(Class<T> iface) {
        if (iface.isInstance(this)) {
            return iface.cast(this);
        }
        final OlapElement element = getOlapElement();
        if (element != null && iface.isInstance(element)) {
            return iface.cast(element);
        } else {
            return null;
        }
    }

    /**
     * Returns the Mondrian metadata element inside this wrapper, or null if
     * there is none.
     *
     * @return The Mondrian metadata element, if any
     */
    protected abstract OlapElement getOlapElement();

    public <T> T unwrap(Class<T> iface) throws SQLException {
        final T t = unwrapImpl(iface);
        if (t == null) {
            throw new SQLException("not a wrapper for " + iface);
        }
        return t;
    }

    public boolean isWrapperFor(Class<?> iface) {
        return unwrapImpl(iface) != null;
    }
}

// End MondrianOlap4jMetadataElement.java
