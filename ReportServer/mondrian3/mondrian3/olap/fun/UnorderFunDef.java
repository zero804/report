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
package mondrian3.olap.fun;

import mondrian3.calc.Calc;
import mondrian3.calc.ExpCompiler;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.FunDef;

/**
 * Definition of the <code>Unorder</code> MDX function.
 *
 * @author jhyde
 * @since Sep 06, 2008
 */
class UnorderFunDef extends FunDefBase {

    static final ReflectiveMultiResolver Resolver =
        new ReflectiveMultiResolver(
            "Unorder",
            "Unorder(<Set>)",
            "Removes any enforced ordering from a specified set.",
            new String[]{"fxx"},
            UnorderFunDef.class);

    public UnorderFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        // Currently Unorder has no effect. In future, we may use the function
        // as a marker to weaken the ordering required from an expression and
        // therefore allow the compiler to use a more efficient implementation
        // that does not return a strict order.
        return compiler.compile(call.getArg(0));
    }
}

// End UnorderFunDef.java
