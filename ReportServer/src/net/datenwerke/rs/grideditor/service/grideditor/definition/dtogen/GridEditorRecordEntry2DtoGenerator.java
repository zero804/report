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
 
 
package net.datenwerke.rs.grideditor.service.grideditor.definition.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.grideditor.client.grideditor.dto.GridEditorRecordEntryDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.decorator.GridEditorRecordEntryDtoDec;
import net.datenwerke.rs.grideditor.service.grideditor.definition.GridEditorRecordEntry;
import net.datenwerke.rs.grideditor.service.grideditor.definition.dtogen.GridEditorRecordEntry2DtoGenerator;

/**
 * Poso2DtoGenerator for GridEditorRecordEntry
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class GridEditorRecordEntry2DtoGenerator implements Poso2DtoGenerator<GridEditorRecordEntry,GridEditorRecordEntryDtoDec> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public GridEditorRecordEntry2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public GridEditorRecordEntryDtoDec instantiateDto(GridEditorRecordEntry poso)  {
		GridEditorRecordEntryDtoDec dto = new GridEditorRecordEntryDtoDec();
		return dto;
	}

	public GridEditorRecordEntryDtoDec createDto(GridEditorRecordEntry poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final GridEditorRecordEntryDtoDec dto = new GridEditorRecordEntryDtoDec();
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
			dto.setType(poso.getType() );

		}

		return dto;
	}


}
