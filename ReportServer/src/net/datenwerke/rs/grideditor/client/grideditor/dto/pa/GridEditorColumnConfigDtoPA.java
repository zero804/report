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
 
 
package net.datenwerke.rs.grideditor.client.grideditor.dto.pa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import java.lang.Boolean;
import java.lang.String;
import java.util.List;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.rs.base.client.reportengines.table.dto.OrderDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.EditorDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.GridEditorColumnConfigDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.GridEditorRecordEntryDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.ValidatorDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.decorator.GridEditorColumnConfigDtoDec;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
@CorrespondingPoso(net.datenwerke.rs.grideditor.service.grideditor.definition.GridEditorColumnConfig.class)
public interface GridEditorColumnConfigDtoPA extends PropertyAccess<GridEditorColumnConfigDto> {


	public static final GridEditorColumnConfigDtoPA INSTANCE = GWT.create(GridEditorColumnConfigDtoPA.class);


	/* Properties */
	public ValueProvider<GridEditorColumnConfigDto,Boolean> defaultCaseSensitive();
	public ValueProvider<GridEditorColumnConfigDto,GridEditorRecordEntryDto> defaultValue();
	public ValueProvider<GridEditorColumnConfigDto,String> displayName();
	public ValueProvider<GridEditorColumnConfigDto,Boolean> editable();
	public ValueProvider<GridEditorColumnConfigDto,EditorDto> editor();
	public ValueProvider<GridEditorColumnConfigDto,Boolean> enforceCaseSensitivity();
	public ValueProvider<GridEditorColumnConfigDto,Boolean> filterable();
	public ValueProvider<GridEditorColumnConfigDto,Boolean> hidden();
	public ValueProvider<GridEditorColumnConfigDto,String> name();
	public ValueProvider<GridEditorColumnConfigDto,OrderDto> order();
	public ValueProvider<GridEditorColumnConfigDto,Boolean> primaryKey();
	public ValueProvider<GridEditorColumnConfigDto,Boolean> sortable();
	public ValueProvider<GridEditorColumnConfigDto,Integer> type();
	public ValueProvider<GridEditorColumnConfigDto,List<ValidatorDto>> validators();
	public ValueProvider<GridEditorColumnConfigDto,Integer> width();


}
