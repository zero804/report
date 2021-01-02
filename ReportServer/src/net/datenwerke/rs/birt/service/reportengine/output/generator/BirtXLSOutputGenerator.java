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
 
 
package net.datenwerke.rs.birt.service.reportengine.output.generator;

import java.io.ByteArrayOutputStream;

import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.RenderOption;

import com.google.inject.Inject;

import net.datenwerke.rs.birt.service.reportengine.output.object.CompiledRSBirtReport;
import net.datenwerke.rs.birt.service.reportengine.output.object.CompiledXLSBirtReport;
import net.datenwerke.rs.birt.service.reportengine.output.object.CompiledXLSXBirtReport;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorRuntimeException;
import net.datenwerke.rs.utils.config.ConfigService;

public class BirtXLSOutputGenerator extends BirtOutputGeneratorImpl {

	public static final String CONFIG_FILE = "exportfilemd/excelexport.cf";

	public static final String XLS_EXPORT_FORMAT_PROPERTY = "xls.format";

	private final ConfigService configService; 
	
	@Inject
	public BirtXLSOutputGenerator(
		ConfigService configService
		) {
		super();
		
		/* store objects */
		this.configService = configService;
	}
	
	@Override
	public CompiledRSBirtReport exportReport(
			Object oRunAndRenderTask, String outputFormat,
			ReportExecutionConfig... configs) {

		try {
			IRunAndRenderTask runAndRenderTask = (IRunAndRenderTask) oRunAndRenderTask;
			EXCELRenderOption options = new EXCELRenderOption();

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			options.setOutputStream(bos);
			
			String excelFormat = getExportFormat();
			
			if("xls".equals(excelFormat)){
				options.setOutputFormat("xls");
				options.setOfficeVersion("office2003");
			} else {
				options.setOutputFormat("xlsx");
				options.setOfficeVersion("office2007");
			}

			/* adapt render options */
			RenderOption renderOptions = adapt(options);
			
			/* render */
			runAndRenderTask.setRenderOption(renderOptions);
			runAndRenderTask.run();
		
			
			/* create return object */
			CompiledRSBirtReport cbReport;
			
			if("xls".equals(excelFormat)){
				cbReport = new CompiledXLSBirtReport();
				((CompiledXLSBirtReport)cbReport).setReport(bos.toByteArray());
			} else {
				cbReport = new CompiledXLSXBirtReport();
				((CompiledXLSXBirtReport)cbReport).setReport(bos.toByteArray());
			}

			/* return compiled report */
			return cbReport;

		} catch (EngineException e) {
			ReportExecutorRuntimeException rere = new ReportExecutorRuntimeException();
			rere.initCause(e);
			throw rere;
		}
	}
	
	protected String getExportFormat(){
		return configService.getConfigFailsafe(CONFIG_FILE).getString(XLS_EXPORT_FORMAT_PROPERTY, "xlsx");
	}	

	@Override
	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_EXCEL};
	}
	
	@Override
	public CompiledReport getFormatInfo() {
		return new CompiledXLSBirtReport();
	}

}
