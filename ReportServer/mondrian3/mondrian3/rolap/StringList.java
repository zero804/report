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
// Copyright (C) 2001-2005 Julian Hyde
// Copyright (C) 2005-2006 Pentaho and others
// All Rights Reserved.
//
// jhyde, 29 December, 2001
*/
package mondrian3.rolap;

import mondrian3.olap.Util;

/**
 * <code>StringList</code> makes it easy to build up a comma-separated string.
 *
 * @author jhyde
 * @since 29 December, 2001
 */
class StringList
{
    private final StringBuilder buf;
    private final String first, mid, last;
    private int count;

    StringList(String first, String mid)
    {
        this.buf = new StringBuilder(first);
        this.count = 0;
        this.first = first;
        this.mid = mid;
        this.last = "";
    }
    StringList(String first)
    {
        this(first, ", ");
    }
    int getCount()
    {
        return count;
    }
    boolean isEmpty()
    {
        return count == 0;
    }
    /** Creates a new item. */
    void newItem(String s)
    {
        if (count++ > 0) {
            buf.append(mid);
        }
        buf.append(s);
    }
    /** Appends to an existing item. */
    void append(String s)
    {
        Util.assertTrue(count > 0);
        buf.append(s);
    }
    // override Object
    public String toString()
    {
        buf.append(last);
        return buf.toString();
    }
};


// End StringList.java
