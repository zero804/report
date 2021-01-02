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
 * Definition of the <code>Subset</code> MDX function.
 *
 * @author jhyde
 * @since Mar 23, 2006
 */
class SubsetFunDef extends FunDefBase {
    static final ReflectiveMultiResolver Resolver =
        new ReflectiveMultiResolver(
            "Subset",
            "Subset(<Set>, <Start>[, <Count>])",
            "Returns a subset of elements from a set.",
            new String[] {"fxxn", "fxxnn"},
            SubsetFunDef.class);

    public SubsetFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final ListCalc listCalc =
            compiler.compileList(call.getArg(0));
        final IntegerCalc startCalc =
            compiler.compileInteger(call.getArg(1));
        final IntegerCalc countCalc =
            call.getArgCount() > 2
            ? compiler.compileInteger(call.getArg(2))
            : null;
        return new AbstractListCalc(
            call, new Calc[] {listCalc, startCalc, countCalc})
        {
            public TupleList evaluateList(Evaluator evaluator) {
                final int savepoint = evaluator.savepoint();
                try {
                    evaluator.setNonEmpty(false);
                    final TupleList list = listCalc.evaluateList(evaluator);
                    final int start = startCalc.evaluateInteger(evaluator);
                    int end;
                    if (countCalc != null) {
                        final int count = countCalc.evaluateInteger(evaluator);
                        end = start + count;
                    } else {
                        end = list.size();
                    }
                    if (end > list.size()) {
                        end = list.size();
                    }
                    if (start >= end || start < 0) {
                        return TupleCollections.emptyList(list.getArity());
                    }
                    if (start == 0 && end == list.size()) {
                        return list;
                    }
                    assert 0 <= start;
                    assert start < end;
                    assert end <= list.size();
                    return list.subList(start, end);
                } finally {
                    evaluator.restore(savepoint);
                }
            }
        };
    }
}

// End SubsetFunDef.java
