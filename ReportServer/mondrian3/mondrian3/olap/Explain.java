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
// Copyright (C) 2011-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap;

import java.io.PrintWriter;

/**
 * Explain statement.
 *
 * @author jhyde
 */
public class Explain extends QueryPart {
    private final QueryPart query;

    /**
     * Creates an Explain statement.
     *
     * @param query Query (SELECT or DRILLTHROUGH)
     */
    Explain(
        QueryPart query)
    {
        this.query = query;
        assert this.query != null;
        assert this.query instanceof Query
            || this.query instanceof DrillThrough;
    }

    @Override
    public void unparse(PrintWriter pw) {
        pw.print("EXPLAIN PLAN FOR ");
        query.unparse(pw);
    }

    @Override
    public Object[] getChildren() {
        return new Object[] {query};
    }

    public QueryPart getQuery() {
        return query;
    }
}

// End Explain.java
