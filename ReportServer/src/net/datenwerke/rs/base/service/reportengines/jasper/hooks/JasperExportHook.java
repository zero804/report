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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper.hooks;

import java.util.Collection;

import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.output.object.CompiledRSJasperReport;
import net.datenwerke.security.service.usermanager.entities.User;
import net.sf.jasperreports.engine.JRExporter;

/**
 * Interface for the export callbacks.
 * 
 */
public interface JasperExportHook extends Hook {

	/**
	 * Provides a list of supported export formats.
	 * 
	 * @return A string array with each element holding one supported export
	 *         format.
	 */
	public Collection<String> getFormats();

	/**
	 * Method to be called just before exportReport() method.
	 * 
	 * @param type
	 *            The type of the exporter.
	 * @param exporter
	 *            The JRExporter to operate on.
	 */
	public void beforeExport(String type, JRExporter exporter, JasperReport report, User user);

	/**
	 * Method to be called after the call to the exportReport() method.
	 * 
	 * @param type
	 *            The type of the exporter.
	 * @param exporter
	 *            The JRExporter to operate on.
	 */
	public void afterExport(String type, JRExporter exporter, JasperReport report, CompiledRSJasperReport compiledReport, User user);
}
