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
// Copyright (C) 2005-2009 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap.fun;

import mondrian3.calc.*;
import mondrian3.calc.impl.GenericCalc;
import mondrian3.calc.impl.GenericIterCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.*;
import mondrian3.olap.type.SetType;
import mondrian3.olap.type.Type;

import java.io.PrintWriter;
import java.util.List;

/**
 * Definition of the <code>Cache</code> system function, which is smart enough
 * to evaluate its argument only once.
 *
 * @author jhyde
 * @since 2005/8/14
 */
public class CacheFunDef extends FunDefBase {
    static final String NAME = "Cache";
    private static final String SIGNATURE = "Cache(<<Exp>>)";
    private static final String DESCRIPTION =
        "Evaluates and returns its sole argument, applying statement-level caching";
    private static final Syntax SYNTAX = Syntax.Function;
    static final CacheFunResolver Resolver = new CacheFunResolver();

    CacheFunDef(
        String name,
        String signature,
        String description,
        Syntax syntax,
        int category,
        Type type)
    {
        super(
            name, signature, description, syntax,
            category, new int[] {category});
        Util.discard(type);
    }

    public void unparse(Exp[] args, PrintWriter pw) {
        args[0].unparse(pw);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final Exp exp = call.getArg(0);
        final ExpCacheDescriptor cacheDescriptor =
                new ExpCacheDescriptor(exp, compiler);
        if (call.getType() instanceof SetType) {
            return new GenericIterCalc(call) {
                public Object evaluate(Evaluator evaluator) {
                    return evaluator.getCachedResult(cacheDescriptor);
                }

                public Calc[] getCalcs() {
                    return new Calc[] {cacheDescriptor.getCalc()};
                }

                public ResultStyle getResultStyle() {
                    // cached lists are immutable
                    return ResultStyle.LIST;
                }
            };
        } else {
            return new GenericCalc(call) {
                public Object evaluate(Evaluator evaluator) {
                    return evaluator.getCachedResult(cacheDescriptor);
                }

                public Calc[] getCalcs() {
                    return new Calc[] {cacheDescriptor.getCalc()};
                }

                public ResultStyle getResultStyle() {
                    return ResultStyle.VALUE;
                }
            };
        }
    }

    public static class CacheFunResolver extends ResolverBase {
        CacheFunResolver() {
            super(NAME, SIGNATURE, DESCRIPTION, SYNTAX);
        }

        public FunDef resolve(
            Exp[] args,
            Validator validator,
            List<Conversion> conversions)
        {
            if (args.length != 1) {
                return null;
            }
            final Exp exp = args[0];
            final int category = exp.getCategory();
            final Type type = exp.getType();
            return new CacheFunDef(
                NAME, SIGNATURE, DESCRIPTION, SYNTAX,
                category, type);
        }

        public boolean requiresExpression(int k) {
            return false;
        }
    }
}

// End CacheFunDef.java
