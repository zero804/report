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
// Copyright (C) 2008-2011 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap;

/**
 * Strategies for applying solve order, exposed via the property
 * {@link MondrianProperties#SolveOrderMode}.
 */
public enum SolveOrderMode {

    /**
     * The SOLVE_ORDER value is absolute regardless of
     * where it is defined; e.g. a query defined calculated
     * member with a SOLVE_ORDER of 1 always takes precedence
     * over a cube defined value of 2.
     *
     * <p>Compatible with Analysis Services 2000, and default behavior
     * up to mondrian-3.0.3.
     */
    ABSOLUTE,

    /**
     * Cube calculated members are resolved before any session
     * scope calculated members, and session scope members are
     * resolved before any query defined calculation.  The
     * SOLVE_ORDER value only applies within the scope in which
     * it was defined.
     *
     * <p>Compatible with Analysis Services 2005, and default behavior
     * from mondrian-3.0.4 and later.
     */
    SCOPED
}

// End SolveOrderMode.java
