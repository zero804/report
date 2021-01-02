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
 
 
package net.datenwerke.gxtdto.client.utilityservices.toolbar;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.messages.client.DefaultMessages;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

import net.datenwerke.gxtdto.client.theme.CssClassConstant;

public class DwPagingToolBar extends PagingToolBar {

	protected class DwDefaultPagingToolBarMessages extends DefaultPagingToolBarMessages{
	    @Override
	    public String afterPageText(int page) {
	    	if(page >= Integer.MAX_VALUE/10000)
				return "";
		
	    	return DefaultMessages.getMessages().pagingToolBar_afterPageText(page);
	    }
	}

	@CssClassConstant
	public static final String CSS_NAME = "rs-tbar";

	public DwPagingToolBar(int pageSize){
		super(pageSize);

		getElement().addClassName(getCssName());
		setMessages(new DwDefaultPagingToolBarMessages());
		
		for(Widget w : this.getChildren()){
			if(w instanceof TextButton){
				w.addStyleName("rs-btn");
				w.addStyleName("rs-btn-inform");
			}
			if(w instanceof TextBox){
				w.addStyleName("rs-field-ttf");
				w.addStyleName("rs-number-field");
				w.addStyleName("rs-paging-field");
				
			}
		}
	}

	public String getCssName() {
		return CSS_NAME;
	}

	public void addClassName(String className) {
		getElement().addClassName(className);
	}

}
