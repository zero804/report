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
 
 
package net.datenwerke.rs.core.client.datasourcemanager.provider;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTree;
import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTreeFactory;
import net.datenwerke.gf.client.treedb.TreeDBUIService;
import net.datenwerke.gf.client.treedb.stores.EnhancedTreeStore;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.rs.base.client.datasources.dto.posomap.CsvDatasourceDto2PosoMap;
import net.datenwerke.rs.base.client.datasources.dto.posomap.DatabaseDatasourceDto2PosoMap;
import net.datenwerke.rs.base.client.datasources.dto.posomap.FormatBasedDatasourceDefinitionDto2PosoMap;
import net.datenwerke.rs.birt.client.datasources.dto.posomap.BirtReportDatasourceDefinitionDto2PosoMap;
import net.datenwerke.rs.core.client.datasourcemanager.DatasourceTreeLoaderDao;
import net.datenwerke.rs.core.client.datasourcemanager.DatasourceTreeManagerDao;
import net.datenwerke.rs.core.client.datasourcemanager.DatasourceUIModule;
import net.datenwerke.rs.core.client.datasourcemanager.dto.AbstractDatasourceManagerNodeDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.posomap.DatasourceFolderDto2PosoMap;
import net.datenwerke.rs.dsbundle.client.dsbundle.dto.posomap.DatabaseBundleDto2PosoMap;
import net.datenwerke.rs.incubator.client.scriptdatasource.dto.posomap.ScriptDatasourceDto2PosoMap;
import net.datenwerke.rs.saiku.client.datasource.dto.posomap.MondrianDatasourceDto2PosoMap;

public class NoMondrianTreeProvider implements Provider<ManagerHelperTree>{

	private final TreeDBUIService treeDBUIService;
	private final DatasourceTreeLoaderDao datasourceTreeLoader;
	private final DatasourceTreeManagerDao datasourceTreeManager;
	private final ManagerHelperTreeFactory treeFactory;
	
	@Inject
	public NoMondrianTreeProvider(
		TreeDBUIService treeDBUIService,	
		DatasourceTreeLoaderDao datasourceTreeLoader,
		DatasourceTreeManagerDao datasourceTreeManager,
		ManagerHelperTreeFactory treeFactory
		){
		
		this.treeDBUIService = treeDBUIService;
		this.datasourceTreeLoader = datasourceTreeLoader;
		this.datasourceTreeManager = datasourceTreeManager;
		this.treeFactory = treeFactory;
	}

	public ManagerHelperTree get() {
		/* store */
		List<Dto2PosoMapper> filters = new ArrayList<Dto2PosoMapper>();
		filters.add(new DatasourceFolderDto2PosoMap());
		
		filters.add(new BirtReportDatasourceDefinitionDto2PosoMap());
		filters.add(new DatabaseDatasourceDto2PosoMap());
		filters.add(new DatabaseBundleDto2PosoMap());
		filters.add(new FormatBasedDatasourceDefinitionDto2PosoMap());
		filters.add(new CsvDatasourceDto2PosoMap());
		filters.add(new ScriptDatasourceDto2PosoMap());
		
		EnhancedTreeStore store = treeDBUIService.getUITreeStore(AbstractDatasourceManagerNodeDto.class, datasourceTreeLoader, false, filters);
		
		/* build tree */
		final ManagerHelperTree tree = treeFactory.create(DatasourceUIModule.class, store, datasourceTreeLoader, datasourceTreeManager);
		tree.configureIconProvider();

		return tree;
	}
}
