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
// Copyright (C) 2004-2005 Julian Hyde
// Copyright (C) 2005-2011 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap.agg;

import mondrian3.rolap.*;

import java.util.List;

/**
 * <p>A collection
 * of {@link mondrian3.rolap.agg.Segment}s that can be represented
 * as a GROUP BY GROUPING SET in a SQL query.</p>
 *
 * @author Thiyagu
 * @since 05-Jun-2007
 */
public class GroupingSet {
    private final List<Segment> segments;
    final Segment segment0;
    private final BitKey levelBitKey;
    private final BitKey measureBitKey;
    private final StarColumnPredicate[] predicates;
    private final SegmentAxis[] axes;
    private final RolapStar.Column[] columns;

    /**
     * Creates a GroupingSet.
     *
     * @param segments Constituent segments
     * @param levelBitKey Levels
     * @param measureBitKey Measures
     * @param predicates Predicates
     * @param columns Columns
     */
    public GroupingSet(
        List<Segment> segments,
        BitKey levelBitKey,
        BitKey measureBitKey,
        StarColumnPredicate[] predicates,
        RolapStar.Column[] columns)
    {
        this.segments = segments;
        this.segment0 = segments.get(0);
        this.levelBitKey = levelBitKey;
        this.measureBitKey = measureBitKey;
        this.predicates = predicates;
        this.axes = new SegmentAxis[predicates.length];
        this.columns = columns;
    }


    public List<Segment> getSegments() {
        return segments;
    }

    public BitKey getLevelBitKey() {
        return levelBitKey;
    }

    public BitKey getMeasureBitKey() {
        return measureBitKey;
    }

    public SegmentAxis[] getAxes() {
        return axes;
    }

    public StarColumnPredicate[] getPredicates() {
        return predicates;
    }

    public RolapStar.Column[] getColumns() {
        return columns;
    }

    /**
     * Sets all the segments which are in loading state as failed
     */
    public void setSegmentsFailed() {
        for (Segment segment : segments) {
            // TODO: segment.setFailIfStillLoading();
        }
    }
}

// End GroupingSet.java
