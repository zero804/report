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
 
 
package net.datenwerke.rs.base.client.reportengines.table.dto.decorator;

import java.util.List;

import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportVariantDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.reportmanager.dto.interfaces.ReportVariantDto;

/**
 * Dto Decorator for {@link TableReportVariantDto}
 *
 */
public class TableReportVariantDtoDec extends TableReportVariantDto implements ReportVariantDto {


	private static final long serialVersionUID = 1L;

	public TableReportVariantDtoDec() {
		super();
	}

	@Override
	public List<ParameterDefinitionDto> getParameterDefinitions() {
		if(null != getBaseReport())
			return getBaseReport().getParameterDefinitions();
		return null;
	}

}
