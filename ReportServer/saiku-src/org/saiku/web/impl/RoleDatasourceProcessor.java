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
 
 
package org.saiku.web.impl;

import org.saiku.datasources.datasource.SaikuDatasource;
import org.saiku.service.datasource.IDatasourceProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleDatasourceProcessor implements IDatasourceProcessor {

	private static final Logger log = LoggerFactory.getLogger(RoleDatasourceProcessor.class);

  public SaikuDatasource process(SaikuDatasource ds) {
//		if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {			
//			String roles = null;
//		  String ROLEFILTER = "role.filter";
//		  String filter =
//					ds.getProperties().containsKey(ROLEFILTER) ? 
//							ds.getProperties().getProperty(ROLEFILTER) : null;
//			List<String> allowedRoles = new ArrayList<>();
//			if (filter != null) {
//				String[] filterRoles = filter.split(",");
//				allowedRoles.addAll(Arrays.asList(filterRoles));
//			}
//					
//			for (GrantedAuthority ga : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
//				String r = ga.getAuthority();
//				boolean isAllowed = true;
//				if (filter != null) {
//					isAllowed = false;
//					for (String allowed : allowedRoles) {
//						if (r.toUpperCase().contains(allowed.toUpperCase())) {
//							isAllowed = true;
//						}
//					}
//				}
//				if (isAllowed) {
//					if (roles == null) {
//						roles =  r;
//					} else {
//						roles += "," + r;
//					}
//				}
//				
//			}
//			String location = ds.getProperties().getProperty("location");
//			if (!location.endsWith(";")) {
//				location +=";";
//			}
//			if (roles != null) {
//				location += "Role=" + roles + ";";
//			}
//			log.debug(RoleDatasourceProcessor.class.getCanonicalName() + " : location = " + location);
//			ds.getProperties().put("location", location);
//		}
		return ds;
	}

}
