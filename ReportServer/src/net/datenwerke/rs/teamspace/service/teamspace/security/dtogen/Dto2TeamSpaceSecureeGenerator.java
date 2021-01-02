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
 
 
package net.datenwerke.rs.teamspace.service.teamspace.security.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.Exception;
import java.lang.RuntimeException;
import java.lang.reflect.Field;
import java.util.Collection;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoGenerator;
import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.validator.DtoPropertyValidator;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.teamspace.client.teamspace.security.TeamSpaceSecureeDto;
import net.datenwerke.rs.teamspace.service.teamspace.security.TeamSpaceSecuree;
import net.datenwerke.rs.teamspace.service.teamspace.security.dtogen.Dto2TeamSpaceSecureeGenerator;
import net.datenwerke.rs.utils.entitycloner.annotation.TransientID;
import net.datenwerke.rs.utils.reflection.ReflectionService;

/**
 * Dto2PosoGenerator for TeamSpaceSecuree
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2TeamSpaceSecureeGenerator implements Dto2PosoGenerator<TeamSpaceSecureeDto,TeamSpaceSecuree> {

	private final Provider<DtoService> dtoServiceProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	private final ReflectionService reflectionService;
	@Inject
	public Dto2TeamSpaceSecureeGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider,
		ReflectionService reflectionService
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
		this.reflectionService = reflectionService;
	}

	public TeamSpaceSecuree loadPoso(TeamSpaceSecureeDto dto)  {
		/* Poso is not an entity .. so what should I load? */
		return null;
	}

	public TeamSpaceSecuree instantiatePoso()  {
		TeamSpaceSecuree poso = new TeamSpaceSecuree();
		return poso;
	}

	public TeamSpaceSecuree createPoso(TeamSpaceSecureeDto dto)  throws ExpectedException {
		TeamSpaceSecuree poso = new TeamSpaceSecuree();

		/* merge data */
		mergePoso(dto, poso);
		return poso;
	}

	public TeamSpaceSecuree createUnmanagedPoso(TeamSpaceSecureeDto dto)  throws ExpectedException {
		TeamSpaceSecuree poso = new TeamSpaceSecuree();

		mergePlainDto2UnmanagedPoso(dto,poso);

		return poso;
	}

	public void mergePoso(TeamSpaceSecureeDto dto, final TeamSpaceSecuree poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2Poso(dto, poso);
		else
			mergePlainDto2Poso(dto, poso);
	}

	protected void mergePlainDto2Poso(TeamSpaceSecureeDto dto, final TeamSpaceSecuree poso)  throws ExpectedException {
	}

	protected void mergeProxy2Poso(TeamSpaceSecureeDto dto, final TeamSpaceSecuree poso)  throws ExpectedException {
	}

	public void mergeUnmanagedPoso(TeamSpaceSecureeDto dto, final TeamSpaceSecuree poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2UnmanagedPoso(dto, poso);
		else
			mergePlainDto2UnmanagedPoso(dto, poso);
	}

	protected void mergePlainDto2UnmanagedPoso(TeamSpaceSecureeDto dto, final TeamSpaceSecuree poso)  throws ExpectedException {
	}

	protected void mergeProxy2UnmanagedPoso(TeamSpaceSecureeDto dto, final TeamSpaceSecuree poso)  throws ExpectedException {
	}

	public TeamSpaceSecuree loadAndMergePoso(TeamSpaceSecureeDto dto)  throws ExpectedException {
		TeamSpaceSecuree poso = loadPoso(dto);
		if(null != poso){
			mergePoso(dto, poso);
			return poso;
		}
		return createPoso(dto);
	}

	public void postProcessCreate(TeamSpaceSecureeDto dto, TeamSpaceSecuree poso)  {
	}


	public void postProcessCreateUnmanaged(TeamSpaceSecureeDto dto, TeamSpaceSecuree poso)  {
	}


	public void postProcessLoad(TeamSpaceSecureeDto dto, TeamSpaceSecuree poso)  {
	}


	public void postProcessMerge(TeamSpaceSecureeDto dto, TeamSpaceSecuree poso)  {
	}


	public void postProcessInstantiate(TeamSpaceSecuree poso)  {
	}



}
