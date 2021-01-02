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
 
 
package net.datenwerke.rs.birt.service.datasources.birtreport.entities.dtogen;

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
import net.datenwerke.rs.birt.client.datasources.dto.BirtReportDatasourceTargetTypeDto;
import net.datenwerke.rs.birt.service.datasources.birtreport.entities.BirtReportDatasourceTargetType;
import net.datenwerke.rs.birt.service.datasources.birtreport.entities.dtogen.Dto2BirtReportDatasourceTargetTypeGenerator;

/**
 * Dto2PosoGenerator for BirtReportDatasourceTargetType
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2BirtReportDatasourceTargetTypeGenerator implements Dto2PosoGenerator<BirtReportDatasourceTargetTypeDto,BirtReportDatasourceTargetType> {

	private final Provider<DtoService> dtoServiceProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	@Inject
	public Dto2BirtReportDatasourceTargetTypeGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public BirtReportDatasourceTargetType loadPoso(BirtReportDatasourceTargetTypeDto dto)  {
		return createPoso(dto);
	}

	public BirtReportDatasourceTargetType instantiatePoso()  {
		throw new IllegalStateException("Cannot instantiate enum!");
	}

	public BirtReportDatasourceTargetType createPoso(BirtReportDatasourceTargetTypeDto dto)  {
		if (null == dto)
			return null;
		switch(dto){
			case DATASET:
				return BirtReportDatasourceTargetType.DATASET;
			case PARAMETER:
				return BirtReportDatasourceTargetType.PARAMETER;
		}
		throw new IllegalArgumentException("unknown enum type for: " + dto);
	}

	public BirtReportDatasourceTargetType createUnmanagedPoso(BirtReportDatasourceTargetTypeDto dto)  {
		return createPoso(dto);
	}

	public void mergePoso(BirtReportDatasourceTargetTypeDto dto, BirtReportDatasourceTargetType poso)  {
		/* no merging for enums */
	}

	public void mergeUnmanagedPoso(BirtReportDatasourceTargetTypeDto dto, BirtReportDatasourceTargetType poso)  {
		/* no merging for enums */
	}

	public BirtReportDatasourceTargetType loadAndMergePoso(BirtReportDatasourceTargetTypeDto dto)  {
		return createPoso(dto);
	}

	public void postProcessCreate(BirtReportDatasourceTargetTypeDto dto, BirtReportDatasourceTargetType poso)  {
	}


	public void postProcessCreateUnmanaged(BirtReportDatasourceTargetTypeDto dto, BirtReportDatasourceTargetType poso)  {
	}


	public void postProcessLoad(BirtReportDatasourceTargetTypeDto dto, BirtReportDatasourceTargetType poso)  {
	}


	public void postProcessMerge(BirtReportDatasourceTargetTypeDto dto, BirtReportDatasourceTargetType poso)  {
	}


	public void postProcessInstantiate(BirtReportDatasourceTargetType poso)  {
	}



}
