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
 
 
package net.datenwerke.treedb.ext.service.eximport.http;

import java.util.HashMap;
import java.util.Map;

import net.datenwerke.eximport.exceptions.IllegalImportConfigException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.eximport.client.eximport.im.dto.ImportConfigDto;
import net.datenwerke.rs.eximport.client.eximport.im.dto.ImportItemConfigDto;
import net.datenwerke.rs.eximport.service.eximport.im.http.HttpImportService;
import net.datenwerke.rs.eximport.service.eximport.im.http.hooks.HttpImportConfigurationProviderHook;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.SecurityTarget;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Write;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.treedb.ext.client.eximport.im.dto.ImportTreeModel;
import net.datenwerke.treedb.ext.client.eximport.im.dto.TreeImportConfigDto;
import net.datenwerke.treedb.ext.client.eximport.im.dto.TreeImportNodeConfigDto;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeImportItemConfig;
import net.datenwerke.treedb.service.treedb.AbstractNode;

import com.google.inject.Provider;

abstract public class HttpImportConfigurationProviderHookImplForTrees<N extends AbstractNode<?>, D extends AbstractNodeDto> implements
		HttpImportConfigurationProviderHook {

	protected final DtoService dtoService;
	protected final Provider<HttpImportService> httpImportServiceProvider;
	private final SecurityService securityService;
	
	public HttpImportConfigurationProviderHookImplForTrees(
		DtoService dtoService,
		Provider<HttpImportService> httpImportServiceProvider, 
		SecurityService securityService
		){
	
		/* store objects */
		this.dtoService = dtoService;
		this.httpImportServiceProvider = httpImportServiceProvider;
		this.securityService = securityService;
	}

	@Override
	public void configureImport(ImportConfigDto config) {
		TreeImportConfigDto<D> treeConfig = (TreeImportConfigDto<D>) config;

		/* get parent */
		if(null == treeConfig.getParent()){
			finishUpConfiguration(treeConfig);
			return;
		}
		
		N parent = (N) dtoService.loadPoso(treeConfig.getParent());
		if(parent instanceof SecurityTarget){
			if(!securityService.checkRights((SecurityTarget)parent, Write.class)){
				throw new ViolatedSecurityException("No write-permission on " + parent + "(id="+parent.getId()+")");
			}
		}
		
		
		/* load service */
		HttpImportService httpImportService = httpImportServiceProvider.get();
		
		/* start configuring */
		Map<String, ImportTreeModel> lookupMap = new HashMap<String, ImportTreeModel>();
		Map<String, TreeNodeImportItemConfig> lookupConfigMap = new HashMap<String, TreeNodeImportItemConfig>();
		/* build basic config */
		for(ImportItemConfigDto itemConfig : treeConfig.getConfigs()){
			TreeImportNodeConfigDto nodeConfig = (TreeImportNodeConfigDto)itemConfig;
			
			ImportTreeModel model = nodeConfig.getModel();
			if(null == model.getId())
				throw new RuntimeException("Id may not be null");
			
			TreeNodeImportItemConfig realConfigNode = initItemConfig(model);
			doConfigureNodeConfig(realConfigNode, model, treeConfig);
			
			lookupMap.put(model.getId(), model);
			lookupConfigMap.put(model.getId(), realConfigNode);
			
			httpImportService.addItemConfig(realConfigNode);
		}
		
		/* set correct parents */
		for(ImportTreeModel model : lookupMap.values()){
			if(! lookupMap.containsKey(model.getParentId())){
				if(! validateParent(model, parent))
					throw new IllegalImportConfigException("Invalid parent for configuration");
				lookupConfigMap.get(model.getId()).setParent(parent);
			}
		}
		
		finishUpConfiguration(lookupMap, lookupConfigMap, treeConfig);
	}
	
	protected TreeNodeImportItemConfig initItemConfig(ImportTreeModel model) {
		return new TreeNodeImportItemConfig(model.getId());
	}

	protected boolean validateParent(ImportTreeModel model, N parent) {
		return true;
	}

	protected void finishUpConfiguration(TreeImportConfigDto<D> treeConfig) {
		
	}

	protected void finishUpConfiguration(Map<String, ImportTreeModel> lookupMap,
			Map<String, TreeNodeImportItemConfig> lookupConfigMap, TreeImportConfigDto<D> treeConfig) {
		
	}

	protected void doConfigureNodeConfig(TreeNodeImportItemConfig realConfigNode, ImportTreeModel model, TreeImportConfigDto<D> treeConfig) {
		
	}

}
