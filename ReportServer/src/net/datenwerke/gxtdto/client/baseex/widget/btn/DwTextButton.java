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
 
 
package net.datenwerke.gxtdto.client.baseex.widget.btn;

import net.datenwerke.gxtdto.client.theme.CssClassConstant;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;


public class DwTextButton extends TextButton {
	
	@CssClassConstant
	public static final String CSS_NAME = "rs-btn";

	@CssClassConstant
	public static final String CSS_BODY_NAME = "rs-btn-body";
	
	public DwTextButton() {
		super();
		initCss();
	}
	
	public DwTextButton(String label) {
		super(label);
		initCss();
	}

	public DwTextButton(String label, ImageResource icon) {
		super(label, icon);
		initCss();
	}

	public DwTextButton(String label, SelectHandler selectHandler) {
		super(label, selectHandler);
		initCss();
	}

	public DwTextButton(BaseIcon icon) {
		super();
		initCss();
		setIcon(icon);
	}
	
	public DwTextButton(String label, BaseIcon icon) {
		super(label, null == icon ? null : icon.toImageResource());
		initCss();
	}


	private void initCss() {
		getElement().addClassName(getCssName());
		getCell().getAppearance().getButtonElement(getElement()).addClassName(getCssBodyName());
	}

	public String getCssName() {
		return CSS_NAME;
	}
	
	public String getCssBodyName() {
		return CSS_BODY_NAME;
	}

	public void setIcon(BaseIcon icon) {
		super.setIcon(icon.toImageResource());
	}
}
