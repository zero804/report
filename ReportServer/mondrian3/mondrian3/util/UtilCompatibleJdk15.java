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

import java.lang.annotation.Annotation;
import java.lang.management.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Pattern;

// Only in Java5 and above

/**
 * Implementation of {@link UtilCompatible} which runs in
 * JDK 1.5.
 *
 * <p>Prior to JDK 1.5, this class should never be loaded. Applications should
 * instantiate this class via {@link Class#forName(String)} or better, use
 * methods in {@link mondrian3.olap.Util}, and not instantiate it at all.
 *
 * @author jhyde
 * @since Feb 5, 2007
 */
public class UtilCompatibleJdk15 implements UtilCompatible {
    private static final Logger LOGGER = Logger.getLogger(Util.class);

    /**
     * This generates a BigDecimal with a precision reflecting
     * the precision of the input double.
     *
     * @param d input double
     * @return BigDecimal
     */
    public BigDecimal makeBigDecimalFromDouble(double d) {
        return new BigDecimal(d, MathContext.DECIMAL64);
    }

    public String quotePattern(String s) {
        return Pattern.quote(s);
    }

    @SuppressWarnings("unchecked")
    public <T> T getAnnotation(
        Method method, String annotationClassName, T defaultValue)
    {
        try {
            Class<? extends Annotation> annotationClass =
                (Class<? extends Annotation>)
                    Class.forName(annotationClassName);
            if (method.isAnnotationPresent(annotationClass)) {
                final Annotation annotation =
                    method.getAnnotation(annotationClass);
                final Method method1 =
                    annotation.getClass().getMethod("value");
                return (T) method1.invoke(annotation);
            }
        } catch (IllegalAccessException e) {
            return defaultValue;
        } catch (InvocationTargetException e) {
            return defaultValue;
        } catch (NoSuchMethodException e) {
            return defaultValue;
        } catch (ClassNotFoundException e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public String generateUuidString() {
        return UUID.randomUUID().toString();
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
        threadLocal.remove();
    }

    public Util.MemoryInfo getMemoryInfo() {
        return new Util.MemoryInfo() {
            protected final MemoryPoolMXBean TENURED_POOL =
                findTenuredGenPool();

            public Util.MemoryInfo.Usage get() {
                final MemoryUsage memoryUsage = TENURED_POOL.getUsage();
                return new Usage() {
                    public long getUsed() {
                        return memoryUsage.getUsed();
                    }

                    public long getCommitted() {
                        return memoryUsage.getCommitted();
                    }

                    public long getMax() {
                        return memoryUsage.getMax();
                    }
                };
            }
        };
    }

    public Timer newTimer(String name, boolean isDaemon) {
        return new Timer(name, isDaemon);
    }

    private static MemoryPoolMXBean findTenuredGenPool() {
        for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
            if (pool.getType() == MemoryType.HEAP) {
                return pool;
            }
        }
        throw new AssertionError("Could not find tenured space");
    }

    public void cancelStatement(Statement stmt) {
        try {
            stmt.cancel();
        } catch (Throwable t) {
            // We can't call stmt.isClosed(); the method doesn't exist until
            // JDK 1.6. So, mask out the error.

            // Also, we MUST catch all throwables. Some drivers (ie. Hive)
            // will choke on canceled queries and throw a OutOfMemoryError.
            // We can't protect ourselves against this. That's a bug on their
            // side.
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
        final int i = Collections.binarySearch(
            Arrays.asList(ts).subList(start, end), t,
            RolapUtil.ROLAP_COMPARATOR);
        return (i < 0) ? (i - start) : (i + start);
    }
}

// End UtilCompatibleJdk15.java
