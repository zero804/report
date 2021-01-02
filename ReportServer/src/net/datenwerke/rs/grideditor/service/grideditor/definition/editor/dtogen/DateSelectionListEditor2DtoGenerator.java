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
 
 
package net.datenwerke.rs.grideditor.service.grideditor.definition.editor.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.grideditor.client.grideditor.dto.DateSelectionListEditorDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.decorator.DateSelectionListEditorDtoDec;
import net.datenwerke.rs.grideditor.service.grideditor.definition.editor.DateSelectionListEditor;
import net.datenwerke.rs.grideditor.service.grideditor.definition.editor.dtogen.DateSelectionListEditor2DtoGenerator;

/**
 * Poso2DtoGenerator for DateSelectionListEditor
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class DateSelectionListEditor2DtoGenerator implements Poso2DtoGenerator<DateSelectionListEditor,DateSelectionListEditorDtoDec> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public DateSelectionListEditor2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public DateSelectionListEditorDtoDec instantiateDto(DateSelectionListEditor poso)  {
		DateSelectionListEditorDtoDec dto = new DateSelectionListEditorDtoDec();
		return dto;
	}

	public DateSelectionListEditorDtoDec createDto(DateSelectionListEditor poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final DateSelectionListEditorDtoDec dto = new DateSelectionListEditorDtoDec();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set forceSelection */
			dto.setForceSelection(poso.isForceSelection() );

			/*  set valueMap */
			dto.setValueMap(poso.getValueMap() );

			/*  set values */
			List<Date> col_values = new ArrayList<Date>();
			if( null != poso.getValues()){
				for(Date obj : poso.getValues())
					col_values.add((Date) obj);
				dto.setValues(col_values);
			}

		}

		return dto;
	}


}
