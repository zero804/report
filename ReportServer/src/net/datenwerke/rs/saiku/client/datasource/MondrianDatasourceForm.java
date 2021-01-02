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
 
 
package net.datenwerke.rs.saiku.client.datasource;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.DefaultInfoConfig;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.info.InfoConfig;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

import net.datenwerke.gf.client.managerhelper.mainpanel.SimpleFormView;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.codemirror.CodeMirrorPanel.ToolBarEnhancer;
import net.datenwerke.gxtdto.client.dialog.error.DetailErrorDialog;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCCodeMirror;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCNoHtmlDecode;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCPasswordField;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCTextAreaImpl;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.core.client.datasourcemanager.locale.DatasourcesMessages;
import net.datenwerke.rs.saiku.client.datasource.dto.MondrianDatasourceDto;
import net.datenwerke.rs.saiku.client.datasource.dto.pa.MondrianDatasourceDtoPA;
import net.datenwerke.rs.saiku.client.saiku.SaikuDao;
import net.datenwerke.rs.saiku.client.saiku.locale.SaikuMessages;

public class MondrianDatasourceForm extends SimpleFormView {
	
	private final SaikuDao saikuDao;
	
	@Inject
	public MondrianDatasourceForm(
		SaikuDao saikuDao
		){
		
		/* store objects */
		this.saikuDao = saikuDao;
	}

	@Override
	protected void configureSimpleForm(SimpleForm form) {
		
		final DwTextButton clearCacheButton = new DwTextButton(SaikuMessages.INSTANCE.clearCache());
		clearCacheButton.addSelectHandler((new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				clearCache();
			}
		}));
		form.clearButtonBar();
		/* add clear cache button */
		form.addButton(clearCacheButton);
		
		form.addSubmitButton();
		
		/* configure form */
		form.setHeading(SaikuMessages.INSTANCE.editDataSource() + (getSelectedNode() == null ? "" : " (" + getSelectedNode().getId() + ")")); 
		
		/* add form fields */
		form.addField(String.class, MondrianDatasourceDtoPA.INSTANCE.name(), BaseMessages.INSTANCE.propertyName()); 
		form.addField(String.class, MondrianDatasourceDtoPA.INSTANCE.description(), BaseMessages.INSTANCE.propertyDescription(), new SFFCTextAreaImpl());
		
		form.setFieldWidth(250);
		form.beginFloatRow();
		
		/* username */
		form.addField(String.class, MondrianDatasourceDtoPA.INSTANCE.username(), DatasourcesMessages.INSTANCE.username()); 
		
		/* password */
		String passwordKey = form.addField(String.class, MondrianDatasourceDtoPA.INSTANCE.password(), DatasourcesMessages.INSTANCE.password(), new SFFCPasswordField() {
			@Override
			public Boolean isPasswordSet() {
				return ((MondrianDatasourceDto)getSelectedNode()).isHasPassword();
			}
		}); //$NON-NLS-1$
		Menu clearPwMenu = new DwMenu();
		MenuItem clearPwItem = new DwMenuItem(DatasourcesMessages.INSTANCE.clearPassword());
		clearPwMenu.add(clearPwItem);
		clearPwItem.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				((MondrianDatasourceDto)getSelectedNode()).setPassword(null);
			}
		});
		form.addFieldMenu(passwordKey, clearPwMenu);
		
		form.endRow();
		
		/* url */
		form.setFieldWidth(1);
		form.addField(String.class, MondrianDatasourceDtoPA.INSTANCE.url(), DatasourcesMessages.INSTANCE.url()); 
		
		form.addField(String.class, MondrianDatasourceDtoPA.INSTANCE.properties(), SaikuMessages.INSTANCE.propertyProperties(),  new SFFCCodeMirror() {
			
			@Override
			public int getWidth() {
				return -1;
			}
			
			@Override
			public int getHeight() {
				return 150;
			}
			
			@Override
			public String getLanguage() {
				return "text/x-ini";
			}
			
			@Override
			public boolean lineNumbersVisible() {
				return true;
			}

			@Override
			public ToolBarEnhancer getEnhancer() {
				return null;
			}
		},SFFCNoHtmlDecode.INSTANCE);
		
		form.addField(Boolean.class, MondrianDatasourceDtoPA.INSTANCE.mondrian3(), DatasourcesMessages.INSTANCE.mondrian3());
		
		form.addField(String.class, MondrianDatasourceDtoPA.INSTANCE.mondrianSchema(), SaikuMessages.INSTANCE.propertySchema(), new SFFCCodeMirror() {
			
			@Override
			public int getWidth() {
				return -1;
			}
			
			@Override
			public int getHeight() {
				return 300;
			}

			@Override
			public boolean lineNumbersVisible() {
				return true;
			}
			
			@Override
			public String getLanguage() {
				return "application/xml";
			}
			
			@Override
			public ToolBarEnhancer getEnhancer() {
				return null;
			}
		},SFFCNoHtmlDecode.INSTANCE);
		
	}
	
	private void clearCache() {
		saikuDao.clearCache((MondrianDatasourceDto)getSelectedNode(), new RsAsyncCallback<Void>(){
			@Override
			public void onSuccess(Void result) {
				InfoConfig infoConfig = new DefaultInfoConfig(SaikuMessages.INSTANCE.cacheCleared(), SaikuMessages.INSTANCE.cacheIsCleared());
				infoConfig.setWidth(350);
				Info.display(infoConfig);
			}
			public void onFailure(Throwable ex) { 
				new DetailErrorDialog(ex).show();
	        } 
		});
	}

}
