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
 
 
package net.datenwerke.rs.reportdoc.client.terminal.commandproc;

import com.google.inject.Inject;

import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.reportdoc.client.ReportDocumentationUiService;
import net.datenwerke.rs.reportdoc.client.dto.DeployAnalyzeCommandResultExtensionDto;
import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultDto;
import net.datenwerke.rs.terminal.client.terminal.hooks.CommandResultProcessorHook;

public class DeployAnalyzeTerminalCommandProcessor implements CommandResultProcessorHook {
	
	private final ReportDocumentationUiService reportDocService;
	
	@Inject
	public DeployAnalyzeTerminalCommandProcessor(
			ReportDocumentationUiService reportDocService
			) {
		this.reportDocService = reportDocService;
	}

	@Override
	public void process(CommandResultDto result) {
		if(result.getExtensions().size() == 1 && result.getExtensions().get(0) instanceof DeployAnalyzeCommandResultExtensionDto){
			final DeployAnalyzeCommandResultExtensionDto analyzeCmd = (DeployAnalyzeCommandResultExtensionDto) result.getExtensions().get(0);
			final ReportDto leftReport = analyzeCmd.getLeftReport();
			final ReportDto rightReport = analyzeCmd.getRightReport();
			
			reportDocService.openDeployAnalyzeForopen(leftReport, rightReport);
			
		}
	}

}
