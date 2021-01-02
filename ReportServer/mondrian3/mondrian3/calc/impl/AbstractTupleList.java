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
import mondrian3.olap.Evaluator;
import mondrian3.olap.Member;

import java.util.*;

/**
 * Abstract implementation of {@link TupleList}.
 *
 * @author jhyde
 */
public abstract class AbstractTupleList
    extends AbstractList<List<Member>>
    implements RandomAccess, Cloneable, TupleList
{
    protected final int arity;
    protected boolean mutable = true;

    public AbstractTupleList(int arity) {
        this.arity = arity;
    }

    public int getArity() {
        return arity;
    }

    protected abstract TupleIterator tupleIteratorInternal();

    @Override
    public abstract TupleList subList(int fromIndex, int toIndex);

    public TupleList fix() {
        return new DelegatingTupleList(
            arity,
            new ArrayList<List<Member>>(this));
    }

    @Override
    public final Iterator<List<Member>> iterator() {
        return tupleIteratorInternal();
    }

    public final TupleIterator tupleIterator() {
        return tupleIteratorInternal();
    }

    /**
     * Creates a {@link TupleCursor} over this list.
     *
     * <p>Any implementation of {@link TupleList} must implement all three
     * methods {@link #iterator()}, {@link #tupleIterator()} and
     * {@code tupleCursor}. The default implementation returns the same
     * for all three, but a derived classes can override this method to create a
     * more efficient implementation that implements cursor but not iterator.
     *
     * @return A cursor over this list
     */
    public TupleCursor tupleCursor() {
        return tupleIteratorInternal();
    }

    public void addCurrent(TupleCursor tupleIter) {
        add(tupleIter.current());
    }

    public Member get(int slice, int index) {
        return get(index).get(slice);
    }

    /**
     * Implementation of {@link mondrian3.calc.TupleIterator} for
     * {@link ArrayTupleList}.
     * Based upon AbstractList.Itr, but with concurrent modification checking
     * removed.
     */
    protected class AbstractTupleListIterator
        implements TupleIterator
    {
        /**
         * Index of element to be returned by subsequent call to next.
         */
        int cursor = 0;

        /**
         * Index of element returned by most recent call to next or
         * previous.  Reset to -1 if this element is deleted by a call
         * to remove.
         */
        int lastRet = -1;

        public boolean hasNext() {
            return cursor != size();
        }

        public List<Member> next() {
            try {
                List<Member> next = get(cursor);
                lastRet = cursor++;
                return next;
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }

        public boolean forward() {
            if (cursor == size()) {
                return false;
            }
            lastRet = cursor++;
            return true;
        }

        public List<Member> current() {
            return get(lastRet);
        }

        public void currentToArray(Member[] members, int offset) {
            final List<Member> current = current();
            if (offset == 0) {
                current.toArray(members);
            } else {
                //noinspection SuspiciousSystemArraycopy
                System.arraycopy(current.toArray(), 0, members, offset, arity);
            }
        }

        public int getArity() {
            return AbstractTupleList.this.getArity();
        }

        public void remove() {
            assert mutable;
            if (lastRet == -1) {
                throw new IllegalStateException();
            }
            try {
                AbstractTupleList.this.remove(lastRet);
                if (lastRet < cursor) {
                    cursor--;
                }
                lastRet = -1;
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }

        public void setContext(Evaluator evaluator) {
            evaluator.setContext(current());
        }

        public Member member(int column) {
            return get(lastRet).get(column);
        }
    }
}

// End AbstractTupleList.java
