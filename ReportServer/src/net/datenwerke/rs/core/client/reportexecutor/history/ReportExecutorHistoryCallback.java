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
 
 
package net.datenwerke.rs.core.client.reportexecutor.history;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import net.datenwerke.gf.client.history.HistoryCallback;
import net.datenwerke.gf.client.history.HistoryLocation;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.rs.core.client.reportexecutor.ExecuteReportConfiguration;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorDao;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorUIService;
import net.datenwerke.rs.core.client.reportexecutor.variantstorer.VariantStorerConfig;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.scripting.client.scripting.ScriptingUiService;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceDao;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.decorator.TeamSpaceDtoDec;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.TsDiskTreeLoaderDao;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskFolderDto;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

public class ReportExecutorHistoryCallback implements HistoryCallback {

	private final ReportExecutorDao reportExecutorDao;
	private final ReportExecutorUIService executorService;
	private final TeamSpaceDao teamSpaceDao;
	private final TsDiskTreeLoaderDao diskDao;
	private final WaitOnEventUIService waitOnEventService;

	@Inject
	public ReportExecutorHistoryCallback(
		ReportExecutorDao reportExecutorDao,
		ReportExecutorUIService executorService,
		TeamSpaceDao teamSpaceDao,
		TsDiskTreeLoaderDao diskDao,
		WaitOnEventUIService waitOnEventService
		){
		
		this.reportExecutorDao = reportExecutorDao;
		this.executorService = executorService;
		this.teamSpaceDao = teamSpaceDao;
		this.diskDao = diskDao;
		this.waitOnEventService = waitOnEventService;
	}
	
	@Override
	public void locationChanged(final HistoryLocation location) {
		String teamSpaceId = location.getParameterValue("ts");
		if(null == teamSpaceId)
			loadReport(location, null, null);
		
		TeamSpaceDtoDec tsDto = new TeamSpaceDtoDec();
		Long tsId = Long.valueOf(teamSpaceId);
		tsDto.setId(tsId);

		teamSpaceDao.reloadTeamSpace(tsDto, new RsAsyncCallback<TeamSpaceDto>() {
			@Override
			public void onSuccess(final TeamSpaceDto result) {
				String teamSpaceFolderId = location.getParameterValue("tsf");
				if(null == teamSpaceFolderId)
					loadReport(location, result, null);
				else {
					Long tsfId = Long.valueOf(teamSpaceFolderId);
					
					diskDao.loadNodeById(tsfId, new RsAsyncCallback<AbstractNodeDto>(){
						public void onSuccess(AbstractNodeDto folder) {
							if(folder instanceof TsDiskFolderDto)
								loadReport(location, result, (TsDiskFolderDto) folder);
							else
								loadReport(location, result, null);
						};
					});
				}
			}
		});	
	}
	
	private void loadReport(final HistoryLocation location, final TeamSpaceDto tsDto, final TsDiskFolderDto folder){
		
		waitOnEventService.callbackOnEvent(ScriptingUiService.REPORTSERVER_EVENT_AFTER_EXECUTE_LOGIN_SCRIPT, new SynchronousCallbackOnEventTrigger() {
			
			@Override
			public void execute(final WaitOnEventTicket ticket) {
				reportExecutorDao.loadReportForExecutionFrom(location, new RsAsyncCallback<ReportDto>(){
					@Override
					public void onSuccess(ReportDto result) {
						executorService.executeReportDirectly(result, null, new ExecuteReportConfiguration() {
							
							@Override
							public String getViewId() {
								return location.hasParameter("v") ? location.getParameterValue("v") : null;
							}
							
							@Override
							public String getUrlParameters() {
								return null;
							}
							
							@Override
							public VariantStorerConfig getVariantStorerConfig() {
								return new VariantStorerConfig() {
									
									@Override
									public VariantStorerHandleServerCalls getServerCallHandler() {
										return new VariantStorerHandleServerCalls() {
											
											@Override
											public void editVariant(ReportDto report, String executeToken, String name,
													String description, AsyncCallback<ReportDto> callback) {
												throw new IllegalStateException("not implemented");
											}
											
											@Override
											public void deleteVariant(ReportDto report, AsyncCallback<Void> callback) {
												throw new IllegalStateException("not implemented");
											}
											
											@Override
											public void createNewVariant(ReportDto report, TeamSpaceDto teamSpace, TsDiskFolderDto folder, String executeToken,
													String name, String description, AsyncCallback<ReportDto> callback) {
												executorService.createNewVariant(report, teamSpace, folder, executeToken, name, description, callback);
											}
										};
									}
									
									@Override
									public boolean displayVariantStorer() {
										return true;
									}
									
									@Override
									public boolean displayEditVariantOnStore() {
										return false;
									}
									
									@Override
									public boolean allowEditVariant() {
										return false;
									}
									
									@Override
									public TeamSpaceDto getTeamSpace() {
										return tsDto;
									}
									@Override
									public TsDiskFolderDto getTeamSpaceFolder() {
										return folder;
									}
									@Override
									public boolean allowNullTeamSpace() {
										return null == getTeamSpace();
									}
								};
							}
							
							@Override
							public boolean handleError(Throwable t) {
								return false;
							}
							
							@Override
							public boolean acceptView(String viewId) {
								return true;
							}
						});
						waitOnEventService.signalProcessingDone(ticket);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						waitOnEventService.signalProcessingDone(ticket);
					}
				});
			}
			
		});
		
	}

}
