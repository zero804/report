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
import mondrian3.calc.impl.AbstractDoubleCalc;
import mondrian3.calc.impl.ValueCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.*;

/**
 * Definition of the <code>Avg</code> MDX function.
 *
 * @author jhyde
 * @since Mar 23, 2006
 */
class AvgFunDef extends AbstractAggregateFunDef {
    static final ReflectiveMultiResolver Resolver = new ReflectiveMultiResolver(
        "Avg",
            "Avg(<Set>[, <Numeric Expression>])",
            "Returns the average value of a numeric expression evaluated over a set.",
            new String[]{"fnx", "fnxn"},
            AvgFunDef.class);

    public AvgFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final ListCalc listCalc = compiler.compileList(call.getArg(0));
        final Calc calc = call.getArgCount() > 1
            ? compiler.compileScalar(call.getArg(1), true)
            : new ValueCalc(call);
        return new AbstractDoubleCalc(call, new Calc[]{listCalc, calc}) {
            public double evaluateDouble(Evaluator evaluator) {
                TupleList memberList = evaluateCurrentList(listCalc, evaluator);
                final int savepoint = evaluator.savepoint();
                evaluator.setNonEmpty(false);
                try {
                    final double avg =
                        (Double) avg(
                            evaluator, memberList, calc);
                    return avg;
                } finally {
                    evaluator.restore(savepoint);
                }
            }

            public boolean dependsOn(Hierarchy hierarchy) {
                return anyDependsButFirst(getCalcs(), hierarchy);
            }
        };
    }
}

// End AvgFunDef.java