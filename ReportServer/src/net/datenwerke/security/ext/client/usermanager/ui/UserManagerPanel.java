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
 
 
package net.datenwerke.security.ext.client.usermanager.ui;

import net.datenwerke.gf.client.managerhelper.ui.AbstractTreeManagerPanel;
import net.datenwerke.security.ext.client.usermanager.locale.UsermanagerMessages;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserManagerPanel extends AbstractTreeManagerPanel {

	@Inject
	public UserManagerPanel(
		UserMainPanel mainPanel,
		UserTreePanel treePanel
		){
		
		super(mainPanel, treePanel);
	}

	@Override
	protected String getHeadingText() {
		return UsermanagerMessages.INSTANCE.usermanagement();
	}
}
