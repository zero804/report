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
 *   Copyright 2012 OSBI Ltd
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package mondrian3.olap4j;

import java.util.ArrayList;
import java.util.List;

import mondrian3.olap.MondrianServer;
import mondrian3.olap.Role;
import mondrian3.olap.RoleImpl;
import mondrian3.olap.Schema;
import mondrian3.olap.Util;
import mondrian3.rolap.RolapConnection;

import org.olap4j.OlapConnection;
import org.olap4j.OlapException;

public class SaikuMondrianHelper {

	public static RolapConnection getMondrianConnection(OlapConnection con) {
		try {
			if (!(con instanceof MondrianOlap4jConnection)) {
				throw new IllegalArgumentException("Connection has to be instance of MondrianOlap4jConnection");
			}
			MondrianOlap4jConnection mcon = (MondrianOlap4jConnection) con;
			return mcon.getMondrianConnection();
		} catch (OlapException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static MondrianServer getMondrianServer(OlapConnection con) {
		RolapConnection rcon = getMondrianConnection(con);
		return rcon != null ? rcon.getServer() : null;
	}
	
	public static boolean isMondrianConnection(OlapConnection con) {
		return (con instanceof MondrianOlap4jConnection);
	}

	public static void setRoles(OlapConnection con, String[] roleNames) throws Exception {
		if (!(con instanceof MondrianOlap4jConnection)) {
			throw new IllegalArgumentException("Connection has to be instance of MondrianOlap4jConnection");
		}
		if (roleNames == null) {
			con.setRoleName(null);
			return;
		}
		MondrianOlap4jConnection mcon = (MondrianOlap4jConnection) con;
		RolapConnection rcon = getMondrianConnection(mcon);
		Schema schema = rcon.getSchema();
		List<Role> roleList = new ArrayList<Role>();
		Role role;
		for (String roleName : roleNames) {
			Role role1 =  schema.lookupRole(roleName);
			if (role1 == null) {
				throw Util.newError(
						"Role '" + roleName + "' not found");
			}
			roleList.add(role1);
		}
		switch (roleList.size()) {
		case 0:
			role = null;
			break;
		case 1:
			role = roleList.get(0);
			break;
		default:
			role = RoleImpl.union(roleList);
			break;
		}
		rcon.setRole(role);
	}

}
