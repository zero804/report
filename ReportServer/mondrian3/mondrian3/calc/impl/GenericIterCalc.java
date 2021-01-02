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
// Copyright (C) 2008-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.calc.impl;

import mondrian3.calc.*;
import mondrian3.olap.Evaluator;
import mondrian3.olap.Exp;
import mondrian3.olap.type.SetType;

/**
 * Adapter which computes a set expression and converts it to any list or
 * iterable type.
 *
 * @author jhyde
 * @since Nov 7, 2008
 */
public abstract class GenericIterCalc
    extends AbstractCalc
    implements ListCalc, IterCalc
{
    /**
     * Creates a GenericIterCalc without specifying child calculated
     * expressions.
     *
     * <p>Subclass should override {@link #getCalcs()}.
     *
     * @param exp Source expression
     */
    protected GenericIterCalc(Exp exp) {
        super(exp, null);
    }

    /**
     * Creates an GenericIterCalc.
     *
     * @param exp Source expression
     * @param calcs Child compiled expressions
     */
    protected GenericIterCalc(Exp exp, Calc[] calcs) {
        super(exp, calcs);
    }

    public SetType getType() {
        return (SetType) type;
    }

    public TupleList evaluateList(Evaluator evaluator) {
        Object o = evaluate(evaluator);
        if (o instanceof TupleList) {
            return (TupleList) o;
        } else {
            // Iterable
            final TupleIterable iterable = (TupleIterable) o;
            TupleList tupleList =
                TupleCollections.createList(iterable.getArity());
            TupleCursor cursor = iterable.tupleCursor();
            while (cursor.forward()) {
                tupleList.addCurrent(cursor);
            }
            return tupleList;
        }
    }

    public TupleIterable evaluateIterable(Evaluator evaluator) {
        Object o = evaluate(evaluator);
        return (TupleIterable) o;
    }
}

// End GenericIterCalc.java
