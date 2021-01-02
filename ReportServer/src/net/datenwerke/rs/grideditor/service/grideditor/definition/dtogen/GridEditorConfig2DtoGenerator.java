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
import java.util.ArrayList;
import java.util.List;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.grideditor.client.grideditor.dto.GridEditorColumnConfigDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.GridEditorConfigDto;
import net.datenwerke.rs.grideditor.service.grideditor.definition.GridEditorColumnConfig;
import net.datenwerke.rs.grideditor.service.grideditor.definition.GridEditorConfig;
import net.datenwerke.rs.grideditor.service.grideditor.definition.dtogen.GridEditorConfig2DtoGenerator;

/**
 * Poso2DtoGenerator for GridEditorConfig
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class GridEditorConfig2DtoGenerator implements Poso2DtoGenerator<GridEditorConfig,GridEditorConfigDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public GridEditorConfig2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public GridEditorConfigDto instantiateDto(GridEditorConfig poso)  {
		GridEditorConfigDto dto = new GridEditorConfigDto();
		return dto;
	}

	public GridEditorConfigDto createDto(GridEditorConfig poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final GridEditorConfigDto dto = new GridEditorConfigDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set columnConfigs */
			final List<GridEditorColumnConfigDto> col_columnConfigs = new ArrayList<GridEditorColumnConfigDto>();
			if( null != poso.getColumnConfigs()){
				for(GridEditorColumnConfig refPoso : poso.getColumnConfigs()){
					final Object tmpDtoGridEditorColumnConfigDtogetColumnConfigs = dtoServiceProvider.get().createDto(refPoso, here, referenced);
					col_columnConfigs.add((GridEditorColumnConfigDto) tmpDtoGridEditorColumnConfigDtogetColumnConfigs);
					/* ask for dto with higher view if generated */
					((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoGridEditorColumnConfigDtogetColumnConfigs, refPoso, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
						public void callback(Object dto){
							if(null == dto)
								throw new IllegalArgumentException("expected to get dto object (columnConfigs)");
							int tmp_index = col_columnConfigs.indexOf(tmpDtoGridEditorColumnConfigDtogetColumnConfigs);
							col_columnConfigs.set(tmp_index,(GridEditorColumnConfigDto) dto);
						}
					});
				}
				dto.setColumnConfigs(col_columnConfigs);
			}

			/*  set defaultPageSize */
			dto.setDefaultPageSize(poso.getDefaultPageSize() );

			/*  set filterable */
			dto.setFilterable(poso.isFilterable() );

			/*  set hasForm */
			dto.setHasForm(poso.isHasForm() );

			/*  set maxPageSize */
			dto.setMaxPageSize(poso.getMaxPageSize() );

			/*  set paging */
			dto.setPaging(poso.isPaging() );

			/*  set sortable */
			dto.setSortable(poso.isSortable() );

			/*  set totalNumberOfRecords */
			dto.setTotalNumberOfRecords(poso.getTotalNumberOfRecords() );

		}

		return dto;
	}


}
