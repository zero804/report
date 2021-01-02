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
// Copyright (C) 2012-2013 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap.fun;

import mondrian3.calc.*;
import mondrian3.calc.impl.AbstractListCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.*;
import mondrian3.olap.type.LevelType;
import mondrian3.olap.type.Type;

import java.util.ArrayList;
import java.util.List;


/**
 * Definition of the <code>Ancestors</code> MDX function.
 *
 * @author lboudreau
 * @since Nov 27 2012
 */
class AncestorsFunDef extends FunDefBase {
    static final ReflectiveMultiResolver Resolver =
        new ReflectiveMultiResolver(
            "Ancestors",
            "Ancestors(<Member>, {<Level>|<Numeric Expression>})",
            "Returns the set of all ancestors of a specified member at a specified level or at a specified distance from the member",
            new String[] {"fxml", "fxmn"},
            AncestorsFunDef.class);

    public AncestorsFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
    }

    public int getReturnCategory() {
        return Category.Set;
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final MemberCalc memberCalc =
            compiler.compileMember(call.getArg(0));
        final Type type1 = call.getArg(1).getType();
        if (type1 instanceof LevelType) {
            final LevelCalc levelCalc =
                compiler.compileLevel(call.getArg(1));
            return new AbstractListCalc(
                call, new Calc[] {memberCalc, levelCalc})
            {
                public TupleList evaluateList(Evaluator evaluator) {
                    Level level = levelCalc.evaluateLevel(evaluator);
                    Member member = memberCalc.evaluateMember(evaluator);
                    int distance =
                        member.getDepth() - level.getDepth();
                    List<Member> ancestors = new ArrayList<Member>();
                    for (int curDist = 1; curDist <= distance; curDist++) {
                        ancestors.add(
                            ancestor(evaluator, member, curDist, null));
                    }
                    return TupleCollections.asTupleList(ancestors);
                }
            };
        } else {
            final IntegerCalc distanceCalc =
                compiler.compileInteger(call.getArg(1));
            return new AbstractListCalc(
                call, new Calc[] {memberCalc, distanceCalc})
            {
                public TupleList evaluateList(Evaluator evaluator) {
                    Member member = memberCalc.evaluateMember(evaluator);
                    int distance = distanceCalc.evaluateInteger(evaluator);
                    List<Member> ancestors = new ArrayList<Member>();
                    for (int curDist = 1; curDist <= distance; curDist++) {
                        ancestors.add(
                            ancestor(evaluator, member, curDist, null));
                    }
                    return TupleCollections.asTupleList(ancestors);
                }
            };
        }
    }
}

// End AncestorsFunDef.java
