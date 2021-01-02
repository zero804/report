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
 
 
package net.datenwerke.rs.base.service.datasources.connectors.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.client.datasources.dto.ArgumentDatasourceConnectorDto;
import net.datenwerke.rs.base.client.datasources.dto.decorator.ArgumentDatasourceConnectorDtoDec;
import net.datenwerke.rs.base.service.datasources.connectors.ArgumentDatasourceConnector;
import net.datenwerke.rs.base.service.datasources.connectors.dtogen.ArgumentDatasourceConnector2DtoGenerator;

/**
 * Poso2DtoGenerator for ArgumentDatasourceConnector
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class ArgumentDatasourceConnector2DtoGenerator implements Poso2DtoGenerator<ArgumentDatasourceConnector,ArgumentDatasourceConnectorDtoDec> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public ArgumentDatasourceConnector2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public ArgumentDatasourceConnectorDtoDec instantiateDto(ArgumentDatasourceConnector poso)  {
		ArgumentDatasourceConnectorDtoDec dto = new ArgumentDatasourceConnectorDtoDec();
		return dto;
	}

	public ArgumentDatasourceConnectorDtoDec createDto(ArgumentDatasourceConnector poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final ArgumentDatasourceConnectorDtoDec dto = new ArgumentDatasourceConnectorDtoDec();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.MINIMAL) >= 0){
			/*  set id */
			dto.setId(poso.getId() );

		}

		return dto;
	}


}
