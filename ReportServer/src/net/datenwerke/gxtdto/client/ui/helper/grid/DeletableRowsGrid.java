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
 
 
package net.datenwerke.gxtdto.client.ui.helper.grid;

import java.util.List;

import net.datenwerke.gxtdto.client.baseex.widget.mb.DwConfirmMessageBox;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.locale.BaseMessages;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public class DeletableRowsGrid<M> extends Grid<M> {

	public DeletableRowsGrid(ListStore<M> store, ColumnModel<M> cm){
		super(store, cm);
		
        getSelectionModel().setSelectionMode(SelectionMode.MULTI);

        /* init context menu */
        initContextMenu();

        new ExtendedKeyNav(this){
			protected void onSelectAll() {
				getSelectionModel().selectAll();
			};
			@Override
			public void onDelete(NativeEvent evt) {
				deleteSelection();
			}
        };
	}

	protected void initContextMenu() {
		Menu menu = new DwMenu();

		MenuItem delete = new DwMenuItem();
		delete.setText(BaseMessages.INSTANCE.remove());
		menu.add(delete);

		delete.addSelectionHandler(new SelectionHandler<Item>() {

			@Override
			public void onSelection(SelectionEvent<Item> event) {
				deleteSelection();
			}
		});
		
		MenuItem deleteAll = new DwMenuItem();
		deleteAll.setText(BaseMessages.INSTANCE.removeAll());
		menu.add(deleteAll);

		deleteAll.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				deleteAll();
			}
		});

		setContextMenu(menu);
	}

	public void deleteSelection(){
		List<M> items = this.getSelectionModel().getSelectedItems();

		for(M model : items){
			this.getStore().remove(model);
			deletedModel(model);
		}
	}

	public void deleteAll(){
		ConfirmMessageBox cmb = new DwConfirmMessageBox(BaseMessages.INSTANCE.removeAll(), BaseMessages.INSTANCE.confirmDeleteMsg(""));
		cmb.addDialogHideHandler(new DialogHideHandler() {
			@Override
			public void onDialogHide(DialogHideEvent event) {
				if (event.getHideButton() == PredefinedButton.YES) {
					DeletableRowsGrid.this.getStore().clear();

					deletedAllModels();
				}
	
			}
		});
		cmb.show();
	}

	protected void deletedModel(M model) {

	}

	protected void deletedAllModels() {

	}
}
