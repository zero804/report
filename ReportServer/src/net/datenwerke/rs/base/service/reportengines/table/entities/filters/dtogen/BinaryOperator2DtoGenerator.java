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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities.filters.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.IllegalArgumentException;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.client.reportengines.table.dto.BinaryOperatorDto;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.BinaryOperator;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.dtogen.BinaryOperator2DtoGenerator;

/**
 * Poso2DtoGenerator for BinaryOperator
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class BinaryOperator2DtoGenerator implements Poso2DtoGenerator<BinaryOperator,BinaryOperatorDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public BinaryOperator2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public BinaryOperatorDto instantiateDto(BinaryOperator poso)  {
		/* Simply return the first enum! */
		BinaryOperatorDto dto = BinaryOperatorDto.LESS;
		return dto;
	}

	public BinaryOperatorDto createDto(BinaryOperator poso, DtoView here, DtoView referenced)  {
		switch(poso){
			case LESS:
				return BinaryOperatorDto.LESS;
			case LESS_OR_EQUALS:
				return BinaryOperatorDto.LESS_OR_EQUALS;
			case EQUALS:
				return BinaryOperatorDto.EQUALS;
			case NOT_EQUALS:
				return BinaryOperatorDto.NOT_EQUALS;
			case GREATER:
				return BinaryOperatorDto.GREATER;
			case GREATER_OR_EQUALS:
				return BinaryOperatorDto.GREATER_OR_EQUALS;
		}
		throw new IllegalArgumentException("unknown enum type for: " + poso);
	}


}
