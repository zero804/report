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


public class Parameter {
	
	private String name;
	private String value;
	private ParameterType type = ParameterType.SIMPLE;
	private boolean mandatory = false;
	
	public enum ParameterType {
		SIMPLE,
		LIST,
		MDX
	}
	
	public Parameter() {}

  public Parameter(ParameterType type, String name, String value, boolean mandatory) {
		this.type = type;
		this.value = value;
		this.name = name;
		this.mandatory = mandatory;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the type
	 */
	public ParameterType getType() {
		return type;
	}
	
	public boolean isMandatory() {
		return mandatory;
	}

	

}
