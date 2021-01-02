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
 
 
package net.datenwerke.scheduler.service.scheduler.entities.history.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import net.datenwerke.scheduler.client.scheduler.dto.OutcomeDto;
import net.datenwerke.scheduler.client.scheduler.dto.history.ActionEntryDto;
import net.datenwerke.scheduler.service.scheduler.entities.history.ActionEntry;
import net.datenwerke.scheduler.service.scheduler.entities.history.dtogen.ActionEntry2DtoGenerator;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for ActionEntry
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class ActionEntry2DtoGenerator implements Poso2DtoGenerator<ActionEntry,ActionEntryDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public ActionEntry2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public ActionEntryDto instantiateDto(ActionEntry poso)  {
		ActionEntryDto dto = new ActionEntryDto();
		return dto;
	}

	public ActionEntryDto createDto(ActionEntry poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final ActionEntryDto dto = new ActionEntryDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.MINIMAL) >= 0){
			/*  set id */
			dto.setId(poso.getId() );

		}
		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set actionName */
			dto.setActionName(StringEscapeUtils.escapeXml(StringUtils.left(poso.getActionName(),8192)));

			/*  set errorDescription */
			dto.setErrorDescription(StringEscapeUtils.escapeXml(StringUtils.left(poso.getErrorDescription(),8192)));

			/*  set outcome */
			Object tmpDtoOutcomeDtogetOutcome = dtoServiceProvider.get().createDto(poso.getOutcome(), referenced, referenced);
			dto.setOutcome((OutcomeDto)tmpDtoOutcomeDtogetOutcome);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoOutcomeDtogetOutcome, poso.getOutcome(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setOutcome((OutcomeDto)refDto);
				}
			});

		}

		return dto;
	}


}
