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
import mondrian3.calc.impl.AbstractBooleanCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.*;

/**
 * Definition of the <code>IS</code> MDX function.
 *
 * @see IsNullFunDef
 * @author jhyde
 * @since Mar 23, 2006
 */
class IsFunDef extends FunDefBase {
    static final ReflectiveMultiResolver Resolver =
        new ReflectiveMultiResolver(
            "IS",
            "<Expression> IS <Expression>",
            "Returns whether two objects are the same",
            new String[] {"ibmm", "ibll", "ibhh", "ibdd", "ibtt"},
            IsFunDef.class);

    public IsFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final int category = call.getArg(0).getCategory();
        switch (category) {
        case Category.Tuple:
            final TupleCalc tupleCalc0 = compiler.compileTuple(call.getArg(0));
            final TupleCalc tupleCalc1 = compiler.compileTuple(call.getArg(1));
            return new AbstractBooleanCalc(
                call, new Calc[] {tupleCalc0, tupleCalc1})
            {
                public boolean evaluateBoolean(Evaluator evaluator) {
                    Member[] o0 = tupleCalc0.evaluateTuple(evaluator);
                    Member[] o1 = tupleCalc1.evaluateTuple(evaluator);
                    return equalTuple(o0, o1);
                }
            };
        default:
            assert category == call.getArg(1).getCategory();
            final Calc calc0 = compiler.compile(call.getArg(0));
            final Calc calc1 = compiler.compile(call.getArg(1));
            return new AbstractBooleanCalc(call, new Calc[] {calc0, calc1}) {
                public boolean evaluateBoolean(Evaluator evaluator) {
                    Object o0 = calc0.evaluate(evaluator);
                    Object o1 = calc1.evaluate(evaluator);
                    return o0.equals(o1);
                }
            };
        }
    }
}

// End IsFunDef.java
