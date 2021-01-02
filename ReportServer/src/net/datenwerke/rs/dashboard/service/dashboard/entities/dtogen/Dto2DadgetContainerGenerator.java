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
 
 
package net.datenwerke.rs.dashboard.service.dashboard.entities.dtogen;

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
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetContainerDto;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DadgetContainer;
import net.datenwerke.rs.dashboard.service.dashboard.entities.dtogen.Dto2DadgetContainerGenerator;

/**
 * Dto2PosoGenerator for DadgetContainer
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2DadgetContainerGenerator implements Dto2PosoGenerator<DadgetContainerDto,DadgetContainer> {

	private final Provider<DtoService> dtoServiceProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	@Inject
	public Dto2DadgetContainerGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public DadgetContainer loadPoso(DadgetContainerDto dto)  {
		return createPoso(dto);
	}

	public DadgetContainer instantiatePoso()  {
		throw new IllegalStateException("Cannot instantiate enum!");
	}

	public DadgetContainer createPoso(DadgetContainerDto dto)  {
		if (null == dto)
			return null;
		switch(dto){
			case NORTH:
				return DadgetContainer.NORTH;
			case CENTER:
				return DadgetContainer.CENTER;
			case SOUTH:
				return DadgetContainer.SOUTH;
		}
		throw new IllegalArgumentException("unknown enum type for: " + dto);
	}

	public DadgetContainer createUnmanagedPoso(DadgetContainerDto dto)  {
		return createPoso(dto);
	}

	public void mergePoso(DadgetContainerDto dto, DadgetContainer poso)  {
		/* no merging for enums */
	}

	public void mergeUnmanagedPoso(DadgetContainerDto dto, DadgetContainer poso)  {
		/* no merging for enums */
	}

	public DadgetContainer loadAndMergePoso(DadgetContainerDto dto)  {
		return createPoso(dto);
	}

	public void postProcessCreate(DadgetContainerDto dto, DadgetContainer poso)  {
	}


	public void postProcessCreateUnmanaged(DadgetContainerDto dto, DadgetContainer poso)  {
	}


	public void postProcessLoad(DadgetContainerDto dto, DadgetContainer poso)  {
	}


	public void postProcessMerge(DadgetContainerDto dto, DadgetContainer poso)  {
	}


	public void postProcessInstantiate(DadgetContainer poso)  {
	}



}
