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
 
 
package net.datenwerke.rs.birt.service.datasources.birtreport.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.datasourcemanager.entities.CacheableDatasource;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinitionConfig;

import org.hibernate.envers.Audited;

@Entity
@Table(name="BIRT_REPORT_DATASRC")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.birt.client.datasources.dto"
)
public class BirtReportDatasourceDefinition extends DatasourceDefinition implements CacheableDatasource {

	private static final long serialVersionUID = -2945350730488549534L;

	@Override
	@Transient
	public DatasourceDefinitionConfig createConfigObject() {
		return new BirtReportDatasourceConfig();
	}
	
	@ExposeToClient
	private int databaseCache = 0;

	public int getDatabaseCache() {
		return databaseCache;
	}

	public void setDatabaseCache(int databaseCache) {
		this.databaseCache = databaseCache;
	}


}
