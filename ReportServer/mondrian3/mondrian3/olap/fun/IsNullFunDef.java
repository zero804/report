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
 * Definition of the <code>IS NULL</code> MDX function.
 *
 * @author medstat
 * @since Aug 21, 2006
 */
class IsNullFunDef extends FunDefBase {
    /**
     * Resolves calls to the <code>IS NULL</code> postfix operator.
     */
    static final ReflectiveMultiResolver Resolver =
        new ReflectiveMultiResolver(
            "IS NULL",
            "<Expression> IS NULL",
            "Returns whether an object is null",
            new String[]{"Qbm", "Qbl", "Qbh", "Qbd"},
            IsNullFunDef.class);

    public IsNullFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        assert call.getArgCount() == 1;
        final MemberCalc memberCalc = compiler.compileMember(call.getArg(0));
        return new AbstractBooleanCalc(call, new Calc[]{memberCalc}) {
            public boolean evaluateBoolean(Evaluator evaluator) {
                Member member = memberCalc.evaluateMember(evaluator);
                return member.isNull();
            }
        };
    }
}

// End IsNullFunDef.java
