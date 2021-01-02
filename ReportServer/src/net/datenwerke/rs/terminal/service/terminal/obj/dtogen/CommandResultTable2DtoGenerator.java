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
import net.datenwerke.rs.base.client.reportengines.table.dto.RSTableModelDto;
import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultTableDto;
import net.datenwerke.rs.terminal.client.terminal.dto.decorator.CommandResultTableDtoDec;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResultTable;
import net.datenwerke.rs.terminal.service.terminal.obj.dtogen.CommandResultTable2DtoGenerator;

/**
 * Poso2DtoGenerator for CommandResultTable
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class CommandResultTable2DtoGenerator implements Poso2DtoGenerator<CommandResultTable,CommandResultTableDtoDec> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public CommandResultTable2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public CommandResultTableDtoDec instantiateDto(CommandResultTable poso)  {
		CommandResultTableDtoDec dto = new CommandResultTableDtoDec();
		return dto;
	}

	public CommandResultTableDtoDec createDto(CommandResultTable poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final CommandResultTableDtoDec dto = new CommandResultTableDtoDec();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set table */
			Object tmpDtoRSTableModelDtogetTable = dtoServiceProvider.get().createDto(poso.getTable(), here, referenced);
			dto.setTable((RSTableModelDto)tmpDtoRSTableModelDtogetTable);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoRSTableModelDtogetTable, poso.getTable(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setTable((RSTableModelDto)refDto);
				}
			});

		}

		return dto;
	}


}
