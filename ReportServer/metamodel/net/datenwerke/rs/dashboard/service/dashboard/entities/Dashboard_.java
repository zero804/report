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
 
 
package net.datenwerke.rs.dashboard.service.dashboard.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Dashboard.class)
public abstract class Dashboard_ {

	public static volatile SingularAttribute<Dashboard, LayoutType> layout;
	public static volatile ListAttribute<Dashboard, Dadget> dadgets;
	public static volatile SingularAttribute<Dashboard, Long> reloadInterval;
	public static volatile SingularAttribute<Dashboard, Boolean> singlePage;
	public static volatile SingularAttribute<Dashboard, String> name;
	public static volatile SingularAttribute<Dashboard, String> description;
	public static volatile SingularAttribute<Dashboard, Long> id;
	public static volatile SingularAttribute<Dashboard, Long> version;
	public static volatile SingularAttribute<Dashboard, Integer> n;

}

