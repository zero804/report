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
 
 
package net.datenwerke.rs.incubator.client.managerhelpersearch.hookers;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import net.datenwerke.gf.client.history.HistoryLocation;
import net.datenwerke.gf.client.history.dto.HistoryLinkDto;
import net.datenwerke.gf.client.managerhelper.hooks.TreeSelectionToolbarEnhancerHook;
import net.datenwerke.gf.client.treedb.TreeDBHistoryCallback;
import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.search.client.search.SearchDao;
import net.datenwerke.rs.search.client.search.SearchUiService;
import net.datenwerke.rs.search.client.search.dto.SearchResultEntryDto;
import net.datenwerke.treedb.client.treedb.TreeDbManagerContainer;

public class TreeSelectionSearchBar extends ManagerHelperSearchBar implements TreeSelectionToolbarEnhancerHook{

	@Inject
	public TreeSelectionSearchBar(SearchDao searcher, SearchUiService searchService, ToolbarService toolbarService) {
		super(searcher, searchService, toolbarService);
	}

	@Override
	public void treeNavigationToolbarEnhancerHook_addLeft(final ToolBar toolbar, final UITree tree, final TreeDbManagerContainer treeManagerContainer) {
		
		SelectionHandler<SearchResultEntryDto> selectionHandler = new SelectionHandler<SearchResultEntryDto>() {
			
			@Override
			public void onSelection(SelectionEvent<SearchResultEntryDto> event) {
				SearchResultEntryDto selection = event.getSelectedItem();
				if(null == selection)
					return;
				
				List<HistoryLinkDto> links = selection.getLinks();
				for (HistoryLinkDto link: links) {
					String historyToken = link.getHistoryToken();
					if (historyToken.contains("tsselect"))
						continue;
					
					String[] pathElements = HistoryLocation.fromString(historyToken).getParameterValue(TreeDBHistoryCallback.HISTORY_PARAMETER_TREE_PATH).split("\\.");
					final List<Long> nodes = new ArrayList<Long>();
					for(String elem : pathElements){
						nodes.add(Long.valueOf(elem));
					}
					
					tree.expandPathByIds(nodes);
					break;
				}
				
			}
		};
		
		addSearchBar(toolbar, tree, treeManagerContainer, selectionHandler);
	}
}
