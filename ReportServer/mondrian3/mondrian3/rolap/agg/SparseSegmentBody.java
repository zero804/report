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
// Copyright (C) 2011-2012 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap.agg;

import mondrian3.rolap.CellKey;
import mondrian3.util.Pair;

import java.util.*;

/**
 * Implementation of a segment body which stores the data of a
 * sparse segment data set into a dense array of java objects.
 *
 * @author LBoudreau
 */
class SparseSegmentBody extends AbstractSegmentBody {
    private static final long serialVersionUID = -6684830985364895836L;
    final CellKey[] keys;
    final Object[] data;

    SparseSegmentBody(
        Map<CellKey, Object> dataToSave,
        List<Pair<SortedSet<Comparable>, Boolean>> axes)
    {
        super(axes);

        this.keys = new CellKey[dataToSave.size()];
        this.data = new Object[dataToSave.size()];
        int i = 0;
        for (Map.Entry<CellKey, Object> entry : dataToSave.entrySet()) {
            keys[i] = entry.getKey();
            data[i] = entry.getValue();
            ++i;
        }
    }

    @Override
    protected int getSize() {
        return keys.length;
    }

    @Override
    protected Object getObject(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<CellKey, Object> getValueMap() {
        final Map<CellKey, Object> map =
            new HashMap<CellKey, Object>(keys.length * 3 / 2);
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], data[i]);
        }
        return map;
    }
}

// End SparseSegmentBody.java
