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
// Copyright (C) 2010-2013 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap.agg;

import mondrian3.rolap.CellKey;
import mondrian3.rolap.SqlStatement;
import mondrian3.spi.SegmentBody;
import mondrian3.util.Pair;

import java.util.List;
import java.util.SortedSet;

/**
 * Implementation of {@link mondrian3.rolap.agg.DenseSegmentDataset} that stores
 * values of type {@link Object}.
 *
 * <p>The storage requirements are as follows. Table requires 1 word per
 * cell.</p>
 *
 * @author jhyde
 * @since 21 March, 2002
 */
class DenseObjectSegmentDataset extends DenseSegmentDataset {
    final Object[] values; // length == m[0] * ... * m[axes.length-1]

    /**
     * Creates a DenseSegmentDataset.
     *
     * @param axes Segment axes, containing actual column values
     * @param size Number of coordinates
     */
    DenseObjectSegmentDataset(SegmentAxis[] axes, int size) {
        this(axes, new Object[size]);
    }

    /**
     * Creates and populates a DenseSegmentDataset. The data set is not copied.
     *
     * @param axes Axes
     * @param values Data set
     */
    DenseObjectSegmentDataset(SegmentAxis[] axes, Object[] values) {
        super(axes);
        this.values = values;
    }

    public Object getObject(CellKey key) {
        if (values.length == 0) {
            // No values means they are all null.
            // We can't call isNull because we risk going into a SOE. Besides,
            // this is a tight loop and we can skip over one VFC.
            return null;
        }
        int offset = key.getOffset(axisMultipliers);
        return values[offset];
    }

    public boolean isNull(CellKey pos) {
        if (values.length == 0) {
            // No values means they are all null.
            return true;
        }
        return getObject(pos) != null;
    }

    public boolean exists(CellKey pos) {
        return getObject(pos) != null;
    }

    public void populateFrom(int[] pos, SegmentDataset data, CellKey key) {
        values[getOffset(pos)] = data.getObject(key);
    }

    public void populateFrom(
        int[] pos, SegmentLoader.RowList rowList, int column)
    {
        int offset = getOffset(pos);
        values[offset] = rowList.getObject(column);
    }

    public SqlStatement.Type getType() {
        return SqlStatement.Type.OBJECT;
    }

    public void put(CellKey key, Object value) {
        int offset = key.getOffset(axisMultipliers);
        values[offset] = value;
    }

    protected Object getObject(int i) {
        return values[i];
    }

    protected int getSize() {
        return values.length;
    }

    public SegmentBody createSegmentBody(
        List<Pair<SortedSet<Comparable>, Boolean>> axes)
    {
        return new DenseObjectSegmentBody(
            values,
            axes);
    }
}

// End DenseObjectSegmentDataset.java
