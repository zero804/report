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

import mondrian3.calc.TupleIterator;
import mondrian3.calc.TupleList;
import mondrian3.olap.Member;
import mondrian3.olap.Util;

import java.util.*;

/**
 * Implementation of {@link mondrian3.calc.TupleList} that stores tuples
 * end-to-end in a backing list.
 *
 * <pre>
 * l1: {A,B,C},{D,E,F}
 * l2: {a,b},{c,d},{e,f}
 *
 * externally looks like:
 *  [] <- {A,B,C,a,b}
 *  [] <- {A,B,C,c,d}
 *  [] <- {A,B,C,e,f}
 *  [] <- {D,E,F,a,b}
 *  [] <- {D,E,F,c,d}
 *  [] <- {D,E,F,e,d}
 *
 * but internally is:
 *  A,B,C,a,b,A,B,C,c,d,A,B,C,e,f,D,E,F,a,b,D,E,F,c,d,D,E,F,e,d
 * </pre>
 *
 * @author jhyde
 */
public class ListTupleList extends AbstractEndToEndTupleList
{
    private final List<Member> list;

    /**
     * Creates a ListTupleList.
     *
     * @param arity Arity
     * @param list Backing list
     */
    public ListTupleList(int arity, List<Member> list) {
        super(arity);
        this.list = list;
    }

    protected List<Member> backingList() {
        return list;
    }

    public Member get(int slice, int index) {
        return list.get(index * arity + slice);
    }

    public List<Member> get(int index) {
        final int startIndex = index * arity;
        final List<Member> list1 =
            new AbstractList<Member>() {
                public Member get(int index) {
                    return list.get(startIndex + index);
                }

                public int size() {
                    return arity;
                }
            };
        if (mutable) {
            return Util.flatList(list1);
        }
        return list1;
    }

    public void add(int index, List<Member> element) {
        assert mutable;
        list.addAll(index * arity, element);
    }

    public void addTuple(Member... members) {
        assert mutable;
        list.addAll(Arrays.asList(members));
    }

    @Override
    public void clear() {
        assert mutable;
        list.clear();
    }

    @Override
    public List<Member> remove(int index) {
        assert mutable;
        for (int i = 0, n = index * arity; i < arity; i++) {
            list.remove(n);
        }
        return null; // breach of List contract
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        assert mutable;
        list.subList(fromIndex * arity, toIndex * arity).clear();
    }

    public int size() {
        return list.size() / arity;
    }

    public List<Member> slice(final int column) {
        if (column < 0 || column >= arity) {
            throw new IllegalArgumentException();
        }
        return new AbstractList<Member>() {
            @Override
            public Member get(int index) {
                return ListTupleList.this.get(column, index);
            }

            @Override
            public int size() {
                return ListTupleList.this.size();
            }
        };
    }

    public TupleList cloneList(int capacity) {
        return new ListTupleList(
            arity,
            capacity < 0
                ? new ArrayList<Member>(list)
                : new ArrayList<Member>(capacity * arity));
    }

    public TupleIterator tupleIteratorInternal() {
        return new AbstractTupleListIterator();
    }
}

// End ListTupleList.java
