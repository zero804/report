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

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by bugg on 15/09/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThinCalculatedMember {

  private String parentMember;
  private String parentMemberLevel;
  private String previousLevel;
  private String dimension;
  private String name;
  private String uniqueName;
  private String caption;
  private Map<String, String> properties = new HashMap<>();
  private String formula;
  private String hierarchyName;
  private String assignedLevel;


  public ThinCalculatedMember() {}

  public ThinCalculatedMember(String dimension, String hierarchyName, String name, String uniqueName, String caption,
                              String formula, Map<String, String> properties, String parentMember, String assignedLevel) {
    this(dimension, hierarchyName, name, uniqueName, caption, formula, properties);
    this.parentMember = parentMember;
  }
  public ThinCalculatedMember(String dimension, String hierarchyName, String name, String uniqueName, String caption,
                              String formula, Map<String, String> properties, String parentMember, String
                                  parentMemberLevel, String assignedLevel) {
    this(dimension, hierarchyName, name, uniqueName, caption, formula, properties);
    this.parentMember = parentMember;
    this.parentMemberLevel = parentMemberLevel;
  }

  public ThinCalculatedMember(String dimension, String hierarchyName, String name, String uniqueName, String caption,
                              String formula, Map<String, String> properties, String parentMember, String
                                  parentMemberLevel, String lastLevel,String assignedLevel) {
    this(dimension, hierarchyName, name, uniqueName, caption, formula, properties);
    this.parentMember = parentMember;
    this.parentMemberLevel = parentMemberLevel;
    this.previousLevel = lastLevel;
  }

  public ThinCalculatedMember(String dimension, String hierarchyName, String name, String uniqueName, String caption,
                              String formula, Map<String, String> properties) {
    this.dimension = dimension;
    this.hierarchyName = hierarchyName;
    this.uniqueName = uniqueName;
    this.formula = formula;
    this.name = name;
    this.caption = caption;
    this.properties = properties;
  }

  public String getDimension() {
    return dimension;
  }


  /**
   * @return the uniqueName
   */
  public String getUniqueName() {
    return uniqueName;
  }

  /**
   * @return the name
   */
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
   * @return the properties
   */
  public Map<String, String> getProperties() {
    return properties;
  }

  /**
   * @return the formula
   */
  public String getFormula() {
    return formula;
  }

  /**
   * @return the hierarchyUniqueName
   */
  public String getHierarchyName() {
    return hierarchyName;
  }

  /**
   *
   * @return the parent member.
   */
  public String getParentMember() {
    return parentMember;
  }

  public String getParentMemberLevel() {
    return parentMemberLevel;
  }

  public String getPreviousLevel() {
    return previousLevel;
  }

  public String getAssignedLevel() {
    return assignedLevel;
  }

  public void setAssignedLevel(String assignedLevel) {
    this.assignedLevel = assignedLevel;
  }
}