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
package mondrian3.olap4j;

import mondrian3.olap.QueryTiming;
import mondrian3.spi.ProfileHandler;

import org.olap4j.OlapStatement;

import java.io.PrintWriter;

/**
 * Access to non-public methods in the package of the mondrian olap4j driver.
 *
 * <p>All methods in this class are subject to change without notice.
 *
 * @author jhyde
 * @since October, 2010
 */
public final class Unsafe {
    public static final Unsafe INSTANCE = new Unsafe();

    private Unsafe() {
    }

    public void setStatementProfiling(
        OlapStatement statement,
        final PrintWriter pw)
    {
        ((MondrianOlap4jStatement) statement).enableProfiling(
            new ProfileHandler() {
                public void explain(String plan, QueryTiming timing) {
                    pw.println(plan);
                    if (timing != null) {
                        pw.println(timing);
                    }
                }
            }
        );
    }
}

// End Unsafe.java
