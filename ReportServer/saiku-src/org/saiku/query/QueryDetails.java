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
 *   Copyright 2014 Paul Stoellberger
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
package org.saiku.query;

import org.olap4j.Axis;
import org.olap4j.impl.Named;
import org.olap4j.metadata.Measure;

import java.util.ArrayList;
import java.util.List;

public class QueryDetails implements Named {

	protected List<Measure> measures = new ArrayList<Measure>();
	
	private Location location = Location.BOTTOM;
	
	private Axis axis;

	private Query query;


  public enum Location {
		TOP,
		BOTTOM
	}
	
	public QueryDetails(Query query, Axis axis) {
		this.axis = axis;
		this.query = query;
	}
	
	public void add(Measure measure) {
		if (!measures.contains(measure)) {
			measures.add(measure);
		}
	}


  public void set(Measure measure, int position) {
		if (!measures.contains(measure)) {
			measures.add(position, measure);
		} else {
			int oldindex = measures.indexOf(measure);
			if (oldindex <= position) {
				measures.add(position, measure);
				measures.remove(oldindex);
			}
		}
	}
	
	public void remove(Measure measure) {
		measures.remove(measure);
	}
	
	
	public List<Measure> getMeasures() {
		return measures;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Axis getAxis() {
		return axis;
	}
	
	public void setAxis(Axis axis) {
		this.axis = axis;
	}

	@Override
	public String getName() {
		return "DETAILS";
	}

}
