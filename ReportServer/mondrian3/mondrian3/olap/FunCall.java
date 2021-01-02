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
// Copyright (C) 1998-2005 Julian Hyde
// Copyright (C) 2005-2006 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap;

/**
 * A <code>FunCall</code> is a function applied to a list of operands.
 *
 * <p>The parser creates function calls as an
 * {@link mondrian3.mdx.UnresolvedFunCall unresolved  function call}.
 * The validator converts it to a
 * {@link  mondrian3.mdx.ResolvedFunCall resolved function call},
 * which has a {@link FunDef function definition} and extra type information.
 *
 * @author jhyde
 * @since Jan 6, 2006
 */
public interface FunCall extends Exp {
    /**
     * Returns the <code>index</code><sup>th</sup> argument to this function
     * call.
     *
     * @param index Ordinal of the argument
     * @return <code>index</code><sup>th</sup> argument to this function call
     */
    Exp getArg(int index);

    /**
     * Returns the arguments to this function.
     *
     * @return array of arguments
     */
    Exp[] getArgs();

    /**
     * Returns the number of arguments to this function.
     *
     * @return number of arguments
     */
    int getArgCount();

    /**
     * Returns the name of the function.
     */
    String getFunName();

    /**
     * Returns the syntax of the call.
     */
    Syntax getSyntax();
}

// End FunCall.java
