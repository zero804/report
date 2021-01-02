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
 
 
package net.datenwerke.rs.base.service.dbhelper.db.teradata;

import net.datenwerke.rs.base.service.dbhelper.queries.OrderOffsetQuery;
import net.datenwerke.rs.base.service.dbhelper.queries.Query;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.ColumnNamingService;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder.OrderDefinition;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.base.service.reportengines.table.entities.Order;

public class TeradataOrderOffsetQuery extends OrderOffsetQuery {

	public TeradataOrderOffsetQuery(Query nestedQuery, QueryBuilder queryBuilder,
			ColumnNamingService columnNamingService) {
		super(nestedQuery, queryBuilder, columnNamingService);
		this.columnNamingService = columnNamingService;
	}

	@Override
	public void appendToBuffer(StringBuffer buf) {
		buf.append("SELECT ");

		if(queryBuilder.getColumns().size() == 0){
			buf.append("teraOrdOffQry2.*"); //$NON-NLS-1$
		} else {
			int i = 1;
			for(Column col : queryBuilder.getColumns()){
				/* if distinct && hidden -> ignore */
				if(queryBuilder.ignoreHiddenColumns() && null != col.isHidden() && col.isHidden())
					continue;
				
				if(i > 1)
					buf.append(", "); //$NON-NLS-1$

				/* column name */
				buf.append(columnNamingService.getColumnName(col));

				i++;
			}
		}
		

		buf.append(" FROM (SELECT teraOrdOffQry1.*, (ROW_NUMBER() OVER(ORDER BY ");
		
		int i = 1;
		for(OrderDefinition def : queryBuilder.getOrderDefinitions()){
			/* if distinct && hidden -> ignore */
			if(queryBuilder.ignoreHiddenColumns() && null != def.getColumn().isHidden() && def.getColumn().isHidden())
				continue;
			
			if(i > 1)
				buf.append(", "); //$NON-NLS-1$

			buf.append(columnNamingService.getColumnName(def.getColumn()))
				.append(' ') //$NON-NLS-1$
				.append( def.getOrder().equals(Order.ASC) ? "ASC" : "DESC" ); //$NON-NLS-1$ //$NON-NLS-2$

			i++;
		}
				
		buf.append(")) teraRowNum FROM (");

		nestedQuery.appendToBuffer(buf);
		buf.append(") teraOrdOffQry1 ) teraOrdOffQry2 WHERE teraOrdOffQry2.teraRowNum BETWEEN ")
		.append(queryBuilder.getOffset())
		.append(" AND ") //$NON-NLS-1$
		.append(queryBuilder.getLimit() + queryBuilder.getOffset());

	}
}
