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
 
 
package net.datenwerke.rs.adminutils.client.suuser.security;

import net.datenwerke.rs.adminutils.client.suuser.locale.SuMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.client.security.GenericTargetIdentifier;
import net.datenwerke.security.client.security.hooks.GenericSecurityViewDomainHook;

import com.google.gwt.resources.client.ImageResource;

public class SuViewSecurityTargetDomainHooker implements
		GenericSecurityViewDomainHook {

	public ImageResource genericSecurityViewDomainHook_getIcon() {
		return BaseIcon.USER_MD.toImageResource();
	}

	public String genericSecurityViewDomainHook_getName() {
		return SuMessages.INSTANCE.suCommandName();
	}
	
	public String genericSecurityViewDomainHook_getDescription() {
		return SuMessages.INSTANCE.suPermissionModuleDescription();
	}

	public GenericTargetIdentifier genericSecurityViewDomainHook_getTargetId() {
		return new SuGenericTargetIdentifier();
	}

}
