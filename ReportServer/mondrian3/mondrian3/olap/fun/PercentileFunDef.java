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
// Copyright (C) 2006-2012 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap.fun;

import mondrian3.calc.*;
import mondrian3.calc.impl.AbstractDoubleCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.*;

/**
 * Definition of the <code>Percentile</code> MDX function.
 *
 * <p>There is some discussion about what the "right" percentile function is.
 * Here is a <a href="http://cnx.org/content/m10805/latest/">good overview</a>.
 * Wikipedia also lists
 * <a href="http://en.wikipedia.org/wiki/Percentile">another method</a>:
 *
 * <blockquote>rank = P / 100 * (N - 1) + 1</blockquote>
 *
 * <p>Implemented here is example 1 on that page, except we use</p>
 *
 * <blockquote>rank = P / 100 * N</blockquote>
 *
 * <p>for the rank instead of</p>
 *
 * <blockquote>rank = P / 100 * (N + 1)</blockquote>
 *
 * <p>That's because this is how it used to be in the code.</p>
 */
class PercentileFunDef extends AbstractAggregateFunDef {
    static final ReflectiveMultiResolver Resolver =
        new ReflectiveMultiResolver(
            "Percentile",
            "Percentile(<Set>, <Numeric Expression>, <Percent>)",
            "Returns the value of the tuple that is at a given percentile of a set.",
            new String[] {"fnxnn"},
            PercentileFunDef.class);

    public PercentileFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final ListCalc listCalc =
            compiler.compileList(call.getArg(0));
        final Calc calc =
            compiler.compileScalar(call.getArg(1), true);
        final DoubleCalc percentCalc =
            compiler.compileDouble(call.getArg(2));
        return new AbstractDoubleCalc(
            call, new Calc[] {listCalc, calc, percentCalc})
        {
            public double evaluateDouble(Evaluator evaluator) {
                TupleList list = evaluateCurrentList(listCalc, evaluator);
                double percent = percentCalc.evaluateDouble(evaluator) * 0.01;
                final int savepoint = evaluator.savepoint();
                try {
                    evaluator.setNonEmpty(false);
                    final double percentile =
                        percentile(evaluator, list, calc, percent);
                    return percentile;
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

// End PercentileFunDef.java
