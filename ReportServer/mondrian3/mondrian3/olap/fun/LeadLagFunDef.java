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

import mondrian3.calc.*;
import mondrian3.calc.impl.AbstractMemberCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.*;

/**
 * Definition of the <code>Lead</code> and <code>Lag</code> MDX functions.
 *
 * @author jhyde
 * @since Mar 23, 2006
 */
class LeadLagFunDef extends FunDefBase {
    static final ReflectiveMultiResolver LagResolver =
        new ReflectiveMultiResolver(
            "Lag",
            "<Member>.Lag(<Numeric Expression>)",
            "Returns a member further along the specified member's dimension.",
            new String[]{"mmmn"},
            LeadLagFunDef.class);

    static final ReflectiveMultiResolver LeadResolver =
        new ReflectiveMultiResolver(
            "Lead",
            "<Member>.Lead(<Numeric Expression>)",
            "Returns a member further along the specified member's dimension.",
            new String[]{"mmmn"},
            LeadLagFunDef.class);

    public LeadLagFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final MemberCalc memberCalc =
                compiler.compileMember(call.getArg(0));
        final IntegerCalc integerCalc =
                compiler.compileInteger(call.getArg(1));
        final boolean lag = call.getFunName().equals("Lag");
        return new AbstractMemberCalc(
            call,
            new Calc[] {memberCalc, integerCalc})
        {
            public Member evaluateMember(Evaluator evaluator) {
                Member member = memberCalc.evaluateMember(evaluator);
                int n = integerCalc.evaluateInteger(evaluator);
                if (lag) {
                    if (n == Integer.MIN_VALUE) {
                        // Bump up lagValue by one, otherwise -n (used
                        // in the getLeadMember call below) is out of
                        // range because Integer.MAX_VALUE ==
                        // -(Integer.MIN_VALUE + 1).
                        n += 1;
                    }

                    n = -n;
                }
                return evaluator.getSchemaReader().getLeadMember(member, n);
            }
        };
    }
}

// End LeadLagFunDef.java
