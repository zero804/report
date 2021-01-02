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
 
 
package net.datenwerke.scheduler.service.scheduler.entities;

import net.datenwerke.dtoservices.dtogenerator.annotations.EnumLabel;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.scheduler.client.scheduler.locale.SchedulerMessages;

@GenerateDto(
	dtoPackage="net.datenwerke.scheduler.client.scheduler.dto"
)
public enum Outcome {
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelOutcomeSuccess")
	SUCCESS,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelOutcomeFailure")
	FAILURE,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelOutcomeVeto")
	VETO,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelOutcomeExecuting")
	EXECUTING,
	
	@EnumLabel(msg=SchedulerMessages.class,key="enumLabelOutcomeActionVeto")
	ACTION_VETO,
}