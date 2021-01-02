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
 
 
package org.saiku.olap.dto.filter;

import org.saiku.olap.dto.SimpleCubeElement;

import java.util.ArrayList;
import java.util.List;

public class SaikuFilter {

  private String name;
  private String description;
  private SimpleCubeElement dimension;


  private SimpleCubeElement hierarchy;
  private List<SimpleCubeElement> members = new ArrayList<>();
  private String owner;

  public SaikuFilter() {
  }

  public SaikuFilter( String name, String description, SimpleCubeElement dimension, SimpleCubeElement hierarchy,
                      List<SimpleCubeElement> members ) {
    this( name, description, dimension, hierarchy, members, null );
  }

  public SaikuFilter( String name, String description, SimpleCubeElement dimension, SimpleCubeElement hierarchy,
                      List<SimpleCubeElement> members, String owner ) {
    this.name = name;
    this.description = description;
    this.dimension = dimension;
    this.hierarchy = hierarchy;
    this.members = members;
    this.owner = owner;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return the dimension
   */
  public SimpleCubeElement getDimension() {
    return dimension;
  }

  /**
   * @return the hierarchy
   */
  public SimpleCubeElement getHierarchy() {
    return hierarchy;
  }

  /**
   * @return the members
   */
  public List<SimpleCubeElement> getMembers() {
    return members;
  }

  /**
   * @return the owner
   */
  public String getOwner() {
    return owner;
  }

  public void setOwner( String owner ) {
    this.owner = owner;
  }

}
