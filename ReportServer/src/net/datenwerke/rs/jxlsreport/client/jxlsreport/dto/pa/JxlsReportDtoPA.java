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
 
 
package net.datenwerke.rs.jxlsreport.client.jxlsreport.dto.pa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.pa.ReportDtoPA;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.dto.JxlsReportDto;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.dto.JxlsReportFileDto;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.dto.decorator.JxlsReportDtoDec;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
@CorrespondingPoso(net.datenwerke.rs.jxlsreport.service.jxlsreport.entities.JxlsReport.class)
public interface JxlsReportDtoPA extends ReportDtoPA {


	public static final JxlsReportDtoPA INSTANCE = GWT.create(JxlsReportDtoPA.class);


	/* Properties */
	public ValueProvider<JxlsReportDto,Boolean> jxlsOne();
	public ValueProvider<JxlsReportDto,JxlsReportFileDto> reportFile();


}
