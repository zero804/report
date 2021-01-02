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

import mondrian3.calc.TupleList;
import mondrian3.olap.Member;

import java.util.*;

/**
 * Abstract implementation of a {@link mondrian3.calc.TupleList} that stores
 * tuples in end-to-end format.
 *
 * <p>For example, if the arity is 3, the tuples {(A1, B1, C1), (A1, B2, C2)}
 * will be stored as {A1, B1, C1, A2, B2, C2}. This is memory-efficient (only
 * one array, compared to 3 arrays or one array per tuple in other
 * representations), has good locality of reference, and typical operations
 * require few indirections.
 *
 * <p>Concrete subclasses can store the data in various backing lists.
 *
 * @author jhyde
*/
abstract class AbstractEndToEndTupleList extends AbstractTupleList {

    AbstractEndToEndTupleList(int arity) {
        super(arity);
    }

    public TupleList project(final int[] destIndices) {
        final List<Member> backingList = backingList();
        final int originalArity = getArity();
        return new DelegatingTupleList(
            destIndices.length,
            new AbstractList<List<Member>>() {
                public List<Member> get(int index) {
                    final int n = index * originalArity;
                    return new AbstractList<Member>() {
                        public Member get(int index) {
                            return backingList.get(n + destIndices[index]);
                        }

                        public int size() {
                            return destIndices.length;
                        }
                    };
                }

                public int size() {
                    return backingList.size() / originalArity;
                }
            });
    }

    protected abstract List<Member> backingList();

    @Override
    public List<Member> set(int index, List<Member> element) {
        assert mutable;
        final List<Member> list = backingList();
        for (int i = 0, startIndex = index * arity; i < arity; i++) {
            list.set(startIndex + i, element.get(i));
        }
        return null; // not compliant with List contract
    }

    @Override
    public boolean addAll(Collection<? extends List<Member>> c) {
        return addAll(size(), c);
    }

    @Override
    public boolean addAll(int i, Collection<? extends List<Member>> c) {
        assert mutable;
        if (c instanceof AbstractEndToEndTupleList) {
            return backingList().addAll(
                i * arity,
                ((AbstractEndToEndTupleList) c).backingList());
        }
        return super.addAll(i, c);
    }

    @Override
    public TupleList subList(int fromIndex, int toIndex) {
        return new ListTupleList(
            arity,
            backingList().subList(fromIndex * arity, toIndex * arity));
    }

    public TupleList withPositionCallback(
        final PositionCallback positionCallback)
    {
        assert !(backingList() instanceof PositionSensingList);
        return new ListTupleList(
            arity, new PositionSensingList(positionCallback));
    }

    private class PositionSensingList extends AbstractList<Member> {
        private final PositionCallback positionCallback;
        private final List<Member> backingList = backingList();

        public PositionSensingList(
            PositionCallback positionCallback)
        {
            this.positionCallback = positionCallback;
        }

        @Override
        public Member get(int index) {
            positionCallback.onPosition(index / arity);
            return backingList.get(index);
        }

        @Override
        public int size() {
            return backingList.size();
        }

        @Override
        public Member set(int index, Member element) {
            assert mutable;
            positionCallback.onPosition(index / arity);
            return backingList.set(index, element);
        }

        @Override
        public void add(int index, Member element) {
            assert mutable;
            positionCallback.onPosition(index);
            backingList.add(index, element);
        }

        @Override
        public Member remove(int index) {
            assert mutable;
            positionCallback.onPosition(index);
            return backingList.remove(index);
        }
    }
}

// End AbstractEndToEndTupleList.java
