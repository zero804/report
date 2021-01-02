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
 
 
package net.datenwerke.rs.eximport.service.eximport.im.http;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.datenwerke.eximport.ExportDataAnalyzerService;
import net.datenwerke.eximport.ExportDataProviderImpl;
import net.datenwerke.eximport.ExportService;
import net.datenwerke.eximport.ex.Exporter;
import net.datenwerke.eximport.exceptions.InvalidImportDocumentException;
import net.datenwerke.eximport.im.ImportConfig;
import net.datenwerke.eximport.im.ImportItemConfig;
import net.datenwerke.eximport.im.ImportResult;
import net.datenwerke.eximport.obj.ExportedItem;
import net.datenwerke.gf.service.tempfile.TempFileService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.eximport.client.eximport.im.dto.ImportConfigDto;
import net.datenwerke.rs.eximport.client.eximport.im.dto.ImportPostProcessConfigDto;
import net.datenwerke.rs.eximport.service.eximport.im.http.hooks.HttpImportConfigurationProviderHook;
import net.datenwerke.rs.eximport.service.eximport.im.http.hooks.HttpImportPostProcessProviderHook;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.servlet.SessionScoped;

/**
 * 
 *
 */
@SessionScoped
public class HttpImportServiceImpl implements HttpImportService {

	protected final DtoService dtoService;
	protected final ExportDataAnalyzerService analizerService;
	protected final ExportService exportService;
	protected final HookHandlerService hookHandler;
	protected final Provider<HttpImportConfiguration> configurationProvider;
	protected final TempFileService tempFileService;
	protected HttpImportConfiguration currentConfiguration;
	
	@Inject
	public HttpImportServiceImpl(
		DtoService dtoService,
		ExportDataAnalyzerService analizerService,
		ExportService exportService,
		HookHandlerService hookHandler,
		Provider<HttpImportConfiguration> configurationProvider,
		TempFileService tempFileService
		){
		
		/* store objects */
		this.dtoService = dtoService;
		this.analizerService = analizerService;
		this.exportService = exportService;
		this.hookHandler = hookHandler;
		this.configurationProvider = configurationProvider;
		this.tempFileService = tempFileService;
	}
	
	@Override
	public HttpImportConfiguration createNewConfig(){
		currentConfiguration = configurationProvider.get();
		return currentConfiguration;
	}
	
	@Override
	public boolean hasCurrentConfig(){
		return null != currentConfiguration;
	}

	@Override
	public HttpImportConfiguration getCurrentConfig(){
		return currentConfiguration;
	}
	
	@Override
	public void invalidateCurrentConfig(){
		currentConfiguration = null;
	}

	@Override
	public void setData(String xmldata) throws InvalidImportDocumentException {
		if(! hasCurrentConfig())
			throw new IllegalArgumentException("Do not have config");
		
		final File xmlFile;
		try {
			xmlFile = tempFileService.createTempFile();
			FileWriter writer = new FileWriter(xmlFile);
			writer.write(xmldata);
			writer.close();
			
			currentConfiguration.setImportData(new ExportDataProviderImpl(xmldata.getBytes()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<String> getInvolvedExporterIds() {
		if(! hasCurrentConfig())
			throw new IllegalArgumentException("Do not have config");
		
		try{
			Collection<Class<?>> exporters = analizerService.getExporterClasses(getCurrentConfig().getImportData());
			return exportService.getExporterIds(exporters);
		} catch(Exception e){
			throw new IllegalArgumentException("Could not load exporters", e);
		}
	}

	@Override
	public void resetImportConfig() {
		if(! hasCurrentConfig())
			throw new IllegalArgumentException("Do not have config");
			
		ImportConfig importConfig = new ImportConfig(currentConfiguration.getImportData());
		getCurrentConfig().setImportConfig(importConfig);
	}
	

	@Override
	public void addItemConfig(ImportItemConfig nodeConfig) {
		if(! hasCurrentConfig())
			throw new IllegalArgumentException("Do not have config");
		
		ImportConfig importConfig = currentConfiguration.getImportConfig();
		if(null == importConfig)
			throw new IllegalArgumentException("Storing of configs was not propertly started.");
		
		importConfig.addItemConfig(nodeConfig);
	}

	@Override
	public void configureImport(Map<String, ImportConfigDto> configMap) {
		Collection<HttpImportConfigurationProviderHook> configProviders = hookHandler.getHookers(HttpImportConfigurationProviderHook.class);
		
		for(Entry<String, ImportConfigDto> entry : configMap.entrySet()){
			for(HttpImportConfigurationProviderHook provider : configProviders){
				if(provider.consumes(entry.getKey())){
					provider.validate(entry.getValue());
					provider.configureImport(entry.getValue());
					break;
				}
			}
		}
	}

	@Override
	public List<ExportedItem> getExportedItemsFor(
			Class<? extends Exporter> exporterType) throws ClassNotFoundException {
		if(! hasCurrentConfig())
			throw new IllegalArgumentException("Do not have config");
		
		return analizerService.getExportedItemsFor(currentConfiguration.getImportData(), exporterType);
	}

	@Override
	public void runPostProcess(Map<String, ImportPostProcessConfigDto> configMap,
			ImportResult result) {
		Collection<HttpImportPostProcessProviderHook> postProcessProviders = hookHandler.getHookers(HttpImportPostProcessProviderHook.class);
		
		for(Entry<String, ImportPostProcessConfigDto> entry : configMap.entrySet()){
			for(HttpImportPostProcessProviderHook provider : postProcessProviders){
				if(provider.consumes(entry.getKey())){
					provider.postProcess(entry.getValue(), result);
					break;
				}
			}
		}
	}

	
}
