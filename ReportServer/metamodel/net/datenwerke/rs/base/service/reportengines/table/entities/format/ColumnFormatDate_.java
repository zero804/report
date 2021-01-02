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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities.format;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ColumnFormatDate.class)
public abstract class ColumnFormatDate_ extends net.datenwerke.rs.base.service.reportengines.table.entities.format.ColumnFormat_ {

	public static volatile SingularAttribute<ColumnFormatDate, String> errorReplacement;
	public static volatile SingularAttribute<ColumnFormatDate, Boolean> replaceErrors;
	public static volatile SingularAttribute<ColumnFormatDate, String> baseFormat;
	public static volatile SingularAttribute<ColumnFormatDate, String> targetFormat;
	public static volatile SingularAttribute<ColumnFormatDate, Boolean> rollOver;

}

