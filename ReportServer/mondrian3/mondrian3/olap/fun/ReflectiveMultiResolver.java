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
// Copyright (C) 2002-2005 Julian Hyde
// Copyright (C) 2005-2009 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap.fun;

import mondrian3.olap.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Resolver which uses reflection to instantiate a {@link FunDef}.
 * This reduces the amount of anonymous classes.
 *
 * @author jhyde
 * @since Mar 23, 2006
 */
public class ReflectiveMultiResolver extends MultiResolver {
    private final Constructor constructor;
    private final String[] reservedWords;

    public ReflectiveMultiResolver(
        String name,
        String signature,
        String description,
        String[] signatures,
        Class clazz)
    {
        this(name, signature, description, signatures, clazz, null);
    }

    public ReflectiveMultiResolver(
        String name,
        String signature,
        String description,
        String[] signatures,
        Class clazz,
        String[] reservedWords)
    {
        super(name, signature, description, signatures);
        try {
            this.constructor = clazz.getConstructor(new Class[] {FunDef.class});
        } catch (NoSuchMethodException e) {
            throw Util.newInternal(
                e, "Error while registering resolver class " + clazz);
        }
        this.reservedWords = reservedWords;
    }

    protected FunDef createFunDef(Exp[] args, FunDef dummyFunDef) {
        try {
            return (FunDef) constructor.newInstance(new Object[] {dummyFunDef});
        } catch (InstantiationException e) {
            throw Util.newInternal(
                e, "Error while instantiating FunDef '" + getSignature() + "'");
        } catch (IllegalAccessException e) {
            throw Util.newInternal(
                e, "Error while instantiating FunDef '" + getSignature() + "'");
        } catch (InvocationTargetException e) {
            throw Util.newInternal(
                e, "Error while instantiating FunDef '" + getSignature() + "'");
        }
    }

    public String[] getReservedWords() {
        if (reservedWords != null) {
            return reservedWords;
        }
        return super.getReservedWords();
    }
}

// End ReflectiveMultiResolver.java
