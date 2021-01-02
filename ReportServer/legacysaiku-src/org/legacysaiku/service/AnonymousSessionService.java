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
 
 
package org.legacysaiku.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class AnonymousSessionService implements ISessionService {

	
	HashMap<String, Object> session = new HashMap<String, Object>();
	
	public AnonymousSessionService() {
		session.put("username", "anonymous");
		session.put("sessionid", UUID.randomUUID().toString());
		session.put("roles", new ArrayList<String>());
		
		
	}
	public Map<String, Object> login(HttpServletRequest req, String username,
			String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public void logout(HttpServletRequest req) {
		// TODO Auto-generated method stub

	}

	public void authenticate(HttpServletRequest req, String username,
			String password) {
		// TODO Auto-generated method stub

	}

	public Map<String, Object> getSession() {
		return session;
	}

	public Map<String, Object> getAllSessionObjects() {
		return session;
	}

}
