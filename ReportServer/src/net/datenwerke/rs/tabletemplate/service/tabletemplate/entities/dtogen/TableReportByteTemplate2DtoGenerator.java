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
 
 
package net.datenwerke.rs.tabletemplate.service.tabletemplate.entities.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.tabletemplate.client.tabletemplate.dto.TableReportByteTemplateDto;
import net.datenwerke.rs.tabletemplate.service.tabletemplate.entities.TableReportByteTemplate;
import net.datenwerke.rs.tabletemplate.service.tabletemplate.entities.dtogen.TableReportByteTemplate2DtoGenerator;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for TableReportByteTemplate
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class TableReportByteTemplate2DtoGenerator implements Poso2DtoGenerator<TableReportByteTemplate,TableReportByteTemplateDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public TableReportByteTemplate2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public TableReportByteTemplateDto instantiateDto(TableReportByteTemplate poso)  {
		TableReportByteTemplateDto dto = new TableReportByteTemplateDto();
		return dto;
	}

	public TableReportByteTemplateDto createDto(TableReportByteTemplate poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final TableReportByteTemplateDto dto = new TableReportByteTemplateDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.MINIMAL) >= 0){
			/*  set id */
			dto.setId(poso.getId() );

			/*  set name */
			dto.setName(StringEscapeUtils.escapeXml(StringUtils.left(poso.getName(),8192)));

			/*  set temporaryId */
			dto.setTemporaryId(poso.getTemporaryId() );

		}
		if(here.compareTo(DtoView.LIST) >= 0){
			/*  set contentType */
			dto.setContentType(StringEscapeUtils.escapeXml(StringUtils.left(poso.getContentType(),8192)));

			/*  set description */
			dto.setDescription(StringEscapeUtils.escapeXml(StringUtils.left(poso.getDescription(),8192)));

			/*  set fileExtension */
			dto.setFileExtension(StringEscapeUtils.escapeXml(StringUtils.left(poso.getFileExtension(),8192)));

			/*  set key */
			dto.setKey(StringEscapeUtils.escapeXml(StringUtils.left(poso.getKey(),8192)));

			/*  set templateType */
			dto.setTemplateType(StringEscapeUtils.escapeXml(StringUtils.left(poso.getTemplateType(),8192)));

		}

		return dto;
	}


}
