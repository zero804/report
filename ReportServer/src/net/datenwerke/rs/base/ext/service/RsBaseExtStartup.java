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
 
 
package net.datenwerke.rs.base.ext.service;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.eximport.hooks.ExporterProviderHook;
import net.datenwerke.eximport.hooks.ImporterProviderHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.ext.service.dashboardmanager.eximport.DashboardManagerExporter;
import net.datenwerke.rs.base.ext.service.dashboardmanager.eximport.DashboardManagerImporter;
import net.datenwerke.rs.base.ext.service.dashboardmanager.eximport.HttpDashboardManagerImportConfigurationHooker;
import net.datenwerke.rs.base.ext.service.datasourcemanager.eximport.DatasourceManagerExporter;
import net.datenwerke.rs.base.ext.service.datasourcemanager.eximport.DatasourceManagerImporter;
import net.datenwerke.rs.base.ext.service.datasourcemanager.eximport.HttpDatasourceManagerImportConfigurationHooker;
import net.datenwerke.rs.base.ext.service.datasourcemanager.eximport.hookers.ExportAllDatasourcesHooker;
import net.datenwerke.rs.base.ext.service.datasourcemanager.eximport.hookers.ImportAllDatasourcesHooker;
import net.datenwerke.rs.base.ext.service.reportmanager.eximport.HttpReportManagerImportConfigurationHooker;
import net.datenwerke.rs.base.ext.service.reportmanager.eximport.ParameterInstanceExporter;
import net.datenwerke.rs.base.ext.service.reportmanager.eximport.ReportManagerExporter;
import net.datenwerke.rs.base.ext.service.reportmanager.eximport.ReportManagerImporter;
import net.datenwerke.rs.base.ext.service.reportmanager.eximport.hookers.ExportAllReportsHooker;
import net.datenwerke.rs.base.ext.service.reportmanager.eximport.hookers.ImportAllReportsHooker;
import net.datenwerke.rs.base.ext.service.reportmanager.hooks.ReportModSubCommandHook;
import net.datenwerke.rs.base.ext.service.reportmanager.terminal.commands.ListPropertyCommand;
import net.datenwerke.rs.base.ext.service.reportmanager.terminal.commands.RemovePropertyCommand;
import net.datenwerke.rs.base.ext.service.reportmanager.terminal.commands.ReportModCommand;
import net.datenwerke.rs.base.ext.service.reportmanager.terminal.commands.SetPropertyCommand;
import net.datenwerke.rs.base.ext.service.reportmanager.terminal.commands.SetUuidCommand;
import net.datenwerke.rs.base.ext.service.security.eximport.GenericRightsExporter;
import net.datenwerke.rs.base.ext.service.security.eximport.GenericRightsImporter;
import net.datenwerke.rs.base.ext.service.security.eximport.hookers.ExportAllGenericRightsHooker;
import net.datenwerke.rs.base.ext.service.security.eximport.hookers.ImportAllGenericRightsHooker;
import net.datenwerke.rs.eximport.service.eximport.hooks.ExportAllHook;
import net.datenwerke.rs.eximport.service.eximport.hooks.ImportAllHook;
import net.datenwerke.rs.eximport.service.eximport.im.http.hooks.HttpImportConfigurationProviderHook;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;

public class RsBaseExtStartup {

	@Inject
	public RsBaseExtStartup(
		HookHandlerService hookHandler,
		
		Provider<DashboardManagerExporter> dashboardExporterProvider,
		Provider<DashboardManagerImporter> dashboardImporterProvider,
		Provider<HttpDashboardManagerImportConfigurationHooker> dashboardHttpImportConfigHookerProvider,

		Provider<DatasourceManagerExporter> datasourceExporterProvider,
		Provider<DatasourceManagerImporter> datasourceImporterProvider,
		Provider<HttpDatasourceManagerImportConfigurationHooker> datasourceHttpImportConfigHookerProvider,

		Provider<ReportManagerExporter> exporterProvider,
		Provider<ReportManagerImporter> importerProvider,
		Provider<HttpReportManagerImportConfigurationHooker> httpImportConfigHookerProvider,
		Provider<ParameterInstanceExporter> parameterInstanceExporter,
		
		Provider<GenericRightsExporter> genericRightsExporterProvider,
		Provider<GenericRightsImporter> genericRIghtsImporterProvider,
		
		Provider<ExportAllDatasourcesHooker> exportAllDatasources,
		Provider<ImportAllDatasourcesHooker> importAllDatasources,
		
		Provider<ExportAllReportsHooker> exportAllReports,
		Provider<ImportAllReportsHooker> importAllReports,
		
		Provider<ExportAllGenericRightsHooker> exportAllGenericRights,
		Provider<ImportAllGenericRightsHooker> importAllGenericRights,

		Provider<ReportModCommand> reportModCommand,
		Provider<SetUuidCommand> setUUIDCommand,
		Provider<SetPropertyCommand> setPropertyCommand,
		Provider<RemovePropertyCommand> removePropertyCommand,
		Provider<ListPropertyCommand> listPropertyCommand
		
		){
		
		hookHandler.attachHooker(ExporterProviderHook.class, new ExporterProviderHook(dashboardExporterProvider));
		hookHandler.attachHooker(ImporterProviderHook.class, new ImporterProviderHook(dashboardImporterProvider));
		hookHandler.attachHooker(HttpImportConfigurationProviderHook.class, dashboardHttpImportConfigHookerProvider);

		hookHandler.attachHooker(ExporterProviderHook.class, new ExporterProviderHook(datasourceExporterProvider));
		hookHandler.attachHooker(ImporterProviderHook.class, new ImporterProviderHook(datasourceImporterProvider));
		hookHandler.attachHooker(HttpImportConfigurationProviderHook.class, datasourceHttpImportConfigHookerProvider);
		
		hookHandler.attachHooker(ExporterProviderHook.class, new ExporterProviderHook(exporterProvider));
		hookHandler.attachHooker(ImporterProviderHook.class, new ImporterProviderHook(importerProvider));
		hookHandler.attachHooker(HttpImportConfigurationProviderHook.class, httpImportConfigHookerProvider);
		hookHandler.attachHooker(ExporterProviderHook.class, new ExporterProviderHook(parameterInstanceExporter));
		
		hookHandler.attachHooker(ExporterProviderHook.class, new ExporterProviderHook(genericRightsExporterProvider));
		hookHandler.attachHooker(ImporterProviderHook.class, new ImporterProviderHook(genericRIghtsImporterProvider));
		
		hookHandler.attachHooker(ExportAllHook.class, exportAllReports);
		hookHandler.attachHooker(ImportAllHook.class, importAllReports);
		
		hookHandler.attachHooker(ExportAllHook.class, exportAllDatasources);
		hookHandler.attachHooker(ImportAllHook.class, importAllDatasources);
		
		hookHandler.attachHooker(ExportAllHook.class, exportAllGenericRights);
		hookHandler.attachHooker(ImportAllHook.class, importAllGenericRights);
		
		hookHandler.attachHooker(TerminalCommandHook.class, reportModCommand);
		
		hookHandler.attachHooker(ReportModSubCommandHook.class, setUUIDCommand);
		hookHandler.attachHooker(ReportModSubCommandHook.class, setPropertyCommand);
		hookHandler.attachHooker(ReportModSubCommandHook.class, removePropertyCommand);
		hookHandler.attachHooker(ReportModSubCommandHook.class, listPropertyCommand);
	}
}
