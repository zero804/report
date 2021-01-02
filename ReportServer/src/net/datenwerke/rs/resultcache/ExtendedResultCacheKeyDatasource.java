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
 
 
package net.datenwerke.rs.resultcache;

import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainerProvider;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinitionConfig;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;

public class ExtendedResultCacheKeyDatasource extends ResultCacheKeyDatasource {

	protected ParameterSet parameters;
	protected DatasourceContainerProvider containerProvider;
	private Class<?> transformationDestination;

	public ExtendedResultCacheKeyDatasource(DatasourceDefinition datasource, DatasourceDefinitionConfig config) {
		super(datasource, config);
	}
	public ExtendedResultCacheKeyDatasource(DatasourceDefinition ds) {
		super(ds);
	}
	
	public ExtendedResultCacheKeyDatasource(DatasourceDefinition datasource, DatasourceDefinitionConfig config,
			ParameterSet parameters, DatasourceContainerProvider containerProvider, Class<?> dst) {
		super(datasource, config);
		this.parameters = parameters;
		this.containerProvider = containerProvider;
		this.transformationDestination = dst;
	}
	
	public ParameterSet getParameters() {
		return parameters;
	}
	
	public DatasourceContainerProvider getContainerProvider() {
		return containerProvider;
	}
	
	public DatasourceContainer getContainer(){
		return null != containerProvider ? containerProvider.getDatasourceContainer() : null;
	}
	
	public Class<?> getTransformationDestination() {
		return transformationDestination;
	}
	
}
