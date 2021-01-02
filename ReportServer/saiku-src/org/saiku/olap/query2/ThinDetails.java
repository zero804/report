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

import org.saiku.olap.query2.ThinQueryModel.AxisLocation;

public class ThinDetails {
	
	private ThinQueryModel.AxisLocation axis;
	private Location location = Location.BOTTOM;
	private List<ThinMeasure> measures = new ArrayList<>();
	
	public enum Location {
		TOP,
		BOTTOM
	}
	
	public ThinDetails() {}

	public ThinDetails(AxisLocation axis, Location location, List<ThinMeasure> measures) {
		this.axis = axis;
		this.measures = measures;
		this.location = location;
	}

  /**
	 * @return the axis
	 */
	public ThinQueryModel.AxisLocation getAxis() {
		return axis;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @return the measures
	 */
	public List<ThinMeasure> getMeasures() {
		return measures;
	}

	
	
	
	
	

}
