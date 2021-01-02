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
 
 
package net.datenwerke.gxtdto.client.ui.helper.panel.treepanelform;

import net.datenwerke.gxtdto.client.model.DwModel;


class NavigationModelData<M> implements DwModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8783945078009624370L;

	private int id;
	private String name;
	private M component;
	private String iconPath;
	
	public NavigationModelData(){
	}
	
	public NavigationModelData(int id, String name, String iconPath, M component){
		setId(id); 
		setName(name); 
		setModel(component);
		setIconPath(iconPath);
	}

	public void setModel(M Model) {
		this.component = Model;
	}

	public M getModel() {
		return component;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}

