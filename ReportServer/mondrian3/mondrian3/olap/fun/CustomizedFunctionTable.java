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
// Copyright (C) 2008-2009 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap.fun;

import mondrian3.olap.FunDef;
import mondrian3.olap.FunTable;

import java.util.HashSet;
import java.util.Set;

/**
 * Interface to build a customized function table, selecting functions from the
 * set of supported functions in an instance of {@link BuiltinFunTable}.
 *
 * @author Rushan Chen
 */
public class CustomizedFunctionTable extends FunTableImpl {

    Set<String> supportedBuiltinFunctions;
    Set<FunDef> specialFunctions;

    public CustomizedFunctionTable(Set<String> builtinFunctions) {
        supportedBuiltinFunctions = builtinFunctions;
        this.specialFunctions = new HashSet<FunDef>();
    }

    public CustomizedFunctionTable(
        Set<String> builtinFunctions,
        Set<FunDef> specialFunctions)
    {
        this.supportedBuiltinFunctions = builtinFunctions;
        this.specialFunctions = specialFunctions;
    }

    public void defineFunctions(Builder builder) {
        final FunTable builtinFunTable = BuiltinFunTable.instance();

        // Includes all the keywords form builtin function table
        for (String reservedWord : builtinFunTable.getReservedWords()) {
            builder.defineReserved(reservedWord);
        }

        // Add supported builtin functions
        for (Resolver resolver : builtinFunTable.getResolvers()) {
            if (supportedBuiltinFunctions.contains(resolver.getName())) {
                builder.define(resolver);
            }
        }

        // Add special function definitions
        for (FunDef funDef : specialFunctions) {
            builder.define(funDef);
        }
    }
}

// End CustomizedFunctionTable.java
