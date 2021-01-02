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
import net.datenwerke.rs.dashboard.client.dashboard.dto.LayoutTypeDto;
import net.datenwerke.rs.dashboard.service.dashboard.entities.LayoutType;
import net.datenwerke.rs.dashboard.service.dashboard.entities.dtogen.LayoutType2DtoGenerator;

/**
 * Poso2DtoGenerator for LayoutType
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class LayoutType2DtoGenerator implements Poso2DtoGenerator<LayoutType,LayoutTypeDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public LayoutType2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public LayoutTypeDto instantiateDto(LayoutType poso)  {
		/* Simply return the first enum! */
		LayoutTypeDto dto = LayoutTypeDto.ONE_COLUMN;
		return dto;
	}

	public LayoutTypeDto createDto(LayoutType poso, DtoView here, DtoView referenced)  {
		switch(poso){
			case ONE_COLUMN:
				return LayoutTypeDto.ONE_COLUMN;
			case TWO_COLUMN_EQUI:
				return LayoutTypeDto.TWO_COLUMN_EQUI;
			case TWO_COLUMN_LEFT_LARGE:
				return LayoutTypeDto.TWO_COLUMN_LEFT_LARGE;
			case TWO_COLUMN_RIGHT_LARGE:
				return LayoutTypeDto.TWO_COLUMN_RIGHT_LARGE;
			case THREE_COLUMN:
				return LayoutTypeDto.THREE_COLUMN;
		}
		throw new IllegalArgumentException("unknown enum type for: " + poso);
	}


}
