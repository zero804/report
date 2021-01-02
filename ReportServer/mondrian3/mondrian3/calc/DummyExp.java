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
// Copyright (C) 2006-2006 Pentaho
// All Rights Reserved.
*/
package mondrian3.calc;

import mondrian3.mdx.MdxVisitor;
import mondrian3.olap.Exp;
import mondrian3.olap.Validator;
import mondrian3.olap.type.Type;

import java.io.PrintWriter;

/**
 * Dummy expression which exists only to wrap a
 * {@link mondrian3.olap.type.Type}.
 *
 * @author jhyde
 * @since Sep 26, 2005
 */
public class DummyExp implements Exp {
    private final Type type;

    public DummyExp(Type type) {
        this.type = type;
    }

    public DummyExp clone() {
        throw new UnsupportedOperationException();
    }

    public int getCategory() {
        throw new UnsupportedOperationException();
    }

    public Type getType() {
        return type;
    }

    public void unparse(PrintWriter pw) {
        throw new UnsupportedOperationException();
    }

    public Exp accept(Validator validator) {
        throw new UnsupportedOperationException();
    }

    public Calc accept(ExpCompiler compiler) {
        throw new UnsupportedOperationException();
    }

    public Object accept(MdxVisitor visitor) {
        throw new UnsupportedOperationException();
    }

}

// End DummyExp.java
