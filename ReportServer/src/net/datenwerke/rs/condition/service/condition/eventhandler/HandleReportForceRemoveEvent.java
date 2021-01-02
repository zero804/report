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
 
 
package net.datenwerke.rs.condition.service.condition.eventhandler;

import java.util.List;

import com.google.inject.Inject;

import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.condition.service.condition.ConditionService;
import net.datenwerke.rs.condition.service.condition.entity.ReportCondition;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.security.service.eventlogger.jpa.ForceRemoveEntityEvent;

public class HandleReportForceRemoveEvent implements EventHandler<ForceRemoveEntityEvent> {

	private final ConditionService conditionService;
	
	@Inject
	public HandleReportForceRemoveEvent(ConditionService conditionService) {
		this.conditionService = conditionService;
	}

	@Override
	public void handle(ForceRemoveEntityEvent event) {
		TableReport report = (TableReport) event.getObject();
		
		List<ReportCondition> conditions = conditionService.getReportConditionsFor(report);
		if(null != conditions && ! conditions.isEmpty()){
			for(ReportCondition condition : conditions){
				conditionService.remove(condition);
			}
		}
	}


}