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
 
 
package net.datenwerke.rs.core.server.sendto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.helper.simpleform.ExportTypeSelection;
import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.sendto.SendToClientConfig;
import net.datenwerke.rs.core.client.sendto.rpc.SendToRpcService;
import net.datenwerke.rs.core.server.reportexport.hooks.ReportExportViaSessionHook;
import net.datenwerke.rs.core.service.reportmanager.ReportDtoService;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.RECReportExecutorToken;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.sendto.hooks.SendToTargetProviderHook;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.annotation.ArgumentVerification;
import net.datenwerke.security.service.security.annotation.RightsVerification;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Execute;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class SendToRpcServiceImpl extends SecuredRemoteServiceServlet implements SendToRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final HookHandlerService hookHandlerService;
	private final DtoService dtoService;
	private final ReportDtoService reportDtoService;
	private final ReportExecutorService reportExecutorService;
	private final ReportService reportService;
	private final SecurityService securityService;
	
	@Inject
	public SendToRpcServiceImpl(
			HookHandlerService hookHandlerService,
			DtoService dtoService,
			ReportDtoService reportDtoService,
			ReportExecutorService reportExecutorService,
			ReportService reportService,
			SecurityService securityService
			) {
		super();
		this.hookHandlerService = hookHandlerService;
		this.dtoService = dtoService;
		this.reportDtoService = reportDtoService;
		this.reportExecutorService = reportExecutorService;
		this.reportService = reportService;
		this.securityService = securityService;
	}



	@SecurityChecked(
			argumentVerification = {
					@ArgumentVerification(
							name = "report",
							isDto = true,
							verify = @RightsVerification(rights={Execute.class})
							)
			}
			)
	@Override
	public ArrayList<SendToClientConfig> loadClientConfigsFor(@Named("report")ReportDto reportDto)
			throws ServerCallFailedException {
		Report report = (Report) dtoService.loadPoso(reportDto);
		
		/* check rights */
		if(! securityService.checkRights(report, Execute.class))
			throw new ViolatedSecurityException();
		
		ArrayList<SendToClientConfig> list = new ArrayList<SendToClientConfig>();
		
		for(SendToTargetProviderHook hooker : hookHandlerService.getHookers(SendToTargetProviderHook.class)){
			SendToClientConfig config = hooker.consumes(report);
			if(null != config){
				config.setId(hooker.getId());
				config.setSupportsScheduling(hooker.supportsScheduling());
				list.add(config);
			}
		}

		return list;
	}
	
	@SecurityChecked(
			argumentVerification = {
					@ArgumentVerification(
							name = "report",
							isDto = true,
							verify = @RightsVerification(rights={Execute.class})
							)
			}
			)
	@Override
	public String sendTo(@Named("report") ReportDto reportDto, String executorToken, String id, String format, List<ReportExecutionConfigDto> formatConfig, HashMap<String, String> values) throws ServerCallFailedException {
		for(SendToTargetProviderHook hooker : hookHandlerService.getHookers(SendToTargetProviderHook.class)){
			if(! id.equals(hooker.getId()))
				continue;
			/* get a clean and unmanaged report from the database */
			Report referenceReport = reportDtoService.getReferenceReport(reportDto);
			Report orgReport = (Report) reportService.getUnmanagedReportById(reportDto.getId());
			
			/* check rights */
			if(! securityService.checkRights(referenceReport, Execute.class))
				throw new ViolatedSecurityException();
			
			/* create variant */
			Report adjustedReport = (Report) dtoService.createUnmanagedPoso(reportDto);
			Report toExecute = orgReport.createTemporaryVariant(adjustedReport);
			
			final ReportExecutionConfig[] configArray = getConfigArray(executorToken, formatConfig);
			for(ReportExportViaSessionHook viaSessionHooker : hookHandlerService.getHookers(ReportExportViaSessionHook.class)){
				viaSessionHooker.adjustReport(toExecute, configArray);
			}

			if(null == format){
				/* call handler without executed report */
				try{
					return hooker.sendTo(toExecute, values, new ReportExecutionConfig[]{new RECReportExecutorToken(executorToken)});
				} catch(Exception e){
					throw new ServerCallFailedException("Could not execute send to " + id, e);
				}
			} else {
				/* execute report */
				try {
					ReportExecutionConfig[] executionConfig = getConfigArray(executorToken, formatConfig);
					
					CompiledReport cReport = reportExecutorService.execute(toExecute, format, executionConfig);
					
					return hooker.sendTo(cReport, toExecute, format, values, new ReportExecutionConfig[]{new RECReportExecutorToken(executorToken)});
				} catch(Exception e){
					throw new ServerCallFailedException("Could not execute send to " + id, e);
				}
			}
			
			
		}
		throw new ServerCallFailedException("Could not find SendToProvider for " + id);
	}

	private ReportExecutionConfig[] getConfigArray(String executorToken,
			List<ReportExecutionConfigDto> configs) throws ExpectedException {
		
		if(null == configs) {
			ReportExecutionConfig[] configArray = { new RECReportExecutorToken(executorToken) };
			return configArray;
		} 
			
		ReportExecutionConfig[] configArray = new ReportExecutionConfig[configs.size()+1];
		for(int i = 0; i < configs.size(); i++)
			configArray[i] = (ReportExecutionConfig) dtoService.createPoso(configs.get(i));
		configArray[configs.size()] = new RECReportExecutorToken(executorToken);
		
		return configArray;
	}

}
