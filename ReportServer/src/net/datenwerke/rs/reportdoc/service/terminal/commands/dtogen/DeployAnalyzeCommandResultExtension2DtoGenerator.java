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
 
 
package net.datenwerke.rs.reportdoc.service.terminal.commands.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.reportdoc.client.dto.DeployAnalyzeCommandResultExtensionDto;
import net.datenwerke.rs.reportdoc.service.terminal.commands.DeployAnalyzeCommandResultExtension;
import net.datenwerke.rs.reportdoc.service.terminal.commands.dtogen.DeployAnalyzeCommandResultExtension2DtoGenerator;

/**
 * Poso2DtoGenerator for DeployAnalyzeCommandResultExtension
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class DeployAnalyzeCommandResultExtension2DtoGenerator implements Poso2DtoGenerator<DeployAnalyzeCommandResultExtension,DeployAnalyzeCommandResultExtensionDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public DeployAnalyzeCommandResultExtension2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public DeployAnalyzeCommandResultExtensionDto instantiateDto(DeployAnalyzeCommandResultExtension poso)  {
		DeployAnalyzeCommandResultExtensionDto dto = new DeployAnalyzeCommandResultExtensionDto();
		return dto;
	}

	public DeployAnalyzeCommandResultExtensionDto createDto(DeployAnalyzeCommandResultExtension poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final DeployAnalyzeCommandResultExtensionDto dto = new DeployAnalyzeCommandResultExtensionDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set leftReport */
			Object tmpDtoReportDtogetLeftReport = dtoServiceProvider.get().createDto(poso.getLeftReport(), referenced, referenced);
			dto.setLeftReport((ReportDto)tmpDtoReportDtogetLeftReport);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoReportDtogetLeftReport, poso.getLeftReport(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setLeftReport((ReportDto)refDto);
				}
			});

			/*  set rightReport */
			Object tmpDtoReportDtogetRightReport = dtoServiceProvider.get().createDto(poso.getRightReport(), referenced, referenced);
			dto.setRightReport((ReportDto)tmpDtoReportDtogetRightReport);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoReportDtogetRightReport, poso.getRightReport(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setRightReport((ReportDto)refDto);
				}
			});

		}

		return dto;
	}


}
