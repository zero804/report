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
// Copyright (C) 2006-2010 Pentaho
// All Rights Reserved.
*/
package mondrian3.mdx;

import mondrian3.calc.Calc;
import mondrian3.calc.ExpCompiler;
import mondrian3.calc.impl.ConstantCalc;
import mondrian3.olap.*;
import mondrian3.olap.type.MemberType;
import mondrian3.olap.type.Type;

/**
 * Usage of a {@link mondrian3.olap.Member} as an MDX expression.
 *
 * @author jhyde
 * @since Sep 26, 2005
 */
public class MemberExpr extends ExpBase implements Exp {
    private final Member member;
    private MemberType type;

    /**
     * Creates a member expression.
     *
     * @param member Member
     * @pre member != null
     */
    public MemberExpr(Member member) {
        Util.assertPrecondition(member != null, "member != null");
        this.member = member;
    }

    /**
     * Returns the member.
     *
     * @post return != null
     */
    public Member getMember() {
        return member;
    }

    public String toString() {
        return member.getUniqueName();
    }

    public Type getType() {
        if (type == null) {
            type = MemberType.forMember(member);
        }
        return type;
    }

    public MemberExpr clone() {
        return new MemberExpr(member);
    }

    public int getCategory() {
        return Category.Member;
    }

    public Exp accept(Validator validator) {
        return this;
    }

    public Calc accept(ExpCompiler compiler) {
        return ConstantCalc.constantMember(member);
    }

    public Object accept(MdxVisitor visitor) {
        return visitor.visit(this);
    }
}

// End MemberExpr.java
