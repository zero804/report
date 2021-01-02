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
// Copyright (C) 2009-2010 Pentaho
// All Rights Reserved.
*/
package mondrian3.util;

import java.util.ArrayList;
import java.util.EmptyStackException;

/**
 * Stack implementation based on {@link java.util.ArrayList}.
 *
 * <p>More efficient than {@link java.util.Stack}, which extends
 * {@link java.util.Vector} and is
 * therefore synchronized whether you like it or not.
 *
 * @param <E> Element type
 *
 * @author jhyde
 */
public class ArrayStack<E> extends ArrayList<E> {
    /**
     * Default constructor.
     */
    public ArrayStack() {
        super();
    }

    /**
     * Copy Constructor
     * @param toCopy Instance of {@link ArrayStack} to copy.
     */
    public ArrayStack(ArrayStack<E> toCopy) {
        super();
        this.addAll(toCopy);
    }

    /**
     * Analogous to {@link java.util.Stack#push}.
     */
    public E push(E item) {
        add(item);
        return item;
    }

    /**
     * Analogous to {@link java.util.Stack#pop}.
     */
    public E pop() {
        int len = size();
        E obj = peek();
        remove(len - 1);
        return obj;
    }

    /**
     * Analogous to {@link java.util.Stack#peek}.
     */
    public E peek() {
        int len = size();
        if (len <= 0) {
            throw new EmptyStackException();
        }
        return get(len - 1);
    }
}

// End ArrayStack.java
