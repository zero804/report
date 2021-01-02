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
import mondrian3.calc.impl.ConstantCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.Evaluator;
import mondrian3.olap.FunDef;

/**
 * Definition of the <code>Head</code> and <code>Tail</code>
 * MDX builtin functions.
 *
 * @author jhyde
 * @since Mar 23, 2006
 */
class HeadTailFunDef extends FunDefBase {
    static final Resolver TailResolver =
        new ReflectiveMultiResolver(
            "Tail",
            "Tail(<Set>[, <Count>])",
            "Returns a subset from the end of a set.",
            new String[] {"fxx", "fxxn"},
            HeadTailFunDef.class);

    static final Resolver HeadResolver =
        new ReflectiveMultiResolver(
            "Head",
            "Head(<Set>[, < Numeric Expression >])",
            "Returns the first specified number of elements in a set.",
            new String[] {"fxx", "fxxn"},
            HeadTailFunDef.class);

    private final boolean head;

    public HeadTailFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
        head = dummyFunDef.getName().equals("Head");
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final ListCalc listCalc =
            compiler.compileList(call.getArg(0));
        final IntegerCalc integerCalc =
            call.getArgCount() > 1
            ? compiler.compileInteger(call.getArg(1))
            : ConstantCalc.constantInteger(1);
        if (head) {
            return new AbstractListCalc(
                call, new Calc[] {listCalc, integerCalc})
            {
                public TupleList evaluateList(Evaluator evaluator) {
                    final int savepoint = evaluator.savepoint();
                    try {
                        evaluator.setNonEmpty(false);
                        TupleList list = listCalc.evaluateList(evaluator);
                        int count = integerCalc.evaluateInteger(evaluator);
                        return head(count, list);
                    } finally {
                        evaluator.restore(savepoint);
                    }
                }
            };
        } else {
            return new AbstractListCalc(
                call, new Calc[] {listCalc, integerCalc})
            {
                public TupleList evaluateList(Evaluator evaluator) {
                    final int savepoint = evaluator.savepoint();
                    try {
                        evaluator.setNonEmpty(false);
                        TupleList list = listCalc.evaluateList(evaluator);
                        int count = integerCalc.evaluateInteger(evaluator);
                        return tail(count, list);
                    } finally {
                        evaluator.restore(savepoint);
                    }
                }
            };
        }
    }

    static TupleList tail(final int count, final TupleList members) {
        assert members != null;
        final int memberCount = members.size();
        if (count >= memberCount) {
            return members;
        }
        if (count <= 0) {
            return TupleCollections.emptyList(members.getArity());
        }
        return members.subList(members.size() - count, members.size());
    }

    static TupleList head(final int count, final TupleList members) {
        assert members != null;
        if (count <= 0) {
            return TupleCollections.emptyList(members.getArity());
        }
        return members.subList(0, Math.min(count, members.size()));
    }
}

// End HeadTailFunDef.java
