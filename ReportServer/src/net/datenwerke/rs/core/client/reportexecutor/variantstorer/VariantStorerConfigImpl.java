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
import com.google.inject.Inject;

import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorDao;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorUIService;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskFolderDto;


public class VariantStorerConfigImpl implements VariantStorerConfig {

	private final ReportExecutorDao executerDao;
	private final ReportExecutorUIService executorService;
	
	private boolean display = true;
	
	
	@Inject
	public VariantStorerConfigImpl(ReportExecutorDao executerDao,
			ReportExecutorUIService executorService){
		this.executerDao = executerDao;
		this.executorService = executorService;
	}
	
	@Override
	public boolean displayVariantStorer() {
		return display;
	}
	
	public void setDisplay(boolean display) {
		this.display = display;
	}
	
	@Override
	public boolean allowEditVariant() {
		return true;
	}
	
	@Override
	public boolean displayEditVariantOnStore() {
		return true;
	}
	
	@Override
	public TeamSpaceDto getTeamSpace() {
		return null;
	}
	@Override
	public TsDiskFolderDto getTeamSpaceFolder() {
		return null;
	}
	@Override
	public boolean allowNullTeamSpace() {
		return true;
	}

	@Override
	public VariantStorerHandleServerCalls getServerCallHandler() {
		return new VariantStorerHandleServerCalls() {
			
			@Override
			public void editVariant(ReportDto report, String executeToken, String name,
					String description, AsyncCallback<ReportDto> callback) {
				executerDao.editVariant(
					report,
					executeToken,
					name,
					description,
					callback
				);				
			}
			
			@Override
			public void deleteVariant(ReportDto report, AsyncCallback<Void> callback) {
				executerDao.deleteVariant(report, callback);
			}
			
			@Override
			public void createNewVariant(ReportDto report, TeamSpaceDto teamSpace, TsDiskFolderDto folder, String executeToken,
					String name, String description, AsyncCallback<ReportDto> callback) {
				executorService.createNewVariant(report, teamSpace, folder, executeToken, name, description, callback);
			}
		};
	}
	
	

}
