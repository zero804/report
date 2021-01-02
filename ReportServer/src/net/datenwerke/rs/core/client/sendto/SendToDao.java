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
 
 
package net.datenwerke.rs.core.client.sendto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.helper.simpleform.ExportTypeSelection;
import net.datenwerke.rs.core.client.reportexecutor.hooks.PrepareReportModelForStorageOrExecutionHook;
import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.sendto.rpc.SendToRpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class SendToDao extends Dao {

	private final SendToRpcServiceAsync rpcService;
	private final HookHandlerService hookHandler;

	@Inject
	public SendToDao(SendToRpcServiceAsync rpcService,
			HookHandlerService hookHandler) {
		super();
		this.rpcService = rpcService;
		this.hookHandler = hookHandler;
	}
	
	public void loadClientConfigsFor(ReportDto report,
			RsAsyncCallback<ArrayList<SendToClientConfig>> callback){
		rpcService.loadClientConfigsFor(report, callback);
	}

	public void sendTo(ReportDto report, String executorToken, String id, String format, List<ReportExecutionConfigDto> formatConfig, HashMap<String, String> values, AsyncCallback<String> callback){
		for(PrepareReportModelForStorageOrExecutionHook hooker : hookHandler.getHookers(PrepareReportModelForStorageOrExecutionHook.class)){
			if(hooker.consumes(report)){
				hooker.prepareForExecutionOrStorage(report, executorToken);
			}
		}
		
		rpcService.sendTo(report, executorToken, id, format, formatConfig, values, callback);
	}
}
