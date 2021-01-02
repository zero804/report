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
package mondrian3.calc;

import mondrian3.olap.Member;

import java.util.List;

/**
 * Extension to {@link Iterable} that returns a {@link TupleIterator}.
 *
 * <p>If efficiency is important, call {@link #tupleCursor()} rather than
 * {@link #tupleIterator()} if possible. Because {@link TupleCursor} is a
 * simpler API to implement than {@link TupleIterator}, in some cases the
 * implementation may be more efficient.
 *
 * @author jhyde
 */
public interface TupleIterable extends Iterable<List<Member>> {
    /**
     * Creates an iterator over the contents of this iterable.
     *
     * <p>Always has the same effect as calling {@link #iterator()}.
     *
     * @see #tupleCursor()
     *
     * @return cursor over the tuples returned by this iterable
     */
    TupleIterator tupleIterator();

    /**
     * Creates a cursor over the contents of this iterable.
     *
     * <p>The contents of the cursor will always be the same as those returned
     * by {@link #tupleIterator()}. Because {@link TupleCursor} is a simpler API
     * to implement than {@link TupleIterator}, in some cases the implementation
     * may be more efficient.
     *
     * @return cursor over the tuples returned by this iterable
     */
    TupleCursor tupleCursor();

    /**
     * Returns the number of members in each tuple.
     *
     * @return The number of members in each tuple
     */
    int getArity();

    /**
     * Returns an iterable over the members at a given column.
     *
     * <p>The iteratble returns an interator that is modifiable if and only if
     * this TupleIterable is modifiable.
     *
     * <p>If this {@code TupleIterable} happens to be a {@link TupleList},
     * the method is overridden to return a {@link List}&lt;{@link Member}&gt;.
     *
     * @param column Ordinal of the member in each tuple to project
     * @return Iterable that returns an iterator over members
     * @throws IllegalArgumentException if column is not less than arity
     */
    Iterable<Member> slice(int column);
}

// End TupleIterable.java
