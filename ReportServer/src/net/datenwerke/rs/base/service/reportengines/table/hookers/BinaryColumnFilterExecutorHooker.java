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
 
 
package net.datenwerke.rs.base.service.reportengines.table.hookers;

import java.sql.Connection;
import java.util.Map;

import net.datenwerke.rs.base.service.dbhelper.hooks.DbFilterExecutorHook;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.ManagedQuery;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryReplacementHelper;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.queryconditions.QryCondition;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.BinaryColumnFilter;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.FilterSpec;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterValue;

public class BinaryColumnFilterExecutorHooker implements DbFilterExecutorHook {

	@Override
	public QryCondition getQueryCondition(FilterSpec filter,
			Map<String, ParameterValue> pMap, QueryReplacementHelper prefixer,
			QueryBuilder queryBuilder, ManagedQuery managedQuery,
			Connection connection) {
		
		Column a = ((BinaryColumnFilter)filter).getColumnA();
		Column b = ((BinaryColumnFilter)filter).getColumnB();
		
		switch(((BinaryColumnFilter)filter).getOperator()){
		case EQUALS:
			return queryBuilder.getNewEqualQueryCondition(a,b);
		case NOT_EQUALS:
			return queryBuilder.getNewNotQueryCondition(queryBuilder.getNewEqualQueryCondition(a,b));
		case LESS:
			return queryBuilder.getNewLessQueryCondition(a,b);
		case LESS_OR_EQUALS:
			return queryBuilder.getNewLessEqualQueryCondition(a,b);
		case GREATER:
			return queryBuilder.getNewGreaterQueryCondition(a,b);
		case GREATER_OR_EQUALS:
			return queryBuilder.getNewGreaterEqualQueryCondition(a,b);
		}
		return null;
	}

	@Override
	public boolean consumes(FilterSpec filter) {
		return filter instanceof BinaryColumnFilter;
	}

}
