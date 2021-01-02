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
 
 
package net.datenwerke.rs.base.ext.service.security.eximport.hookers;

import net.datenwerke.eximport.ex.ExportConfig;
import net.datenwerke.eximport.ex.entity.EntityExportItemConfig;
import net.datenwerke.rs.eximport.service.eximport.hooks.ExportAllHook;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.entities.GenericSecurityTargetEntity;

import com.google.inject.Inject;

public class ExportAllGenericRightsHooker implements ExportAllHook {

	private final SecurityService securityService;
	
	@Inject
	public ExportAllGenericRightsHooker(
		SecurityService securityService
		) {
		this.securityService = securityService;
	}

	@Override
	public void configure(ExportConfig config) {
		for(Class<?> targetType : securityService.getRegisteredGenericSecurityTargets()){
			GenericSecurityTargetEntity entity = securityService.loadGenericTarget(targetType);
			config.addItemConfig(new EntityExportItemConfig(entity));
		}
	}

}
