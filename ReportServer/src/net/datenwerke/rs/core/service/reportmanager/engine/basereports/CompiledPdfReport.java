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
 
 
package net.datenwerke.rs.core.service.reportmanager.engine.basereports;

import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;

public class CompiledPdfReport implements CompiledReport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2495473276012232477L;

	private final Object report;

	public CompiledPdfReport(Object report) {
		this.report = report;
	}

	public Object getReport(){
		return report;
	}
	
	public String getMimeType(){
		return "application/pdf";
	}
	
	public String getFileExtension(){
		return "pdf";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((report == null) ? 0 : report.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CompiledPdfReport)) {
			return false;
		}
		CompiledPdfReport other = (CompiledPdfReport) obj;
		if (report == null) {
			if (other.report != null) {
				return false;
			}
		} else if (!report.equals(other.report)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean hasData() {
		return report != null;
	}
	
	@Override
	public boolean isStringReport() {
		return false;
	}
}
