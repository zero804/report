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
// Copyright (C) 2010-2010 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap.fun;

import mondrian3.calc.*;
import mondrian3.calc.impl.AbstractMemberCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.*;
import mondrian3.resource.MondrianResource;

/**
 * Definition of the <code>StrToMember</code> MDX function.
 *
 * <p>Syntax:
 * <blockquote><code>StrToMember(&lt;String Expression&gt;)
 * </code></blockquote>
 */
class StrToMemberFunDef extends FunDefBase {
    public static final FunDef INSTANCE = new StrToMemberFunDef();

    private StrToMemberFunDef() {
        super(
            "StrToMember",
            "Returns a member from a unique name String in MDX format.",
            "fmS");
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final StringCalc memberNameCalc =
            compiler.compileString(call.getArg(0));
        return new AbstractMemberCalc(call, new Calc[] {memberNameCalc}) {
            public Member evaluateMember(Evaluator evaluator) {
                String memberName =
                    memberNameCalc.evaluateString(evaluator);
                if (memberName == null) {
                    throw newEvalException(
                        MondrianResource.instance().NullValue.ex());
                }
                return parseMember(evaluator, memberName, null);
            }
        };
    }
}

// End StrToMemberFunDef.java
