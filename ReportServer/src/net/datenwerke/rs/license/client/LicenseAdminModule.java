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
 
 
package net.datenwerke.rs.license.client;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gf.client.administration.interfaces.AdminModule;
import net.datenwerke.rs.license.client.locale.LicenseMessages;
import net.datenwerke.rs.license.client.ui.LicenseAdminPanel;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

/**
 * 
 *
 */
public class LicenseAdminModule implements AdminModule {

	private final Provider<LicenseAdminPanel> mainWidgetProvider;
	
	@Inject
	public LicenseAdminModule(Provider<LicenseAdminPanel> mainWidgetProvider){
		this.mainWidgetProvider = mainWidgetProvider;
	}
	
	@Override
	public Widget getMainWidget() {
		return mainWidgetProvider.get();
	}

	@Override
	public ImageResource getNavigationIcon() {
		return BaseIcon.CERTIFICATE.toImageResource();
	}

	@Override
	public String getNavigationText() {
		return LicenseMessages.INSTANCE.viewNavigationTitle();
	}

	@Override
	public void notifyOfSelection() {
		mainWidgetProvider.get().notifyOfSelection();
	}
}
