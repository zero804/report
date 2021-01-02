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
 
 
package net.datenwerke.rs.base.service.datasources.transformers.database;

import java.sql.Connection;

import com.google.inject.Inject;

import net.datenwerke.dbpool.DbPoolService;
import net.datenwerke.dbpool.config.predefined.ReadOnlyConnectionConfig;
import net.datenwerke.rs.base.service.datasources.definitions.DatabaseDatasource;
import net.datenwerke.rs.base.service.datasources.definitions.DatabaseDatasourceConfig;
import net.datenwerke.rs.base.service.datasources.table.impl.TableDBDataSource;
import net.datenwerke.rs.base.service.datasources.table.impl.TableDataSource;
import net.datenwerke.rs.base.service.datasources.transformers.DataSourceDefinitionTransformer;
import net.datenwerke.rs.base.service.dbhelper.DBHelperService;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainerProvider;
import net.datenwerke.rs.core.service.reportmanager.exceptions.DatabaseConnectionException;
import net.datenwerke.rs.core.service.reportmanager.exceptions.UnsupportedDriverException;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;


/**
 * Transforms DataSourceDefitions into QRDataSources
 *
 */
public class Database2TableTransformer implements DataSourceDefinitionTransformer<TableDataSource> {
	
	private final DbPoolService<?> dbPoolService;
	private final DBHelperService dbHelper;
	
	
	@Inject
	public Database2TableTransformer(
		DbPoolService dbPoolService,
		DBHelperService dbHelper
		) {
		super();
		
		/* store objects */
		this.dbPoolService = dbPoolService;
		this.dbHelper = dbHelper;
	}

	@Override
	public TableDataSource transform(DatasourceContainerProvider containerProvider, Class<?> dst, ParameterSet parameters) throws UnsupportedDriverException, DatabaseConnectionException {
		
		/* get correct datasource definition and config */
		DatasourceContainer container = containerProvider.getDatasourceContainer();
		DatabaseDatasource ds = (DatabaseDatasource) container.getDatasource();
		DatabaseDatasourceConfig dsConfig = (DatabaseDatasourceConfig) container.getDatasourceConfig();
		
		/* open connection */
		try {
			Connection conn = dbPoolService.getConnection(ds.getConnectionConfig(), new ReadOnlyConnectionConfig()).get();
			return new TableDBDataSource(conn, dsConfig.getQuery(), containerProvider, dbHelper.getDatabaseHelper(ds.getDatabaseDescriptor()));
		} catch (Exception e) {
			DatabaseConnectionException dce = new DatabaseConnectionException(ds.getUrl(), ds.getUsername(), e);
			throw dce;
		}
	}

	@Override
	public boolean consumes(DatasourceContainerProvider containerProvider, Class<?> dst) {
		DatasourceContainer container = containerProvider.getDatasourceContainer();
		return (null != container && container.getDatasource() instanceof DatabaseDatasource && dst.isAssignableFrom(TableDBDataSource.class));
	}

}
