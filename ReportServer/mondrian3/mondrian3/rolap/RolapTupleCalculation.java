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
import mondrian3.olap.Util;

import java.util.List;

/**
 * Implementation of {@link mondrian3.rolap.RolapCalculation}
 * that changes one or more dimensions, then evaluates a given calculation.
 *
 * <p>It is used to implement sets in slicers, in particular sets of tuples in
 * the slicer.
 *
 * @author jhyde
 * @since May 15, 2009
 */
class RolapTupleCalculation implements RolapCalculation {
    private final List<RolapHierarchy> hierarchyList;
    private final Calc calc;
    private final int hashCode;

    /**
     * Creates a RolapTupleCalculation.
     *
     * @param hierarchyList List of hierarchies to be replaced.
     * @param calc Compiled scalar expression to compute cell
     */
    public RolapTupleCalculation(
        List<RolapHierarchy> hierarchyList,
        Calc calc)
    {
        this.hierarchyList = hierarchyList;
        this.calc = calc;
        this.hashCode = Util.hash(hierarchyList.hashCode(), calc);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof RolapTupleCalculation) {
            RolapTupleCalculation calculation = (RolapTupleCalculation) obj;
            return this.hierarchyList.equals(calculation.hierarchyList)
                && this.calc.equals(calculation.calc);
        }
        return false;
    }

    @Override
    public String toString() {
        return calc.toString();
    }

    public void setContextIn(RolapEvaluator evaluator) {
        // Restore default member for each hierarchy
        // in the tuple.
        for (RolapHierarchy hierarchy : hierarchyList) {
            final int ordinal = hierarchy.getOrdinalInCube();
            final RolapMember defaultMember =
                evaluator.root.defaultMembers[ordinal];
            evaluator.setContext(defaultMember);
        }

        evaluator.removeCalculation(this, true);
    }

    public int getSolveOrder() {
        return Integer.MIN_VALUE;
    }

    public int getHierarchyOrdinal() {
        throw new UnsupportedOperationException();
    }

    public Calc getCompiledExpression(RolapEvaluatorRoot root) {
        return calc;
    }

    public boolean containsAggregateFunction() {
        return false;
    }

    public boolean isCalculatedInQuery() {
        return true;
    }
}

// End RolapTupleCalculation.java
