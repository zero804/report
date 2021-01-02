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
 
 
package net.datenwerke.rs.core.client.helper.simpleform;

import java.util.List;

import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;

public class ExportTypeSelection {

	private String outputFormat;
	private List<ReportExecutionConfigDto> exportConfiguration;
	private boolean configured = false;
	
	public String getOutputFormat() {
		return outputFormat;
	}
	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}
	
	public List<ReportExecutionConfigDto> getExportConfiguration() {
		return exportConfiguration;
	}
	public void setExportConfiguration(
			List<ReportExecutionConfigDto> exportConfiguration) {
		this.exportConfiguration = exportConfiguration;
	}
	
	public void setConfigured(boolean configured) {
		this.configured = configured;
	}
	public boolean isConfigured() {
		return configured;
	}
	
	
}
