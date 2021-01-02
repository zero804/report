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
 
 
package net.datenwerke.rs.base.service.reportengines.table.output.object.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.ArrayList;
import java.util.List;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableDefinitionDto;
import net.datenwerke.rs.base.service.reportengines.table.output.object.TableDefinition;
import net.datenwerke.rs.base.service.reportengines.table.output.object.dtogen.TableDefinition2DtoGenerator;

/**
 * Poso2DtoGenerator for TableDefinition
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class TableDefinition2DtoGenerator implements Poso2DtoGenerator<TableDefinition,TableDefinitionDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public TableDefinition2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public TableDefinitionDto instantiateDto(TableDefinition poso)  {
		TableDefinitionDto dto = new TableDefinitionDto();
		return dto;
	}

	public TableDefinitionDto createDto(TableDefinition poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final TableDefinitionDto dto = new TableDefinitionDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set columnIndex */
			dto.setColumnIndex(poso.getColumnIndex() );

			/*  set columnNames */
			List<String> col_columnNames = new ArrayList<String>();
			if( null != poso.getColumnNames()){
				for(String obj : poso.getColumnNames())
					col_columnNames.add((String) obj);
				dto.setColumnNames(col_columnNames);
			}

			/*  set displaySizes */
			List<Integer> col_displaySizes = new ArrayList<Integer>();
			if( null != poso.getDisplaySizes()){
				for(Integer obj : poso.getDisplaySizes())
					col_displaySizes.add((Integer) obj);
				dto.setDisplaySizes(col_displaySizes);
			}

			/*  set sqlColumnTypes */
			List<Integer> col_sqlColumnTypes = new ArrayList<Integer>();
			if( null != poso.getSqlColumnTypes()){
				for(Integer obj : poso.getSqlColumnTypes())
					col_sqlColumnTypes.add((Integer) obj);
				dto.setSqlColumnTypes(col_sqlColumnTypes);
			}

		}

		return dto;
	}


}
