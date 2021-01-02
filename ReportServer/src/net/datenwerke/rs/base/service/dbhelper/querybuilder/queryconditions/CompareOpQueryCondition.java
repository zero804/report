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
 
 
package net.datenwerke.rs.base.service.dbhelper.querybuilder.queryconditions;

import java.util.LinkedList;
import java.util.List;

import net.datenwerke.rs.base.service.dbhelper.DatabaseHelper;
import net.datenwerke.rs.base.service.dbhelper.queries.Query;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.ColumnNamingService;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;

public abstract class CompareOpQueryCondition implements QryCondition {
	
	
	public enum CompareOpConfigs {
		COLUMN_DATA, 
		COLUMN_SUBQUERY,
		COLUMN_COLUMN
	};
	
	protected CompareOpConfigs compareOpConfig;

	protected Column column;
	protected Query query;
	protected Object data;

	protected QueryBuilder queryBuilder;

	public CompareOpQueryCondition(Column column, Object data, QueryBuilder queryBuilder) {
		if(data instanceof Query){
			compareOpConfig = CompareOpConfigs.COLUMN_SUBQUERY;
			query = (Query) data;
		} else if(data instanceof Column)
			compareOpConfig = CompareOpConfigs.COLUMN_COLUMN;
		else
			compareOpConfig = CompareOpConfigs.COLUMN_DATA;
		this.column = column;
		this.data = data;
		this.queryBuilder = queryBuilder;
	}
	
	protected abstract String getCompareOpSymbol();
	
	@Override
	public void appendToBuffer(StringBuffer buf, ColumnNamingService columnNamingService) {
		DatabaseHelper dbHelper = queryBuilder.getDbHelper();
		
		switch(compareOpConfig){
			
			case COLUMN_DATA:
				buf.append(' ') //$NON-NLS-1$
				.append(dbHelper.prepareColumnForComparison(columnNamingService.getColumnName(column), column))
				.append(' ') //$NON-NLS-1$
				.append(getCompareOpSymbol())
				.append(' ') //$NON-NLS-1$
				.append(queryBuilder.nextReplacement(data, column.getType()));
			break;
			
			case COLUMN_SUBQUERY:
				buf.append(' ') //$NON-NLS-1$
				.append(dbHelper.prepareColumnForComparison(columnNamingService.getColumnName(column), column))
				.append(' ') //$NON-NLS-1$
				.append(getCompareOpSymbol())
				.append(' ') //$NON-NLS-1$
				.append('(');
				query.appendToBuffer(buf);
				buf.append(')');
			break;
			
			case COLUMN_COLUMN:
				buf.append(' ') //$NON-NLS-1$
				.append(dbHelper.prepareColumnForComparison(columnNamingService.getColumnName(column), column))
				.append(' ') //$NON-NLS-1$
				.append(getCompareOpSymbol())
				.append(' ') //$NON-NLS-1$
				.append(columnNamingService.getColumnName((Column)data));
			break;
			default:
				throw new RuntimeException("Invalid Query Condition Configuration");
		}
	}
	
	@Override
	public List<Column> getContainedColumns() {
		LinkedList<Column> res = new LinkedList<Column>();
		res.add(column);
		
		if(compareOpConfig == CompareOpConfigs.COLUMN_COLUMN)
			res.add((Column) data);
		
		return res;
	}
	

}
