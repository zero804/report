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
// Copyright (C) 2007-2009 Pentaho
// All Rights Reserved.
*/
package mondrian3.calc;

import java.util.Arrays;
import java.util.List;

/**
 * Enumeration of ways that a compiled expression can return its result to
 * its caller.
 *
 * @author jhyde
 */
public enum ResultStyle {
    /**
     * Indicates that caller will accept any applicable style.
     */
    ANY,

    /**
     * Indicates that the expression returns its result as a list which may
     * safely be modified by the caller.
     */
    MUTABLE_LIST,

    /**
     * Indicates that the expression returns its result as a list which must
     * not be modified by the caller.
     */
    LIST,

    /**
     * Indicates that the expression returns its result as an Iterable
     * which must not be modified by the caller.
     */
    ITERABLE,

    /**
     * Indicates that the expression results its result as an immutable
     * value. This is typical for expressions which return string, datetime and
     * numeric values.
     */
    VALUE,

    /**
     * Indicates that the expression results its result as an immutable
     * value which will never be null. This is typical for expressions which
     * return string, datetime and numeric values.
     */
    VALUE_NOT_NULL;

    // ---------------------------------------------------------------
    // There follow a set of convenience constants for commonly-used
    // collections of result styles.

    public static final List<ResultStyle> ANY_LIST =
        Arrays.asList(
            ANY);

    public static final List<ResultStyle> ITERABLE_ONLY =
        Arrays.asList(
            ITERABLE);

    public static final List<ResultStyle> MUTABLELIST_ONLY =
        Arrays.asList(
            MUTABLE_LIST);

    public static final List<ResultStyle> LIST_ONLY =
        Arrays.asList(
            LIST);

    public static final List<ResultStyle> ITERABLE_ANY =
        Arrays.asList(
            ITERABLE,
            ANY);

    public static final List<ResultStyle> ITERABLE_LIST =
        Arrays.asList(
            ITERABLE,
            LIST);

    public static final List<ResultStyle> ITERABLE_MUTABLELIST =
        Arrays.asList(
            ITERABLE,
            MUTABLE_LIST);

    public static final List<ResultStyle> ITERABLE_LIST_MUTABLELIST =
        Arrays.asList(
            ITERABLE,
            LIST,
            MUTABLE_LIST);

    public static final List<ResultStyle> LIST_MUTABLELIST =
        Arrays.asList(
            LIST,
            MUTABLE_LIST);

    public static final List<ResultStyle> MUTABLELIST_LIST =
        Arrays.asList(
            MUTABLE_LIST,
            LIST);

    public static final List<ResultStyle> ITERABLE_LIST_MUTABLELIST_ANY =
        Arrays.asList(
            ITERABLE,
            LIST,
            MUTABLE_LIST,
            ANY);

    public static final List<ResultStyle> ITERABLE_MUTABLELIST_LIST =
        Arrays.asList(
            ITERABLE,
            MUTABLE_LIST,
            LIST);

    public static final List<ResultStyle> ANY_ONLY =
        Arrays.asList(
            ANY);
}

// End ResultStyle.java
