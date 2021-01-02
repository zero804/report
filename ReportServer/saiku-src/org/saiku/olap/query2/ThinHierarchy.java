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

import org.saiku.ArrayMapDeserializer;
import org.saiku.olap.query2.common.AbstractThinSortableQuerySet;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.olap4j.impl.Named;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThinHierarchy extends AbstractThinSortableQuerySet implements Named {

	private String name;
	private String caption;
	private String dimension;
	
	private Map<String, ThinLevel> levels = new HashMap<>();

	@JsonDeserialize(using = ArrayMapDeserializer.class)
	private Map<String,String> cmembers = new HashMap<>();

	public ThinHierarchy() {}

  public ThinHierarchy(String uniqueName, String caption, String dimension, Map<String, ThinLevel> levels) {
		this.name = uniqueName;
		this.caption = caption;
		this.dimension = dimension;
		if (levels != null) {
			this.levels = levels;
		}
	}
  	public ThinHierarchy(String uniqueName, String caption, String dimension, Map<String, ThinLevel> levels,
						 List<String> tcm) {
	this.name = uniqueName;
	this.caption = caption;
	this.dimension = dimension;
	if (levels != null) {
	  this.levels = levels;
	}
	  for(String t : tcm){
			cmembers.put(t, t);
	  }

  	}
	@Override
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
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return the levels
	 */
	public Map<String, ThinLevel> getLevels() {
		return levels;
	}
	
	public ThinLevel getLevel(String name) {
		if (levels.containsKey(name)) {
			return levels.get(name);
		}
		return null;

	}

	/**
	 * @param levels the levels to set
	 */
	public void setLevels(Map<String, ThinLevel> levels) {
		this.levels = levels;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the dimension
	 */
	public String getDimension() {
		return dimension;
	}

	/**
	 * @param dimension the dimension to set
	 */
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

  public Map<String, String> getCmembers() {
	return cmembers;
  }

  public void setCmembers(Map<String,String> cmembers) {
	this.cmembers = cmembers;
  }
}
