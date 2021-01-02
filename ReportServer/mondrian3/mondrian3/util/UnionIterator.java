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
// Copyright (C) 2008-2009 Pentaho
// All Rights Reserved.
*/
package mondrian3.util;

import mondrian3.olap.Util;

import java.util.*;

/**
 * Iterator over union of several {@link Iterable} collections.
 *
 * <p>Try, for instance, using the {@link #over} helper method:</p>
 *
 * <blockquote>
 * <code>
 * List&lt;String&gt; names;<br/>
 * List&lt;String&gt; addresses;<br/>
 * for (Sstring s : UnionIterator.over(names, addresses)) {
 * &nbsp;&nbsp;print(s);<br/>
 * }
 * </code>
 * </blockquote>
 *
 * @author jhyde
 * @since Apr 28, 2008
 */
public class UnionIterator<T> implements Iterator<T> {
    private final Iterator<Iterable<? extends T>> iterableIterator;
    private Iterator<? extends T> iterator;

    /**
     * Creates a UnionIterator.
     *
     * @param iterables Array of iterables
     */
    public UnionIterator(Iterable<? extends T>... iterables) {
        List<Iterable<? extends T>> list;
        if (Util.Retrowoven) {
            // Retroweaver has its own version of Iterable, but
            // Collection doesn't implement it. Solve the problem by
            // creating an explicit Iterable wrapper.
            list = new ArrayList<Iterable<? extends T>>(iterables.length);
            for (Iterable<? extends T> iterable : iterables) {
                //noinspection unchecked
                list.add(new MyIterable(iterable));
            }
        } else {
            list = Arrays.asList(iterables);
        }
        this.iterableIterator = list.iterator();
        moveToNext();
    }

    /**
     * Creates a UnionIterator over a list of collections.
     *
     * @param iterables Array of collections
     */
    public UnionIterator(Collection<? extends T>... iterables) {
        List<Iterable<? extends T>> list =
            new ArrayList<Iterable<? extends T>>(iterables.length);
        for (Iterable<? extends T> iterable : iterables) {
            //noinspection unchecked
            list.add(new MyIterable(iterable));
        }
        this.iterableIterator = list.iterator();
        moveToNext();
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public T next() {
        final T t = iterator.next();
        if (!iterator.hasNext()) {
            moveToNext();
        }
        return t;
    }

    /**
     * Moves to the next iterator that has at least one element.
     * Called after finishing an iterator, or at the start.
     */
    private void moveToNext() {
        do {
            if (iterableIterator.hasNext()) {
                iterator = iterableIterator.next().iterator();
            } else {
                iterator = Collections.<T>emptyList().iterator();
                break;
            }
        } while (!iterator.hasNext());
    }

    public void remove() {
        iterator.remove();
    }

    /**
     * Returns the union of a list of iterables.
     *
     * <p>You can use it like this:
     * <blockquote><pre>
     * Iterable&lt;String&gt; iter1;
     * Iterable&lt;String&gt; iter2;
     * for (String s : union(iter1, iter2)) {
     *   print(s);
     * }</pre></blockquote>
     *
     * @param iterables Array of one or more iterables
     * @return iterable over the union of the iterables
     */
    public static <T> Iterable<T> over(
        final Iterable<? extends T>... iterables)
    {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new UnionIterator<T>(iterables);
            }
        };
    }

    /**
     * Returns the union of a list of collections.
     *
     * <p>This method exists for code that will be retrowoven to run on JDK 1.4.
     * Retroweaver has its own version of the {@link Iterable} interface, which
     * is problematic since the {@link java.util.Collection} classes don't
     * implement it. This method solves some of these problems by working in
     * terms of collections; retroweaver deals with these correctly.
     *
     * @see #over(Iterable[])
     *
     * @param collections Array of one or more collections
     * @return iterable over the union of the collections
     */
    public static <T> Iterable<T> over(
        final Collection<? extends T>... collections)
    {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new UnionIterator<T>(collections);
            }
        };
    }

    private static class MyIterable<T> implements Iterable {
        private final Iterable<T> iterable;

        /**
         * Creates a MyIterable.
         *
         * @param iterable Iterable
         */
        public MyIterable(Iterable<T> iterable) {
            this.iterable = iterable;
        }

        public Iterator<T> iterator() {
            return iterable.iterator();
        }
    }
}

// End UnionIterator.java
