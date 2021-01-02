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
 
 
package net.datenwerke.rs.dashboard.service.dashboard.entities.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.IllegalArgumentException;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetContainerDto;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DadgetContainer;
import net.datenwerke.rs.dashboard.service.dashboard.entities.dtogen.DadgetContainer2DtoGenerator;

/**
 * Poso2DtoGenerator for DadgetContainer
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class DadgetContainer2DtoGenerator implements Poso2DtoGenerator<DadgetContainer,DadgetContainerDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public DadgetContainer2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public DadgetContainerDto instantiateDto(DadgetContainer poso)  {
		/* Simply return the first enum! */
		DadgetContainerDto dto = DadgetContainerDto.NORTH;
		return dto;
	}

	public DadgetContainerDto createDto(DadgetContainer poso, DtoView here, DtoView referenced)  {
		switch(poso){
			case NORTH:
				return DadgetContainerDto.NORTH;
			case CENTER:
				return DadgetContainerDto.CENTER;
			case SOUTH:
				return DadgetContainerDto.SOUTH;
		}
		throw new IllegalArgumentException("unknown enum type for: " + poso);
	}


}
