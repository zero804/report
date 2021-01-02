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
 
 
package net.datenwerke.rs.base.client.reportengines.table;

import com.google.inject.Inject;

import net.datenwerke.gxtdto.client.utils.SqlTypes;
import net.datenwerke.rs.base.client.reportengines.table.dto.AggregateFunctionDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;

public class TableReportUiServiceImpl implements TableReportUiService {

	final protected SqlTypes sqlTypes;
	
	
	@Inject
	public TableReportUiServiceImpl(SqlTypes sqlTypes) {
		super();
		this.sqlTypes = sqlTypes;
	}

	@Override
	public AggregateFunctionDto[] getAvailableAggregateFunctionsFor(ColumnDto col){
		AggregateFunctionDto[] values = null;
		
		if(sqlTypes.isDateLikeType(col.getType()) || sqlTypes.isString(col.getType())){
			values = new AggregateFunctionDto[]{
					AggregateFunctionDto.MAX,
					AggregateFunctionDto.MIN,
					AggregateFunctionDto.COUNT,
					AggregateFunctionDto.COUNT_DISTINCT
			};
		} else if(sqlTypes.isLob(col.getType())){
			values = new AggregateFunctionDto[]{};
		} else {
			values = AggregateFunctionDto.values();
		}
		
		return values;
	}
	
	@Override
	public AggregateFunctionDto[] getAvailableNumericAggregateFunctionsFor(ColumnDto col){
		AggregateFunctionDto[] values = null;
		
		if(sqlTypes.isDateLikeType(col.getType()) || sqlTypes.isString(col.getType())){
			values = new AggregateFunctionDto[]{
					AggregateFunctionDto.COUNT,
					AggregateFunctionDto.COUNT_DISTINCT
			};
		} else if(sqlTypes.isLob(col.getType())){
			values = new AggregateFunctionDto[]{};
		} else {
			values = AggregateFunctionDto.values();
		}
		
		return values;
	}
}