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
package mondrian3.calc.impl;

import mondrian3.calc.*;
import mondrian3.olap.Member;

import java.util.Iterator;
import java.util.List;

/**
* Abstract implementation of {@link mondrian3.calc.TupleIterable}.
 *
 * <p>Derived classes need to implement only {@link #tupleCursor()},
 * and this implementation will implement {@link #tupleIterator()} and
 * {@link #iterator()} by creating a wrapper around that cursor. (The cursor
 * interface is easier to implement efficiently than the wider iterator
 * interface.) If you have a more efficient implementation of cursor, override
 * the {@code tupleIterator} method.
 *
 * @author jhyde
 */
public abstract class AbstractTupleIterable
    implements TupleIterable
{
    protected final int arity;

    /**
     * Creates an AbstractTupleIterable.
     *
     * @param arity Arity (number of members per tuple)
     */
    public AbstractTupleIterable(int arity) {
        this.arity = arity;
    }

    public int getArity() {
        return arity;
    }

    public Iterable<Member> slice(int column) {
        return TupleCollections.slice(this, column);
    }

    public final Iterator<List<Member>> iterator() {
        return tupleIterator();
    }

    public TupleIterator tupleIterator() {
        return TupleCollections.iterator(tupleCursor());
    }
}

// End AbstractTupleIterable.java
