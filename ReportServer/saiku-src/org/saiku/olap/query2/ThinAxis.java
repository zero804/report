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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.olap4j.impl.NamedListImpl;
import org.olap4j.metadata.NamedList;
import org.saiku.olap.query2.ThinQueryModel.AxisLocation;
import org.saiku.olap.query2.common.AbstractThinSortableQuerySet;

@JsonIgnoreProperties
public class ThinAxis extends AbstractThinSortableQuerySet {

	private AxisLocation location;
	private List<ThinHierarchy> hierarchies = new NamedListImpl<>();
	private boolean nonEmpty;
	private List<String> aggs = new ArrayList<>();
	
	
	public ThinAxis() {}

  public ThinAxis(AxisLocation location, NamedList<ThinHierarchy> hierarchies, boolean nonEmpty, List<String> aggs) {
		this.location = location;
		if (hierarchies != null) {
			this.hierarchies = hierarchies;
		}
		if (aggs != null) {
			this.aggs = aggs;
		}
		this.nonEmpty = nonEmpty;
	}

	@JsonIgnore
	@Override
	public String getName() {
		return location.toString();
	}

	/**
	 * @return the location
	 */
	public AxisLocation getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(AxisLocation location) {
		this.location = location;
	}

	/**
	 * @return the hierarchies
	 */
	public List<ThinHierarchy> getHierarchies() {
		return hierarchies;
	}
	
	public ThinHierarchy getHierarchy(String name) {
		return ((NamedListImpl<ThinHierarchy>) hierarchies).get(name);
	}

	/**
	 * @return the nonEmpty
	 */
	public boolean isNonEmpty() {
		return nonEmpty;
	}

	/**
	 * @param nonEmpty the nonEmpty to set
	 */
	public void setNonEmpty(boolean nonEmpty) {
		this.nonEmpty = nonEmpty;
	}
		
	public List<String> getAggregators() {
		return aggs;
	}

}
