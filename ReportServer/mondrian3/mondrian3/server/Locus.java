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
// Copyright (C) 2011-2013 Pentaho
// All Rights Reserved.
*/
package mondrian3.server;

import mondrian3.olap.MondrianServer;
import mondrian3.rolap.RolapConnection;
import mondrian3.util.ArrayStack;

/**
 * Point of execution from which a service is invoked.
 */
public class Locus {
    public final Execution execution;
    public final String message;
    public final String component;

    private static final ThreadLocal<ArrayStack<Locus>> THREAD_LOCAL =
        new ThreadLocal<ArrayStack<Locus>>() {
            protected ArrayStack<Locus> initialValue() {
                return new ArrayStack<Locus>();
            }
        };

    /**
     * Creates a Locus.
     *
     * @param execution Execution context
     * @param component Description of a the component executing the query,
     *   generally a method name, e.g. "SqlTupleReader.readTuples"
     * @param message Description of the purpose of this statement, to be
     *   printed if there is an error
     */
    public Locus(
        Execution execution,
        String component,
        String message)
    {
        assert execution != null;
        this.execution = execution;
        this.component = component;
        this.message = message;
    }

    public static void pop(Locus locus) {
        final Locus pop = THREAD_LOCAL.get().pop();
        assert locus == pop;
    }

    public static void push(Locus locus) {
        THREAD_LOCAL.get().push(locus);
    }

    public static Locus peek() {
        return THREAD_LOCAL.get().peek();
    }

    public static <T> T execute(
        RolapConnection connection,
        String component,
        Action<T> action)
    {
        final Statement statement = connection.getInternalStatement();
        final Execution execution = new Execution(statement, 0);
        return execute(execution, component, action);
    }

    public static <T> T execute(
        Execution execution,
        String component,
        Action<T> action)
    {
        final Locus locus =
            new Locus(
                execution,
                component,
                null);
        Locus.push(locus);
        try {
            return action.execute();
        } finally {
            Locus.pop(locus);
        }
    }

    public final MondrianServer getServer() {
        return execution.statement.getMondrianConnection().getServer();
    }

    public interface Action<T> {
        T execute();
    }
}

// End Locus.java
