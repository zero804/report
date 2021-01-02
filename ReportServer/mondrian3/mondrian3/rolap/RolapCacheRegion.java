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
// Copyright (C) 2007-2012 Pentaho
// All Rights Reserved.
*/
package mondrian3.rolap;

import java.util.*;

/**
 * A <code>RolapCacheRegion</code> represents a region of multidimensional space
 * in the cache.
 *
 * <p>The region is represented in terms of the columns of a given
 * {@link mondrian3.rolap.RolapStar}, and constraints on those columns.
 *
 * <p>Compare with {@link mondrian3.olap.CacheControl.CellRegion}: a
 * <code>CellRegion</code> is in terms of {@link mondrian3.olap.Member} objects
 * (logical); whereas a <code>RolapCacheRegion</code> is in terms of columns
 * (physical).
 */
public class RolapCacheRegion {
    private final BitKey bitKey;
    private final Map<Integer, StarColumnPredicate> columnPredicates =
        new HashMap<Integer, StarColumnPredicate>();
    private final Map<String, StarColumnPredicate> columnPredicatesByName =
        new HashMap<String, StarColumnPredicate>();
    private Map<List<RolapStar.Column>, StarPredicate> predicates =
        new HashMap<List<RolapStar.Column>, StarPredicate>();

    public RolapCacheRegion(
        RolapStar star,
        List<RolapStar.Measure> starMeasureList)
    {
        bitKey = BitKey.Factory.makeBitKey(star.getColumnCount());
        for (RolapStar.Measure measure : starMeasureList) {
            bitKey.set(measure.getBitPosition());
        }
    }

    public BitKey getConstrainedColumnsBitKey() {
        return bitKey;
    }

    /**
     * Adds a predicate which applies to a single column.
     *
     * @param column Constrained column
     * @param predicate Predicate
     */
    public void addPredicate(
        RolapStar.Column column,
        StarColumnPredicate predicate)
    {
        int bitPosition = column.getBitPosition();
        assert !bitKey.get(bitPosition);
        bitKey.set(bitPosition);
        columnPredicates.put(bitPosition, predicate);
        columnPredicatesByName.put(
            column.getExpression().getGenericExpression(),
            predicate);
    }

    /**
     * Returns the predicate associated with the
     * <code>columnOrdinal</code>th column.
     *
     * @param columnOrdinal Column ordinal
     * @return Predicate, or null if not constrained
     */
    public StarColumnPredicate getPredicate(int columnOrdinal) {
        return columnPredicates.get(columnOrdinal);
    }

    /**
     * Returns the predicate associated with the
     * <code>columnName</code>, where column name is
     * the generic SQL expression in the form of:
     *
     * <p>&nbsp;&nbsp;&nbsp;&nbsp;table.column
     *
     * @param columnName Column name
     * @return Predicate, or null if not constrained
     */
    public StarColumnPredicate getPredicate(String columnName) {
        return columnPredicatesByName.get(columnName);
    }

    /**
     * Adds a predicate which applies to multiple columns.
     *
     * <p>The typical example of a multi-column predicate is a member
     * constraint. For example, the constraint "m between 1997.Q3 and
     * 1998.Q2" translates into "year = 1997 and quarter >= Q3 or year =
     * 1998 and quarter <= Q2".
     *
     * @param predicate Predicate
     */
    public void addPredicate(StarPredicate predicate)
    {
        final List<RolapStar.Column> columnList =
            predicate.getConstrainedColumnList();
        predicates.put(
            new ArrayList<RolapStar.Column>(columnList),
            predicate);
        for (RolapStar.Column column : columnList) {
            bitKey.set(column.getBitPosition());
        }
    }

    /**
     * Returns a collection of all multi-column predicates.
     *
     * @return Collection of all multi-column constraints
     */
    public Collection<StarPredicate> getPredicates() {
        return predicates.values();
    }

    /**
     * Returns the list of all column predicates.
     */
    public Collection<StarColumnPredicate> getColumnPredicates() {
        return columnPredicates.values();
    }
}

// End RolapCacheRegion.java
