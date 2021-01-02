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
 
 
package net.datenwerke.gf.client.managerhelper.hookers;

import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import net.datenwerke.gf.client.managerhelper.hooks.ManagerHelperTreeToolbarEnhancerHook;
import net.datenwerke.gf.client.managerhelper.hooks.TreeSelectionToolbarEnhancerHook;
import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.treedb.client.treedb.TreeDbManagerContainer;

public class BaseTreeComponentsForTreeNavToolbarHooker implements
		ManagerHelperTreeToolbarEnhancerHook, TreeSelectionToolbarEnhancerHook {
	
	@Override
	public void treeNavigationToolbarEnhancerHook_addLeft(ToolBar toolbar,
			UITree tree, TreeDbManagerContainer treeManagerContainer) {
	}
	
	@Override
	public void treeNavigationToolbarEnhancerHook_addRight(ToolBar toolbar, final UITree tree, TreeDbManagerContainer treeManagerContainer) {
		/* expand all button */
		DwTextButton expandAllButton = new DwTextButton(BaseIcon.EXPAND_ALL);
		expandAllButton.setToolTip(BaseMessages.INSTANCE.expandAll());
		expandAllButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				tree.expandAll();
			}
		});
		toolbar.add(expandAllButton);

		/* collapse all button */
		DwTextButton collapseAllButton = new DwTextButton(BaseIcon.COLLAPSE_ALL);
		collapseAllButton.setToolTip(BaseMessages.INSTANCE.collapseAll());
		collapseAllButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				tree.collapseAll();
			}
		});
		toolbar.add(collapseAllButton);

		/* reload button */
		DwTextButton reloadButton = new DwTextButton(BaseIcon.REFRESH);
		reloadButton.setToolTip(BaseMessages.INSTANCE.refresh());
		reloadButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				tree.reload();
			}
		});
		toolbar.add(reloadButton);
	}

}
