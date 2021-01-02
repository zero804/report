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
 
 
package net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.output.generator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;

import com.google.inject.Inject;

import net.datenwerke.async.DwAsyncService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.client.AvailableReportProperties;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportStringProperty;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterValue;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.entities.JxlsReport;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.hooks.JxlsContextVariableProvider;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.output.object.CompiledJxlsXlsReport;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.output.object.CompiledJxlsXlsmReport;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.output.object.CompiledJxlsXlsxReport;
import net.sf.jxls.transformer.XLSTransformer;

public class JxlsOutputGeneratorImpl implements JxlsOutputGenerator {

	private final HookHandlerService hookHandler;
	private final DwAsyncService asyncService;
	
	@Inject
	public JxlsOutputGeneratorImpl(HookHandlerService hookHandler, DwAsyncService asyncService){
		this.hookHandler = hookHandler;
		this.asyncService = asyncService;
	}
	
	@Override
	public boolean isCatchAll() {
		return false;
	}

	@Override
	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_EXCEL};
	}

	@Override
	public CompiledReport getFormatInfo() {
		return null;
	}

	@Override
	public CompiledReport exportReport(OutputStream os, byte[] template, Connection connection, JxlsReport jxlsReport, ParameterSet parameters, String outputFormat, ReportExecutionConfig... configs) throws ReportExecutorException {
	    if(jxlsReport.isJxlsOne()) {
            Workbook workbook = processWorkbookLegacy(parameters, template, connection);
            return exportReportLegacy(os, workbook);
        } else {
            Workbook workbook = processWorkbook(parameters, template, connection, jxlsReport, outputFormat);
            return exportReport(os, workbook);
        }
	}
	
	private CompiledReport exportReport(OutputStream os, Workbook workbook) throws ReportExecutorException {
        /* write out file */
        try{
            byte[] content = null;
            if(null == os) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                workbook.write(bos);
                content = bos.toByteArray();
            } else {
                workbook.write(os);
                os.close();
            }
            
            /* new excel */
            if(workbook instanceof XSSFWorkbook){
                if(((XSSFWorkbook)workbook).isMacroEnabled()) 
                    return new CompiledJxlsXlsmReport(content);
                else
                    return new CompiledJxlsXlsxReport(content);
            } 
            
            /* old excel */
            return new CompiledJxlsXlsReport(content);
        } catch (Exception e) {
            throw new ReportExecutorException(e);
        }
    }
    
    private CompiledReport exportReportLegacy(OutputStream os, Workbook workbook) throws ReportExecutorException {
        /* write out file */
        try{
            byte[] content = null;
            if(null == os) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                workbook.write(bos);
                content = bos.toByteArray();
            } else {
                workbook.write(os);
                os.close();
            }
            
            /* new excel */
            if(workbook instanceof XSSFWorkbook){
                if(((XSSFWorkbook)workbook).isMacroEnabled()) 
                    return new CompiledJxlsXlsmReport(content);
                else
                    return new CompiledJxlsXlsxReport(content);
            } 
            
            /* old excel */
            return new CompiledJxlsXlsReport(content);
        } catch (Exception e) {
            throw new ReportExecutorException(e);
        }
    }
    
    protected Workbook processWorkbook(ParameterSet parameters, byte[] template, Connection connection, JxlsReport report, String outputFormat) throws ReportExecutorException {
		/* prepare parameters */
		Map<String, ParameterValue> parameterMap = parameters.getParameterMap();
		Map<String, Object> simpleParams = parameters.getParameterMapSimple();

		Context context = new Context();
		context.putVar("_parameters", parameterMap);
		context.putVar("parameters", simpleParams);

		JxlsSqlExecutor rm = new JxlsSqlExecutor(connection, parameters);
		context.putVar("sql", rm);
		context.putVar("rm", rm);
		context.putVar("jdbc", rm);
		context.putVar("StringUtils", new StringUtils());
		context.putVar("StringEscapeUtils", new StringEscapeUtils());

		for (JxlsContextVariableProvider provider : hookHandler.getHookers(JxlsContextVariableProvider.class))
			provider.adaptContext(context);

		// read properties
		boolean jxlsStream = (Boolean) report.getEffectiveReportStringPropertyValue(
				AvailableReportProperties.PROPERTY_JXLS_STREAM.getValue(), false, ReportStringProperty::toBoolean);

		int rowAccessWindowSize = (Integer) report.getEffectiveReportStringPropertyValue(
				AvailableReportProperties.PROPERTY_JXLS_STREAM_ROW_ACCESS_WINDOW_SIZE.getValue(),
				SXSSFWorkbook.DEFAULT_WINDOW_SIZE, ReportStringProperty::toInteger);

		boolean compressTmpFiles = (Boolean) report.getEffectiveReportStringPropertyValue(
				AvailableReportProperties.PROPERTY_JXLS_STREAM_COMPRESS_TMP_FILES.getValue(), true,
				ReportStringProperty::toBoolean);

		boolean useSharedStringsTable = (Boolean) report.getEffectiveReportStringPropertyValue(
				AvailableReportProperties.PROPERTY_JXLS_STREAM_USE_SHARED_STRINGS_TABLE.getValue(), true,
				ReportStringProperty::toBoolean);

		try (ByteArrayInputStream is = new ByteArrayInputStream(template)) {

			if (!jxlsStream) {

				try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
					JxlsHelper.getInstance().processTemplate(is, bos, context);

					byte[] result = bos.toByteArray();
					ByteArrayInputStream bis = new ByteArrayInputStream(result);
					Workbook wb = WorkbookFactory.create(bis);
					return wb;
				}

			} else {
				Workbook workbook = WorkbookFactory.create(is);

				if (outputFormat.contentEquals("HTML")) {
					try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
						workbook.write(bos); // only template gets exported

						byte[] result = bos.toByteArray();
						ByteArrayInputStream bis = new ByteArrayInputStream(result);
						Workbook wb = WorkbookFactory.create(bis);
						return wb;
					}

				} else {
					context.putVar("cellRefUpdater", new CellRefUpdater());
					
					/* With SXSSF you cannot use normal formula processing */
					context.getConfig().setIsFormulaProcessingRequired(false);
					
					workbook.setForceFormulaRecalculation(true);
					workbook.setActiveSheet(0);
					workbook.setSelectedTab(0);
					
					final JxlsStreamingTransformer transformer = new JxlsStreamingTransformer(workbook, rowAccessWindowSize,
							compressTmpFiles, useSharedStringsTable);
					AreaBuilder areaBuilder = new XlsCommentAreaBuilder(transformer);
					List<Area> xlsAreaList = areaBuilder.build();
					for (Area xlsArea : xlsAreaList) {
						xlsArea.applyAt(xlsArea.getStartCellRef(), context);
					}

					try (PipedInputStream in = new PipedInputStream()) {
						
						final PipedOutputStream out = new PipedOutputStream(in);

						asyncService.submit( () -> {
								try {
									// write the original outputStream to the PipedOutputStream
									((PoiTransformer) transformer).getWorkbook().write(out);
								} catch (IOException e) {
									throw new RuntimeException(e);
								} finally {
									if (transformer.getWorkbook() instanceof SXSSFWorkbook) {
										   ((SXSSFWorkbook) transformer.getWorkbook()).dispose();
									}
									// close the PipedOutputStream here because we're done writing data
									// once this thread has completed its run
									if (out != null) {
										try {
											out.close();
										} catch (IOException e) {
											throw new RuntimeException(e);
										}
									}
								}
						});

						Workbook wb = WorkbookFactory.create(in);
						return wb;
					}
				}
			}

		} catch (IOException e) {
			throw new ReportExecutorException(e);
		} finally {
			for (JxlsContextVariableProvider provider : hookHandler.getHookers(JxlsContextVariableProvider.class))
				provider.finishedExport();
		}
    }

    protected Workbook processWorkbookLegacy(ParameterSet parameters, byte[] template,
            Connection connection) throws ReportExecutorException {
        /* prepare parameters */
        Map<String, ParameterValue> parameterMap = parameters.getParameterMap();
        Map<String, Object> simpleParams = parameters.getParameterMapSimple();
        
        Map<String, Object> beans = new HashMap<>();
        beans.put("_parameters", parameterMap);
        beans.put("parameters", simpleParams);
        
        XLSTransformer transformer = new XLSTransformer();
        
        ByteArrayInputStream is = new ByteArrayInputStream(template);
        
        JxlsSqlExecutor rm = new JxlsSqlExecutor(connection, parameters);
        beans.put("sql", rm);
        beans.put("rm", rm);
        beans.put("StringUtils", new StringUtils());
        beans.put("StringEscapeUtils", new StringEscapeUtils());
        
        for(JxlsContextVariableProvider provider : hookHandler.getHookers(JxlsContextVariableProvider.class))
            provider.adaptLegacyContext(beans);
        
        try {   
            return transformer.transformXLS(is, beans); 
        } catch (Exception e) {
            throw new ReportExecutorException(e);
        } finally {
            for(JxlsContextVariableProvider provider : hookHandler.getHookers(JxlsContextVariableProvider.class))
                provider.finishedExport();
        }
    }

}
