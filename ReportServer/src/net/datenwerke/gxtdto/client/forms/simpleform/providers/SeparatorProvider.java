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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.providers;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHookImpl;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCSeparator;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCSpace;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.dummy.Separator;
import net.datenwerke.gxtdto.client.widgets.SeparatorTextLabel;

public class SeparatorProvider extends FormFieldProviderHookImpl {

	@Override
	public boolean doConsumes(Class<?> type,
			SimpleFormFieldConfiguration... configs) {
		return type.equals(Separator.class);
	}

	@Override
	public void init(String name, SimpleForm form) {
		
	}

	@Override
	public Widget createFormField() {
		SFFCSeparator config = getSeparatorConfig();
		if(null != config && null != config.getType()){
			switch(config.getType()){
			case LINE:
				return new HTML("<hr/>");
			case TEXT:
				return SeparatorTextLabel.createText(config.getText(), config.getMargins());
			case H_LARGE:
				return SeparatorTextLabel.createHeadlineLarge(config.getText(), config.getMargins());
			case H_MEDIUM:
				return SeparatorTextLabel.createHeadlineMedium(config.getText(), config.getMargins());
			case H_SMALL:
				return SeparatorTextLabel.createHeadlineSmall(config.getText(), config.getMargins());
			}
		}
		SFFCSpace spaceConfig = getSpaceConfig();
		if(null != spaceConfig){
			SafeHtmlBuilder sb = new SafeHtmlBuilder();
			sb.appendHtmlConstant("<div class='rs-sf-space' style='display: block; height: " + spaceConfig.getSpace() + "px;'></div>");
			return new HTML(sb.toSafeHtml());
		}
		
		return new HTML("<hr/>");
	}

	@Override
	public Object getValue(Widget field) {
		return null;
	}
	
	@Override
	public void setValue(Widget field, Object value) {
	}

	@Override
	public Widget reload(Widget field) {
		return createFormField();
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler handler) {
		throw new IllegalStateException();
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		
	}

	@Override
	public void addFieldBindings(Object model, ValueProvider vp, Widget field) {
	}

	@Override
	public void removeFieldBindings(Object model, Widget field) {
	}

	protected SFFCSeparator getSeparatorConfig(){
		for(SimpleFormFieldConfiguration config : configs)
			if(config instanceof SFFCSeparator)
				return (SFFCSeparator) config;
		return null;
	}
	
	protected SFFCSpace getSpaceConfig(){
		for(SimpleFormFieldConfiguration config : configs)
			if(config instanceof SFFCSpace)
				return (SFFCSpace) config;
		return null;
	}

	@Override
	public boolean isDecorateable() {
		return false;
	}

}
