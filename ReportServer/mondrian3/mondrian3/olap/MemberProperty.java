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
// Copyright (C) 2000-2005 Julian Hyde
// Copyright (C) 2005-2006 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap;

import java.io.PrintWriter;

/**
 * Member property or solve order specification.
 *
 * @author jhyde, 1 March, 2000
 */
public class MemberProperty extends QueryPart {

    private final String name;
    private Exp exp;

    public MemberProperty(String name, Exp exp) {
        this.name = name;
        this.exp = exp;
    }

    protected Object clone() {
        return new MemberProperty(name, (Exp) exp.clone());
    }

    static MemberProperty[] cloneArray(MemberProperty[] x) {
        MemberProperty[] x2 = new MemberProperty[x.length];
        for (int i = 0; i < x.length; i++) {
            x2[i] = (MemberProperty) x[i].clone();
        }
        return x2;
    }

    void resolve(Validator validator) {
        exp = validator.validate(exp, false);
    }

    public Exp getExp() {
        return exp;
    }

    public String getName() {
        return name;
    }

    public Object[] getChildren() {
        return new Exp[] {exp};
    }

    public void unparse(PrintWriter pw) {
        pw.print(name + " = ");
        exp.unparse(pw);
    }

    /**
     * Retrieves a property by name from an array.
     */
    static Exp get(MemberProperty[] a, String name) {
        // TODO: Linear search may be a performance problem.
        for (int i = 0; i < a.length; i++) {
            if (Util.equalName(a[i].name, name)) {
                return a[i].exp;
            }
        }
        return null;
    }
}


// End MemberProperty.java
