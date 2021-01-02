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

import net.datenwerke.rs.base.service.dbhelper.queries.OffsetQuery;
import net.datenwerke.rs.base.service.dbhelper.queries.Query;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.ColumnNamingService;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;

public class TeradataOffsetQuery extends OffsetQuery{

	/*
	 * SELECT "xx__rs_col_0", "xx__rs_col_1" FROM (SELECT teradataOffQry1.*, (ROW_NUMBER() OVER(ORDER BY 1)) rowNum FROM (SELECT "xx__rs_col_0", "xx__rs_col_1" FROM ( SELECT AccessRight AS "xx__rs_col_0", CreateTimeStamp AS "xx__rs_col_1" FROM ( select * from accessRights
	 * ) colQry) aliasQry) teradataOffQry1 ) teradataOffQry2 WHERE teradataOffQry2.rowNum BETWEEN 0 AND 50  
	 */
	public TeradataOffsetQuery(Query nestedQuery, QueryBuilder queryBuilder, ColumnNamingService columnNamingService) {
		super(nestedQuery, queryBuilder, columnNamingService);
		this.columnNamingService = columnNamingService;
	}

	@Override
	public void appendToBuffer(StringBuffer buf) {
		
		buf.append("SELECT ");

		if(queryBuilder.getColumns().size() == 0){
			buf.append("teradataOffQry2.*"); //$NON-NLS-1$
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

		buf.append(" FROM (SELECT teradataOffQry1.*, (ROW_NUMBER() OVER(ORDER BY 1)) rowNum FROM (");

		nestedQuery.appendToBuffer(buf);
		buf.append(") teradataOffQry1 ) teradataOffQry2 WHERE teradataOffQry2.rowNum BETWEEN ")
		.append(queryBuilder.getOffset())
		.append(" AND ") //$NON-NLS-1$
		.append(queryBuilder.getLimit() + queryBuilder.getOffset());

	}

}
