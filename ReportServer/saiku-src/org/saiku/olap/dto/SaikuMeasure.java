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
 
 
package org.saiku.olap.dto;

public class SaikuMeasure extends SaikuMember {

  private String measureGroup;
  public SaikuMeasure() {
  }

  public SaikuMeasure(
    String name,
    String uniqueName,
    String caption,
    String description,
    String dimensionUniqueName,
    String hierarchyUniqueName,
    String levelUniqueName,
    boolean visible,
    boolean calculated,
    String measuregroup) {
    super( name, uniqueName, caption, description, dimensionUniqueName, hierarchyUniqueName, levelUniqueName, calculated );
    this.measureGroup = measuregroup;
  }

  /**
   *
   * @return the measuregroup
   */
  public String getMeasureGroup() {
    return measureGroup;
  }
  
  public void setMeasureGroup(String measureGroup) {
	  this.measureGroup = measureGroup;
  }

}
