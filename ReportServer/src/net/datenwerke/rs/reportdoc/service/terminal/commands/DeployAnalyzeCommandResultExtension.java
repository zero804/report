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
 
 
package net.datenwerke.rs.reportdoc.service.terminal.commands;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResultExtension;

@GenerateDto(dtoPackage = "net.datenwerke.rs.reportdoc.client.dto")
public class DeployAnalyzeCommandResultExtension extends CommandResultExtension {

	@ExposeToClient
	private Report leftReport;

	@ExposeToClient
	private Report rightReport;
	
	public void setLeftReport(Report leftReport) {
		this.leftReport = leftReport;
	}

	public Report getLeftReport() {
		return leftReport;
	}

	public void setRightReport(Report rightReport) {
		this.rightReport = rightReport;
	}

	public Report getRightReport() {
		return rightReport;
	}
	
}
