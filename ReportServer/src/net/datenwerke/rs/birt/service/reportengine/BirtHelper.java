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
 
 
package net.datenwerke.rs.birt.service.reportengine;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.LocaleUtils;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IDatasetPreviewTask;
import org.eclipse.birt.report.engine.api.IEngineTask;
import org.eclipse.birt.report.engine.api.IExtractionResults;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefn;
import org.eclipse.birt.report.engine.api.IParameterSelectionChoice;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.model.api.DataSetHandle;
import org.eclipse.birt.report.model.api.ParameterHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;

import com.google.inject.Inject;

import net.datenwerke.rs.base.service.datasources.table.impl.TableDataSource;
import net.datenwerke.rs.base.service.datasources.transformers.DatasourceTransformationService;
import net.datenwerke.rs.base.service.parameterreplacements.provider.LocaleInfoParameterReplacement;
import net.datenwerke.rs.birt.service.reportengine.entities.BirtReport;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;

public class BirtHelper {

	private BirtReportService reportService;

	private IReportRunnable reportDesign;
	
	private List<IEngineTask> cleanup = new ArrayList<IEngineTask>();

	private DatasourceTransformationService datasourceTransformationService;


	@Inject
	public BirtHelper(
			BirtReportService reportService, 
			DatasourceTransformationService datasourceTransformationService
		){
		
		this.reportService = reportService;
		this.datasourceTransformationService = datasourceTransformationService;
		
	}

	public IReportRunnable loadReportDesign(InputStream reportDesignData) throws BirtException {
		IReportEngine engine = reportService.getReportEngine();
		this.reportDesign = engine.openReportDesign(reportDesignData);
		return reportDesign;
	}
	
	public IReportRunnable loadReportDesign(BirtReport report) throws BirtException {
		return loadReportDesign(new ByteArrayInputStream(report.getReportFile().getContent().getBytes()));
	}
	
	public void cleanup() {
		Iterator<IEngineTask> it = cleanup.iterator();
		while(it.hasNext()){
			IEngineTask task = it.next();
			try{
				task.close();
			}catch(Exception e){};
			it.remove();
		}
	}

	public IExtractionResults getDataSetData(BirtReport bReport, DataSetHandle dataSetHandle, ParameterSet parameters) throws BirtException{
		IDatasetPreviewTask dataSetPreviewTask = reportService.getReportEngine().createDatasetPreviewTask();
		setLocale(parameters.getParameterMapSimple(), dataSetPreviewTask);
		
		cleanup.add(dataSetPreviewTask);
		dataSetPreviewTask.setDataSet(dataSetHandle);
		dataSetPreviewTask.setMaxRow(Integer.MAX_VALUE);
		
//		Connection connection = dataSourceTransformer.transform(bReport, parameters);
		Connection connection = datasourceTransformationService.transform(Connection.class, bReport, parameters);
		if(null != connection)
			dataSetPreviewTask.getAppContext().put("OdaJDBCDriverPassInConnection", connection);

		applyParameters(dataSetPreviewTask, parameters);

		IExtractionResults execute = dataSetPreviewTask.execute();
		return execute;
	}

	private void applyParameters(IEngineTask engineTask, ParameterSet parameters) {
		if(null != parameters){
			for(Entry<String, Object> parameterEntry : parameters.getParameterMapSimple().entrySet()){
				String parameterName = parameterEntry.getKey();
				Object parameterValue = parameterEntry.getValue();
	
				/* As birt other than jasper does not handle multiselect parameters as List, but 
				 * expects Object[] we need to convert them */
				if(parameterValue instanceof List){
					engineTask.setParameterValue(parameterName, ((List)parameterValue).toArray());
				}else{
					engineTask.setParameterValue(parameterName, parameterValue);
				}
			}
		}
	}
	
	public void setLocale(Map<String, Object> parameterMap, IEngineTask task){
		try{
			String sLocale = (String) parameterMap.get(LocaleInfoParameterReplacement.RS_LOCALE);
			if(null != sLocale && !sLocale.isEmpty()){
				Locale locale = LocaleUtils.toLocale(sLocale);
				task.setLocale(locale);
			}
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}
		try{
			String sLocale2 = (String) parameterMap.get(LocaleInfoParameterReplacement.RS_LOCALE2);
			if(null != sLocale2 && !sLocale2.isEmpty()){
				Locale Locale2 = LocaleUtils.toLocale(sLocale2);
				task.setLocale(Locale2);
			}
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}
	}

	public Collection<IParameterDefn> getParameterDefinitions() throws BirtException{
		IGetParameterDefinitionTask task = reportService.getReportEngine().createGetParameterDefinitionTask(reportDesign);
		
		cleanup.add(task);
		return task.getParameterDefns(false);
	}
	
	public Collection<IParameterSelectionChoice> getParameterSelectionList(IParameterDefn parameterDefinition, ParameterSet parameters) throws BirtException{
		IGetParameterDefinitionTask task = reportService.getReportEngine().createGetParameterDefinitionTask(reportDesign);
		setLocale(parameters.getParameterMapSimple(), task);
		cleanup.add(task);
		applyParameters(task, parameters);
		
		return task.getSelectionList(parameterDefinition.getName());
	}
	
	public ParameterHandle getParameterHandle(IParameterDefn parameterDefinition){
		ReportDesignHandle reportHandle = (ReportDesignHandle) reportDesign.getDesignHandle();
		return reportHandle.findParameter(parameterDefinition.getName());
	}

	public Collection<DataSetHandle> getDataSetHandles(){
		Iterator it = reportDesign.getDesignHandle().getModuleHandle().getDataSets().iterator();
		ArrayList<DataSetHandle> dshandles = new ArrayList<DataSetHandle>();

		while(it.hasNext()){
			DataSetHandle dsh = (DataSetHandle) it.next();
			dshandles.add(dsh);
		}
		return dshandles;
	}

	private EngineConfig createEngineConfig(){
		EngineConfig config = new EngineConfig();

		return config;
	}
}
