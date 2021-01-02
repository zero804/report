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
 
 
package net.datenwerke.rs.condition.server.condition;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import com.google.inject.Inject;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.condition.client.condition.Condition;
import net.datenwerke.rs.condition.client.condition.dto.ReportConditionDto;
import net.datenwerke.rs.condition.client.condition.dto.ScheduleConditionDto;
import net.datenwerke.rs.condition.client.condition.dto.SimpleCondition;
import net.datenwerke.rs.condition.client.condition.rpc.ConditionRpcService;
import net.datenwerke.rs.condition.service.condition.ConditionService;
import net.datenwerke.rs.condition.service.condition.entity.ReportCondition;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Execute;
import net.datenwerke.security.service.security.rights.Read;

@Singleton
public class ConditionRpcServiceImpl extends SecuredRemoteServiceServlet
		implements ConditionRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2080108094731030733L;

	
	private final ConditionService conditionService;
	private final DtoService dtoService;
	private final SecurityService securityService;


	@Inject
	public ConditionRpcServiceImpl(
		ConditionService conditionService,
		DtoService dtoService,
		SecurityService securityService
		) {

		/* store objects */
		this.conditionService = conditionService;
		this.dtoService = dtoService;
		this.securityService = securityService;
	}


	@Override
	public List<Condition> getConditions(ReportDto reportDto) throws ServerCallFailedException {
		Report report = (Report) dtoService.loadPoso(reportDto);
		
		if ( ! securityService.checkRights(report, Execute.class)) {
			throw new ViolatedSecurityException("insufficient rights");
		}
		
		List<Condition> result = new ArrayList<Condition>();
		for(ReportCondition cond : conditionService.getReportConditions()){
			Report reportCond = cond.getReport();
			if(null == reportCond || ! (reportCond instanceof TableReport))
				continue;
			
			if(securityService.checkRights(reportCond, Read.class, Execute.class))
				result.add((ReportConditionDto) dtoService.createDto(cond));
		}
		
		/* add simple conditions */
		result.addAll(conditionService.getSimpleConditionsFor(report));
		
		return result;
	}


	@Override
	public List<String> getReplacementsFor(ReportConditionDto conditionDto)
			throws ServerCallFailedException {
		ReportCondition cond = (ReportCondition) dtoService.loadPoso(conditionDto);
		Report report = cond.getReport();
		if(null == report || ! (report instanceof TableReport))
			throw new ServerCallFailedException("Could not resolve report.");
		
		if(! securityService.checkRights(report, Read.class, Execute.class))
			throw new ViolatedSecurityException();
		
		return conditionService.getReplacementsFor(cond);
	}


	@Override
	public boolean executeCondition(ScheduleConditionDto scheduleCondition)
			throws ServerCallFailedException, ExpectedException {
		
		Condition condition = scheduleCondition.getCondition();
		if(condition instanceof ReportConditionDto){
			ReportCondition cond = (ReportCondition) dtoService.loadPoso(condition);
			Report report = cond.getReport();
			if(null == report || ! (report instanceof TableReport))
				throw new ServerCallFailedException("Could not resolve report.");
			
			if(! securityService.checkRights(report, Read.class, Execute.class))
				throw new ViolatedSecurityException();
	
			try {
				return conditionService.executeCondition(cond, scheduleCondition.getExpression());
			} catch (Exception e) {
				throw new ExpectedException(e);
			}
		} else if(condition instanceof SimpleCondition){
			try {
				return conditionService.executeCondition(condition, scheduleCondition.getExpression());
			} catch (Exception e) {
				throw new ExpectedException(e);
			}
		}
		
		throw new IllegalArgumentException("Expected Report- or SimpleCondtion");
	}
	
}
