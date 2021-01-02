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
// Copyright (C) 2009-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.rolap;

import mondrian3.calc.Calc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.Exp;
import mondrian3.olap.fun.AggregateFunDef;

/**
 * Implementation of {@link mondrian3.rolap.RolapCalculation}
 * that wraps a {@link RolapMember calculated member}.
 *
 * @author jhyde
 * @since May 15, 2009
 */
class RolapMemberCalculation implements RolapCalculation {
    private final RolapMember member;
    private final int solveOrder;
    private Boolean containsAggregateFunction;

    /**
     * Creates a RolapMemberCalculation.
     *
     * @param member Calculated member
     */
    public RolapMemberCalculation(RolapMember member) {
        this.member = member;
        // compute and solve order: it is used frequently
        solveOrder = this.member.getSolveOrder();
        assert member.isEvaluated();
    }

    public int hashCode() {
        return member.hashCode();
    }

    public boolean equals(Object obj) {
        return obj instanceof RolapMemberCalculation
            && member == ((RolapMemberCalculation) obj).member;
    }

    public void setContextIn(RolapEvaluator evaluator) {
        final RolapMember defaultMember =
            evaluator.root.defaultMembers[getHierarchyOrdinal()];

        // This method does not need to call RolapEvaluator.removeCalcMember.
        // That happens implicitly in setContext.
        evaluator.setContext(defaultMember);
        evaluator.setExpanding(member);
    }

    public int getSolveOrder() {
        return solveOrder;
    }

    public int getHierarchyOrdinal() {
        return member.getHierarchy().getOrdinalInCube();
    }

    public Calc getCompiledExpression(RolapEvaluatorRoot root) {
        final Exp exp = member.getExpression();
        return root.getCompiled(exp, true, null);
    }

    public boolean isCalculatedInQuery() {
        return member.isCalculatedInQuery();
    }

    public boolean containsAggregateFunction() {
        // searching for agg functions is expensive, so cache result
        if (containsAggregateFunction == null) {
            containsAggregateFunction =
                foundAggregateFunction(member.getExpression());
        }
        return containsAggregateFunction;
    }

    /**
     * Returns whether an expression contains a call to an aggregate
     * function such as "Aggregate" or "Sum".
     *
     * @param exp Expression
     * @return Whether expression contains a call to an aggregate function.
     */
    private static boolean foundAggregateFunction(Exp exp) {
        if (exp instanceof ResolvedFunCall) {
            ResolvedFunCall resolvedFunCall = (ResolvedFunCall) exp;
            if (resolvedFunCall.getFunDef() instanceof AggregateFunDef) {
                return true;
            } else {
                for (Exp argExp : resolvedFunCall.getArgs()) {
                    if (foundAggregateFunction(argExp)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

// End RolapMemberCalculation.java
