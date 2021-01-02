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

import java.util.ArrayList;
import java.util.List;



public class ThinSelection {
	
	public enum Type {
		INCLUSION,
		EXCLUSION,
		RANGE
	}
	
	private Type type = Type.INCLUSION;
	private List<ThinMember> members = new ArrayList<>();
	private String parameter = null;
	
	public ThinSelection() {}

  public ThinSelection(Type type, List<ThinMember> members) {
		this(type, members, null);
	}
	private ThinSelection(Type type, List<ThinMember> members, String parameter) {
		this.type = type;
		if (members != null) {
			this.members.addAll(members);
		}
		this.parameter = parameter;
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
	 * @return the members
	 */
	public List<ThinMember> getMembers() {
		return members;
	}
	/**
	 * @param members the members to set
	 */
	public void setMembers(List<ThinMember> members) {
		this.members = members;
	}
	
	public String getParameterName() {
		return parameter;
	}
	public void setParameterName(String name) {
		this.parameter = name;
		
	}
}
