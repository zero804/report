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
 
 
package net.datenwerke.rs.base.ext.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.ext.client.dashboard.ReportDadgetExporter;
import net.datenwerke.rs.base.ext.client.dashboardmanager.eximport.im.hookers.DashboardManagerUIImporterHooker;
import net.datenwerke.rs.base.ext.client.datasourcemanager.eximport.im.hookers.DatasourceManagerUIImporterHooker;
import net.datenwerke.rs.base.ext.client.parameters.fileselection.FileSelectionParameterConfigurator;
import net.datenwerke.rs.base.ext.client.reportmanager.eximport.im.hookers.ReportManagerUIImporterHooker;
import net.datenwerke.rs.core.client.parameters.config.ParameterConfigurator;
import net.datenwerke.rs.core.client.parameters.hooks.ParameterProviderHook;
import net.datenwerke.rs.dashboard.client.dashboard.hooks.ReportDadgetExportHook;
import net.datenwerke.rs.dashboard.client.dashboard.security.DashboardViewGenericTargetIdentifier;
import net.datenwerke.rs.eximport.client.eximport.im.hooks.ImporterConfiguratorHook;
import net.datenwerke.security.client.security.SecurityUIService;
import net.datenwerke.security.client.security.dto.ReadDto;

public class RsBaseExtUiStartup implements ParameterProviderHook {

	@SuppressWarnings("unchecked")
	private final List<Provider<? extends ParameterConfigurator>> parameters;
	
	@Inject
	public RsBaseExtUiStartup(
		final HookHandlerService hookHandler,
		final WaitOnEventUIService waitOnEventService,
		final SecurityUIService securityService,

		final DashboardManagerUIImporterHooker dashboardImporterHooker,
		final DatasourceManagerUIImporterHooker datasourceImporterHooker,
		final ReportManagerUIImporterHooker reportmanagerImporterHooker,

		final Provider<ReportDadgetExporter> reportDadgetExporterProvider,
		
		Provider<FileSelectionParameterConfigurator> fileSelectionParameter
		){
		
		/* store parameters */
		parameters = new ArrayList<Provider<? extends ParameterConfigurator>>();
		parameters.add(fileSelectionParameter);
		hookHandler.attachHooker(ParameterProviderHook.class, this);
		
		/* attach importer */
		hookHandler.attachHooker(ImporterConfiguratorHook.class, dashboardImporterHooker);
		hookHandler.attachHooker(ImporterConfiguratorHook.class, datasourceImporterHooker);
		hookHandler.attachHooker(ImporterConfiguratorHook.class, reportmanagerImporterHooker);

		/* request callback after login and check for rights */
		waitOnEventService.callbackOnEvent(SecurityUIService.REPORTSERVER_EVENT_GENERIC_RIGHTS_LOADED, new SynchronousCallbackOnEventTrigger() {
			
			public void execute(final WaitOnEventTicket ticket) {
				if(securityService.hasRight(DashboardViewGenericTargetIdentifier.class, ReadDto.class)){
					hookHandler.attachHooker(ReportDadgetExportHook.class, reportDadgetExporterProvider);
				}

				waitOnEventService.signalProcessingDone(ticket);
			}
		});
	}

	@Override
	public Collection<ParameterConfigurator> parameterProviderHook_getConfigurators() {
	List<ParameterConfigurator> configurations = new ArrayList<ParameterConfigurator>();
		
		for(Provider<? extends ParameterConfigurator> provider : parameters)
			configurations.add(provider.get());
		
		return configurations;
	}
}
