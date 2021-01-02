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
 
 
package net.datenwerke.gf.client.treedb.icon;

import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.resources.client.ImageResource;


public class IconMapping{
	
	final private Class<? extends AbstractNodeDto> type;
	private ImageResource icon;
	
	public IconMapping(Class<? extends AbstractNodeDto> type) {
		this.type = type;
	}
	
	
	public IconMapping(Class<? extends AbstractNodeDto> type, ImageResource icon) {
		this(type);
		this.icon = icon;
	}
	
	public IconMapping(Class<? extends AbstractNodeDto> type, BaseIcon icon) {
		this(type, icon.toImageResource());
	}


	public Class<? extends AbstractNodeDto> getType() {
		return type;
	}
	
	public ImageResource getIcon(AbstractNodeDto node) {
		return icon;
	}
	
}
