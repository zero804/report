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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper.output.generator;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.output.object.CompiledRSJasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.output.object.CompiledXLSJasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.output.object.CompiledXLSXJasperReport;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.security.service.usermanager.entities.User;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 * Exports a jasper report to PDF.
 *
 */
public class JasperXLSOutputGenerator extends JasperOutputGeneratorImpl {
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	public static final String CONFIG_FILE = "exportfilemd/excelexport.cf";
	public static final String XLS_EXPORT_FORMAT_PROPERTY = "xls.format";
	
	private final ConfigService configService; 

	@Inject
	public JasperXLSOutputGenerator(
		HookHandlerService hookHandler,
		ConfigService configService
		){
		super(hookHandler);
		
		/* store objects */
		this.configService = configService;
	}
	
	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_EXCEL};
	}
	
	@Override
	public CompiledRSJasperReport exportReport(JasperPrint jasperPrint, String outputFormat, JasperReport report,  User user, ReportExecutionConfig... configs) {
		JRAbstractExporter exporter;
		
		String excelFormat = getExportFormat();
		if("xls".equals(excelFormat)){
			exporter = new JRXlsExporter();
		} else {
			exporter = new JRXlsxExporter();
		}
		
		/* create buffer for output */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		/* configure exporter */
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		
		callPreHooks(outputFormat, exporter, report, user);
		
		/* export */
		try {
			exporter.exportReport();
		} catch (JRException e) {
			logger.warn( e.getMessage(), e);
		}
		
		/* create return object */
		CompiledRSJasperReport cjrReport = null;
		if("xls".equals(excelFormat)){
			cjrReport = new CompiledXLSJasperReport();
		} else {
			cjrReport = new CompiledXLSXJasperReport();
		}
		
		cjrReport.setData(jasperPrint);
		
		/* add report to object */
		cjrReport.setReport(out.toByteArray());
		
		callPostHooks(outputFormat, exporter, report, cjrReport, user);
		
		/* return compiled report */
		return cjrReport;
	}
	
	protected String getExportFormat(){
		return configService.getConfigFailsafe(CONFIG_FILE).getString(XLS_EXPORT_FORMAT_PROPERTY, "xlsx");
	}	

	@Override
	public CompiledReport getFormatInfo() {
		return new CompiledXLSJasperReport();
	}

}
