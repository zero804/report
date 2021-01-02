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
 
 
package net.datenwerke.rs.dashboard.client.dashboard;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.rs.dashboard.client.dashboard.provider.BasicTreeProvider;
import net.datenwerke.rs.dashboard.client.dashboard.provider.DadgetsTreeProvider;
import net.datenwerke.rs.dashboard.client.dashboard.provider.DashboardsTreeProvider;
import net.datenwerke.rs.dashboard.client.dashboard.provider.FolderTreeProvider;
import net.datenwerke.rs.dashboard.client.dashboard.provider.FullTreeProvider;
import net.datenwerke.rs.dashboard.client.dashboard.provider.annotations.DashboardManagerAdminViewTree;
import net.datenwerke.rs.dashboard.client.dashboard.provider.annotations.DashboardTreeBasic;
import net.datenwerke.rs.dashboard.client.dashboard.provider.annotations.DashboardTreeDadgets;
import net.datenwerke.rs.dashboard.client.dashboard.provider.annotations.DashboardTreeDashboards;
import net.datenwerke.rs.dashboard.client.dashboard.provider.annotations.DashboardTreeFolders;
import net.datenwerke.rs.dashboard.client.dashboard.provider.annotations.DashboardTreeFull;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DadgetCatalogFactory;

public class DashboardUiModule extends AbstractGinModule {

	public static final String ADMIN_TREE_MENU_NAME = "dashboard:admin:tree:menu";
	
	public static final String DASHBOARD_HISTORY_TOKEN = "dashboardmgr";

	
	@Override
	protected void configure() {
		/* bind trees */
		bind(UITree.class).annotatedWith(DashboardTreeBasic.class).toProvider(BasicTreeProvider.class);
		bind(UITree.class).annotatedWith(DashboardTreeFull.class).toProvider(FullTreeProvider.class);
		bind(UITree.class).annotatedWith(DashboardTreeFolders.class).toProvider(FolderTreeProvider.class);
		bind(UITree.class).annotatedWith(DashboardTreeDashboards.class).toProvider(DashboardsTreeProvider.class);
		bind(UITree.class).annotatedWith(DashboardTreeDadgets.class).toProvider(DadgetsTreeProvider.class);
		bind(UITree.class).annotatedWith(DashboardManagerAdminViewTree.class).toProvider(FullTreeProvider.class).in(Singleton.class);

		install(new GinFactoryModuleBuilder().build(DadgetCatalogFactory.class));
		
		bind(DashboardUiStartup.class).asEagerSingleton();
	}

}
