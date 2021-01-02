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
 
 
package net.datenwerke.rs.core.service.reportmanager.entities.reports.post;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoPostProcessor;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportPropertyDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ServerSidePropertyDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.decorator.ReportDtoDec;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportProperty;
import net.datenwerke.rs.core.service.reportmanager.interfaces.ReportVariant;

import com.google.inject.Inject;

/**
 * 
 *
 */
public class Report2DtoPostProcessor implements Poso2DtoPostProcessor<Report, ReportDto> {

	private final DtoService dtoService;
	
	@Inject
	public Report2DtoPostProcessor(
		DtoService dtoService
		){
		
		/* store objects */
		this.dtoService = dtoService;
	}
	
	@Override
	public void dtoCreated(Report poso, ReportDto dto) {
		if(poso instanceof ReportVariant){
			Report parentReport = (Report) poso.getParent();

			if(DtoView.LIST.compareTo(dto.getDtoView()) <= 0){
				dto.setParentReportName(parentReport.getName());
			}
			
			if(DtoView.NORMAL.compareTo(dto.getDtoView()) <= 0){
				HashSet<ReportPropertyDto> properties = new HashSet<ReportPropertyDto>();
				for(ReportProperty prop : parentReport.getReportProperties()){
					properties.add((ReportPropertyDto) dtoService.createDto(prop));
				}
				((ReportDtoDec)dto).setParentReportProperties(properties);
				
				dto.setParentReportKey(parentReport.getKey());
				dto.setParentReportDescription(parentReport.getDescription());
			}
		}
		
		// remove server side properties */
		Set<ReportPropertyDto> properties = dto.getReportProperties();
		Iterator<ReportPropertyDto> it = properties.iterator();
		while(it.hasNext())
			if(it.next() instanceof ServerSidePropertyDto)
				it.remove();
		dto.setReportProperties(properties);
		
		// mark report as not having children
		dto.setHasChildren(false);
	}

	@Override
	public void dtoInstantiated(Report poso, ReportDto dto) {
		
	}

}
