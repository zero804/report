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
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.DailyPatternDto;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.DailyPattern;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.dtogen.Dto2DailyPatternGenerator;

/**
 * Dto2PosoGenerator for DailyPattern
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2DailyPatternGenerator implements Dto2PosoGenerator<DailyPatternDto,DailyPattern> {

	private final Provider<DtoService> dtoServiceProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	@Inject
	public Dto2DailyPatternGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public DailyPattern loadPoso(DailyPatternDto dto)  {
		return createPoso(dto);
	}

	public DailyPattern instantiatePoso()  {
		throw new IllegalStateException("Cannot instantiate enum!");
	}

	public DailyPattern createPoso(DailyPatternDto dto)  {
		if (null == dto)
			return null;
		switch(dto){
			case DAILY_EVERY_Nth_DAY:
				return DailyPattern.DAILY_EVERY_Nth_DAY;
			case DAILY_WORKDAY:
				return DailyPattern.DAILY_WORKDAY;
		}
		throw new IllegalArgumentException("unknown enum type for: " + dto);
	}

	public DailyPattern createUnmanagedPoso(DailyPatternDto dto)  {
		return createPoso(dto);
	}

	public void mergePoso(DailyPatternDto dto, DailyPattern poso)  {
		/* no merging for enums */
	}

	public void mergeUnmanagedPoso(DailyPatternDto dto, DailyPattern poso)  {
		/* no merging for enums */
	}

	public DailyPattern loadAndMergePoso(DailyPatternDto dto)  {
		return createPoso(dto);
	}

	public void postProcessCreate(DailyPatternDto dto, DailyPattern poso)  {
	}


	public void postProcessCreateUnmanaged(DailyPatternDto dto, DailyPattern poso)  {
	}


	public void postProcessLoad(DailyPatternDto dto, DailyPattern poso)  {
	}


	public void postProcessMerge(DailyPatternDto dto, DailyPattern poso)  {
	}


	public void postProcessInstantiate(DailyPattern poso)  {
	}



}
