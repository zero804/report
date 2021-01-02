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
 
 
package net.datenwerke.eximport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;

import org.hibernate.FlushMode;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.eximport.exceptions.ImportException;
import net.datenwerke.eximport.hooks.ImporterProviderHook;
import net.datenwerke.eximport.im.ImportConfig;
import net.datenwerke.eximport.im.ImportItemConfig;
import net.datenwerke.eximport.im.ImportResult;
import net.datenwerke.eximport.im.ImportSupervisor;
import net.datenwerke.eximport.im.ImportSupervisorFactory;
import net.datenwerke.eximport.im.Importer;
import net.datenwerke.eximport.obj.EnclosedItemProperty;
import net.datenwerke.eximport.obj.ExportedItem;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

/**
 * 
 *
 */
public class ImportServiceImpl implements ImportService {

	final ExportDataAnalyzerService dataAnalizer;
	private final HookHandlerService hookHandler;
	private final ImportSupervisorFactory importSupervisorFactory;
	private final Provider<EntityManager> entityManagerProvider;
	
	@Inject
	public ImportServiceImpl(
		ExportDataAnalyzerService dataAnalizer,
		HookHandlerService hookHandler,
		ImportSupervisorFactory importSupervisorFactory,
		Provider<EntityManager> entityManagerProvider
		){
		
		/* store objects */
		this.dataAnalizer = dataAnalizer;
		this.hookHandler = hookHandler;
		this.importSupervisorFactory = importSupervisorFactory;
		this.entityManagerProvider = entityManagerProvider;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.datenwerke.eximport.ImportService#importData(net.datenwerke.eximport.im.ImportConfig)
	 */
	@Override
	public ImportResult importData(ImportConfig config) {
		FlushModeType oldFlush = entityManagerProvider.get().getFlushMode();
		entityManagerProvider.get().setFlushMode(FlushModeType.COMMIT);
		
		List<Importer> importers = getImporters();
		 
		 ImportSupervisor supervisor = importSupervisorFactory.create(config, importers);
		 
		 try{
			 ImportResult importedData = supervisor.importData();
			 if(FlushMode.AUTO.equals(oldFlush))
				 entityManagerProvider.get().flush();
			 return importedData;
		 } catch(Exception e){
			 throw new ImportException(e);
		 } finally { 
			 entityManagerProvider.get().setFlushMode(oldFlush);
		 }

	}
	
	protected List<Importer> getImporters() {
		List<Importer> importers = new ArrayList<Importer>();
		
		for(ImporterProviderHook hooker : hookHandler.getHookers(ImporterProviderHook.class))
			importers.add(hooker.getObject());
		
		return importers;
	}

	/*
	 * (non-Javadoc)
	 * @see net.datenwerke.eximport.ImportService#getToBeConfigured(net.datenwerke.eximport.im.ImportConfig)
	 */
	@Override
	public List<ExportedItem> getToBeConfigured(ImportConfig config) {
		Set<String> referencedIds = new HashSet<String>();
		Set<String> configuredIds = new HashSet<String>();
		
		for(ImportItemConfig itemConfig : config.getItemConfigs()){
			String id = itemConfig.getId();
			ExportedItem exportedItem = getExportedItemById(config.getExportDataProvider(), id);
			referencedIds.addAll(exportedItem.getReferencedIDs());
			configuredIds.add(id);
		}
		
		boolean referencesAdded = true;
		while(referencesAdded){
			referencesAdded = false;
			
			Set<String> newReferences = new HashSet<String>();
			for(String id : referencedIds){
				ExportedItem exportedItem = getExportedItemById(config.getExportDataProvider(), id);
				newReferences.addAll(exportedItem.getReferencedIDs());
			}
			
			referencesAdded |= referencedIds.addAll(newReferences);
		}
		
		List<ExportedItem> exportedItems = new ArrayList<ExportedItem>();
		for(String id : referencedIds){
			if(! configuredIds.contains(id)){
				ExportedItem exportedItem = getExportedItemById(config.getExportDataProvider(), id);
				exportedItems.add(exportedItem);
			}
		}
		
		return exportedItems;
	}

	/*
	 * (non-Javadoc)
	 * @see net.datenwerke.eximport.ImportService#getExportedItemById(net.datenwerke.eximport.ExportDataProvider, java.lang.String)
	 */
	@Override
	public ExportedItem getExportedItemById(ExportDataProvider dataProvider, String id) {
		ExportedItem exportedItem = null;

		try {
			exportedItem = dataAnalizer.getExportedItemById(dataProvider, id);
		} catch (ClassNotFoundException e) {
			throw new ImportException("Could not load exported item", e);
		}
		
		if(null == exportedItem)
			throw new ImportException("Could not find exported item with id " + id);
			
		return exportedItem;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.datenwerke.eximport.ImportService#getEnclosedItemPropertyForId(net.datenwerke.eximport.ExportDataProvider, java.lang.String)
	 */
	@Override
	public EnclosedItemProperty getEnclosedItemPropertyForId(ExportDataProvider dataProvider, String id){
		EnclosedItemProperty exportedItem = null;
		try {
			exportedItem = dataAnalizer.getEnclosedPropertyFor(dataProvider, id);
		} catch (ClassNotFoundException e) {
			throw new ImportException("Could not load exported item", e);
		}
		
		if(null == exportedItem)
			throw new ImportException("Could not find exported item with id " + id);
			
		return exportedItem;
	}
}
