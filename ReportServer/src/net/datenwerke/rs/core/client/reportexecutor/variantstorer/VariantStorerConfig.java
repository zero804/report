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
 
 
package net.datenwerke.rs.core.client.reportexecutor.variantstorer;

import com.google.gwt.user.client.rpc.AsyncCallback;

import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskFolderDto;

public interface VariantStorerConfig {

	public boolean displayVariantStorer();
	
	public boolean displayEditVariantOnStore();
	
	public boolean allowEditVariant();
	
	public VariantStorerHandleServerCalls getServerCallHandler();
	
	public TeamSpaceDto getTeamSpace();
	
	public TsDiskFolderDto getTeamSpaceFolder();
	
	public interface VariantStorerHandleServerCalls{

		void createNewVariant(ReportDto report, TeamSpaceDto teamSpace, TsDiskFolderDto folder, String executeToken,
				String name, String desc, AsyncCallback<ReportDto> callback);

		void deleteVariant(ReportDto report, AsyncCallback<Void> callback);

		void editVariant(ReportDto report, String executeToken, String name,
				String description, AsyncCallback<ReportDto> callback);
		
	}
	
	public boolean allowNullTeamSpace();
	
}
