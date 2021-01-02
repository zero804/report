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
package mondrian3.olap.fun;

import mondrian3.calc.*;
import mondrian3.calc.impl.AbstractIterCalc;
import mondrian3.mdx.NamedSetExpr;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.*;

import java.util.List;


/**
 * Definition of the <code>AS</code> MDX operator.
 *
 * <p>Using <code>AS</code>, you can define an alias for an MDX expression
 * anywhere it appears in a query, and use that alias as you would a calculated
 * yet.
 *
 * @author jhyde
 * @since Oct 7, 2009
 */
class AsFunDef extends FunDefBase {
    public static final Resolver RESOLVER = new ResolverImpl();
    private final Query.ScopedNamedSet scopedNamedSet;

    /**
     * Creates an AsFunDef.
     *
     * @param scopedNamedSet Named set definition
     */
    private AsFunDef(Query.ScopedNamedSet scopedNamedSet) {
        super(
            "AS",
            "<Expression> AS <Name>",
            "Assigns an alias to an expression",
            "ixxn");
        this.scopedNamedSet = scopedNamedSet;
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        // Argument 0, the definition of the set, has been resolved since the
        // scoped named set was created. Implicit conversions, like converting
        // a member to a set, have been performed. Use the new expression.
        scopedNamedSet.setExp(call.getArg(0));

        return new AbstractIterCalc(call, new Calc[0]) {
            public TupleIterable evaluateIterable(
                Evaluator evaluator)
            {
                final Evaluator.NamedSetEvaluator namedSetEvaluator =
                    evaluator.getNamedSetEvaluator(scopedNamedSet, false);
                return namedSetEvaluator.evaluateTupleIterable(evaluator);
            }
        };
    }

    private static class ResolverImpl extends ResolverBase {
        public ResolverImpl() {
            super("AS", null, null, Syntax.Infix);
        }

        public FunDef resolve(
            Exp[] args,
            Validator validator,
            List<Conversion> conversions)
        {
            final Exp exp = args[0];
            if (!validator.canConvert(
                    0, args[0], Category.Set, conversions))
            {
                return null;
            }

            // By the time resolve is called, the id argument has already been
            // resolved... to a named set, namely itself. That's not pretty.
            // We'd rather it stayed as an id, and we'd rather that a named set
            // was not visible in the scope that defines it. But we can work
            // with this.
            final String name =
                ((NamedSetExpr) args[1]).getNamedSet().getName();

            final Query.ScopedNamedSet scopedNamedSet =
                (Query.ScopedNamedSet) ((NamedSetExpr) args[1]).getNamedSet();
//                validator.getQuery().createScopedNamedSet(
//                    name, (QueryPart) exp, exp);
            return new AsFunDef(scopedNamedSet);
        }
    }
}

// End AsFunDef.java
