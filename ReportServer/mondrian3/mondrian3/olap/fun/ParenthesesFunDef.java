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
// Copyright (C) 2002-2005 Julian Hyde
// Copyright (C) 2005-2009 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap.fun;

import mondrian3.calc.Calc;
import mondrian3.calc.ExpCompiler;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.*;
import mondrian3.olap.type.Type;

import java.io.PrintWriter;

/**
 * <code>ParenthesesFunDef</code> implements the parentheses operator as if it
 * were a function.
 *
 * @author jhyde
 * @since 3 March, 2002
 */
public class ParenthesesFunDef extends FunDefBase {
    private final int argType;
    public ParenthesesFunDef(int argType) {
        super(
            "()",
            "(<Expression>)",
            "Parenthesis enclose an expression and indicate precedence.",
            Syntax.Parentheses,
            argType,
            new int[] {argType});
        this.argType = argType;
    }
    public void unparse(Exp[] args, PrintWriter pw) {
        if (args.length != 1) {
            ExpBase.unparseList(pw, args, "(", ",", ")");
        } else {
            // Don't use parentheses unless necessary. We add parentheses around
            // expressions because we're not sure of operator precedence, so if
            // we're not careful, the parentheses tend to multiply ad infinitum.
            args[0].unparse(pw);
        }
    }

    public Type getResultType(Validator validator, Exp[] args) {
        Util.assertTrue(args.length == 1);
        return args[0].getType();
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        return compiler.compile(call.getArg(0));
    }
}

// End ParenthesesFunDef.java
