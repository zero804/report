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
 
 
package org.saiku.query;

import org.olap4j.impl.Named;
import org.olap4j.metadata.Member;

/**
 * Created by bugg on 07/07/15.
 */
public class RootMember implements Named {
  private final Member member;
  private final QueryHierarchy hierarchy;

  public RootMember(QueryHierarchy queryHierarchy, Member member) {
    super();
    this.member = member;
    this.hierarchy = queryHierarchy;

  }

  @Override
  public String getName() {
    return this.member.getName();
  }

  public String getUniqueName(){
    return this.member.getUniqueName();
  }

  public String getCaption(){
    return this.member.getCaption();
  }

  public String getDescription(){
    return this.member.getDescription();
  }

}
