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
 
 
package net.datenwerke.rs.incubator.client.scriptdatasource.hooker;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.core.client.datasourcemanager.config.DatasourceDefinitionConfigConfigurator;
import net.datenwerke.rs.core.client.datasourcemanager.dto.AbstractDatasourceManagerNodeDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionDto;
import net.datenwerke.rs.core.client.datasourcemanager.hooks.DatasourceDefinitionConfigProviderHook;
import net.datenwerke.rs.enterprise.client.EnterpriseUiService;
import net.datenwerke.rs.incubator.client.scriptdatasource.config.ScriptDatasourceConfigConfigurator;
import net.datenwerke.rs.incubator.client.scriptdatasource.dto.ScriptDatasourceDto;
import net.datenwerke.rs.incubator.client.scriptdatasource.locale.ScriptDatasourceMessages;
import net.datenwerke.rs.incubator.client.scriptdatasource.ui.ScriptDatasourceForm;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class ScriptDatasourceConfigProviderHooker implements
		DatasourceDefinitionConfigProviderHook {

	private final Provider<ScriptDatasourceConfigConfigurator> configurator;
	private final Provider<ScriptDatasourceForm> formProvider;
	private final EnterpriseUiService enterpriseService;
	
	@Inject
	public ScriptDatasourceConfigProviderHooker(
		Provider<ScriptDatasourceConfigConfigurator> configurator,
		Provider<ScriptDatasourceForm> formProvider,
		EnterpriseUiService enterpriseService
		){
		
		/* store objects */
		this.configurator = configurator;
		this.formProvider = formProvider;
		this.enterpriseService = enterpriseService;
	}
	
	@Override
	public Map<? extends Class<? extends DatasourceDefinitionDto>, ? extends Provider<? extends DatasourceDefinitionConfigConfigurator>> getConfigs() {
		Map<Class<? extends DatasourceDefinitionDto>, Provider<? extends DatasourceDefinitionConfigConfigurator>> map =
			new HashMap<Class<? extends DatasourceDefinitionDto>, Provider<? extends DatasourceDefinitionConfigConfigurator>>();
				
		map.put(ScriptDatasourceDto.class, configurator);
		
		return map;
	}

	@Override
	public boolean consumes(DatasourceDefinitionDto dsd) {
		return dsd instanceof ScriptDatasourceDto;
	}

	@Override
	public Collection<? extends MainPanelView> getAdminViews(
			DatasourceDefinitionDto dsd) {
		return Collections.singleton(formProvider.get());
	}

	@Override
	public Class<? extends AbstractDatasourceManagerNodeDto> getDatasourceClass() {
		return ScriptDatasourceDto.class;
	}

	@Override
	public String getDatasourceName() {
		return ScriptDatasourceMessages.INSTANCE.scriptDatasourceTypeName();
	}

	@Override
	public AbstractDatasourceManagerNodeDto instantiateDatasource() {
		return new ScriptDatasourceDto();
	}

	@Override
	public ImageResource getDatasourceIcon() {
		return BaseIcon.SCRIPT.toImageResource();
	}

	@Override
	public boolean isAvailable() {
		return enterpriseService.isEnterprise();
	}
}
