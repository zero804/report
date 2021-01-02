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
 
 
package net.datenwerke.rs.base.service.datasources.definitions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gf.base.service.annotations.Indexed;

@Entity
@Table(name="CSV_DATASOURCE_CONF")
@Audited
@GenerateDto(
dtoPackage="net.datenwerke.rs.base.client.datasources.dto"
)
@Indexed
public class CsvDatasourceConfig extends FormatBasedDatasourceConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4558842881453487382L;

	@ExposeToClient(disableHtmlEncode=true)
	@Column(length = 4096)
	private String queryWrapper;

	public String getQueryWrapper() {
		return queryWrapper;
	}

	public void setQueryWrapper(String queryWrapper) {
		this.queryWrapper = queryWrapper;
	}

}
