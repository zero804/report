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
 
 
package net.datenwerke.gf.client.managerhelper.hooks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.inject.Inject;

public abstract class MainPanelViewProviderHookImpl implements MainPanelViewProviderHook {

	protected final HookHandlerService hookHandler;
	
	@Inject
	public MainPanelViewProviderHookImpl(HookHandlerService hookHandler){
		this.hookHandler = hookHandler;
	}
	
	@Override
	public List<MainPanelView> mainPanelViewProviderHook_getView(AbstractNodeDto node) {
		List<MainPanelView> views = new ArrayList<MainPanelView>();
		
		views.addAll(getPrimaryViews(node));
		
		for(MainPanelViewProviderConfigHook config : hookHandler.getHookers(MainPanelViewProviderConfigHook.class)){
			if(config.applies(getViewProviderId(), node)){
				views.addAll(config.gatherViews(getViewProviderId(), node));
			}
		}
		
		return views;
	}
	
	protected Collection<? extends MainPanelView> getPrimaryViews(AbstractNodeDto node){
		return Collections.emptyList();
	}

	abstract public String getViewProviderId();

}
