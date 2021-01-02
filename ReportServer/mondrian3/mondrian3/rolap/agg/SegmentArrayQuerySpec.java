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
// Copyright (C) 2005-2005 Julian Hyde
// Copyright (C) 2005-2011 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap.agg;

import mondrian3.rolap.*;
import mondrian3.rolap.sql.SqlQuery;

import java.util.*;

/**
 * Provides the information necessary to generate a SQL statement to
 * retrieve a list of segments.
 *
 * @author jhyde
 * @author Richard M. Emberson
 */
class SegmentArrayQuerySpec extends AbstractQuerySpec {
    private final List<Segment> segments;
    private final Segment segment0;
    private final GroupingSetsList groupingSetsList;

    /*
     * Compound member predicates.
     * Each list constrains one dimension.
     */
    private final List<StarPredicate> compoundPredicateList;

    /**
     * Creates a SegmentArrayQuerySpec.
     *
     * @param groupingSetsList Collection of grouping sets
     * @param compoundPredicateList list of predicates representing the
     * compound member constraints
     */
    SegmentArrayQuerySpec(
        GroupingSetsList groupingSetsList,
        List<StarPredicate> compoundPredicateList)
    {
        super(groupingSetsList.getStar(), false);
        this.segments = groupingSetsList.getDefaultSegments();
        this.segment0 = segments.get(0);
        this.groupingSetsList = groupingSetsList;
        this.compoundPredicateList = compoundPredicateList;
        assert isValid(true);
    }

    /**
     * Returns whether this query specification is valid, or throws if invalid
     * and <code>fail</code> is true.
     *
     * @param fail Whether to throw if invalid
     * @return Whether this query specification is valid
     */
    private boolean isValid(boolean fail) {
        assert segments.size() > 0;
        for (Segment segment : segments) {
            int n = segment.predicates.length;
            if (n != segment0.predicates.length) {
                assert !fail;
                return false;
            }
            for (int j = 0; j < segment.predicates.length; j++) {
                // We only require that the two arrays have the same
                // contents, we but happen to know they are the same array,
                // because we constructed them at the same time.
                if (segment.predicates[j] != segment0.predicates[j]) {
                    assert !fail;
                    return false;
                }
            }
        }
        return true;
    }

    public int getMeasureCount() {
        return segments.size();
    }

    public RolapStar.Measure getMeasure(final int i) {
        return segments.get(i).measure;
    }

    public String getMeasureAlias(final int i) {
        return "m" + Integer.toString(i);
    }

    public RolapStar.Column[] getColumns() {
        return segment0.getColumns();
    }

    /**
     * SqlQuery relies on "c" and index. All this should go into SqlQuery!
     *
     * @see mondrian3.rolap.sql.SqlQuery#addOrderBy
     */
    public String getColumnAlias(final int i) {
        return "c" + Integer.toString(i);
    }

    public StarColumnPredicate getColumnPredicate(final int i) {
        return segment0.predicates[i];
    }

    protected List<StarPredicate> getPredicateList() {
        if (compoundPredicateList == null) {
            return super.getPredicateList();
        } else {
            return compoundPredicateList;
        }
    }

    protected void addGroupingFunction(SqlQuery sqlQuery) {
        List<RolapStar.Column> list = groupingSetsList.getRollupColumns();
        for (RolapStar.Column column : list) {
            sqlQuery.addGroupingFunction(column.generateExprString(sqlQuery));
        }
    }

    protected void addGroupingSets(
        SqlQuery sqlQuery,
        Map<String, String> groupingSetsAliases)
    {
        List<RolapStar.Column[]> groupingSetsColumns =
            groupingSetsList.getGroupingSetsColumns();
        for (RolapStar.Column[] groupingSetsColumn : groupingSetsColumns) {
            ArrayList<String> groupingColumnsExpr = new ArrayList<String>();
            for (RolapStar.Column aColumn : groupingSetsColumn) {
                final String columnExpr = aColumn.generateExprString(sqlQuery);
                if (groupingSetsAliases.containsKey(columnExpr)) {
                    groupingColumnsExpr.add(
                        groupingSetsAliases.get(columnExpr));
                } else {
                    groupingColumnsExpr.add(columnExpr);
                }
            }
            sqlQuery.addGroupingSet(groupingColumnsExpr);
        }
    }

    protected boolean isAggregate() {
        return true;
    }
}

// End SegmentArrayQuerySpec.java
