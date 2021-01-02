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
// Copyright (C) 2009-2009 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap.fun;

import mondrian3.calc.*;
import mondrian3.calc.impl.AbstractHierarchyCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.*;
import mondrian3.olap.type.HierarchyType;
import mondrian3.olap.type.Type;
import mondrian3.rolap.RolapCube;
import mondrian3.rolap.RolapHierarchy;

import java.util.List;

/**
 * Definition of the <code>Dimensions(&lt;Numeric Expression&gt;)</code>
 * MDX builtin function.
 *
 * <p>NOTE: Actually returns a hierarchy. This is consistent with Analysis
 * Services.
 *
 * @author jhyde
 * @since Jul 20, 2009
 */
class DimensionsNumericFunDef extends FunDefBase {
    public static final FunDefBase INSTANCE = new DimensionsNumericFunDef();

    private DimensionsNumericFunDef() {
        super(
            "Dimensions",
            "Returns the hierarchy whose zero-based position within the cube "
            + "is specified by a numeric expression.",
            "fhn");
    }

    public Type getResultType(Validator validator, Exp[] args) {
        return HierarchyType.Unknown;
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler)
    {
        final IntegerCalc integerCalc =
            compiler.compileInteger(call.getArg(0));
        return new AbstractHierarchyCalc(call, new Calc[] {integerCalc})
        {
            public Hierarchy evaluateHierarchy(Evaluator evaluator) {
                int n = integerCalc.evaluateInteger(evaluator);
                return nthHierarchy(evaluator, n);
            }
        };
    }

    RolapHierarchy nthHierarchy(Evaluator evaluator, int n) {
        RolapCube cube = (RolapCube) evaluator.getCube();
        List<RolapHierarchy> hierarchies = cube.getHierarchies();
        if (n >= hierarchies.size() || n < 0) {
            throw newEvalException(
                this, "Index '" + n + "' out of bounds");
        }
        return hierarchies.get(n);
    }
}

// End DimensionsNumericFunDef.java
