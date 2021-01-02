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
 
 
package net.datenwerke.rs.base.service.dbhelper.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.jdbc.PgSQLXML;

import net.datenwerke.rs.base.service.dbhelper.DatabaseHelper;

/**
 * 
 *
 */
public class PostgreSQL extends DatabaseHelper {

	public static final String DB_NAME = "PostgreSQL";
	public static final String DB_DRIVER = "org.postgresql.Driver";
	public static final String DB_DESCRIPTOR = "DBHelper_PostgreSQL";

	@Override
	public String getDescriptor() {
		return DB_DESCRIPTOR; 
	}

	@Override
	public String getDriver() {
		return DB_DRIVER;
	}

	@Override
	public String createDummyQuery() {
		return "SELECT 1+1";
	}
	
	@Override
	public String getName() {
		return DB_NAME;
	}

	@Override
	public ResultSetObjectHandler createResultSetHandler(final ResultSet resultSet, final Connection con) throws SQLException {
		return new ResultSetObjectHandler() {
			
			@Override
			public Object getObject(int pos) throws SQLException {
				Object o = resultSet.getObject(pos);
				// we just display the xml text
				if (o instanceof PgSQLXML) {
					PgSQLXML xml = (PgSQLXML)o;
					String xmlString = xml.getString();
					return xmlString;
				}
				return o;
			}
		};
	}
}
