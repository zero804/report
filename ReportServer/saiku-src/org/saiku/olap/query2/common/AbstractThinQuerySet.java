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
 
 
package org.saiku.olap.query2.common;

import java.util.ArrayList;
import java.util.List;

import org.saiku.olap.query2.filter.ThinFilter;

public abstract class AbstractThinQuerySet implements ThinQuerySet {
	

	private String mdx;
	
	private final List<ThinFilter> filters = new ArrayList<>();
	
	public abstract String getName();
	
	public void setMdx(String mdx) {
		this.mdx = mdx;
		
	}
	
	public String getMdx() {
		return this.mdx;
	}

	public void addFilter(ThinFilter filter) {
		filters.add(filter);
	}
	
	public void setFilter(int index, ThinFilter filter) {
		filters.set(index, filter);
	}

	public List<ThinFilter> getFilters() {
		return filters;
	}

	public void clearFilters() {
		filters.clear();
	}
}