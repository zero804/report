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
 
 
package net.datenwerke.rs.grideditor.client.grideditor.dto.decorator;

import net.datenwerke.rs.grideditor.client.grideditor.dto.GridEditorRecordDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.IntBooleanEditorDto;

import com.sencha.gxt.data.shared.Converter;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;

/**
 * Dto Decorator for {@link IntBooleanEditorDto}
 *
 */
public class IntBooleanEditorDtoDec extends IntBooleanEditorDto {


	private static final long serialVersionUID = 1L;

	public IntBooleanEditorDtoDec() {
		super();
	}

	@Override
	public Field addEditor(ColumnConfig columnConfig,
			GridEditing<GridEditorRecordDto> editing) {
		CheckBox field = new CheckBox();
		editing.addEditor(columnConfig, new Converter<Integer, Boolean>(){
			@Override
			public Integer convertFieldValue(Boolean bool) {
				if(null == bool)
					return null;
				return bool ? getTrueInt() : getFalseInt();
			}

			@Override
			public Boolean convertModelValue(Integer intBool) {
				if(null == intBool)
					return null;
				return intBool.equals(getTrueInt());
			}
			
		}, field);
		return field;
	}
}
