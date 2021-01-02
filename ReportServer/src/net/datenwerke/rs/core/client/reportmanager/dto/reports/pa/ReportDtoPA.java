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
 
 
package net.datenwerke.rs.core.client.reportmanager.dto.reports.pa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import java.lang.String;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportMetadataDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportPropertyDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.decorator.ReportDtoDec;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.pa.AbstractReportManagerNodeDtoPA;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
@CorrespondingPoso(net.datenwerke.rs.core.service.reportmanager.entities.reports.Report.class)
public interface ReportDtoPA extends AbstractReportManagerNodeDtoPA {


	public static final ReportDtoPA INSTANCE = GWT.create(ReportDtoPA.class);


	/* Properties */
	public ValueProvider<ReportDto,DatasourceContainerDto> datasourceContainer();
	public ValueProvider<ReportDto,String> description();
	public ValueProvider<ReportDto,String> key();
	public ValueProvider<ReportDto,String> name();
	public ValueProvider<ReportDto,List<ParameterDefinitionDto>> parameterDefinitions();
	public ValueProvider<ReportDto,Set<ParameterInstanceDto>> parameterInstances();
	public ValueProvider<ReportDto,Set<ReportMetadataDto>> reportMetadata();
	public ValueProvider<ReportDto,Set<ReportPropertyDto>> reportProperties();
	public ValueProvider<ReportDto,String> uuid();
	public ValueProvider<ReportDto,String> temporaryUid();
	public ValueProvider<ReportDto,String> parentReportKey();
	public ValueProvider<ReportDto,String> parentReportName();
	public ValueProvider<ReportDto,String> parentReportDescription();
	public ValueProvider<ReportDto,HashSet<ReportPropertyDto>> parentReportProperties();


}
