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
// Copyright (C) 2007-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap4j;

import mondrian3.rolap.RolapConnection;

import org.olap4j.OlapException;

import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * Instantiates classes to implement the olap4j API against the
 * Mondrian OLAP engine.
 *
 * <p>There are implementations for JDBC 3.0 (which occurs in JDK 1.5)
 * and JDBC 4.0 (which occurs in JDK 1.6).
 *
 * @author jhyde
 * @since Jun 14, 2007
 */
interface Factory {
    /**
     * Creates a connection.
     *
     * @param driver Driver
     * @param url URL of server
     * @param info Properties defining the connection
     * @return Connection
     * @throws SQLException on error
     */
    Connection newConnection(
        MondrianOlap4jDriver driver,
        String url,
        Properties info) throws SQLException;

    /**
     * Creates an empty result set.
     *
     * @param olap4jConnection Connection
     * @return Result set
     */
    EmptyResultSet newEmptyResultSet(
        MondrianOlap4jConnection olap4jConnection);

    /**
     * Creates a result set with a fixed set of rows.
     *
     * @param olap4jConnection Connection
     * @param headerList Column headers
     * @param rowList Row values
     * @return Result set
     */
    ResultSet newFixedResultSet(
        MondrianOlap4jConnection olap4jConnection,
        List<String> headerList,
        List<List<Object>> rowList);

    /**
     * Creates a cell set.
     *
     *
     * @param olap4jStatement Statement
     * @return Cell set
     */
    MondrianOlap4jCellSet newCellSet(
        MondrianOlap4jStatement olap4jStatement);

    /**
     * Creates a statement.
     *
     * @param olap4jConnection Connection
     * @return Statement
     */
    MondrianOlap4jStatement newStatement(
        MondrianOlap4jConnection olap4jConnection);

    /**
     * Creates a prepared statement.
     *
     * @param mdx MDX query text
     * @param olap4jConnection Connection
     * @return Prepared statement
     * @throws org.olap4j.OlapException on database error
     */
    MondrianOlap4jPreparedStatement newPreparedStatement(
        String mdx,
        MondrianOlap4jConnection olap4jConnection)
        throws OlapException;

    /**
     * Creates a metadata object.
     *
     * @param olap4jConnection Connection
     * @param mondrianConnection Mondrian connection
     * @return Metadata object
     */
    MondrianOlap4jDatabaseMetaData newDatabaseMetaData(
        MondrianOlap4jConnection olap4jConnection,
        RolapConnection mondrianConnection);
}

// End Factory.java
