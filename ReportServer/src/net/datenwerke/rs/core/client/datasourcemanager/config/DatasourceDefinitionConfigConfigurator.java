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
 
 
package net.datenwerke.rs.core.client.datasourcemanager.config;

import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerProviderDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionConfigDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionDto;
import net.datenwerke.rs.core.client.datasourcemanager.helper.forms.DatasourceSelectionField;

import com.google.gwt.user.client.ui.Widget;

/**
 * 
 *
 */
public interface DatasourceDefinitionConfigConfigurator {

	Iterable<Widget> getDefaultAdditionalFormfields(DatasourceDefinitionConfigDto config, DatasourceDefinitionDto datasourceDefinitionDto, DatasourceSelectionField datasourceSelectionField, DatasourceContainerProviderDto datasourceContainerProvider); 
	
	Iterable<Widget> getOptionalAdditionalFormfields(DatasourceDefinitionConfigDto config, DatasourceDefinitionDto datasourceDefinitionDto, DatasourceSelectionField datasourceSelectionField, DatasourceContainerProviderDto datasourceContainerProvider);
	
	void inheritChanges(DatasourceDefinitionConfigDto config, DatasourceDefinitionDto datasourceDefinitionDto);
	
	DatasourceDefinitionConfigDto createConfigObject(DatasourceDefinitionDto datasourceDefinitionDto, DatasourceContainerProviderDto datasourceContainerProvider);

	boolean consumes(DatasourceDefinitionDto datasourceDefinitionDto, DatasourceDefinitionConfigDto datasourceConfig);

	boolean isValid(DatasourceContainerDto datasourceContainer);

	boolean isReloadOnDatasourceChange();
}
