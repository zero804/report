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
// Copyright (C) 2006-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap.fun;

import mondrian3.calc.*;
import mondrian3.calc.impl.AbstractListCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.Evaluator;
import mondrian3.olap.FunDef;

/**
 * Definition of the <code>Hierarchize</code> MDX function.
 *
 * @author jhyde
 * @since Mar 23, 2006
 */
class HierarchizeFunDef extends FunDefBase {
    static final String[] prePost = {"PRE", "POST"};
    static final ReflectiveMultiResolver Resolver =
        new ReflectiveMultiResolver(
            "Hierarchize",
            "Hierarchize(<Set>[, POST])",
            "Orders the members of a set in a hierarchy.",
            new String[] {"fxx", "fxxy"},
            HierarchizeFunDef.class,
            prePost);

    public HierarchizeFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final ListCalc listCalc =
            compiler.compileList(call.getArg(0), true);
        String order = getLiteralArg(call, 1, "PRE", prePost);
        final boolean post = order.equals("POST");
        return new AbstractListCalc(call, new Calc[] {listCalc}) {
            public TupleList evaluateList(Evaluator evaluator) {
                TupleList list = listCalc.evaluateList(evaluator);
                return hierarchizeTupleList(list, post);
            }
        };
    }
}

// End HierarchizeFunDef.java
