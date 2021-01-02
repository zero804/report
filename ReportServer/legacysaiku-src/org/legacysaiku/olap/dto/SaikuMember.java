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


public class SaikuMember extends AbstractSaikuObject {
	
	private String caption;
	private String dimensionUniqueName;
	private String description;
	private String levelUniqueName;
	private String hierarchyUniqueName;
	
	public SaikuMember() {}

	public SaikuMember(
			String name, 
			String uniqueName, 
			String caption, 
			String description, 
			String dimensionUniqueName, 
			String hierarchyUniqueName, 
			String levelUniqueName) 
	{
		super(uniqueName,name);
		this.caption = caption;
		this.description = description;
		this.dimensionUniqueName = dimensionUniqueName;
		this.levelUniqueName = levelUniqueName;
		this.hierarchyUniqueName = hierarchyUniqueName;
	}

	public String getCaption() {
		return caption;
	}

	public String getDescription() {
		return description;
	}

	public String getLevelUniqueName() {
		return levelUniqueName;
	}
	
	public String getDimensionUniqueName() {
		return dimensionUniqueName;
	}

	public String getHierarchyUniqueName() {
		return hierarchyUniqueName;
	}	
}
