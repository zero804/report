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
// Copyright (C) 2010-2010 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap;

import java.io.PrintWriter;
import java.util.List;

/**
 * Drill through statement.
 *
 * @author jhyde
 */
public class DrillThrough extends QueryPart {
    private final Query query;
    private final int maxRowCount;
    private final int firstRowOrdinal;
    private final List<Exp> returnList;

    /**
     * Creates a DrillThrough.
     *
     * @param query Query
     * @param maxRowCount Maximum number of rows to return, or -1
     * @param firstRowOrdinal Ordinal of first row to return, or -1
     * @param returnList List of columns to return
     */
    DrillThrough(
        Query query,
        int maxRowCount,
        int firstRowOrdinal,
        List<Exp> returnList)
    {
        this.query = query;
        this.maxRowCount = maxRowCount;
        this.firstRowOrdinal = firstRowOrdinal;
        this.returnList = returnList;
    }

    @Override
    public void unparse(PrintWriter pw) {
        pw.print("DRILLTHROUGH");
        if (maxRowCount >= 0) {
            pw.print(" MAXROWS ");
            pw.print(maxRowCount);
        }
        if (firstRowOrdinal >= 0) {
            pw.print(" FIRSTROWSET ");
            pw.print(firstRowOrdinal);
        }
        pw.print(" ");
        query.unparse(pw);
        if (returnList != null) {
            ExpBase.unparseList(
                pw, returnList.toArray(new Exp[returnList.size()]),
                " RETURN ", ", ", "");
        }
    }

    @Override
    public Object[] getChildren() {
        return new Object[] {maxRowCount, firstRowOrdinal, query, returnList};
    }

    public Query getQuery() {
        return query;
    }

    public int getMaxRowCount() {
        return maxRowCount;
    }

    public int getFirstRowOrdinal() {
        return firstRowOrdinal;
    }

    public List<Exp> getReturnList() {
        return returnList;
    }
}

// End DrillThrough.java
