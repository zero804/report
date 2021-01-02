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
 
 
package net.datenwerke.rs.core.service.i18ntools.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.core.client.i18tools.dto.FormatPatternsDto;
import net.datenwerke.rs.core.service.i18ntools.FormatPatterns;
import net.datenwerke.rs.core.service.i18ntools.dtogen.FormatPatterns2DtoGenerator;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for FormatPatterns
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class FormatPatterns2DtoGenerator implements Poso2DtoGenerator<FormatPatterns,FormatPatternsDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public FormatPatterns2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public FormatPatternsDto instantiateDto(FormatPatterns poso)  {
		FormatPatternsDto dto = new FormatPatternsDto();
		return dto;
	}

	public FormatPatternsDto createDto(FormatPatterns poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final FormatPatternsDto dto = new FormatPatternsDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set currencyPattern */
			dto.setCurrencyPattern(StringEscapeUtils.escapeXml(StringUtils.left(poso.getCurrencyPattern(),8192)));

			/*  set integerPattern */
			dto.setIntegerPattern(StringEscapeUtils.escapeXml(StringUtils.left(poso.getIntegerPattern(),8192)));

			/*  set longDatePattern */
			dto.setLongDatePattern(StringEscapeUtils.escapeXml(StringUtils.left(poso.getLongDatePattern(),8192)));

			/*  set longDateTimePattern */
			dto.setLongDateTimePattern(StringEscapeUtils.escapeXml(StringUtils.left(poso.getLongDateTimePattern(),8192)));

			/*  set longTimePattern */
			dto.setLongTimePattern(StringEscapeUtils.escapeXml(StringUtils.left(poso.getLongTimePattern(),8192)));

			/*  set numberPattern */
			dto.setNumberPattern(StringEscapeUtils.escapeXml(StringUtils.left(poso.getNumberPattern(),8192)));

			/*  set percentPattern */
			dto.setPercentPattern(StringEscapeUtils.escapeXml(StringUtils.left(poso.getPercentPattern(),8192)));

			/*  set shortDatePattern */
			dto.setShortDatePattern(StringEscapeUtils.escapeXml(StringUtils.left(poso.getShortDatePattern(),8192)));

			/*  set shortDateTimePattern */
			dto.setShortDateTimePattern(StringEscapeUtils.escapeXml(StringUtils.left(poso.getShortDateTimePattern(),8192)));

			/*  set shortTimePattern */
			dto.setShortTimePattern(StringEscapeUtils.escapeXml(StringUtils.left(poso.getShortTimePattern(),8192)));

		}

		return dto;
	}


}
