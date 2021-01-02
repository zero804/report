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
 
 
package net.datenwerke.rs.terminal.service.terminal.obj.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.terminal.client.terminal.dto.CreMessageDto;
import net.datenwerke.rs.terminal.service.terminal.obj.CreMessage;
import net.datenwerke.rs.terminal.service.terminal.obj.dtogen.CreMessage2DtoGenerator;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for CreMessage
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class CreMessage2DtoGenerator implements Poso2DtoGenerator<CreMessage,CreMessageDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public CreMessage2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public CreMessageDto instantiateDto(CreMessage poso)  {
		CreMessageDto dto = new CreMessageDto();
		return dto;
	}

	public CreMessageDto createDto(CreMessage poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final CreMessageDto dto = new CreMessageDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set height */
			dto.setHeight(poso.getHeight() );

			/*  set html */
			dto.setHtml(poso.getHtml() );

			/*  set text */
			dto.setText(StringEscapeUtils.escapeXml(StringUtils.left(poso.getText(),8192)));

			/*  set title */
			dto.setTitle(StringEscapeUtils.escapeXml(StringUtils.left(poso.getTitle(),8192)));

			/*  set width */
			dto.setWidth(poso.getWidth() );

			/*  set windowTitle */
			dto.setWindowTitle(StringEscapeUtils.escapeXml(StringUtils.left(poso.getWindowTitle(),8192)));

		}

		return dto;
	}


}
