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
 
 
package net.datenwerke.rs.theme.client.field;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.cell.core.client.form.CheckBoxCell;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.core.client.dom.XDOM;
import com.sencha.gxt.theme.neptune.client.base.field.Css3CheckBoxAppearance;

public class RsCheckboxAppearance extends Css3CheckBoxAppearance {

	@Override
	public void render(SafeHtmlBuilder sb, Boolean value, CheckBoxCell.CheckBoxCellOptions options) {
		String checkBoxId = XDOM.getUniqueId();

		String nameParam = options.getName() != null ? " name='" + options.getName() + "' " : "";
		String disabledParam = options.isDisabled() ? " disabled=true" : "";
		String readOnlyParam = options.isReadonly() ? " readonly" : "";
		String idParam = " id=" + checkBoxId;
		String typeParam = " type=" + type;
		String checkedParam = value ? " checked" : "";

		sb.appendHtmlConstant("<div class=\"" + style.wrap() + " rs-field-cb\">");
		sb.appendHtmlConstant(
				"<input " + typeParam + nameParam + disabledParam + readOnlyParam + idParam + checkedParam + " />");
		// on IE11, clicking the checkbox label fires an event for both the
		// label and the input.
		final String labelHtml;
		if (GXT.isIE11()) {
			labelHtml = "<label class=" + style.checkBoxLabel() + ">";
		} else {
			labelHtml = "<label for=" + checkBoxId + " class=" + style.checkBoxLabel() + ">";
		}
		sb.appendHtmlConstant(labelHtml);
		if (options.getBoxLabel() != null) {
			sb.append(options.getBoxLabel());
		}
		sb.appendHtmlConstant("</label></div>");
	}

}
