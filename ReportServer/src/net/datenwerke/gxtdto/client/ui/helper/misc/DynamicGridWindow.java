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
 
 
package net.datenwerke.gxtdto.client.ui.helper.misc;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.model.ListStringBaseModel;
import net.datenwerke.gxtdto.client.utils.modelkeyprovider.BasicObjectModelKeyProvider;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

public class DynamicGridWindow extends DwWindow {

	public interface DataProvider{
		void getData(PagingLoadConfig loadConfig,
				AsyncCallback<PagingLoadResult<ListStringBaseModel>> callback);

		void getColumns(AsyncCallback<List<String>> asyncCallback);
	}
	
	private final DataProvider dataProvider;
	private boolean isConfigured = false;
	
	public DynamicGridWindow(DataProvider dataProvider){
		this.dataProvider = dataProvider;
		
		setSize(800, 430);
	}

	@Override
	public void show() {
		if(! isConfigured)
			init();
		
	    super.show();
	}

	protected void init() {
		final VerticalLayoutContainer container = new VerticalLayoutContainer();
		add(container);
	
	    /* get column config */
	    dataProvider.getColumns(new AsyncCallback<List<String>>() {

			@Override
			public void onSuccess(List<String> result) {
				RpcProxy<PagingLoadConfig, PagingLoadResult<ListStringBaseModel>> proxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<ListStringBaseModel>>() {  
			      
					@Override
					public void load(
							PagingLoadConfig loadConfig,
							AsyncCallback<PagingLoadResult<ListStringBaseModel>> callback) {
						dataProvider.getData((PagingLoadConfig) loadConfig, callback);
					}  
			    };
				
				/* loader */  
			    final PagingLoader<PagingLoadConfig, PagingLoadResult<ListStringBaseModel>> loader = new PagingLoader<PagingLoadConfig, PagingLoadResult<ListStringBaseModel>>(proxy);  
			    loader.setRemoteSort(true);  
				
				/* create store for results */
			    ListStore<ListStringBaseModel> store = new ListStore<ListStringBaseModel>(new BasicObjectModelKeyProvider<ListStringBaseModel>());
			    loader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, ListStringBaseModel, PagingLoadResult<ListStringBaseModel>>(store));
				
			    /* column config */
			    List<ColumnConfig<ListStringBaseModel, ?>> columns = new ArrayList<ColumnConfig<ListStringBaseModel, ?>>();
			    int i = 0;
			    for(String name : result){
			    	ColumnConfig<ListStringBaseModel, ListStringBaseModel> conf = new ColumnConfig<ListStringBaseModel, ListStringBaseModel>(new IdentityValueProvider<ListStringBaseModel>("col_" + (i++)), 70, name);
			    	conf.setCell(new AbstractCell<ListStringBaseModel>() {
						@Override
						public void render(
								com.google.gwt.cell.client.Cell.Context context,
								ListStringBaseModel model, SafeHtmlBuilder sb) {
							String value = model.getValue().get(context.getColumn());
							sb.appendEscaped(null == value ? "" : value);
						}
					});
			    	conf.setMenuDisabled(true);
			    	conf.setSortable(false);
			    	columns.add(conf);
			    }
			    
			    /* create grid */
				Grid<ListStringBaseModel> grid = new Grid<ListStringBaseModel>(store, new ColumnModel<ListStringBaseModel>(columns));
				grid.setStateful(false);
				grid.setLoadMask(true);
				grid.setBorders(true);
				
				
				container.add(grid, new VerticalLayoutData(1, 1));

				loader.load();
				
				/* toolbar */
			    final PagingToolBar toolBar = new PagingToolBar(15);  
			    toolBar.bind(loader);  
			    container.add(toolBar, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
			    
			    forceLayout();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				hide();
			}
		});
	    
	    addSpecButton(new OnButtonClickHandler() {
			
			@Override
			public void onClick() {
				hide();
			}
		}, BaseMessages.INSTANCE.ok());
	}
}
