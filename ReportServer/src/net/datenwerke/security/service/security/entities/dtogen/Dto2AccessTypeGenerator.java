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
 
 
package net.datenwerke.security.service.security.entities.dtogen;

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
import net.datenwerke.security.client.security.dto.AccessTypeDto;
import net.datenwerke.security.service.security.entities.AccessType;
import net.datenwerke.security.service.security.entities.dtogen.Dto2AccessTypeGenerator;

/**
 * Dto2PosoGenerator for AccessType
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2AccessTypeGenerator implements Dto2PosoGenerator<AccessTypeDto,AccessType> {

	private final Provider<DtoService> dtoServiceProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	@Inject
	public Dto2AccessTypeGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public AccessType loadPoso(AccessTypeDto dto)  {
		return createPoso(dto);
	}

	public AccessType instantiatePoso()  {
		throw new IllegalStateException("Cannot instantiate enum!");
	}

	public AccessType createPoso(AccessTypeDto dto)  {
		if (null == dto)
			return null;
		switch(dto){
			case ALLOW:
				return AccessType.ALLOW;
			case DENY:
				return AccessType.DENY;
		}
		throw new IllegalArgumentException("unknown enum type for: " + dto);
	}

	public AccessType createUnmanagedPoso(AccessTypeDto dto)  {
		return createPoso(dto);
	}

	public void mergePoso(AccessTypeDto dto, AccessType poso)  {
		/* no merging for enums */
	}

	public void mergeUnmanagedPoso(AccessTypeDto dto, AccessType poso)  {
		/* no merging for enums */
	}

	public AccessType loadAndMergePoso(AccessTypeDto dto)  {
		return createPoso(dto);
	}

	public void postProcessCreate(AccessTypeDto dto, AccessType poso)  {
	}


	public void postProcessCreateUnmanaged(AccessTypeDto dto, AccessType poso)  {
	}


	public void postProcessLoad(AccessTypeDto dto, AccessType poso)  {
	}


	public void postProcessMerge(AccessTypeDto dto, AccessType poso)  {
	}


	public void postProcessInstantiate(AccessType poso)  {
	}



}
