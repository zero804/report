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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper.output.object;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.client.reportexecutor.dto.CompiledHtmlReportDto;
import net.datenwerke.rs.core.service.reportmanager.engine.basereports.CompiledHtmlReport;


/**
 *  Wrapper for HTML Jasper Reports.  
 *  
 *
 */
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.reportengines.jasper.dto",
	dtoImplementInterfaces=CompiledHtmlReportDto.class
)
public class CompiledHTMLJasperReport extends CompiledRSJasperReport implements CompiledHtmlReport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3761756857789223803L;
	
	@ExposeToClient
	private String report;
	
	public String getReport() {
		return this.report;
	}

	public void setReport(Object report) {
		if(! (report instanceof String))
			throw new IllegalArgumentException("An HTML Jasper Report should be of type String."); //$NON-NLS-1$
		
		this.report = (String) report;
	}
	
	public String getFileExtension() {
		return "html"; //$NON-NLS-1$
	}

	public String getMimeType() {
		return "text/html"; //$NON-NLS-1$
	}
	
	@Override
	public boolean isStringReport() {
		return true;
	}

}
