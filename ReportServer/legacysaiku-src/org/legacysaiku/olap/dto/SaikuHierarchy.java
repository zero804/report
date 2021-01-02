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
package org.legacysaiku.olap.dto;

import java.util.List;


public class SaikuHierarchy extends AbstractSaikuObject {
	
	private String caption;
	private String dimensionUniqueName;
	private List<SaikuLevel> levels;
	private List<SaikuMember> rootmembers;
	private String description;
	private boolean visible;
	
	public SaikuHierarchy() {
		super(null,null);
		throw new RuntimeException("Unsupported Constructor. Serialization only");
	};
	
	public SaikuHierarchy(String name, String uniqueName, String caption, String description, String dimensionUniqueName, boolean visible, List<SaikuLevel> levels, List<SaikuMember> rootmembers) {
		super(uniqueName,name);
		this.caption = caption;
		this.dimensionUniqueName = dimensionUniqueName;
		this.levels = levels;
		this.rootmembers = rootmembers;
		this.description = description;
		this.visible = visible;
	}

	public String getCaption() {
		return caption;
	}
	
	public String getDimensionUniqueName() {
		return dimensionUniqueName;
	}
	
	public List<SaikuLevel> getLevels() {
		return levels;
	}
	
	public List<SaikuMember> getRootMembers() {
		return this.rootmembers;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	public boolean isVisible() {
		return visible;
	}
}
