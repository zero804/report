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
// Copyright (C) 2006-2007 Pentaho
// All Rights Reserved.
*/
package mondrian3.calc;

import mondrian3.olap.Evaluator;
import mondrian3.olap.Member;

/**
 * Expression which yields a tuple.
 *
 * <p>The tuple is represented as an array of {@link Member} objects,
 * <code>null</code> to represent the null tuple.
 *
 * <p>When implementing this interface, it is convenient to extend
 * {@link mondrian3.calc.impl.AbstractTupleCalc}, but it is not required.
 *
 * @author jhyde
 * @since Sep 27, 2005
 */
public interface TupleCalc extends Calc {
    /**
     * Evaluates this expression to yield a tuple.
     *
     * <p>A tuple cannot contain any null members. If any of the members is
     * null, this method must return a null.
     *
     * @post result == null || !tupleContainsNullMember(result)
     *
     * @param evaluator Evaluation context
     * @return an array of members, or null to represent the null tuple
     */
    Member[] evaluateTuple(Evaluator evaluator);
}

// End TupleCalc.java
