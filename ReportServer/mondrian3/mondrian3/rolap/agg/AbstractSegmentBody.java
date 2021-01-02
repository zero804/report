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
// Copyright (C) 2011-2013 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap.agg;

import mondrian3.rolap.CellKey;
import mondrian3.spi.SegmentBody;
import mondrian3.util.Pair;

import java.util.*;

/**
 * Abstract implementation of a SegmentBody.
 *
 * @author LBoudreau
 */
abstract class AbstractSegmentBody implements SegmentBody {
    private static final long serialVersionUID = -7094121704771005640L;

    protected final SortedSet<Comparable>[] axisValueSets;
    private final boolean[] nullAxisFlags;

    public AbstractSegmentBody(
        List<Pair<SortedSet<Comparable>, Boolean>> axes)
    {
        super();
        //noinspection unchecked
        this.axisValueSets = new SortedSet[axes.size()];
        this.nullAxisFlags = new boolean[axes.size()];
        for (int i = 0; i < axes.size(); i++) {
            Pair<SortedSet<Comparable>, Boolean> pair = axes.get(i);
            axisValueSets[i] = pair.left;
            nullAxisFlags[i] = pair.right;
        }
    }

    public SortedSet<Comparable>[] getAxisValueSets() {
        return axisValueSets;
    }

    public boolean[] getNullAxisFlags() {
        return nullAxisFlags;
    }

    public Map<CellKey, Object> getValueMap() {
        return new AbstractMap<CellKey, Object>() {
            public Set<Entry<CellKey, Object>> entrySet() {
                return new AbstractSet<Entry<CellKey, Object>>() {
                    public Iterator<Entry<CellKey, Object>> iterator() {
                        return new SegmentBodyIterator();
                    }

                    public int size() {
                        return getSize();
                    }
                };
            }
        };
    }

    public Object getValueArray() {
        throw new UnsupportedOperationException(
            "This method is only supported for dense segments");
    }

    public BitSet getNullValueIndicators() {
        throw new UnsupportedOperationException(
            "This method is only supported for dense segments "
            + "of native values");
    }

    protected abstract int getSize();

    protected abstract Object getObject(int i);

    /**
     * Iterator over all (cellkey, value) pairs in this data set.
     */
    private class SegmentBodyIterator
        implements Iterator<Map.Entry<CellKey, Object>>
    {
        private int i = -1;
        private final int[] ordinals;
        private final int size = getSize();
        private boolean hasNext = true;
        private Object next;

        SegmentBodyIterator() {
            ordinals = new int[axisValueSets.length];
            ordinals[ordinals.length - 1] = -1;
            moveToNext();
        }

        public boolean hasNext() {
            return hasNext;
        }

        public Map.Entry<CellKey, Object> next() {
            Pair<CellKey, Object> o =
                Pair.of(CellKey.Generator.newCellKey(ordinals), next);
            moveToNext();
            return o;
        }

        private void moveToNext() {
            for (;;) {
                ++i;
                if (i >= size) {
                    hasNext = false;
                    return;
                }
                int k = ordinals.length - 1;
                while (k >= 0) {
                    if (ordinals[k] < axisValueSets[k].size() - 1) {
                        ++ordinals[k];
                        break;
                    } else {
                        ordinals[k] = 0;
                        --k;
                    }
                }
                next = getObject(i);
                if (next != null) {
                    return;
                }
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

// End AbstractSegmentBody.java
