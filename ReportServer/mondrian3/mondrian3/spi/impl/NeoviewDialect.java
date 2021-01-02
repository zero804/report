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
package mondrian3.spi.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of {@link mondrian3.spi.Dialect} for the Neoview database.
 *
 * @author jhyde
 * @since Dec 4, 2009
 */
public class NeoviewDialect extends JdbcDialectImpl {

    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            NeoviewDialect.class,
            DatabaseProduct.NEOVIEW);

    /**
     * Creates a NeoviewDialect.
     *
     * @param connection Connection
     */
    public NeoviewDialect(Connection connection) throws SQLException {
        super(connection);
    }

    public boolean _supportsOrderByNullsLast() {
        return true;
    }

    public boolean requiresOrderByAlias() {
        return true;
    }

    public boolean requiresAliasForFromQuery() {
        return true;
    }

    public boolean allowsDdl() {
        // We get the following error in the test environment. It might be a bit
        // pessimistic to say DDL is never allowed.
        //
        // ERROR[1116] The current partitioning scheme requires a user-specified
        // clustering key on object NEO.PENTAHO."foo"
        return false;
    }

    public boolean supportsGroupByExpressions() {
        return false;
    }

    public String generateInline(
        List<String> columnNames,
        List<String> columnTypes,
        List<String[]> valueList)
    {
        return generateInlineForAnsi(
            "t", columnNames, columnTypes, valueList, true);
    }
}

// End NeoviewDialect.java
