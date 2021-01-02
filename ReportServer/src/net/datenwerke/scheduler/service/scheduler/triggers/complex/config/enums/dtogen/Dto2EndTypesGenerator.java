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
 
 
package net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.dtogen;

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
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.EndTypesDto;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.EndTypes;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.dtogen.Dto2EndTypesGenerator;

/**
 * Dto2PosoGenerator for EndTypes
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2EndTypesGenerator implements Dto2PosoGenerator<EndTypesDto,EndTypes> {

	private final Provider<DtoService> dtoServiceProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	@Inject
	public Dto2EndTypesGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public EndTypes loadPoso(EndTypesDto dto)  {
		return createPoso(dto);
	}

	public EndTypes instantiatePoso()  {
		throw new IllegalStateException("Cannot instantiate enum!");
	}

	public EndTypes createPoso(EndTypesDto dto)  {
		if (null == dto)
			return null;
		switch(dto){
			case INFINITE:
				return EndTypes.INFINITE;
			case COUNT:
				return EndTypes.COUNT;
			case DATE:
				return EndTypes.DATE;
		}
		throw new IllegalArgumentException("unknown enum type for: " + dto);
	}

	public EndTypes createUnmanagedPoso(EndTypesDto dto)  {
		return createPoso(dto);
	}

	public void mergePoso(EndTypesDto dto, EndTypes poso)  {
		/* no merging for enums */
	}

	public void mergeUnmanagedPoso(EndTypesDto dto, EndTypes poso)  {
		/* no merging for enums */
	}

	public EndTypes loadAndMergePoso(EndTypesDto dto)  {
		return createPoso(dto);
	}

	public void postProcessCreate(EndTypesDto dto, EndTypes poso)  {
	}


	public void postProcessCreateUnmanaged(EndTypesDto dto, EndTypes poso)  {
	}


	public void postProcessLoad(EndTypesDto dto, EndTypes poso)  {
	}


	public void postProcessMerge(EndTypesDto dto, EndTypes poso)  {
	}


	public void postProcessInstantiate(EndTypes poso)  {
	}



}
