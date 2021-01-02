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
import mondrian3.olap.*;

import java.util.*;

/**
 * Definition of the <code>Union</code> MDX function.
 *
 * @author jhyde
 * @since Mar 23, 2006
 */
class UnionFunDef extends FunDefBase {
    static final String[] ReservedWords = new String[] {"ALL", "DISTINCT"};

    static final ReflectiveMultiResolver Resolver =
        new ReflectiveMultiResolver(
            "Union",
            "Union(<Set1>, <Set2>[, ALL])",
            "Returns the union of two sets, optionally retaining duplicates.",
            new String[] {"fxxx", "fxxxy"},
            UnionFunDef.class,
            ReservedWords);

    public UnionFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        String allString = getLiteralArg(call, 2, "DISTINCT", ReservedWords);
        final boolean all = allString.equalsIgnoreCase("ALL");
        // todo: do at validate time
        checkCompatible(call.getArg(0), call.getArg(1), null);
        final ListCalc listCalc0 =
            compiler.compileList(call.getArg(0));
        final ListCalc listCalc1 =
            compiler.compileList(call.getArg(1));
        return new AbstractListCalc(call, new Calc[] {listCalc0, listCalc1}) {
            public TupleList evaluateList(Evaluator evaluator) {
                TupleList list0 = listCalc0.evaluateList(evaluator);
                TupleList list1 = listCalc1.evaluateList(evaluator);
                return union(list0, list1, all);
            }
        };
    }

    TupleList union(TupleList list0, TupleList list1, final boolean all) {
        assert list0 != null;
        assert list1 != null;
        if (all) {
            if (list0.isEmpty()) {
                return list1;
            }
            if (list1.isEmpty()) {
                return list0;
            }
            TupleList result = TupleCollections.createList(list0.getArity());
            result.addAll(list0);
            result.addAll(list1);
            return result;
        } else {
            Set<List<Member>> added = new HashSet<List<Member>>();
            TupleList result = TupleCollections.createList(list0.getArity());
            FunUtil.addUnique(result, list0, added);
            FunUtil.addUnique(result, list1, added);
            return result;
        }
    }
}

// End UnionFunDef.java
