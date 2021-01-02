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
// Copyright (C) 2004-2005 Julian Hyde
// Copyright (C) 2005-2011 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap.fun;

import mondrian3.calc.Calc;
import mondrian3.calc.ExpCompiler;
import mondrian3.calc.impl.GenericCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.*;

import java.util.List;

/**
 * Definition of the <code>CoalesceEmpty</code> MDX function.
 *
 * <p>It evaluates each of the arguments to the function, returning the
 * first such argument that does not return a null value.
 *
 * @author gjohnson
 */
public class CoalesceEmptyFunDef extends FunDefBase {
    static final ResolverBase Resolver = new ResolverImpl();

    public CoalesceEmptyFunDef(ResolverBase resolverBase, int type, int[] types)
    {
        super(resolverBase,  type, types);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final Exp[] args = call.getArgs();
        final Calc[] calcs = new Calc[args.length];
        for (int i = 0; i < args.length; i++) {
            calcs[i] = compiler.compileScalar(args[i], true);
        }
        return new GenericCalc(call) {
            public Object evaluate(Evaluator evaluator) {
                for (Calc calc : calcs) {
                    final Object o = calc.evaluate(evaluator);
                    if (o != null) {
                        return o;
                    }
                }
                return null;
            }

            public Calc[] getCalcs() {
                return calcs;
            }
        };
    }

    private static class ResolverImpl extends ResolverBase {
        public ResolverImpl() {
            super(
                "CoalesceEmpty",
                    "CoalesceEmpty(<Value Expression>[, <Value Expression>...])",
                    "Coalesces an empty cell value to a different value. All of the expressions must be of the same type (number or string).",
                    Syntax.Function);
        }

        public FunDef resolve(
            Exp[] args,
            Validator validator,
            List<Conversion> conversions)
        {
            if (args.length < 1) {
                return null;
            }
            final int[] types = {Category.Numeric, Category.String};
            final int[] argTypes = new int[args.length];
            for (int type : types) {
                int matchingArgs = 0;
                conversions.clear();
                for (int i = 0; i < args.length; i++) {
                    if (validator.canConvert(i, args[i], type, conversions)) {
                        matchingArgs++;
                    }
                    argTypes[i] = type;
                }
                if (matchingArgs == args.length) {
                    return new CoalesceEmptyFunDef(this, type, argTypes);
                }
            }
            return null;
        }

        public boolean requiresExpression(int k) {
            return true;
        }
    }
}

// End CoalesceEmptyFunDef.java
