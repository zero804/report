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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.actions;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;

public class SetValueFieldAction implements SimpleFormAction {

	private String fieldKey;
	private Object value;
	private boolean first = true;
	
	public SetValueFieldAction(String fieldKey, Object value) {
		super();
		this.fieldKey = fieldKey;
		this.value = value;
	}

	@Override
	public void onSuccess(SimpleForm form) {
		if(skipFirst() && first)
			first = false;
		else
			form.setValue(fieldKey, value);
	}

	@Override
	public void onFailure(SimpleForm form) {
	}
	
	protected boolean skipFirst(){
		return true;
	}

}