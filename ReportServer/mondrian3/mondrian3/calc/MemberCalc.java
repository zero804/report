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
 * Expression which yields a {@link Member}.
 *
 * <p>When implementing this interface, it is convenient to extend
 * {@link mondrian3.calc.impl.AbstractMemberCalc}, but it is not required.

 * @author jhyde
 * @since Sep 26, 2005
 */
public interface MemberCalc extends Calc {
    /**
     * Evaluates this expression to yield a member.
     *
     * <p>May return the null member (see
     * {@link mondrian3.olap.Hierarchy#getNullMember()}) but never null.
     *
     * @param evaluator Evaluation context
     * @return a member
     */
    Member evaluateMember(Evaluator evaluator);
}

// End MemberCalc.java
