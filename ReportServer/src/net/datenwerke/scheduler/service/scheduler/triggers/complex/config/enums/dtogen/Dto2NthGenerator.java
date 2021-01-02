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
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.NthDto;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.Nth;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.dtogen.Dto2NthGenerator;

/**
 * Dto2PosoGenerator for Nth
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2NthGenerator implements Dto2PosoGenerator<NthDto,Nth> {

	private final Provider<DtoService> dtoServiceProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	@Inject
	public Dto2NthGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public Nth loadPoso(NthDto dto)  {
		return createPoso(dto);
	}

	public Nth instantiatePoso()  {
		throw new IllegalStateException("Cannot instantiate enum!");
	}

	public Nth createPoso(NthDto dto)  {
		if (null == dto)
			return null;
		switch(dto){
			case FIRST:
				return Nth.FIRST;
			case SECOND:
				return Nth.SECOND;
			case THIRD:
				return Nth.THIRD;
			case FOURTH:
				return Nth.FOURTH;
			case LAST:
				return Nth.LAST;
		}
		throw new IllegalArgumentException("unknown enum type for: " + dto);
	}

	public Nth createUnmanagedPoso(NthDto dto)  {
		return createPoso(dto);
	}

	public void mergePoso(NthDto dto, Nth poso)  {
		/* no merging for enums */
	}

	public void mergeUnmanagedPoso(NthDto dto, Nth poso)  {
		/* no merging for enums */
	}

	public Nth loadAndMergePoso(NthDto dto)  {
		return createPoso(dto);
	}

	public void postProcessCreate(NthDto dto, Nth poso)  {
	}


	public void postProcessCreateUnmanaged(NthDto dto, Nth poso)  {
	}


	public void postProcessLoad(NthDto dto, Nth poso)  {
	}


	public void postProcessMerge(NthDto dto, Nth poso)  {
	}


	public void postProcessInstantiate(Nth poso)  {
	}



}
