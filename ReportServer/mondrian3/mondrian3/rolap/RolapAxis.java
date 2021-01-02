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
// Copyright (C) 2005-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.rolap;

import mondrian3.calc.TupleList;
import mondrian3.olap.*;

import java.util.AbstractList;
import java.util.List;

/**
 * Implementation of the Axis interface.
 *
 * @author Richard M. Emberson
 * @author Julian Hyde
 */
public class RolapAxis implements Axis {
    private final TupleList list;

    public RolapAxis(TupleList list) {
        this.list = list;
    }

    public TupleList getTupleList() {
        return list;
    }

    public List<Position> getPositions() {
        return new PositionList(list);
    }

    public static String toString(Axis axis) {
        List<Position> pl = axis.getPositions();
        return toString(pl);
    }

    public static String toString(List<Position> pl) {
        StringBuilder buf = new StringBuilder();
        for (Position p : pl) {
            buf.append('{');
            boolean firstTime = true;
            for (Member m : p) {
                if (! firstTime) {
                    buf.append(", ");
                }
                buf.append(m.getUniqueName());
                firstTime = false;
            }
            buf.append('}');
            buf.append('\n');
        }
        return buf.toString();
    }

    /**
     * List of positions.
     */
    private static class PositionList extends AbstractList<Position> {
        private final TupleList list;

        PositionList(TupleList list) {
            this.list = list;
        }

        public boolean isEmpty() {
            // may be considerably cheaper than computing size
            return list.isEmpty();
        }

        public int size() {
            return list.size();
        }

        public Position get(int index) {
            return new PositionImpl(list, index);
        }
    }

    /**
     * Implementation of {@link Position} that reads from a given location in
     * a {@link TupleList}.
     */
    private static class PositionImpl
        extends AbstractList<Member>
        implements Position
    {
        private final TupleList tupleList;
        private final int offset;

        PositionImpl(TupleList tupleList, int offset) {
            this.tupleList = tupleList;
            this.offset = offset;
        }

        public Member get(int index) {
            return tupleList.get(index, offset);
        }

        public int size() {
            return tupleList.getArity();
        }
    }
}

// End RolapAxis.java
