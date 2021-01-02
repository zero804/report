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
 * Copyright 2014 OSBI Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.saiku.olap.dto;

import java.util.HashMap;
import java.util.Map;


/**
 * SaikuLevel.
 */
public class SaikuLevel extends AbstractSaikuObject {

	private final Map<String, String> annotations;
	private String levelType;
	private String caption;
	private String hierarchyUniqueName;
	private String dimensionUniqueName;
	//private transient List<SaikuMember> members;
	private boolean visible;
	private String description;

	public SaikuLevel(String name, String uniqueName, String caption, String description, String s, String uniqueName1,
			boolean visible) {
		super(null, null);
		throw new RuntimeException("Unsupported Constructor. Serialization only");
	}


	public SaikuLevel(
			String name,
			String uniqueName,
			String caption,
			String description,
			String dimensionUniqueName,
			String hierarchyUniqueName,
			boolean visible,
			String levelType,
			Map<String, String> annotations) {
		super(uniqueName, name);
		this.caption = caption;
		this.hierarchyUniqueName = hierarchyUniqueName;
		this.dimensionUniqueName = dimensionUniqueName;
		this.visible = visible;
		this.description = description;
		this.annotations = annotations;
		if(levelType!=null) {
			this.levelType = levelType;
		}
		//this.members = members;
	}

	public String getCaption() {
		return caption;
	}

	public String getHierarchyUniqueName() {
		return hierarchyUniqueName;
	}

	public String getDimensionUniqueName() {
		return dimensionUniqueName;
	}

	public boolean isVisible() {
		return visible;
	}

	public String getDescription() {
		return description;
	}

	public Map<String, String> getAnnotations() {
		Map<String, String> m = null;
		if (annotations != null) {
			m = new HashMap<>();
			for (Map.Entry<String, String> entry : annotations.entrySet()) {
				m.put(entry.getKey(), entry.getValue());
			}
		}


		return m;
	}

	public String getLevelType() {
		return levelType;
	}
	//public List<SaikuMember> getMembers() {
	//return members;
	//}


	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}


	public void setCaption(String caption) {
		this.caption = caption;
	}


	public void setHierarchyUniqueName(String hierarchyUniqueName) {
		this.hierarchyUniqueName = hierarchyUniqueName;
	}


	public void setDimensionUniqueName(String dimensionUniqueName) {
		this.dimensionUniqueName = dimensionUniqueName;
	}


	public void setVisible(boolean visible) {
		this.visible = visible;
	}


	public void setDescription(String description) {
		this.description = description;
	}



}
