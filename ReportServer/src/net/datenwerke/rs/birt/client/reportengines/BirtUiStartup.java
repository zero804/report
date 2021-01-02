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
 
 
package net.datenwerke.rs.birt.client.reportengines;

import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewToolbarConfiguratorHook;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.birt.client.datasources.hooker.BirtReportDatasourceConfigProviderHooker;
import net.datenwerke.rs.birt.client.reportengines.execute.Birt2Doc;
import net.datenwerke.rs.birt.client.reportengines.execute.Birt2Excel;
import net.datenwerke.rs.birt.client.reportengines.execute.Birt2HTML;
import net.datenwerke.rs.birt.client.reportengines.execute.Birt2PDF;
import net.datenwerke.rs.birt.client.reportengines.hookers.BirtReportConfigHooker;
import net.datenwerke.rs.birt.client.reportengines.hookers.BirtReportDadgetExporter;
import net.datenwerke.rs.birt.client.reportengines.hookers.BirtReportFileDownloadToolbarConfiguratorHooker;
import net.datenwerke.rs.birt.client.reportengines.ui.BirtReportPreviewViewFactory;
import net.datenwerke.rs.birt.client.utils.hookers.BirtReportParameterProposerToolbarConfiguratorHooker;
import net.datenwerke.rs.core.client.datasourcemanager.hooks.DatasourceDefinitionConfigProviderHook;
import net.datenwerke.rs.core.client.reportexecutor.hooks.ReportViewHook;
import net.datenwerke.rs.core.client.reportexporter.hooks.ReportExporterExportReportHook;
import net.datenwerke.rs.core.client.reportmanager.hooks.ReportTypeConfigHook;
import net.datenwerke.rs.dashboard.client.dashboard.hooks.ReportDadgetExportHook;
import net.datenwerke.rs.dashboard.client.dashboard.security.DashboardViewGenericTargetIdentifier;
import net.datenwerke.security.client.security.SecurityUIService;
import net.datenwerke.security.client.security.dto.ReadDto;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BirtUiStartup {
	
	@Inject
	public BirtUiStartup(
		final HookHandlerService hookHandler,
		final WaitOnEventUIService waitOnEventService,
		final SecurityUIService securityService,
		
		BirtReportConfigHooker birtReportConfigHooker, 
		BirtReportPreviewViewFactory birtReportPreviewViewFactory, 
		
		Provider<Birt2HTML> birt2HTML,
		Provider<Birt2PDF> birt2PDF,
		Provider<Birt2Excel> birt2Excel,
		Provider<Birt2Doc> birt2Doc,
		
		BirtReportFileDownloadToolbarConfiguratorHooker birtReportFileDownloadToolbarConfiguratorHooker,
		BirtReportParameterProposerToolbarConfiguratorHooker birtReportParameterProposerToolbarConfiguratorHooker,
		
		BirtReportDatasourceConfigProviderHooker birtReportDatasourceConfigProviderHooker,
		
		final Provider<BirtReportDadgetExporter> reportDadgetExporterProvider
		){
		
		hookHandler.attachHooker(ReportTypeConfigHook.class, birtReportConfigHooker,20);
		hookHandler.attachHooker(ReportViewHook.class, new ReportViewHook(birtReportPreviewViewFactory), HookHandlerService.PRIORITY_LOW);
		
		hookHandler.attachHooker(ReportExporterExportReportHook.class, new ReportExporterExportReportHook(birt2Doc), HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(ReportExporterExportReportHook.class, new ReportExporterExportReportHook(birt2Excel), HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(ReportExporterExportReportHook.class, new ReportExporterExportReportHook(birt2PDF));
		hookHandler.attachHooker(ReportExporterExportReportHook.class, new ReportExporterExportReportHook(birt2HTML), HookHandlerService.PRIORITY_LOW);
		
		
		hookHandler.attachHooker(MainPanelViewToolbarConfiguratorHook.class, birtReportFileDownloadToolbarConfiguratorHooker);
		hookHandler.attachHooker(MainPanelViewToolbarConfiguratorHook.class, birtReportParameterProposerToolbarConfiguratorHooker);
		
		hookHandler.attachHooker(DatasourceDefinitionConfigProviderHook.class, birtReportDatasourceConfigProviderHooker,40);
		
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

}
