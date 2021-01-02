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
 
 
package net.datenwerke.rs.scheduleasfile.service.scheduleasfile.filter.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.filter.dto.TeamSpaceReportJobFilterDto;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.filter.TeamSpaceReportJobFilter;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.filter.dtogen.TeamSpaceReportJobFilter2DtoGenerator;

/**
 * Poso2DtoGenerator for TeamSpaceReportJobFilter
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class TeamSpaceReportJobFilter2DtoGenerator implements Poso2DtoGenerator<TeamSpaceReportJobFilter,TeamSpaceReportJobFilterDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public TeamSpaceReportJobFilter2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public TeamSpaceReportJobFilterDto instantiateDto(TeamSpaceReportJobFilter poso)  {
		TeamSpaceReportJobFilterDto dto = new TeamSpaceReportJobFilterDto();
		return dto;
	}

	public TeamSpaceReportJobFilterDto createDto(TeamSpaceReportJobFilter poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final TeamSpaceReportJobFilterDto dto = new TeamSpaceReportJobFilterDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set teamspaceId */
			dto.setTeamspaceId(poso.getTeamspaceId() );

		}

		return dto;
	}


}
