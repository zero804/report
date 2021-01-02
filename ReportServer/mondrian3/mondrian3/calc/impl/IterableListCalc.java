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

/**
 * Adapter that converts a {@link mondrian3.calc.IterCalc} to a
 * {@link mondrian3.calc.ListCalc}.
 *
 * @author jhyde
 * @since Oct 23, 2008
 */
public class IterableListCalc extends AbstractListCalc {
    private final IterCalc iterCalc;

    /**
     * Creates an IterableListCalc.
     *
     * @param iterCalc Calculation that returns an iterable.
     */
    public IterableListCalc(IterCalc iterCalc) {
        super(new DummyExp(iterCalc.getType()), new Calc[] {iterCalc});
        this.iterCalc = iterCalc;
    }

    public TupleList evaluateList(Evaluator evaluator) {
        // A TupleIterCalc is allowed to return a list. If so, save the copy.
        final TupleIterable iterable =
            iterCalc.evaluateIterable(evaluator);
        if (iterable instanceof TupleList) {
            return (TupleList) iterable;
        }

        final TupleList list = TupleCollections.createList(iterable.getArity());
        final TupleCursor tupleCursor = iterable.tupleCursor();
        while (tupleCursor.forward()) {
            // REVIEW: Worth creating TupleList.addAll(TupleCursor)?
            list.addCurrent(tupleCursor);
        }
        return list;
    }
}

// End IterableListCalc.java
