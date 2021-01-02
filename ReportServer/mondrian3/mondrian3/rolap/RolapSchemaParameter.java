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
package mondrian3.rolap;

import mondrian3.calc.*;
import mondrian3.calc.impl.GenericCalc;
import mondrian3.olap.*;
import mondrian3.olap.type.Type;
import mondrian3.resource.MondrianResource;

/**
 * Parameter that is defined in a schema.
 *
 * @author jhyde
 * @since Jul 20, 2006
 */
public class RolapSchemaParameter implements Parameter, ParameterCompilable {
    private final RolapSchema schema;
    private final String name;
    private String description;
    private String defaultExpString;
    private Type type;
    private final boolean modifiable;
    private Object value;
    private boolean assigned;
    private Object cachedDefaultValue;

    RolapSchemaParameter(
        RolapSchema schema,
        String name,
        String defaultExpString,
        String description,
        Type type,
        boolean modifiable)
    {
        assert defaultExpString != null;
        assert name != null;
        assert schema != null;
        assert type != null;
        this.schema = schema;
        this.name = name;
        this.defaultExpString = defaultExpString;
        this.description = description;
        this.type = type;
        this.modifiable = modifiable;
        schema.parameterList.add(this);
    }

    RolapSchema getSchema() {
        return schema;
    }

    public boolean isModifiable() {
        return modifiable;
    }

    public Scope getScope() {
        return Scope.Schema;
    }

    public Type getType() {
        return type;
    }

    public Exp getDefaultExp() {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        if (!modifiable) {
            throw MondrianResource.instance().ParameterIsNotModifiable.ex(
                getName(), getScope().name());
        }
        this.assigned = true;
        this.value = value;
    }

    public boolean isSet() {
        return assigned;
    }

    public void unsetValue() {
        if (!modifiable) {
            throw MondrianResource.instance().ParameterIsNotModifiable.ex(
                getName(), getScope().name());
        }
        assigned = false;
        value = null;
    }

    public Calc compile(ExpCompiler compiler) {
        // Parse and compile the expression for the default value.
        Exp defaultExp = compiler.getValidator()
            .getQuery()
            .getConnection()
            .parseExpression(defaultExpString);
        defaultExp = compiler.getValidator().validate(defaultExp, true);
        final Calc defaultCalc = defaultExp.accept(compiler);

        // Generate a program which looks at the assigned value first,
        // and if it is not set, returns the default expression.
        return new GenericCalc(defaultExp) {
            public Calc[] getCalcs() {
                return new Calc[] {defaultCalc};
            }

            public Object evaluate(Evaluator evaluator) {
                if (value != null) {
                    return value;
                }
                if (cachedDefaultValue == null) {
                    cachedDefaultValue = defaultCalc.evaluate(evaluator);
                }
                return cachedDefaultValue;
            }
        };
    }
}

// End RolapSchemaParameter.java
