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
// Copyright (C) 2010-2012 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap.agg;

import mondrian3.rolap.CellKey;
import mondrian3.rolap.SqlStatement;
import mondrian3.spi.SegmentBody;
import mondrian3.util.Pair;

import java.util.*;

/**
 * A <code>SparseSegmentDataset</code> is a means of storing segment values
 * which is suitable when few of the combinations of keys have a value present.
 *
 * <p>The storage requirements are as follows. Key is 1 word for each
 * dimension. Hashtable entry is 3 words. Value is 1 word. Total space is (4 +
 * d) * v. (May also need hash table to ensure that values are only stored
 * once.)</p>
 *
 * <p>NOTE: This class is not synchronized.</p>
 *
 * @author jhyde
 * @since 21 March, 2002
 */
class SparseSegmentDataset implements SegmentDataset {
    private final Map<CellKey, Object> values;

    /**
     * Creates an empty SparseSegmentDataset.
     */
    SparseSegmentDataset() {
        this(new HashMap<CellKey, Object>());
    }

    /**
     * Creates a SparseSegmentDataset with a given value map. The map is not
     * copied; a reference to the map is retained inside the dataset, and
     * therefore the contents of the dataset will change if the map is modified.
     *
     * @param values Value map
     */
    SparseSegmentDataset(Map<CellKey, Object> values) {
        this.values = values;
    }

    public Object getObject(CellKey pos) {
        return values.get(pos);
    }

    public boolean isNull(CellKey pos) {
        // cf exists -- calls values.containsKey
        return values.get(pos) == null;
    }

    public int getInt(CellKey pos) {
        throw new UnsupportedOperationException();
    }

    public double getDouble(CellKey pos) {
        throw new UnsupportedOperationException();
    }

    public boolean exists(CellKey pos) {
        return values.containsKey(pos);
    }

    public void put(CellKey key, Object value) {
        values.put(key, value);
    }

    public Iterator<Map.Entry<CellKey, Object>> iterator() {
        return values.entrySet().iterator();
    }

    public double getBytes() {
        // assume a slot, key, and value are each 4 bytes
        return values.size() * 12;
    }

    public void populateFrom(int[] pos, SegmentDataset data, CellKey key) {
        values.put(CellKey.Generator.newCellKey(pos), data.getObject(key));
    }

    public void populateFrom(
        int[] pos, SegmentLoader.RowList rowList, int column)
    {
        final Object o = rowList.getObject(column);
        put(CellKey.Generator.newCellKey(pos), o);
    }

    public SqlStatement.Type getType() {
        return SqlStatement.Type.OBJECT;
    }

    public SegmentBody createSegmentBody(
        List<Pair<SortedSet<Comparable>, Boolean>> axes)
    {
        return new SparseSegmentBody(
            values,
            axes);
    }
}

// End SparseSegmentDataset.java
