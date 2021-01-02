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
// Copyright (C) 2006-2010 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.udf;

import mondrian3.olap.*;
import mondrian3.olap.type.*;
import mondrian3.spi.UserDefinedFunction;
import mondrian3.util.Format;

import java.util.*;

/**
 * User-defined function <code>CurrentDateMember</code>.  Arguments to the
 * function are as follows:
 *
 * <blockquote>
 * <code>
 * CurrentDateMember(&lt;Hierarchy&gt;, &lt;FormatString&gt;[, &lt;Find&gt;)
 * returns &lt;Member&gt;
 * </code>
 * </blockquote>
 *
 * The function returns the member from the specified hierarchy that matches
 * the current date, to the granularity specified by the &lt;FormatString&gt;.
 *
 * The format string conforms to the format string implemented by
 * {@link Format}.
 *
 * @author Zelaine Fong
 */
public class CurrentDateMemberUdf implements UserDefinedFunction {
    private Object resultDateMember = null;

    public Object execute(Evaluator evaluator, Argument[] arguments) {
        if (resultDateMember != null) {
            return resultDateMember;
        }

        // determine the current date
        Object formatArg = arguments[1].evaluateScalar(evaluator);

        final Locale locale = Locale.getDefault();
        final Format format = new Format((String) formatArg, locale);
        String currDateStr = format.format(getDate(evaluator, arguments));

        // determine the match type
        MatchType matchType;
        if (arguments.length == 3) {
            String matchStr = arguments[2].evaluateScalar(evaluator).toString();
            matchType = Enum.valueOf(MatchType.class, matchStr);
        } else {
            matchType = MatchType.EXACT;
        }

        List<Id.Segment> uniqueNames = Util.parseIdentifier(currDateStr);
        resultDateMember =
            evaluator.getSchemaReader().getMemberByUniqueName(
                uniqueNames, false, matchType);
        if (resultDateMember != null) {
            return resultDateMember;
        }

        // if there is no matching member, return the null member for
        // the specified dimension/hierarchy
        Object arg0 = arguments[0].evaluate(evaluator);
        if (arg0 instanceof Hierarchy) {
            resultDateMember = ((Hierarchy) arg0).getNullMember();
        } else {
            resultDateMember =
                ((Dimension) arg0).getHierarchy().getNullMember();
        }
        return resultDateMember;
    }

    /*
     * Package private function created for proper testing.
     */
    Date getDate(Evaluator evaluator, Argument[] arguments) {
        return evaluator.getQueryStartTime();
    }

    public String getDescription() {
        return "Returns the closest or exact member within the specified "
            + "dimension corresponding to the current date, in the format "
            + "specified by the format parameter. "
            + "Format strings are the same as used by the MDX Format function, "
            + "namely the Visual Basic format strings. "
            + "See http://www.apostate.com/programming/vb-format.html.";
    }

    public String getName() {
        return "CurrentDateMember";
    }

    public Type[] getParameterTypes() {
        return new Type[] {
            new HierarchyType(null, null),
            new StringType(),
            new SymbolType()
        };
    }

    public String[] getReservedWords() {
        return new String[] {
            "EXACT",
            "BEFORE",
            "AFTER"
        };
    }

    public Type getReturnType(Type[] parameterTypes) {
        return MemberType.Unknown;
    }

    public Syntax getSyntax() {
        return Syntax.Function;
    }
}

// End CurrentDateMemberUdf.java
