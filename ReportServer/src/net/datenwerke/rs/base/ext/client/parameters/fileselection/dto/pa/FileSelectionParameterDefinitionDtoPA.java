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
 
 
package net.datenwerke.rs.base.ext.client.parameters.fileselection.dto.pa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import java.lang.Integer;
import java.lang.String;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.rs.base.client.parameters.locale.RsMessages;
import net.datenwerke.rs.base.ext.client.parameters.fileselection.dto.FileSelectionParameterDefinitionDto;
import net.datenwerke.rs.core.client.parameters.dto.pa.ParameterDefinitionDtoPA;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
@CorrespondingPoso(net.datenwerke.rs.base.ext.service.parameters.fileselection.FileSelectionParameterDefinition.class)
public interface FileSelectionParameterDefinitionDtoPA extends ParameterDefinitionDtoPA {


	public static final FileSelectionParameterDefinitionDtoPA INSTANCE = GWT.create(FileSelectionParameterDefinitionDtoPA.class);


	/* Properties */
	public ValueProvider<FileSelectionParameterDefinitionDto,Boolean> allowDownload();
	public ValueProvider<FileSelectionParameterDefinitionDto,Boolean> allowFileServerSelection();
	public ValueProvider<FileSelectionParameterDefinitionDto,Boolean> allowFileUpload();
	public ValueProvider<FileSelectionParameterDefinitionDto,Boolean> allowTeamSpaceSelection();
	public ValueProvider<FileSelectionParameterDefinitionDto,String> allowedFileExtensions();
	public ValueProvider<FileSelectionParameterDefinitionDto,String> fileSizeString();
	public ValueProvider<FileSelectionParameterDefinitionDto,Integer> height();
	public ValueProvider<FileSelectionParameterDefinitionDto,Integer> maxNumberOfFiles();
	public ValueProvider<FileSelectionParameterDefinitionDto,Integer> minNumberOfFiles();
	public ValueProvider<FileSelectionParameterDefinitionDto,Integer> width();
	public ValueProvider<FileSelectionParameterDefinitionDto,Long> fileSize();


}
