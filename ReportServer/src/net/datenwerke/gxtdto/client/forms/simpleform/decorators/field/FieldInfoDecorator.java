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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.decorators.field;

import java.util.HashMap;
import java.util.Map;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.Anchor;
import com.sencha.gxt.core.client.Style.AnchorAlignment;
import com.sencha.gxt.core.client.Style.HideMode;
import com.sencha.gxt.widget.core.client.WidgetComponent;
import com.sencha.gxt.widget.core.client.event.ShowEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent.ShowHandler;
import com.sencha.gxt.widget.core.client.form.error.SideErrorHandler.SideErrorTooltipAppearance;
import com.sencha.gxt.widget.core.client.tips.ToolTip;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;

/**
 * 
 *
 */
public class FieldInfoDecorator implements SimpleFormFieldDecorator {

	public static final String DECORATOR_ID = "FieldInfoDecorator";
	
	protected Map<String, String> fieldInfoMap = new HashMap<String, String>();
	protected Map<String, DelayedInfoMessage> fieldInfoMapDelayed = new HashMap<String, DelayedInfoMessage>();
	
	public interface InfoCallback{
		void setInfo(String msg);
	}
	
	public interface DelayedInfoMessage{
		void getMessage(InfoCallback callback);
		String getWaitText();		
		boolean updateAlways();
	}
	
	@Override
	public String getDecoratorId() {
		return DECORATOR_ID;
	}

	
	@Override
	public void configureFieldOnLoad(SimpleForm form, Widget field, String key) {
	}
	
	@Override
	public void configureFieldAfterLayout(SimpleForm simpleForm, final Widget widget, String key) {
		if(fieldInfoMap.containsKey(key) || fieldInfoMapDelayed.containsKey(key)){
			final WidgetComponent fieldIcon = new WidgetComponent(new HTML(BaseIcon.INFO.toSafeHtml()));
	        fieldIcon.setHideMode(HideMode.VISIBILITY);
	        fieldIcon.getElement().makePositionable(true);
	        fieldIcon.getElement().setDisplayed(true);
	        
        	widget.getElement().getParentElement().appendChild(fieldIcon.getElement());
        	Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				
				@Override
				public void execute() {
					fieldIcon.getElement().alignTo(widget.getElement(), new AnchorAlignment(Anchor.TOP_LEFT, Anchor.TOP_RIGHT), 2, 3);		
				}
			});
        	
        	final ToolTip tip = new ToolTip(fieldIcon, GWT.<SideErrorTooltipAppearance>create(SideErrorTooltipAppearance.class));
	        
        	String info = fieldInfoMap.get(key);
	        if(null != info){
	        	ToolTipConfig config = new ToolTipConfig(info);
	        	tip.update(config);
	        }else {
	        	final DelayedInfoMessage delayedInfo = fieldInfoMapDelayed.get(key);
	        	
	        	tip.addShowHandler(new ShowHandler() {
	        		
	        		boolean tipLoaded = false;
					
	        		@Override
					public void onShow(ShowEvent event) {
						if(tipLoaded && ! delayedInfo.updateAlways())
							return;
						tipLoaded = true;
						ToolTipConfig config = tip.getToolTipConfig();
						config.setBody(delayedInfo.getWaitText());
						tip.update(config);
						
						delayedInfo.getMessage(new InfoCallback() {
							@Override
							public void setInfo(String msg) {
								ToolTipConfig config = tip.getToolTipConfig();
								config.setBody(msg);
								tip.update(config);
							}
						});
					}
				});
	        }
	        
	        /* show icon */
	        fieldIcon.show();
		}
	}

	public void addInfo(String key, String data) {
		fieldInfoMap.put(key, data);
	}
	
	public void addInfo(String key, DelayedInfoMessage data) {
		fieldInfoMapDelayed.put(key, data);
	}


	@Override
	public Widget adjustFieldForDisplay(SimpleForm simpleForm,
			Widget formField, String fieldKey) {
		return formField;
	}

}
