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
// Copyright (C) 2005-2005 Julian Hyde
// Copyright (C) 2005-2011 Pentaho and others
// Copyright (C) 2004-2005 SAS Institute, Inc.
// All Rights Reserved.
*/
package mondrian3.olap.fun;

import mondrian3.calc.*;
import mondrian3.calc.impl.AbstractListCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.*;
import mondrian3.rolap.RolapEvaluator;


/**
 * Definition of the <code>NonEmptyCrossJoin</code> MDX function.
 *
 * @author jhyde
 * @since Mar 23, 2006
 *
 * author 16 December, 2004
 */
public class NonEmptyCrossJoinFunDef extends CrossJoinFunDef {
    static final ReflectiveMultiResolver Resolver = new ReflectiveMultiResolver(
        "NonEmptyCrossJoin",
            "NonEmptyCrossJoin(<Set1>, <Set2>)",
            "Returns the cross product of two sets, excluding empty tuples and tuples without associated fact table data.",
            new String[]{"fxxx"},
            NonEmptyCrossJoinFunDef.class);

    public NonEmptyCrossJoinFunDef(FunDef dummyFunDef) {
        super(dummyFunDef);
    }

    public Calc compileCall(final ResolvedFunCall call, ExpCompiler compiler) {
        final ListCalc listCalc1 = compiler.compileList(call.getArg(0));
        final ListCalc listCalc2 = compiler.compileList(call.getArg(1));
        return new AbstractListCalc(
            call, new Calc[] {listCalc1, listCalc2}, false)
        {
            public TupleList evaluateList(Evaluator evaluator) {
                SchemaReader schemaReader = evaluator.getSchemaReader();

                // Evaluate the arguments in non empty mode, but remove from
                // the slicer any members that will be overridden by args to
                // the NonEmptyCrossjoin function. For example, in
                //
                //   SELECT NonEmptyCrossJoin(
                //       [Store].[USA].Children,
                //       [Product].[Beer].Children)
                //    FROM [Sales]
                //    WHERE [Store].[Mexico]
                //
                // we want all beers, not just those sold in Mexico.
                final int savepoint = evaluator.savepoint();
                try {
                    evaluator.setNonEmpty(true);
                    for (Member member
                        : ((RolapEvaluator) evaluator).getSlicerMembers())
                    {
                        if (getType().getElementType().usesHierarchy(
                                member.getHierarchy(), true))
                        {
                            evaluator.setContext(
                                member.getHierarchy().getAllMember());
                        }
                    }

                    NativeEvaluator nativeEvaluator =
                        schemaReader.getNativeSetEvaluator(
                            call.getFunDef(), call.getArgs(), evaluator, this);
                    if (nativeEvaluator != null) {
                        evaluator.restore(savepoint);
                        return
                            (TupleList) nativeEvaluator.execute(
                                ResultStyle.LIST);
                    }

                    final TupleList list1 = listCalc1.evaluateList(evaluator);
                    if (list1.isEmpty()) {
                        evaluator.restore(savepoint);
                        return list1;
                    }
                    final TupleList list2 = listCalc2.evaluateList(evaluator);
                    TupleList result = mutableCrossJoin(list1, list2);

                    // remove any remaining empty crossings from the result
                    result = nonEmptyList(evaluator, result, call);
                    return result;
                } finally {
                    evaluator.restore(savepoint);
                }
            }

            public boolean dependsOn(Hierarchy hierarchy) {
                if (super.dependsOn(hierarchy)) {
                    return true;
                }
                // Member calculations generate members, which mask the actual
                // expression from the inherited context.
                if (listCalc1.getType().usesHierarchy(hierarchy, true)) {
                    return false;
                }
                if (listCalc2.getType().usesHierarchy(hierarchy, true)) {
                    return false;
                }
                // The implicit value expression, executed to figure out
                // whether a given tuple is empty, depends upon all dimensions.
                return true;
            }
        };
    }

}

// End NonEmptyCrossJoinFunDef.java
