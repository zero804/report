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
import net.datenwerke.scheduler.client.scheduler.dto.history.HistoryEntryPropertyDto;
import net.datenwerke.scheduler.service.scheduler.entities.history.HistoryEntryProperty;
import net.datenwerke.scheduler.service.scheduler.entities.history.dtogen.HistoryEntryProperty2DtoGenerator;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for HistoryEntryProperty
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class HistoryEntryProperty2DtoGenerator implements Poso2DtoGenerator<HistoryEntryProperty,HistoryEntryPropertyDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public HistoryEntryProperty2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public HistoryEntryPropertyDto instantiateDto(HistoryEntryProperty poso)  {
		HistoryEntryPropertyDto dto = new HistoryEntryPropertyDto();
		return dto;
	}

	public HistoryEntryPropertyDto createDto(HistoryEntryProperty poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final HistoryEntryPropertyDto dto = new HistoryEntryPropertyDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.MINIMAL) >= 0){
			/*  set id */
			dto.setId(poso.getId() );

		}
		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set key */
			dto.setKey(StringEscapeUtils.escapeXml(StringUtils.left(poso.getKey(),8192)));

			/*  set value */
			dto.setValue(StringEscapeUtils.escapeXml(StringUtils.left(poso.getValue(),8192)));

		}

		return dto;
	}


}
