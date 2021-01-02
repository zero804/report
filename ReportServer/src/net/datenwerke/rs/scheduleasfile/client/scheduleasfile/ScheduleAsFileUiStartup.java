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
 
 
package net.datenwerke.rs.scheduleasfile.client.scheduleasfile;

import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.objectinformation.hooks.ObjectInfoKeyInfoProvider;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportexporter.hooks.ExportExternalEntryProviderHook;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.filter.TeamspaceFilter;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.hookers.ExportToFtpHooker;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.hookers.ExportToTeamSpaceHooker;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.hookers.FtpExportSnippetProvider;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.hookers.HandleExecutedReportFileHooker;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.hookers.TSMenuDownloadHooker;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.hookers.TeamSpaceExportSnippetProviderHook;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.objectinfo.ExecutedReportObjectInfoHooker;
import net.datenwerke.rs.scheduler.client.scheduler.hooks.ScheduleExportSnippetProviderHook;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hooks.ScheduledReportListFilter;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.hooks.GeneralReferenceHandlerHook;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.hooks.TsFavoriteMenuHook;

public class ScheduleAsFileUiStartup {

	@Inject
	public ScheduleAsFileUiStartup(
		final HookHandlerService hookHandler,
		
		Provider<TeamSpaceExportSnippetProviderHook> teamspaceExportSnippetProvider,
		final Provider<FtpExportSnippetProvider> ftpExportSnippetProvider,
		Provider<HandleExecutedReportFileHooker> handleExecutedFileProvider,
		
		Provider<ExecutedReportObjectInfoHooker> executedReportObjectInfo,
		Provider<TSMenuDownloadHooker> tsMenuDownloadHooker, 
		
		Provider<TeamspaceFilter> teamSpaceFilter,
		
		final Provider<ExportToTeamSpaceHooker> exportToTeamSpaceHooker,
		final Provider<ExportToFtpHooker> exportToFtpHooker,
		
		final WaitOnEventUIService waitOnEventService,
		final ScheduleAsFileDao dao
		){
		
		hookHandler.attachHooker(ScheduledReportListFilter.class, teamSpaceFilter);
		
		/* export */
		hookHandler.attachHooker(ExportExternalEntryProviderHook.class, exportToTeamSpaceHooker);
		hookHandler.attachHooker(ExportExternalEntryProviderHook.class, exportToFtpHooker);

		hookHandler.attachHooker(ScheduleExportSnippetProviderHook.class, teamspaceExportSnippetProvider, HookHandlerService.PRIORITY_LOW);
		
		waitOnEventService.callbackOnEvent(LoginService.REPORTSERVER_EVENT_AFTER_ANY_LOGIN, new SynchronousCallbackOnEventTrigger() {
			@Override
			public void execute(final WaitOnEventTicket ticket) {
				waitOnEventService.signalProcessingDone(ticket);
				
				dao.getStorageEnabledConfigs(new RsAsyncCallback<Map<StorageType,Boolean>>() {
					@Override
					public void onSuccess(final Map<StorageType,Boolean> result) {
						if (result.get(StorageType.FTP) && result.get(StorageType.FTP_SCHEDULING))
							hookHandler.attachHooker(ScheduleExportSnippetProviderHook.class, ftpExportSnippetProvider, HookHandlerService.PRIORITY_LOWER);
						else
							hookHandler.detachHooker(ScheduleExportSnippetProviderHook.class, ftpExportSnippetProvider);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						super.onFailure(caught);
					}
				});
				
			}
		});
		
		hookHandler.attachHooker(GeneralReferenceHandlerHook.class, handleExecutedFileProvider);
		
		hookHandler.attachHooker(TsFavoriteMenuHook.class, tsMenuDownloadHooker);
		
		/* object info */
		hookHandler.attachHooker(ObjectInfoKeyInfoProvider.class, executedReportObjectInfo);


	}
}
