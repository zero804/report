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
 
 
package org.saiku.database.dto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by bugg on 01/05/14.
 */
//@Entity
//@Table(name="USER_ROLES")
public class Role {

//  @Id
//  @GeneratedValue
  private Integer id;

  private String role;

//  @OneToMany(cascade= CascadeType.ALL)
//  @JoinTable(name="USER_ROLES",
//    joinColumns = {@JoinColumn(name="USER_ROLE_ID", referencedColumnName="id")},
//    inverseJoinColumns = {@JoinColumn(name="USER_ID", referencedColumnName="id")}
//  )
  private Set<User> userRoles;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Set<User> getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(Set<User> userRoles) {
    this.userRoles = userRoles;
  }

}