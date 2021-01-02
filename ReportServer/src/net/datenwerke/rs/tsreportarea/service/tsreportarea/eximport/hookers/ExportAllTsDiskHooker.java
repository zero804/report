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
 
 
package net.datenwerke.rs.tsreportarea.service.tsreportarea.eximport.hookers;

import net.datenwerke.eximport.ex.ExportConfig;
import net.datenwerke.rs.eximport.service.eximport.hooks.ExportAllHook;
import net.datenwerke.rs.teamspace.service.teamspace.TeamSpaceService;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskService;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.AbstractTsDiskNode;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeExportItemConfig;

import com.google.inject.Inject;

public class ExportAllTsDiskHooker implements ExportAllHook {

	private final TsDiskService diskService;
	private final TeamSpaceService tsService;
	
	@Inject
	public ExportAllTsDiskHooker(
		TsDiskService diskService,
		TeamSpaceService tsService) {
		
		this.diskService = diskService;
		this.tsService = tsService;
	}

	@Override
	public void configure(ExportConfig config) {
		if(! tsService.isGlobalTsAdmin())
			throw new ViolatedSecurityException();
		
		for(AbstractTsDiskNode node : diskService.getAllNodes())
			config.addItemConfig(new TreeNodeExportItemConfig(node));
	}

	
}
