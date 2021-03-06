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
 
 
package net.datenwerke.rs.core.service.reportmanager.entities.reports;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Report.class)
public abstract class Report_ extends net.datenwerke.rs.core.service.reportmanager.entities.AbstractReportManagerNode_ {

	public static volatile SetAttribute<Report, ParameterInstance> parameterInstances;
	public static volatile SingularAttribute<Report, DatasourceContainer> datasourceContainer;
	public static volatile SetAttribute<Report, ReportMetadata> reportMetadata;
	public static volatile ListAttribute<Report, ParameterDefinition> parameterDefinitions;
	public static volatile SingularAttribute<Report, String> name;
	public static volatile SingularAttribute<Report, String> description;
	public static volatile SingularAttribute<Report, String> uuid;
	public static volatile SetAttribute<Report, ReportProperty> reportProperties;
	public static volatile SingularAttribute<Report, String> key;
	public static volatile SingularAttribute<Report, PreviewImage> previewImage;

}

