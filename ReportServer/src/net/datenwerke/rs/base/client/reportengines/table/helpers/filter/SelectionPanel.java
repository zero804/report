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

import java.util.List;

import net.datenwerke.gxtdto.client.baseex.widget.DwTabPanel;
import net.datenwerke.gxtdto.client.model.StringBaseModel;
import net.datenwerke.gxtdto.client.utils.SqlTypes;
import net.datenwerke.gxtdto.client.utils.handlers.GenericStoreHandler;
import net.datenwerke.gxtdto.client.utils.modelkeyprovider.BasicObjectModelKeyProvider;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.FilterService;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.locale.FilterMessages;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.TabPanel.TabPanelAppearance;
import com.sencha.gxt.widget.core.client.TabPanel.TabPanelBottomAppearance;

/**
 * 
 *
 * @param <M>
 */
public abstract class SelectionPanel<M> {

	@Inject
	protected static FilterService filterService;
	
	@Inject
	protected static SqlTypes sqlTypes;
	
	protected TabPanel tabPanel;
	protected GridView<M> gridView;
	protected TextView<M> textView;
	protected ListStore<M> store;
	protected ColumnDto column;
	
	private boolean fireUpdates = true;

	
	public SelectionPanel(final AbstractFilterAspect<M> filterAspect, ColumnDto column) {
		this.column = column;

		/* create tabpanel and initialize store */
		this.tabPanel = new DwTabPanel(GWT.<TabPanelAppearance> create(TabPanelBottomAppearance.class));
		
		this.store = new ListStore<M>(getModelKeyProvider());
		store.setAutoCommit(true);
		
		/* initialze views */
		this.gridView = initializeGridView();
		this.textView = initializeTextView();
		
		/* init tab panel */
		tabPanel.setBodyBorder(false);
		tabPanel.setBorders(false);
		
		/* add tabs */
		TabItemConfig gridConfig = new TabItemConfig(FilterMessages.INSTANCE.grid());
		TabItemConfig textConfig = new TabItemConfig(FilterMessages.INSTANCE.textViewTitle());
		
		tabPanel.add(gridView, gridConfig);
		tabPanel.add(textView, textConfig);
	
		/* attach store listener */
		store.addStoreHandlers(new GenericStoreHandler<M>(){
			@Override
			protected void handleDataChangeEvent() {
				if(fireUpdates)
					filterAspect.storeConfiguration();
			}
		});
	}

	protected ModelKeyProvider<? super M> getModelKeyProvider(){
		return new BasicObjectModelKeyProvider<M>();
	}

	abstract protected TextView<M> initializeTextView();

	abstract protected GridView<M> initializeGridView();

	public Widget getComponent() {
		return tabPanel;
	}

	public ListStore<M> getStore() {
		return store;
	}
	
	public void setFireUpdates(boolean fire){
		this.fireUpdates = fire;
	}

	public abstract void insertElement(StringBaseModel value);
	
	public void tryParseText(){
		List<M> newDtos = textView.tryParseText();
		store.clear();
		store.addAll(newDtos);
	}

	public void validate() {
	}
}
