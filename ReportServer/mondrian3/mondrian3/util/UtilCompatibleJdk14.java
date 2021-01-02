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
// Copyright (C) 2007-2013 Pentaho
// All Rights Reserved.
*/
package mondrian3.util;

import mondrian3.olap.Util;
import mondrian3.resource.MondrianResource;
import mondrian3.rolap.RolapUtil;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Statement;
import java.util.*;

/**
 * Implementation of {@link UtilCompatible} which runs in
 * JDK 1.4.
 *
 * <p>The code uses JDK 1.5 constructs such as generics and for-each loops,
 * but retroweaver can convert these. It does not use
 * <code>java.util.EnumSet</code>, which is important, because retroweaver has
 * trouble with this.
 *
 * @author jhyde
 * @since Feb 5, 2007
 */
public class UtilCompatibleJdk14 implements UtilCompatible {
    private static final Logger LOGGER = Logger.getLogger(Util.class);
    private static String previousUuid = "";
    private static final String UUID_BASE =
        Long.toHexString(new Random().nextLong());

    /**
     * This generates a BigDecimal that can have a precision that does
     * not reflect the precision of the input double.
     *
     * @param d input double
     * @return BigDecimal
     */
    public BigDecimal makeBigDecimalFromDouble(double d) {
        return new BigDecimal(d);
    }

    public String quotePattern(String s) {
        int slashEIndex = s.indexOf("\\E");
        if (slashEIndex == -1) {
            return "\\Q" + s + "\\E";
        }
        StringBuilder sb = new StringBuilder(s.length() * 2);
        sb.append("\\Q");
        int current = 0;
        while ((slashEIndex = s.indexOf("\\E", current)) != -1) {
            sb.append(s.substring(current, slashEIndex));
            current = slashEIndex + 2;
            sb.append("\\E\\\\E\\Q");
        }
        sb.append(s.substring(current, s.length()));
        sb.append("\\E");
        return sb.toString();
    }

    public <T> T getAnnotation(
        Method method, String annotationClassName, T defaultValue)
    {
        return defaultValue;
    }

    public String generateUuidString() {
        return generateUuidStringStatic();
    }

    public static synchronized String generateUuidStringStatic() {
        while (true) {
            String uuid =
                UUID_BASE
                + Long.toHexString(System.currentTimeMillis());
            if (!uuid.equals(previousUuid)) {
                previousUuid = uuid;
                return uuid;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public <T> T compileScript(
        Class<T> iface,
        String script,
        String engineName)
    {
        throw new UnsupportedOperationException(
            "Scripting not supported until Java 1.6");
    }

    public <T> void threadLocalRemove(ThreadLocal<T> threadLocal) {
        // nothing: ThreadLocal.remove() does not exist until JDK 1.5
    }

    public Util.MemoryInfo getMemoryInfo() {
        return new Util.MemoryInfo() {
            public Usage get() {
                return new Usage() {
                    public long getUsed() {
                        return 0;
                    }

                    public long getCommitted() {
                        return 0;
                    }

                    public long getMax() {
                        return 0;
                    }
                };
            }
        };
    }

    public Timer newTimer(String name, boolean isDaemon) {
        return new Timer(isDaemon);
    }

    public void cancelStatement(Statement stmt) {
        try {
            stmt.cancel();
        } catch (Throwable t) {
            // We can't call stmt.isClosed(); the method doesn't exist until
            // JDK 1.6. So, mask out the error.
            if (t.getMessage().equals(
                    "org.apache.commons.dbcp.DelegatingStatement is closed."))
            {
                return;
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(
                    MondrianResource.instance()
                        .ExecutionStatementCleanupException
                            .ex(t.getMessage(), t),
                    t);
            }
        }
    }

    public <T> Set<T> newIdentityHashSet() {
        return Util.newIdentityHashSetFake();
    }

    public <T extends Comparable<T>> int binarySearch(
        T[] ts, int start, int end, T t)
    {
        return Collections.binarySearch(
            Arrays.asList(ts).subList(start, end), t,
            RolapUtil.ROLAP_COMPARATOR);
    }
}

// End UtilCompatibleJdk14.java
