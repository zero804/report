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
// Copyright (C) 2005-2009 Pentaho
// All Rights Reserved.
*/
package mondrian3.udf;

import mondrian3.olap.*;
import mondrian3.olap.type.NumericType;
import mondrian3.olap.type.Type;
import mondrian3.spi.UserDefinedFunction;

/**
 * Definition of the user-defined function "NullValue" which always
 * returns Java "null".
 *
 * @author remberson,jhyde
 */
public class NullValueUdf implements UserDefinedFunction {

    public String getName() {
        return "NullValue";
    }

    public String getDescription() {
        return "Returns the null value";
    }

    public Syntax getSyntax() {
        return Syntax.Function;
    }

    public Type getReturnType(Type[] parameterTypes) {
        return new NumericType();
    }

    public Type[] getParameterTypes() {
        return new Type[0];
    }

    public Object execute(Evaluator evaluator, Argument[] arguments) {
        return Util.nullValue;
    }

    public String[] getReservedWords() {
        // This function does not require any reserved words.
        return null;
    }
}

// End NullValueUdf.java
