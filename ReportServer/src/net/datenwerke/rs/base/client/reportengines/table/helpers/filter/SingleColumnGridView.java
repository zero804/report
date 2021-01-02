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
 
 
package net.datenwerke.rs.base.client.reportengines.table.helpers.filter;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.model.StringBaseModel;
import net.datenwerke.gxtdto.client.model.pa.StringBaseModelPa;
import net.datenwerke.gxtdto.client.utils.SqlTypes;
import net.datenwerke.gxtdto.client.utils.StringEscapeUtils;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.locale.FilterMessages;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.helpers.validator.IntegerFieldValidator;
import net.datenwerke.rs.base.client.reportengines.table.helpers.validator.NumericalFieldValidator;
import net.datenwerke.rs.base.client.reportengines.table.helpers.validator.SqlDateValidator;
import net.datenwerke.rs.base.client.reportengines.table.helpers.validator.SqlTimestampValidator;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.dnd.core.client.GridDropTarget;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;
import com.sencha.gxt.widget.core.client.grid.editing.ClicksToEdit;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;

/**
 * 
 *
 */
public class SingleColumnGridView extends GridView<StringBaseModel> {

	private static StringBaseModelPa sbmPa = GWT.create(StringBaseModelPa.class);
	
	public SingleColumnGridView(ListStore<StringBaseModel> store, ColumnDto column, SelectionPanel<StringBaseModel> selectionPanel) {
		super(store, column, selectionPanel);
	}

	@Override
	protected Grid<StringBaseModel> createGrid(ListStore<StringBaseModel> store) {
		List<ColumnConfig<StringBaseModel,?>> columns = new ArrayList<ColumnConfig<StringBaseModel,?>>();
		
		ColumnConfig<StringBaseModel, String> column = new ColumnConfig<StringBaseModel, String>(sbmPa.value(), 200, FilterMessages.INSTANCE.value()); 
		column.setMenuDisabled(true);
		
		TextField field = new TextField();
		Integer type = this.column.getType();
		if(SqlTypes.isInteger(type))
			field.addValidator(new IntegerFieldValidator());
		else if(SqlTypes.isNumerical(type)) 
			field.addValidator(new NumericalFieldValidator());
		else if(SqlTypes.isTimeStamp(type))
			field.addValidator(new SqlTimestampValidator());
		else if(SqlTypes.isDate(type))
			field.addValidator(new SqlDateValidator());

		column.setCell(new AbstractCell<String>() {
			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context,
					String value, SafeHtmlBuilder sb) {
				if(null != value)
					sb.appendEscaped(StringEscapeUtils.escapeAngleBrackets(filterService.getStringValue(value, 0)));
			}
		});
		columns.add(column);
		
		Grid<StringBaseModel> grid = new Grid<StringBaseModel>((ListStore<StringBaseModel>) store, new ColumnModel<StringBaseModel>(columns));
		grid.setSelectionModel(new GridSelectionModel<StringBaseModel>());
		
		GridInlineEditing<StringBaseModel> editing = new GridInlineEditing<StringBaseModel>(grid);
		editing.setClicksToEdit(ClicksToEdit.TWO);
		
		editing.addEditor(column, field);
		
		grid.getView().setShowDirtyCells(false);
		
		return grid;
	}

	
	@Override
	protected void createGridDropTarget(Grid<StringBaseModel> grid) {
		new GridDropTarget<StringBaseModel>(grid);
	}
	

}
