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
 
 
package net.datenwerke.rs.incubator.client.jaspertotable.dto.pa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import java.lang.Boolean;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.pa.ReportPropertyDtoPA;
import net.datenwerke.rs.incubator.client.jaspertotable.dto.JasperToTableConfigDto;
import net.datenwerke.rs.incubator.client.jaspertotable.dto.decorator.JasperToTableConfigDtoDec;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
@CorrespondingPoso(net.datenwerke.rs.incubator.service.jaspertotable.entities.JasperToTableConfig.class)
public interface JasperToTableConfigDtoPA extends ReportPropertyDtoPA {


	public static final JasperToTableConfigDtoPA INSTANCE = GWT.create(JasperToTableConfigDtoPA.class);


	/* Properties */
	public ValueProvider<JasperToTableConfigDto,DatasourceContainerDto> datasourceContainer();
	public ValueProvider<JasperToTableConfigDto,Boolean> active();


}
