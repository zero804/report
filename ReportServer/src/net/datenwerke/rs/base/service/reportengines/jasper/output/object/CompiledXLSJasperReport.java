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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper.output.object;

/**
 * 
 *
 */
public class CompiledXLSJasperReport extends CompiledRSJasperReport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1235594273026554741L;
	
	private byte[] report;

	public byte[] getReport() {
		return report;
	}

	public void setReport(Object report) {
		try{
			this.report = (byte[]) report;
		} catch(ClassCastException e){
			IllegalArgumentException iae = new IllegalArgumentException("Expected byte array"); //$NON-NLS-1$
			iae.initCause(e);
			throw iae;
		}
	}
	
	public String getFileExtension() {
		return "xls"; //$NON-NLS-1$
	}

	public String getMimeType() {
		return "application/vnd.ms-excel"; //$NON-NLS-1$
	}
	
}
