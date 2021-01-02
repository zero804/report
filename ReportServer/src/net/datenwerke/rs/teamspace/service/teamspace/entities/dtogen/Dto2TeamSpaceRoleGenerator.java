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
 
 
package net.datenwerke.rs.teamspace.service.teamspace.entities.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
import java.lang.RuntimeException;
import java.util.Collection;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoGenerator;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceRoleDto;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpaceRole;
import net.datenwerke.rs.teamspace.service.teamspace.entities.dtogen.Dto2TeamSpaceRoleGenerator;

/**
 * Dto2PosoGenerator for TeamSpaceRole
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2TeamSpaceRoleGenerator implements Dto2PosoGenerator<TeamSpaceRoleDto,TeamSpaceRole> {

	private final Provider<DtoService> dtoServiceProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	@Inject
	public Dto2TeamSpaceRoleGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public TeamSpaceRole loadPoso(TeamSpaceRoleDto dto)  {
		return createPoso(dto);
	}

	public TeamSpaceRole instantiatePoso()  {
		throw new IllegalStateException("Cannot instantiate enum!");
	}

	public TeamSpaceRole createPoso(TeamSpaceRoleDto dto)  {
		if (null == dto)
			return null;
		switch(dto){
			case ADMIN:
				return TeamSpaceRole.ADMIN;
			case MANAGER:
				return TeamSpaceRole.MANAGER;
			case USER:
				return TeamSpaceRole.USER;
			case GUEST:
				return TeamSpaceRole.GUEST;
		}
		throw new IllegalArgumentException("unknown enum type for: " + dto);
	}

	public TeamSpaceRole createUnmanagedPoso(TeamSpaceRoleDto dto)  {
		return createPoso(dto);
	}

	public void mergePoso(TeamSpaceRoleDto dto, TeamSpaceRole poso)  {
		/* no merging for enums */
	}

	public void mergeUnmanagedPoso(TeamSpaceRoleDto dto, TeamSpaceRole poso)  {
		/* no merging for enums */
	}

	public TeamSpaceRole loadAndMergePoso(TeamSpaceRoleDto dto)  {
		return createPoso(dto);
	}

	public void postProcessCreate(TeamSpaceRoleDto dto, TeamSpaceRole poso)  {
	}


	public void postProcessCreateUnmanaged(TeamSpaceRoleDto dto, TeamSpaceRole poso)  {
	}


	public void postProcessLoad(TeamSpaceRoleDto dto, TeamSpaceRole poso)  {
	}


	public void postProcessMerge(TeamSpaceRoleDto dto, TeamSpaceRole poso)  {
	}


	public void postProcessInstantiate(TeamSpaceRole poso)  {
	}



}
