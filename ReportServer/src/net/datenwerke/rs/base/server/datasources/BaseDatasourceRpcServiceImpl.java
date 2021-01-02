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
 
 
package net.datenwerke.rs.base.server.datasources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import net.datenwerke.gxtdto.client.model.ListStringBaseModel;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ViolatedSecurityExceptionDto;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.client.datasources.rpc.BaseDatasourceRpcService;
import net.datenwerke.rs.base.client.dbhelper.dto.DatabaseHelperDto;
import net.datenwerke.rs.base.service.datasources.definitions.DatabaseDatasourceConfig;
import net.datenwerke.rs.base.service.dbhelper.DBHelperService;
import net.datenwerke.rs.base.service.dbhelper.DatabaseHelper;
import net.datenwerke.rs.base.service.reportengines.table.SimpleDataSupplier;
import net.datenwerke.rs.base.service.reportengines.table.output.object.RSTableModel;
import net.datenwerke.rs.base.service.reportengines.table.output.object.RSTableRow;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainerProviderImpl;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinitionConfig;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.utils.entitycloner.EntityClonerService;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.rights.Execute;
import net.datenwerke.security.service.security.rights.Read;
import net.datenwerke.security.service.security.rights.Write;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.inject.persist.Transactional;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

@Singleton
public class BaseDatasourceRpcServiceImpl extends SecuredRemoteServiceServlet implements BaseDatasourceRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1991463672346224996L;

	final private DtoService dtoService;
	final private DBHelperService dbHelperService;
	private final SimpleDataSupplier simpleDataSupplyer;
	private final EntityClonerService entityCloner;
	private final SecurityService securityService;

	@Inject
	public BaseDatasourceRpcServiceImpl(
		DtoService dtoService,
		EntityClonerService entityCloner,
		DBHelperService dbHelperService,
		SimpleDataSupplier simpleDataSupplyer,
		SecurityService securityService
		){
		
		/* store objects */
		this.dtoService = dtoService;
		this.entityCloner = entityCloner;
		this.dbHelperService = dbHelperService;
		this.simpleDataSupplyer = simpleDataSupplyer;
		this.securityService = securityService;
	}
	

	/**
	 * May be called without having to log in.
	 */
	@SecurityChecked(loginRequired=false)
	@Transactional(rollbackOn={Exception.class})
	@Override
	public ArrayList<DatabaseHelperDto> getDBHelperList()  throws ServerCallFailedException  {
		ArrayList<DatabaseHelperDto> helpers = new ArrayList<DatabaseHelperDto>();
		
		for(DatabaseHelper dh : dbHelperService.getDatabaseHelpers())
			helpers.add((DatabaseHelperDto) dtoService.createDto(dh));
		
		return helpers;
	}


	@Override
	public DatabaseHelperDto dummy(DatabaseHelperDto dto) {
		return null;
		
	}

	@Override
	public List<String> loadColumnDefinition(@Named("datasource")DatasourceContainerDto containerDto,
			String query) throws ServerCallFailedException {
		DatasourceContainer container = (DatasourceContainer) dtoService.loadPoso(containerDto);
		if(null == container)
			throw new ServerCallFailedException("No datasource container found");
		DatasourceDefinition datasource = container.getDatasource();
		if(null == datasource)
			throw new ServerCallFailedException("Container does not contain datasource");
		
		if(! securityService.checkRights(datasource, Read.class, Write.class, Execute.class))
			throw new ViolatedSecurityExceptionDto();
		
		container = entityCloner.cloneEntity(container);
		
		/* update query */
		DatasourceDefinitionConfig dsConfig = container.getDatasourceConfig();
		if(! (dsConfig instanceof DatabaseDatasourceConfig))
			throw new ServerCallFailedException("Expected Database");
		((DatabaseDatasourceConfig)dsConfig).setQuery(query);
		
		try {
			return simpleDataSupplyer.getColumnNames(new DatasourceContainerProviderImpl(container));
		} catch (ReportExecutorException e) {
			throw new ServerCallFailedException(e);
		}
	}


	@Override
	public PagingLoadResult<ListStringBaseModel> loadData(DatasourceContainerDto containerDto, PagingLoadConfig loadConfig,
			String query) throws ServerCallFailedException {
		DatasourceContainer container = (DatasourceContainer) dtoService.loadPoso(containerDto);
		if(null == container)
			throw new ServerCallFailedException("No datasource container found");
		DatasourceDefinition datasource = container.getDatasource();
		if(null == datasource)
			throw new ServerCallFailedException("Container does not contain datasource");
		
		if(! securityService.checkRights(datasource, Read.class, Write.class, Execute.class))
			throw new ViolatedSecurityExceptionDto();
		
		/* update query */
		DatasourceDefinitionConfig dsConfig = container.getDatasourceConfig();
		if(! (dsConfig instanceof DatabaseDatasourceConfig))
			throw new ServerCallFailedException("Expected Database");
		((DatabaseDatasourceConfig)dsConfig).setQuery(query);
		
		
		try {
			RSTableModel tableModel = simpleDataSupplyer.getData(new DatasourceContainerProviderImpl(container), null, loadConfig.getOffset(), loadConfig.getLimit());
			int count = simpleDataSupplyer.getDataCount(new DatasourceContainerProviderImpl(container));
			
			List<ListStringBaseModel> list = new ArrayList<ListStringBaseModel>();
			for(RSTableRow row : tableModel.getData())
				list.add(new ListStringBaseModel(row.getRow()));
			
			return new PagingLoadResultBean<ListStringBaseModel>(list, count, loadConfig.getOffset());
		} catch (ReportExecutorException e) {
			throw new ServerCallFailedException(e);
		}
	}

}
