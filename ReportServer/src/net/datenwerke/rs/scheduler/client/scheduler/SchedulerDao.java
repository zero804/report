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
 
 
package net.datenwerke.rs.scheduler.client.scheduler;

import java.util.List;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinition;
import net.datenwerke.rs.scheduler.client.scheduler.rpc.SchedulerRpcServiceAsync;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto.ReportScheduleJobInformation;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto.ReportScheduleJobListInformation;
import net.datenwerke.scheduler.client.scheduler.dto.filter.JobFilterConfigurationDto;
import net.datenwerke.scheduler.client.scheduler.dto.filter.JobFilterCriteriaDto;
import net.datenwerke.scheduler.client.scheduler.dto.history.ExecutionLogEntryDto;
import net.datenwerke.security.client.security.dto.RightDto;

public class SchedulerDao extends Dao {

	private final SchedulerRpcServiceAsync rpcService;

	@Inject
	public SchedulerDao(SchedulerRpcServiceAsync rpcService) {
		super();
		this.rpcService = rpcService;
	}
	
	public void schedule(ReportScheduleDefinition scheduleDTO, AsyncCallback<Void> callback){
		rpcService.schedule(scheduleDTO, transformAndKeepCallback(callback));
	}

	public void reschedule(Long jobId, ReportScheduleDefinition scheduleDTO, AsyncCallback<Void> callback){
		rpcService.reschedule(jobId, scheduleDTO, transformAndKeepCallback(callback));
	}

	public void unschedule(Long jobId, AsyncCallback<Boolean> callback){
		rpcService.unschedule(jobId, transformAndKeepCallback(callback));
	}

	public void loadFullScheduleInformation(
			ReportScheduleJobListInformation selected,
			AsyncCallback<ReportScheduleJobInformation> callback) {
		rpcService.loadFullScheduleInformation(selected, transformAndKeepCallback(callback));
	}

	public void getReportJobList(
			JobFilterConfigurationDto jobFilterConfig,
			List<JobFilterCriteriaDto> addCriterions, AsyncCallback<PagingLoadResult<ReportScheduleJobListInformation>> callback) {
		rpcService.getReportJobList(jobFilterConfig, addCriterions, transformAndKeepCallback(callback));
	}
	
	public void getReportJobList(ReportDto reportDto, AsyncCallback<List<ReportScheduleJobListInformation>> callback) {
		rpcService.getReportJobList(reportDto, transformAndKeepCallback(callback));
	}
	
	public void getReportJobListAsHtml(ReportDto reportDto, AsyncCallback<SafeHtml> callback) {
		rpcService.getReportJobListAsHtml(reportDto, transformAndKeepCallback(callback));
	}
	
	public void getReportFor(Long jobId, AsyncCallback<ReportDto> callback){
		rpcService.getReportFor(jobId, transformAndKeepCallback(callback));
	}
	
	public void loadDetailsFor(ReportScheduleJobListInformation selected,
			AsyncCallback<ReportScheduleJobInformation> callback){
		rpcService.loadDetailsFor(selected, transformAndKeepCallback(callback));
	}
	
	public void loadFullDetailsFor(Long jobId, ExecutionLogEntryDto entry,
			AsyncCallback<ExecutionLogEntryDto> callback){
		rpcService.loadFullDetailsFor(jobId, entry, transformAndKeepCallback(callback));
	}
	
	public void scheduleOnce(Long jobId,  AsyncCallback<Void> callback) {
		rpcService.scheduleOnce(jobId, transformAndKeepCallback(callback));
	}

	public void remove(Long jobId, RsAsyncCallback<Boolean> callback) {
		rpcService.remove(jobId, transformAndKeepCallback(callback));
	}
	
	public void clearErrorStatus(Long jobId, RsAsyncCallback<Void> callback) {
		rpcService.clearErrorStatus(jobId, transformAndKeepCallback(callback));
	}
	
	public void assertOwnersHaveReportRights(List<Long> ownerIds, ReportDto reportDto,
			List<? extends RightDto> rightDtos, RsAsyncCallback<Void> callback) {
		rpcService.assertOwnersHaveReportRights(ownerIds, reportDto, rightDtos, transformAndKeepCallback(callback));
	}
}
