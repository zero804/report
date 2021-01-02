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

public abstract class AbstractThinSortableQuerySet extends AbstractThinQuerySet implements ThinSortableQuerySet {
	
	private SortOrder sortOrder;
	private String sortEvaluationLiteral;
	private HierarchizeMode hierarchizeMode;
	
	public void sort(SortOrder order) {
		this.sortOrder = order;

	}
	
	public void sort(SortOrder order, String sortEvaluationLiteral) {
		this.sortOrder = order;
		this.sortEvaluationLiteral = sortEvaluationLiteral;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public String getSortEvaluationLiteral() {
		return sortEvaluationLiteral;
	}

	public void clearSort() {
		this.sortOrder = null;
		this.sortEvaluationLiteral = null;
	}

	public HierarchizeMode getHierarchizeMode() {
		return this.hierarchizeMode;
	}

	public void setHierarchizeMode(HierarchizeMode hierarchizeMode) {
		this.hierarchizeMode = hierarchizeMode;

	}

	public void clearHierarchizeMode() {
		this.hierarchizeMode = null;

	}


}
