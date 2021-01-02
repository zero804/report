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
// Copyright (C) 2009-2009 Pentaho
// All Rights Reserved.
*/
package mondrian3.spi;

import java.sql.Connection;
import javax.sql.DataSource;

/**
 * Factory that creates {@link Dialect} objects.
 *
 * <p>If you create a class that implements {@link Dialect}, you may optionally
 * provide a factory by creating a constant field in that class. For
 * example:</p>
 *
 * <pre>
 * public class MyDialect implements Dialect {
 *     public static final DialectFactory FACTORY =
 *         new JdbcDialectFactory(MyDialect.class, null);
 *
 *     public MyDialect(Connection connection) {
 *         ...
 *     }
 *
 *     ...
 * }</pre>
 *
 * <p>(The field must be public, static, final, named "FACTORY", of type
 * {@link mondrian3.spi.DialectFactory} or a subclass, and its value must not be
 * null.)</p>
 *
 * <p>Explicitly providing a factory gives you more control about how dialects
 * are produced.</p>
 *
 * <p>If you do not provide such a field, Mondrian requires that the dialect has
 * a public constructor that takes a {@link java.sql.Connection} as a parameter,
 * and automatically creates a factory that calls the class's public
 * constructor.</p>
 *
 * <p>However, an explicit DialectFactory is superior:<ol>
 * <li>When a dialect cannot handle a given connection, a dialect factory can
 *     return {@code null}, whereas a dialect's constructor can only throw an
 *     exception.
 * <li>A dialect factory can maintain a pool or cache of dialect objects.
 * </ol>
 *
 * <p>If your dialect is a subclass of {@link mondrian3.spi.impl.JdbcDialectImpl}
 * you may wish to use a dialect factory that is a subclass of
 * {@link mondrian3.spi.impl.JdbcDialectFactory}.
 *
 * @author jhyde
 * @since Jan 13, 2009
 */
public interface DialectFactory {
    /**
     * Creates a Dialect.
     *
     * <p>If the dialect cannot handle this connection, returns null.
     *
     * @param dataSource JDBC data source
     * @param connection JDBC connection
     *
     * @return dialect for this connection, or null if this factory's dialect
     * is not appropriate for the connection
     *
     * @throws RuntimeException if underlying systems give an error
     */
    Dialect createDialect(
        DataSource dataSource,
        Connection connection);
}

// End DialectFactory.java
