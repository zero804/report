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
// Copyright (C) 2005-2011 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap.fun;

import mondrian3.calc.*;
import mondrian3.calc.impl.*;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.*;
import mondrian3.olap.type.NullType;
import mondrian3.resource.MondrianResource;
import mondrian3.rolap.RolapMember;

/**
 * Definition of the MDX <code>&lt;Member&gt : &lt;Member&gt;</code> operator,
 * which returns the set of members between a given pair of members.
 *
 * @author jhyde
 * @since 3 March, 2002
 */
class RangeFunDef extends FunDefBase {
    static final RangeFunDef instance = new RangeFunDef();

    private RangeFunDef() {
        super(
            ":",
            "<Member> : <Member>",
            "Infix colon operator returns the set of members between a given pair of members.",
            "ixmm");
    }


    /**
     * Returns two membercalc objects, substituting nulls with the hierarchy
     * null member of the other expression.
     *
     * @param exp0 first expression
     * @param exp1 second expression
     *
     * @return two member calcs
     */
    private MemberCalc[] compileMembers(
        Exp exp0, Exp exp1, ExpCompiler compiler)
    {
        MemberCalc[] members = new MemberCalc[2];

        if (exp0.getType() instanceof NullType) {
            members[0] = null;
        } else {
            members[0] = compiler.compileMember(exp0);
        }

        if (exp1.getType() instanceof NullType) {
            members[1] = null;
        } else {
            members[1] = compiler.compileMember(exp1);
        }

        // replace any null types with hierachy null member
        // if both objects are null, throw exception

        if (members[0] == null && members[1] == null) {
            throw MondrianResource.instance().TwoNullsNotSupported.ex();
        } else if (members[0] == null) {
            Member nullMember =
                ((RolapMember) members[1].evaluate(null)).getHierarchy()
                .getNullMember();
            members[0] = (MemberCalc)ConstantCalc.constantMember(nullMember);
        } else if (members[1] == null) {
            Member nullMember =
                ((RolapMember) members[0].evaluate(null)).getHierarchy()
                .getNullMember();
            members[1] = (MemberCalc)ConstantCalc.constantMember(nullMember);
        }

        return members;
    }

    public Calc compileCall(final ResolvedFunCall call, ExpCompiler compiler) {
        final MemberCalc[] memberCalcs =
            compileMembers(call.getArg(0), call.getArg(1), compiler);
        return new AbstractListCalc(
            call, new Calc[] {memberCalcs[0], memberCalcs[1]})
        {
            public TupleList evaluateList(Evaluator evaluator) {
                final Member member0 = memberCalcs[0].evaluateMember(evaluator);
                final Member member1 = memberCalcs[1].evaluateMember(evaluator);
                if (member0.isNull() || member1.isNull()) {
                    return TupleCollections.emptyList(1);
                }
                if (member0.getLevel() != member1.getLevel()) {
                    throw evaluator.newEvalException(
                        call.getFunDef(),
                        "Members must belong to the same level");
                }
                return new UnaryTupleList(
                    FunUtil.memberRange(evaluator, member0, member1));
            }
        };
    }
}

// End RangeFunDef.java
