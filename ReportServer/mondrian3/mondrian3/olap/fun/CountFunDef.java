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
import mondrian3.calc.impl.AbstractIntegerCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.*;

/**
 * Definition of the <code>Count</code> MDX function.
 *
 * @author jhyde
 * @since Mar 23, 2006
 */
class CountFunDef extends AbstractAggregateFunDef {
    static final String[] ReservedWords =
        new String[] {"INCLUDEEMPTY", "EXCLUDEEMPTY"};

    static final ReflectiveMultiResolver Resolver =
        new ReflectiveMultiResolver(
            "Count",
            "Count(<Set>[, EXCLUDEEMPTY | INCLUDEEMPTY])",
            "Returns the number of tuples in a set, empty cells included unless the optional EXCLUDEEMPTY flag is used.",
            new String[]{"fnx", "fnxy"},
            CountFunDef.class,
            ReservedWords);

    public CountFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final Calc calc =
            compiler.compileAs(
                call.getArg(0), null, ResultStyle.ITERABLE_ANY);
        final boolean includeEmpty =
            call.getArgCount() < 2
            || ((Literal) call.getArg(1)).getValue().equals(
                "INCLUDEEMPTY");
        return new AbstractIntegerCalc(
            call,
            new Calc[] {calc})
        {
            public int evaluateInteger(Evaluator evaluator) {
                final int savepoint = evaluator.savepoint();
                try {
                    evaluator.setNonEmpty(false);
                    final int count;
                    if (calc instanceof IterCalc) {
                        IterCalc iterCalc = (IterCalc) calc;
                        TupleIterable iterable =
                            evaluateCurrentIterable(iterCalc, evaluator);
                        count = count(evaluator, iterable, includeEmpty);
                    } else {
                        // must be ListCalc
                        ListCalc listCalc = (ListCalc) calc;
                        TupleList list =
                            evaluateCurrentList(listCalc, evaluator);
                        count = count(evaluator, list, includeEmpty);
                    }
                    return count;
                } finally {
                    evaluator.restore(savepoint);
                }
            }

            public boolean dependsOn(Hierarchy hierarchy) {
                // COUNT(<set>, INCLUDEEMPTY) is straightforward -- it
                // depends only on the dimensions that <Set> depends
                // on.
                if (super.dependsOn(hierarchy)) {
                    return true;
                }
                if (includeEmpty) {
                    return false;
                }
                // COUNT(<set>, EXCLUDEEMPTY) depends only on the
                // dimensions that <Set> depends on, plus all
                // dimensions not masked by the set.
                return ! calc.getType().usesHierarchy(hierarchy, true);
            }
        };

/*
 RME OLD STUFF
        final ListCalc memberListCalc =
                compiler.compileList(call.getArg(0));
        final boolean includeEmpty =
                call.getArgCount() < 2 ||
                ((Literal) call.getArg(1)).getValue().equals(
                        "INCLUDEEMPTY");
        return new AbstractIntegerCalc(
                call, new Calc[] {memberListCalc}) {
            public int evaluateInteger(Evaluator evaluator) {
                List memberList =
                    evaluateCurrentList(memberListCalc, evaluator);
                return count(evaluator, memberList, includeEmpty);
            }

            public boolean dependsOn(Dimension dimension) {
                // COUNT(<set>, INCLUDEEMPTY) is straightforward -- it
                // depends only on the dimensions that <Set> depends
                // on.
                if (super.dependsOn(dimension)) {
                    return true;
                }
                if (includeEmpty) {
                    return false;
                }
                // COUNT(<set>, EXCLUDEEMPTY) depends only on the
                // dimensions that <Set> depends on, plus all
                // dimensions not masked by the set.
                if (memberListCalc.getType().usesDimension(dimension, true)) {
                    return false;
                }
                return true;
            }
        };
*/
    }
}

// End CountFunDef.java
