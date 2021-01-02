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
// Copyright (C) 2006-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.calc.impl;

import mondrian3.calc.*;
import mondrian3.olap.*;
import mondrian3.olap.type.*;

import java.util.List;

/**
 * Enhanced expression compiler. It can generate code to convert between
 * scalar types.
 *
 * @author jhyde
 * @since Sep 29, 2005
 */
public class BetterExpCompiler extends AbstractExpCompiler {
    public BetterExpCompiler(Evaluator evaluator, Validator validator) {
        super(evaluator, validator);
    }

    public BetterExpCompiler(
        Evaluator evaluator,
        Validator validator,
        List<ResultStyle> resultStyles)
    {
        super(evaluator, validator, resultStyles);
    }

    public TupleCalc compileTuple(Exp exp) {
        final Calc calc = compile(exp);
        final Type type = exp.getType();
        if (type instanceof TupleType) {
            assert calc instanceof TupleCalc;
            return (TupleCalc) calc;
        } else if (type instanceof MemberType) {
            assert calc instanceof MemberCalc;
            final MemberCalc memberCalc = (MemberCalc) calc;
            return new AbstractTupleCalc(exp, new Calc[] {memberCalc}) {
                public Member[] evaluateTuple(Evaluator evaluator) {
                    return new Member[] {memberCalc.evaluateMember(evaluator)};
                }
            };
        } else {
            throw Util.newInternal("cannot cast " + exp);
        }
    }

    public ListCalc compileList(Exp exp, boolean mutable) {
        final ListCalc listCalc = super.compileList(exp, mutable);
        if (mutable && listCalc.getResultStyle() == ResultStyle.LIST) {
            // Wrap the expression in an expression which creates a mutable
            // copy.
            return new CopyListCalc(listCalc);
        }
        return listCalc;
    }

    private static class CopyListCalc extends AbstractListCalc {
        private final ListCalc listCalc;

        public CopyListCalc(ListCalc listCalc) {
            super(new DummyExp(listCalc.getType()), new Calc[]{listCalc});
            this.listCalc = listCalc;
        }

        public TupleList evaluateList(Evaluator evaluator) {
            TupleList list = listCalc.evaluateList(evaluator);
            return list.cloneList(-1);
        }
    }
}

// End BetterExpCompiler.java
