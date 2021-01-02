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
package mondrian3.util;

import mondrian3.olap.Util;
import mondrian3.resource.MondrianResource;
import mondrian3.rolap.RolapUtil;

import org.apache.log4j.Logger;

import java.sql.Statement;
import java.util.*;

import javax.script.*;

// Only in Java6 and above

/**
 * Implementation of {@link mondrian3.util.UtilCompatible} that runs in
 * JDK 1.6.
 *
 * <p>Prior to JDK 1.6, this class should never be loaded. Applications should
 * instantiate this class via {@link Class#forName(String)} or better, use
 * methods in {@link mondrian3.olap.Util}, and not instantiate it at all.
 *
 * @author jhyde
 */
public class UtilCompatibleJdk16 extends UtilCompatibleJdk15 {
    private static final Logger LOGGER =
        Logger.getLogger(Util.class);

    public <T> T compileScript(
        Class<T> iface,
        String script,
        String engineName)
    {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName(engineName);
        try {
            engine.eval(script);
            Invocable inv = (Invocable) engine;
            return inv.getInterface(iface);
        } catch (ScriptException e) {
            throw Util.newError(
                e,
                "Error while compiling script to implement " + iface + " SPI");
        }
    }

    @Override
    public void cancelStatement(Statement stmt) {
        try {
            // A call to statement.isClosed() would be great here, but in
            // reality, some drivers will block on this check and the
            // cancellation will never happen.  This is due to the
            // non-thread-safe nature of JDBC and driver implementations. If a
            // thread is currently using the statement, calls to isClosed() are
            // synchronized internally and won't return until the query
            // completes.
            stmt.cancel();
        } catch (Throwable t) {
            // We crush this one. A lot of drivers will complain if cancel() is
            // called on a closed statement, but a call to isClosed() isn't
            // thread safe and might block. See above.

            // Also, we MUST catch all throwables. Some drivers (ie. Hive)
            // will choke on canceled queries and throw a OutOfMemoryError.
            // We can't protect ourselves against this. That's a bug on their
            // side.
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(
                    MondrianResource.instance()
                        .ExecutionStatementCleanupException
                            .ex(t.getMessage(), t),
                    t);
            }
        }
    }

    @Override
    public <T> Set<T> newIdentityHashSet() {
        return Collections.newSetFromMap(
            new IdentityHashMap<T, Boolean>());
    }

    public <T extends Comparable<T>> int binarySearch(
        T[] ts, int start, int end, T t)
    {
        return Arrays.binarySearch(
            ts, start, end, t,
            RolapUtil.ROLAP_COMPARATOR);
    }
}

// End UtilCompatibleJdk16.java
