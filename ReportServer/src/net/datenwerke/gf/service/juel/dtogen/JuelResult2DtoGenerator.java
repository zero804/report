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
 
 
package net.datenwerke.gf.service.juel.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gf.client.juel.dto.JuelResultDto;
import net.datenwerke.gf.client.juel.dto.JuelResultTypeDto;
import net.datenwerke.gf.client.juel.dto.decorator.JuelResultDtoDec;
import net.datenwerke.gf.service.juel.JuelResult;
import net.datenwerke.gf.service.juel.dtogen.JuelResult2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;

/**
 * Poso2DtoGenerator for JuelResult
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class JuelResult2DtoGenerator implements Poso2DtoGenerator<JuelResult,JuelResultDtoDec> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public JuelResult2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public JuelResultDtoDec instantiateDto(JuelResult poso)  {
		JuelResultDtoDec dto = new JuelResultDtoDec();
		return dto;
	}

	public JuelResultDtoDec createDto(JuelResult poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final JuelResultDtoDec dto = new JuelResultDtoDec();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set booleanValue */
			dto.setBooleanValue(poso.isBooleanValue() );

			/*  set dateValue */
			dto.setDateValue(poso.getDateValue() );

			/*  set decimalValue */
			dto.setDecimalValue(poso.getDecimalValue() );

			/*  set doubleValue */
			dto.setDoubleValue(poso.getDoubleValue() );

			/*  set entryNull */
			dto.setEntryNull(poso.isEntryNull() );

			/*  set floatValue */
			dto.setFloatValue(poso.getFloatValue() );

			/*  set intValue */
			dto.setIntValue(poso.getIntValue() );

			/*  set longValue */
			dto.setLongValue(poso.getLongValue() );

			/*  set stringValue */
			dto.setStringValue(poso.getStringValue() );

			/*  set type */
			Object tmpDtoJuelResultTypeDtogetType = dtoServiceProvider.get().createDto(poso.getType(), referenced, referenced);
			dto.setType((JuelResultTypeDto)tmpDtoJuelResultTypeDtogetType);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoJuelResultTypeDtogetType, poso.getType(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setType((JuelResultTypeDto)refDto);
				}
			});

		}

		return dto;
	}


}
