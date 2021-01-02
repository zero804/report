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
import mondrian3.calc.impl.AbstractStringCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.*;
import mondrian3.util.Format;

import java.util.Locale;

/**
 * Definition of the <code>Format</code> MDX function.
 *
 * @author jhyde
 * @since Mar 23, 2006
 */
class FormatFunDef extends FunDefBase {
    static final ReflectiveMultiResolver Resolver =
        new ReflectiveMultiResolver(
            "Format",
            "Format(<Expression>, <String Expression>)",
            "Formats a number or date to a string.",
            new String[] { "fSmS", "fSnS", "fSDS" },
            FormatFunDef.class);

    public FormatFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final Exp[] args = call.getArgs();
        final Calc calc = compiler.compileScalar(call.getArg(0), true);
        final Locale locale = compiler.getEvaluator().getConnectionLocale();
        if (args[1] instanceof Literal) {
            // Constant string expression: optimize by
            // compiling format string.
            String formatString = (String) ((Literal) args[1]).getValue();
            final Format format = new Format(formatString, locale);
            return new AbstractStringCalc(call, new Calc[] {calc}) {
                public String evaluateString(Evaluator evaluator) {
                    final Object o = calc.evaluate(evaluator);
                    return format.format(o);
                }
            };
        } else {
            // Variable string expression
            final StringCalc stringCalc =
                    compiler.compileString(call.getArg(1));
            return new AbstractStringCalc(call, new Calc[] {calc, stringCalc}) {
                public String evaluateString(Evaluator evaluator) {
                    final Object o = calc.evaluate(evaluator);
                    final String formatString =
                            stringCalc.evaluateString(evaluator);
                    final Format format =
                            new Format(formatString, locale);
                    return format.format(o);
                }
            };
        }
    }
}

// End FormatFunDef.java
