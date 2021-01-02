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

public class ThinMeasure {
	
	
	private String name;
	private String uniqueName;
	private String caption;
	private Type type;
	private final List<String> aggregators = new ArrayList<>();
	
	public enum Type {
		CALCULATED,
		EXACT
	}
	
	public ThinMeasure(){}

	public ThinMeasure(String name, String uniqueName, String caption, Type type) {
    this(name, uniqueName, caption, type, null);
  }

  public ThinMeasure(String name, String uniqueName, String caption, Type type, List<String> aggregators) {
		this.name = name;
		this.uniqueName = uniqueName;
		this.caption = caption;
		this.type = type;

    if (aggregators != null) {
      this.aggregators.addAll(aggregators);
    }
  }

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the uniqueName
	 */
	public String getUniqueName() {
		return uniqueName;
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

  public List<String> getAggregators() {
    return aggregators;
  }
}