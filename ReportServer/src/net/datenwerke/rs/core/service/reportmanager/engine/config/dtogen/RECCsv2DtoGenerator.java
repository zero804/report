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
 
 
package net.datenwerke.rs.core.service.reportmanager.engine.config.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.core.client.reportexporter.dto.RECCsvDto;
import net.datenwerke.rs.core.client.reportexporter.dto.decorator.RECCsvDtoDec;
import net.datenwerke.rs.core.service.reportmanager.engine.config.RECCsv;
import net.datenwerke.rs.core.service.reportmanager.engine.config.dtogen.RECCsv2DtoGenerator;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for RECCsv
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class RECCsv2DtoGenerator implements Poso2DtoGenerator<RECCsv,RECCsvDtoDec> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public RECCsv2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public RECCsvDtoDec instantiateDto(RECCsv poso)  {
		RECCsvDtoDec dto = new RECCsvDtoDec();
		return dto;
	}

	public RECCsvDtoDec createDto(RECCsv poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final RECCsvDtoDec dto = new RECCsvDtoDec();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set charset */
			dto.setCharset(StringEscapeUtils.escapeXml(StringUtils.left(poso.getCharset(),8192)));

			/*  set lineSeparator */
			dto.setLineSeparator(StringEscapeUtils.escapeXml(StringUtils.left(poso.getLineSeparator(),8192)));

			/*  set printHeader */
			dto.setPrintHeader(poso.isPrintHeader() );

			/*  set quote */
			dto.setQuote(StringEscapeUtils.escapeXml(StringUtils.left(poso.getQuote(),8192)));

			/*  set separator */
			dto.setSeparator(StringEscapeUtils.escapeXml(StringUtils.left(poso.getSeparator(),8192)));

		}

		return dto;
	}


}
