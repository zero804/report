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
// Copyright (C) 2006-2009 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap.fun.extra;

import mondrian3.calc.*;
import mondrian3.calc.impl.AbstractMemberCalc;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.*;
import mondrian3.olap.fun.FunDefBase;

import java.util.List;

/**
 * Definition of the <code>CalculatedChild</code> MDX function.
 *
 * <p>Syntax:
 * <blockquote><code>&lt;Member&gt;
 * CalculatedChild(&lt;String&gt;)</code></blockquote>
 *
 * @author bchow
 * @since 2006/4/12
 */
public class CalculatedChildFunDef extends FunDefBase {
    public static final CalculatedChildFunDef instance =
        new CalculatedChildFunDef();

    CalculatedChildFunDef() {
        super(
            "CalculatedChild",
            "Returns an existing calculated child member with name <String> from the specified <Member>.",
            "mmmS");
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final MemberCalc memberCalc = compiler.compileMember(call.getArg(0));
        final StringCalc stringCalc = compiler.compileString(call.getArg(1));

        return new AbstractMemberCalc(
            call,
            new Calc[] {memberCalc, stringCalc})
        {
            public Member evaluateMember(Evaluator evaluator) {
                Member member = memberCalc.evaluateMember(evaluator);
                String name = stringCalc.evaluateString(evaluator);
                return getCalculatedChild(member, name, evaluator);
            }
        };
    }

    private Member getCalculatedChild(
        Member parent,
        String childName,
        Evaluator evaluator)
    {
        final SchemaReader schemaReader =
                evaluator.getQuery().getSchemaReader(true);
        Level childLevel = parent.getLevel().getChildLevel();
        if (childLevel == null) {
            return parent.getHierarchy().getNullMember();
        }
        List<Member> calcMemberList =
            schemaReader.getCalculatedMembers(childLevel);

        for (Member child : calcMemberList) {
            // the parent check is required in case there are parallel children
            // with the same names
            if (child.getParentMember().equals(parent)
                && child.getName().equals(childName))
            {
                return child;
            }
        }

        return parent.getHierarchy().getNullMember();
    }
}


// End CalculatedChildFunDef.java
