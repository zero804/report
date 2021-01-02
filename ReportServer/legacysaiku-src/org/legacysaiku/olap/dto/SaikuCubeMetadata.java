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
 
 
package org.legacysaiku.olap.dto;

import java.util.List;

public class SaikuCubeMetadata {
	
	private List<SaikuDimension> dimensions;
	private List<SaikuMember> measures;

	public SaikuCubeMetadata(List<SaikuDimension> dimensions, List<SaikuMember> measures) {
		this.dimensions = dimensions;
		this.measures = measures;
	}
	

	/**
	 * @return the dimensions
	 */
	public List<SaikuDimension> getDimensions() {
		return dimensions;
	}

	/**
	 * @return the measures
	 */
	public List<SaikuMember> getMeasures() {
		return measures;
	}



}
