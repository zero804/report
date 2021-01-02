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
// Copyright (C) 2006-2007 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.udf;

import mondrian3.olap.Evaluator;
import mondrian3.olap.Syntax;
import mondrian3.olap.type.StringType;
import mondrian3.olap.type.Type;
import mondrian3.spi.UserDefinedFunction;
import mondrian3.util.Format;

import java.util.Date;
import java.util.Locale;

/**
 * User-defined function <code>CurrentDateString<code>, which returns the
 * current date value as a formatted string, based on a format string passed in
 * as a parameter.  The format string conforms to the format string implemented
 * by {@link Format}.
 *
 * @author Zelaine Fong
 */
public class CurrentDateStringUdf implements UserDefinedFunction {

    public Object execute(Evaluator evaluator, Argument[] arguments) {
        Object arg = arguments[0].evaluateScalar(evaluator);

        final Locale locale = Locale.getDefault();
        final Format format = new Format((String) arg, locale);
        Date currDate = evaluator.getQueryStartTime();
        return format.format(currDate);
    }

    public String getDescription() {
        return "Returns the current date formatted as specified by the format "
            + "parameter.";
    }

    public String getName() {
        return "CurrentDateString";
    }

    public Type[] getParameterTypes() {
        return new Type[] { new StringType() };
    }

    public String[] getReservedWords() {
        return null;
    }

    public Type getReturnType(Type[] parameterTypes) {
        return new StringType();
    }

    public Syntax getSyntax() {
        return Syntax.Function;
    }

}

// End CurrentDateStringUdf.java
