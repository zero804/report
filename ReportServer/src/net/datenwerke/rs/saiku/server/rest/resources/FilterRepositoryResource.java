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
package net.datenwerke.rs.saiku.server.rest.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;



/**
 * QueryServlet contains all the methods required when manipulating an OLAP Query.
 * @author Paul Stoellberger
 *
 */
@Path("/saiku/{username}/filters")
@XmlAccessorType(XmlAccessType.NONE)
public class FilterRepositoryResource {

//	private static final Logger log = LoggerFactory.getLogger(FilterRepositoryResource.class);
//
//	private static final String SETTINGS_FILE = "settings.properties";
//	private static final String FILTER_FILENAME = "saiku.filters";
//
//	private OlapQueryService olapQueryService;
//	private ISessionService sessionService;
//
//
//	private Properties settings = new Properties();
//
//	//@Autowired
//	public void setOlapQueryService(OlapQueryService olapqs) {
//		olapQueryService = olapqs;
//	}
//	
//	//@Autowired
//	public void setSessionService(ISessionService ss) {
//		sessionService = ss;
//	}


//	private Map<String, SaikuFilter> getFiltersInternal() throws Exception {
//		return getFiltersInternal(null);
//	}
//
//	private Map<String, SaikuFilter> getFiltersInternal(String query) {
//		Map<String, SaikuFilter> allFilters = new HashMap<>();
//		//Map<String, SaikuFilter> filters = deserialize(getUserFile());
//		//allFilters.putAll(filters);
//		if (StringUtils.isNotBlank(query)) {
//			allFilters = olapQueryService.getValidFilters(query, allFilters);
//		}
//
//		//return MapUtils.orderedMap(allFilters);
//	  return null;
//	}




  /**
   * Get filternames as JSON.
   * @param queryName The query name.
   * @return A response containing the filter names.
   */
	@GET
	@Produces({"application/json" })
	@Path("/names/")
//    @ReturnType("java.lang.List<String>")
    public Response getSavedFilterNames(@QueryParam("queryname") String queryName)
	{
//		try {
//			Map<String, SaikuFilter> allFilters = getFiltersInternal(queryName);
//			List<String> filternames = new ArrayList<>(allFilters.keySet());
//			Collections.sort(filternames);
//			return Response.ok(filternames).build();
//
//		} catch(Exception e){
//			log.error("Cannot filter names",e);
//			String error = ExceptionUtils.getRootCauseMessage(e);
//			return Response.serverError().entity(error).build();
//		}
		throw new RuntimeException("Not implemented.");
	}


  /**
   * Get Saved Filters as JSON.
   * @summary Get filters as JSON.
   * @param queryName The query name.
   * @param filterName The filter name.
   * @return A response containing the JSON.
   */
	@GET
	@Produces({"application/json" })
	public Response getSavedFilters(
			@QueryParam("query") String queryName,
			@QueryParam("filtername") String filterName) 
	{
//		try {
//			Map<String, SaikuFilter> allFilters = new HashMap<>();
//			if (StringUtils.isNotBlank(queryName)) {
//				allFilters = getFiltersInternal(queryName);
//			} else if (StringUtils.isNotBlank(filterName)) {
//				allFilters = getFiltersInternal();
//				Map<String, SaikuFilter> singleFilter = new HashMap<>();
//				if (allFilters.containsKey(filterName)) {
//					singleFilter.put(filterName, allFilters.get(filterName));
//					allFilters = singleFilter;
//				}
//			} else {
//				allFilters = getFiltersInternal();
//			}
//			return Response.ok(allFilters).build();
//		} catch(Exception e){
//			log.error("Cannot get filter details",e);
//			String error = ExceptionUtils.getRootCauseMessage(e);
//			return Response.serverError().entity(error).build();
//		}
		throw new RuntimeException("Not implemented.");
	}

  /**
   * Save filter
   * @summary Save Filter.
   * @param filterJSON The Filter JSON object.
   * @return A response containing the filter.
   */
	@POST
	@Produces({"application/json" })
	@Path("/{filtername}")
//    @ReturnType("org.saiku.olap.dto.filter.SaikuFilter")
    public Response saveFilter(
			@FormParam ("filter") String filterJSON)
	{
//		try {
//			
//			ObjectMapper mapper = new ObjectMapper();
//		    mapper.setVisibilityChecker(mapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
//			SaikuFilter filter = mapper.readValue(filterJSON, SaikuFilter.class);
//			String username = sessionService.getAllSessionObjects().get("username").toString();
//			filter.setOwner(username);
//			Map<String, SaikuFilter> filters = getFiltersInternal();
//			filters.put(filter.getName(), filter);
//			return Response.ok(filter).build();
//		}
//		catch (Exception e) {
//			log.error("Cannot save filter (" + filterJSON + ")",e);
//			String error = ExceptionUtils.getRootCauseMessage(e);
//			return Response.serverError().entity(error).build();
//		}
		throw new RuntimeException("Not implemented.");
	}


	
//	private byte[] getCsv(Map<String, SaikuFilter> filters, String delimiter, String memberdelimiter) {
//		try {
//
//			StringBuilder sb = new StringBuilder();
//			sb.append("User").append(delimiter).append("FilterName").append(delimiter).append("Dimension")
//			  .append(delimiter).append("Hierarchy").append(delimiter).append("Members");
//			sb.append("\r\n");
//			for (SaikuFilter sf : filters.values()) {
//				String row = sf.getOwner() + delimiter + sf.getName() + delimiter + sf.getDimension().getName() + delimiter + sf.getHierarchy().getName() + delimiter;
//				String members = "";
//				boolean first = true;
//				for (SimpleCubeElement e : sf.getMembers()) {
//					if (!first)
//						members += memberdelimiter;
//					else
//						first = false;
//					members += e.getName();
//				}
//				sb.append(row).append(members).append("\r\n");
//			}
//			return sb.toString().getBytes("UTF-8");
//		} catch (Throwable e) {
//			throw new SaikuServiceException("Error creating csv export for filters"); //$NON-NLS-1$
//		}
//	}

	
	



}
