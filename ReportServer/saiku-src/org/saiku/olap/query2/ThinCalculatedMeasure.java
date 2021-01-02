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

import java.util.HashMap;
import java.util.Map;


public class ThinCalculatedMeasure {
	
	private String name;
	private String uniqueName;
	private String caption;
	private Map<String, String> properties = new HashMap<>();
	private String formula;
	private String hierarchyName;
	
	public ThinCalculatedMeasure() {}

  public ThinCalculatedMeasure(String hierarchyName, String name, String uniqueName, String caption, String formula, Map<String, String> properties) {
		this.hierarchyName = hierarchyName;
		this.uniqueName = uniqueName;
		this.formula = formula;
		this.name = name;
		this.caption = caption;
		this.properties = properties;
	}

	/**
	 * @return the uniqueName
	 */
	public String getUniqueName() {
		return uniqueName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @return the properties
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * @return the formula
	 */
	public String getFormula() {
		return formula;
	}

	/**
	 * @return the hierarchyUniqueName
	 */
	public String getHierarchyName() {
		return hierarchyName;
	}
	
	
	

}
