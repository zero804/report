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
// Copyright (C) 2006-2009 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap.fun;

import mondrian3.calc.Calc;
import mondrian3.calc.ExpCompiler;
import mondrian3.calc.impl.AbstractBooleanCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.Evaluator;
import mondrian3.olap.FunDef;

/**
 * Definition of the <code>IsEmpty</code> MDX function.
 *
 * @author jhyde
 * @since Mar 23, 2006
 */
class IsEmptyFunDef extends FunDefBase {
    static final ReflectiveMultiResolver FunctionResolver =
        new ReflectiveMultiResolver(
            "IsEmpty",
            "IsEmpty(<Value Expression>)",
            "Determines if an expression evaluates to the empty cell value.",
            new String[] {"fbS", "fbn"},
            IsEmptyFunDef.class);

    static final ReflectiveMultiResolver PostfixResolver =
        new ReflectiveMultiResolver(
            "IS EMPTY",
            "<Value Expression> IS EMPTY",
            "Determines if an expression evaluates to the empty cell value.",
            new String[] {"Qbm", "Qbt"},
            IsEmptyFunDef.class);

    public IsEmptyFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final Calc calc = compiler.compileScalar(call.getArg(0), true);
        return new AbstractBooleanCalc(call, new Calc[] {calc}) {
            public boolean evaluateBoolean(Evaluator evaluator) {
                Object o = calc.evaluate(evaluator);
                return o == null;
            }
        };
    }
}

// End IsEmptyFunDef.java
