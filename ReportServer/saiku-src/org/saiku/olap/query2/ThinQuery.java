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
 
 
package org.saiku.olap.query2;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.saiku.olap.dto.SaikuCube;
import org.saiku.olap.query2.util.ServiceUtil;
import org.saiku.service.util.ISaikuQuery;

public class ThinQuery implements ISaikuQuery, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8779790895226230299L;
	
	
	private ThinQueryModel queryModel;
	private SaikuCube cube;
	private String mdx;
	private String name;
	private Map<String, String> parameters = new HashMap<>();
	private Map<String, String> plugins = new HashMap<>();
	private Map<String, Object> properties = new HashMap<>();
	private Map<String, String> metadata = new HashMap<>();
	
	private String queryType = "OLAP";
	
	public enum Type {
		MDX,
		QUERYMODEL
	}
	
	private Type type;
	
	public ThinQuery() {}

  public ThinQuery(String name, SaikuCube cube) {
		this(name, cube, new ThinQueryModel());
	}
	public ThinQuery(String name, SaikuCube cube, ThinQueryModel queryModel) {
		super();
		this.queryModel = queryModel;
		this.type = Type.QUERYMODEL;
		this.cube = cube;
		this.name = name;
	}

	public ThinQuery(String name, SaikuCube cube, String mdx) {
		super();
		this.mdx = mdx;
		this.type = Type.MDX;
		this.cube = cube;
		this.name = name;
	}

	
	/**
	 * @return the queryModel
	 */
	public ThinQueryModel getQueryModel() {
		return queryModel;
	}

	/**
	 * @param queryModel the queryModel to set
	 */
	public void setQueryModel(ThinQueryModel queryModel) {
		this.queryModel = queryModel;
		if (queryModel != null) {
			this.type = Type.QUERYMODEL;
		}
	}

	/**
	 * @return the cube
	 */
	public SaikuCube getCube() {
		return cube;
	}

	/**
	 * @param cube the cube to set
	 */
	public void setCube(SaikuCube cube) {
		this.cube = cube;
	}

	/**
	 * @return the mdx
	 */
	public String getMdx() {
		return mdx;
	}
	
	@JsonIgnore
	public String getParameterResolvedMdx() {
		String replacedMdx = mdx;
		if (parameters != null) {
			replacedMdx = ServiceUtil.replaceParameters(replacedMdx, parameters);
		}
		return replacedMdx;
		
	}

	/**
	 * @param mdx the mdx to set
	 */
	public void setMdx(String mdx) {
		this.mdx = mdx;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	
	public void setParameter(String name, String value) {
		this.parameters.put(name, value);
	}

	public String getParameter(String parameter) {
		if (parameters.containsKey(parameter)) {
			return parameters.get(parameter);
		}
		return null;
	}
	
	public void addParameter(String parameter) {
		if (StringUtils.isNotBlank(parameter)) {
			if (!parameters.containsKey(parameter)) {
				parameters.put(parameter, null);
			}
		}
	}
	
	public void addParameters(List<String> parameters) {
		if (parameters != null) {
			for (String param : parameters) {
				addParameter(param);
			}
		}
	}
	
	public boolean hasAggregators() {
	  return Type.QUERYMODEL.equals(type) && queryModel != null && queryModel.hasAggregators();

	}

	/**
	 * @return the plugins
	 */
	public Map<String, String> getPlugins() {
		return plugins;
	}

	/**
	 * @param plugins the plugins to set
	 */
	public void setPlugins(Map<String, String> plugins) {
		this.plugins = plugins;
	}

	/**
	 * @return the properties
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	/**
	 * @param metadata the metadata to set
	 */
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	/**
	 * @return the queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * @param queryType the queryType to set
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
}