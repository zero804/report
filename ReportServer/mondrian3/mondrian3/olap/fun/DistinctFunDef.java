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
// Copyright (C) 2007-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap.fun;

import mondrian3.calc.*;
import mondrian3.calc.impl.AbstractListCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.Evaluator;
import mondrian3.olap.Member;

import java.util.*;

/**
 * Definition of the <code>Distinct</code> MDX function.
 *
 * <p>Syntax:
 * <blockquote><code>Distinct(&lt;Set&gt;)</code></blockquote>
 *
 * @author jhyde
 * @since Jun 10, 2007
*/
class DistinctFunDef extends FunDefBase {
    public static final DistinctFunDef instance = new DistinctFunDef();

    private DistinctFunDef() {
        super(
            "Distinct",
            "Eliminates duplicate tuples from a set.",
            "fxx");
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final ListCalc listCalc =
            compiler.compileList(call.getArg(0));
        return new CalcImpl(call, listCalc);
    }

    static class CalcImpl extends AbstractListCalc {
        private final ListCalc listCalc;

        public CalcImpl(ResolvedFunCall call, ListCalc listCalc) {
            super(call, new Calc[]{listCalc});
            this.listCalc = listCalc;
        }

        public TupleList evaluateList(Evaluator evaluator) {
            TupleList list = listCalc.evaluateList(evaluator);
            Set<List<Member>> set = new HashSet<List<Member>>(list.size());
            TupleList result = list.cloneList(list.size());
            for (List<Member> element : list) {
                if (set.add(element)) {
                    result.add(element);
                }
            }
            return result;
        }
    }
}

// End DistinctFunDef.java
