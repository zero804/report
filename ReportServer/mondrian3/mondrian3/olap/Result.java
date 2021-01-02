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
// Copyright (C) 2001-2005 Julian Hyde
// Copyright (C) 2005-2011 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap;

import java.io.PrintWriter;

/**
 * A <code>Result</code> is the result of running an MDX query. See {@link
 * Connection#execute}.
 *
 * @author jhyde
 * @since 6 August, 2001
 */
public interface Result {
    /** Returns the query which generated this result. */
    Query getQuery();
    /** Returns the non-slicer axes. */
    Axis[] getAxes();
    /** Returns the slicer axis. */
    Axis getSlicerAxis();
    /** Returns the cell at a given set of coordinates. For example, in a result
     * with 4 columns and 6 rows, the top-left cell has coordinates [0, 0],
     * and the bottom-right cell has coordinates [3, 5]. */
    Cell getCell(int[] pos);
    void print(PrintWriter pw);
    void close();
}

// End Result.java