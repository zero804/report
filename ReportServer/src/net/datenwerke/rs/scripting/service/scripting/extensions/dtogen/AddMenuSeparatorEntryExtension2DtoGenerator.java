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
 
 
package net.datenwerke.rs.scripting.service.scripting.extensions.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.scripting.client.scripting.dto.AddMenuSeparatorEntryExtensionDto;
import net.datenwerke.rs.scripting.service.scripting.extensions.AddMenuSeparatorEntryExtension;
import net.datenwerke.rs.scripting.service.scripting.extensions.dtogen.AddMenuSeparatorEntryExtension2DtoGenerator;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for AddMenuSeparatorEntryExtension
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class AddMenuSeparatorEntryExtension2DtoGenerator implements Poso2DtoGenerator<AddMenuSeparatorEntryExtension,AddMenuSeparatorEntryExtensionDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public AddMenuSeparatorEntryExtension2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public AddMenuSeparatorEntryExtensionDto instantiateDto(AddMenuSeparatorEntryExtension poso)  {
		AddMenuSeparatorEntryExtensionDto dto = new AddMenuSeparatorEntryExtensionDto();
		return dto;
	}

	public AddMenuSeparatorEntryExtensionDto createDto(AddMenuSeparatorEntryExtension poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final AddMenuSeparatorEntryExtensionDto dto = new AddMenuSeparatorEntryExtensionDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set menuName */
			dto.setMenuName(StringEscapeUtils.escapeXml(StringUtils.left(poso.getMenuName(),8192)));

		}

		return dto;
	}


}
