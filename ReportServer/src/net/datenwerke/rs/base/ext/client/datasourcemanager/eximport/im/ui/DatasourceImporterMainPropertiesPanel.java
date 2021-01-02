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
 
 
package net.datenwerke.rs.base.ext.client.datasourcemanager.eximport.im.ui;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gf.client.treedb.simpleform.SFFCGenericTreeNode;
import net.datenwerke.rs.base.ext.client.datasourcemanager.eximport.im.dto.DatasourceManagerImportConfigDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceFolderDto;
import net.datenwerke.rs.core.client.datasourcemanager.helper.forms.simpleform.SFFCDatasourceSuppressConfig;
import net.datenwerke.rs.core.client.datasourcemanager.locale.DatasourcesMessages;
import net.datenwerke.rs.core.client.datasourcemanager.provider.annotations.DatasourceTreeFolders;
import net.datenwerke.rs.eximport.client.eximport.im.exceptions.NotProperlyConfiguredException;
import net.datenwerke.treedb.ext.client.eximport.im.ui.ImporterMainPropertiesPanel;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DatasourceImporterMainPropertiesPanel extends
		ImporterMainPropertiesPanel<DatasourceManagerImportConfigDto> {

	private final Provider<UITree> treeProvider;
	
	protected String parentKey;
	protected String defaultDatasource;
	
	@Inject
	public DatasourceImporterMainPropertiesPanel(
		@DatasourceTreeFolders Provider<UITree> treeProvider
		) {

		/* store objects */
		this.treeProvider = treeProvider;
		
		/* init */
		initializeUI();
	}

	@Override
	public void populateConfig(DatasourceManagerImportConfigDto config) throws NotProperlyConfiguredException {
		super.populateConfig(config);
		
		DatasourceFolderDto parent = (DatasourceFolderDto) form.getValue(parentKey);
		config.setParent(parent);
		
		DatasourceContainerDto dsContainer = (DatasourceContainerDto) form.getValue(defaultDatasource);
		if(null != dsContainer)
			config.setDefaultDatasource(dsContainer.getDatasource());
	}
	
	@Override
	public void validateConfig(DatasourceManagerImportConfigDto config)
			throws NotProperlyConfiguredException {
		if(null == config.getParent() && ! config.getConfigs().isEmpty())
			throw new NotProperlyConfiguredException(DatasourcesMessages.INSTANCE.importConfigFailureNoParent());
	}

	@Override
	protected void configureForm() {
		super.configureForm();

		parentKey = form.addField(DatasourceFolderDto.class, DatasourcesMessages.INSTANCE.importWhereTo(), new SFFCGenericTreeNode(){
			@Override
			public UITree getTreeForPopup() {
				return treeProvider.get();
			}
		});
		
		defaultDatasource = form.addField(DatasourceContainerDto.class, DatasourcesMessages.INSTANCE.defaultDatasource(), new SFFCDatasourceSuppressConfig(){}); 
	}
	


	@Override
	protected String getDescription() {
		return DatasourcesMessages.INSTANCE.importMainPropertiesDescription();
	}

	@Override
	protected String getHeadline() {
		return DatasourcesMessages.INSTANCE.importMainPropertiesHeadline();
	}
	
}
