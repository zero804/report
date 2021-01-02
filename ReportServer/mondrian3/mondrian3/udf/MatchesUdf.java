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
// Copyright (C) 2006-2009 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.udf;

import mondrian3.olap.Evaluator;
import mondrian3.olap.Syntax;
import mondrian3.olap.type.*;
import mondrian3.spi.UserDefinedFunction;

import java.util.regex.Pattern;

/**
 * User-defined function <code>MATCHES</code>.
 *
 * @author schoi
 */
public class MatchesUdf implements UserDefinedFunction {

    public Object execute(Evaluator evaluator, Argument[] arguments) {
        Object arg0 = arguments[0].evaluateScalar(evaluator);
        Object arg1 = arguments[1].evaluateScalar(evaluator);

        return Boolean.valueOf(Pattern.matches((String)arg1, (String)arg0));
    }

    public String getDescription() {
        return "Returns true if the string matches the regular expression.";
    }

    public String getName() {
        return "MATCHES";
    }

    public Type[] getParameterTypes() {
        return new Type[] {
            new StringType(),
            new StringType()
        };
    }

    public String[] getReservedWords() {
        // This function does not require any reserved words.
        return null;
    }

    public Type getReturnType(Type[] parameterTypes) {
        return new BooleanType();
    }

    public Syntax getSyntax() {
        return Syntax.Infix;
    }

}

// End MatchesUdf.java
