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
 
 
package org.legacysaiku.service.util;

import java.util.HashMap;
import java.util.Map;

import org.legacysaiku.olap.query.IQuery;
import org.legacysaiku.service.util.exception.SaikuServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectHolder {
	
	ThreadLocal<Map<String, IQuery>> threadQueries = new ThreadLocal<Map<String,IQuery>>();
	
	private static final Logger LOG = LoggerFactory.getLogger(ObjectHolder.class);
	
	public void putIQuery(String queryName, IQuery query) {
		getIQueryMap().put(queryName, query);
	}
	
	public void removeIQuery(String queryName) {
		getIQueryMap().remove(queryName);
		}
	
	
	public IQuery getIQuery(String queryName) {
		IQuery query = getIQueryMap().get(queryName);
		if (query == null) {
			throw new SaikuServiceException("No query with name ("+queryName+") found");
		}
		return query;
	}
	
	public Map<String, IQuery> getIQueryMap() {
		LOG.trace("ObjectHoler.getIQueryMap : Thread ID " + Thread.currentThread().getId() + " Name: " + Thread.currentThread().getName());
		if (threadQueries.get() == null) {
			threadQueries.set(new HashMap<String,IQuery>());
		}
		return threadQueries.get();
	}
	

}
