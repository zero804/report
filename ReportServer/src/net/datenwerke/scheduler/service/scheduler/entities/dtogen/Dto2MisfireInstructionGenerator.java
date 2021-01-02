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
 
 
package net.datenwerke.scheduler.service.scheduler.entities.dtogen;

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
import net.datenwerke.scheduler.client.scheduler.dto.MisfireInstructionDto;
import net.datenwerke.scheduler.service.scheduler.entities.MisfireInstruction;
import net.datenwerke.scheduler.service.scheduler.entities.dtogen.Dto2MisfireInstructionGenerator;

/**
 * Dto2PosoGenerator for MisfireInstruction
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2MisfireInstructionGenerator implements Dto2PosoGenerator<MisfireInstructionDto,MisfireInstruction> {

	private final Provider<DtoService> dtoServiceProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	@Inject
	public Dto2MisfireInstructionGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public MisfireInstruction loadPoso(MisfireInstructionDto dto)  {
		return createPoso(dto);
	}

	public MisfireInstruction instantiatePoso()  {
		throw new IllegalStateException("Cannot instantiate enum!");
	}

	public MisfireInstruction createPoso(MisfireInstructionDto dto)  {
		if (null == dto)
			return null;
		switch(dto){
			case EXECUTE_ONCE:
				return MisfireInstruction.EXECUTE_ONCE;
			case EXECUTE_ALL:
				return MisfireInstruction.EXECUTE_ALL;
		}
		throw new IllegalArgumentException("unknown enum type for: " + dto);
	}

	public MisfireInstruction createUnmanagedPoso(MisfireInstructionDto dto)  {
		return createPoso(dto);
	}

	public void mergePoso(MisfireInstructionDto dto, MisfireInstruction poso)  {
		/* no merging for enums */
	}

	public void mergeUnmanagedPoso(MisfireInstructionDto dto, MisfireInstruction poso)  {
		/* no merging for enums */
	}

	public MisfireInstruction loadAndMergePoso(MisfireInstructionDto dto)  {
		return createPoso(dto);
	}

	public void postProcessCreate(MisfireInstructionDto dto, MisfireInstruction poso)  {
	}


	public void postProcessCreateUnmanaged(MisfireInstructionDto dto, MisfireInstruction poso)  {
	}


	public void postProcessLoad(MisfireInstructionDto dto, MisfireInstruction poso)  {
	}


	public void postProcessMerge(MisfireInstructionDto dto, MisfireInstruction poso)  {
	}


	public void postProcessInstantiate(MisfireInstruction poso)  {
	}



}
