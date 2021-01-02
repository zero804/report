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
 
 
package net.datenwerke.security.client.usermanager.dto.ie;

import net.datenwerke.security.client.usermanager.dto.GroupDto;

public class StrippedDownGroup extends AbstractStrippedDownNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 91919518656343884L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static StrippedDownGroup fromGroup(GroupDto group) {
		StrippedDownGroup sGroup = new StrippedDownGroup();

		sGroup.setId(group.getId());
		sGroup.setName(group.getName());

		return sGroup;
	}

	@Override
	public int hashCode() {
		if(null != getId())
			return getId().hashCode();
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof StrippedDownGroup))
			return false;
		
		if(null == getId())
			return super.equals(obj);
		
		return getId().equals(((StrippedDownGroup)obj).getId());
	}
}
