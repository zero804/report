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
// Copyright (C) 2008-2009 Pentaho
// All Rights Reserved.
*/
package mondrian3.spi.impl;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of {@link mondrian3.spi.Dialect} for old versions of the IBM
 * DB2/AS400 database. Modern versions of DB2/AS400 use
 * {@link mondrian3.spi.impl.Db2Dialect}.
 *
 * @see mondrian3.spi.impl.Db2Dialect
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
public class Db2OldAs400Dialect extends Db2Dialect {

    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            Db2OldAs400Dialect.class,
            DatabaseProduct.DB2_OLD_AS400);

    /**
     * Creates a Db2OldAs400Dialect.
     *
     * @param connection Connection
     */
    public Db2OldAs400Dialect(Connection connection) throws SQLException {
        super(connection);
    }

    public boolean allowsFromQuery() {
        // Older versions of AS400 do not allow FROM
        // subqueries in the FROM clause.
        return false;
    }
}

// End Db2OldAs400Dialect.java
