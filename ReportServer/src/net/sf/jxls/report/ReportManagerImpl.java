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
 
 
package net.sf.jxls.report;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * @author Leonid Vysochyn
 */
public class ReportManagerImpl implements ReportManager {
    protected static final Log log = LogFactory.getLog(ReportManagerImpl.class);
    Connection connection;

    public ReportManagerImpl(Connection connection, Map beans) {
        this.connection = connection;
    }

    public ReportManagerImpl(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List exec(String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        sql = sql.replaceAll("&apos;", "'");
        ResultSet rs = stmt.executeQuery(sql);
        // second parameter to RowSetDynaClass constructor indicates that properties should not be lowercased
        RowSetDynaClass rsdc = new RowSetDynaClass(rs, true);
        stmt.close();
        rs.close();
        return rsdc.getRows();
    }


}
