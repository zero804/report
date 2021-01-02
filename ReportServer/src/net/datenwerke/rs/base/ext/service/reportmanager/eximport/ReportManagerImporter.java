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
 
 
package net.datenwerke.rs.base.ext.service.reportmanager.eximport;

import java.util.HashSet;
import java.util.Set;

import com.google.inject.Inject;

import net.datenwerke.eximport.exceptions.ImportException;
import net.datenwerke.eximport.obj.EnclosedItemProperty;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.interfaces.ReportVariant;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeImportItemConfig;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeImporter;
import net.datenwerke.treedb.service.treedb.AbstractNode;
import net.datenwerke.treedb.service.treedb.TreeDBManager;

/**
 * 
 *
 */
public class ReportManagerImporter extends TreeNodeImporter {

	public static final String IMPORTER_ID = "ReportManagerImporter";
	
	private final ReportService reportService;
	
	@Inject
	public ReportManagerImporter(
		ReportService reportService
		){
		
		/* store objects */
		this.reportService = reportService;
	}
	
	@Override
	public Class<?>[] getRecognizedExporters() {
		return new Class<?>[]{ReportManagerExporter.class};
	}

	@Override
	protected TreeDBManager getTreeDBManager() {
		return reportService;
	}

	@Override
	public String getId() {
		return IMPORTER_ID;
	}
	
	@Override
	protected void finishUpImportCreateMode(
			TreeNodeImportItemConfig itemConfig, AbstractNode<?> node) {
		super.finishUpImportCreateMode(itemConfig, node);
	
		/* check key */
		if(node instanceof Report){
			String key = ((Report)node).getKey();
			if(null != key){
				Report reportByKey = reportService.getReportByKey(key);
				if(null != reportByKey){
					if(itemConfig instanceof ImportReportItemConfig && ((ImportReportItemConfig)itemConfig).isCleanKeys())
						 ((Report)node).setKey(null);
					else
						throw new IllegalStateException("A report with key " + key + " already exists");
				}
			}
		}
		
		/* if we have a variant make sure we get the definitions for the instances right */
		if(node instanceof ReportVariant){
			if(null == itemConfig.getParent())
				return; // everything should be ok
			
			Report parentReport = (Report) node.getParent();
			
			/* parameters */
			Report variant = (Report) node;
			
			if(parentReport.getParameterDefinitions().size() < variant.getParameterInstances().size())
				throw new ImportException("New parent's parameters are not a superset of the old parent (parentId=" + parentReport.getId() + ", variantName=" + variant.getName() + ")");
			
			Set<Long> handledParameters = new HashSet<Long>(); 
			for(ParameterInstance<?> instance : variant.getParameterInstances()){
				String importId = importSupervisor.getImportIdForImportedObject(instance);
				EnclosedItemProperty exItem = importSupervisor.getEnclosedItemPropertyForId(importId);
				String key = exItem.getElement().getAttribute(ParameterInstanceExporter.KEY_ATTRIBUTE).getValue();
				ParameterDefinition<?> definition = parentReport.getParameterDefinitionByKey(key);
				if(null == definition)
					throw new ImportException("New parent's parameters are not a superset of the old parent (parentId=" + parentReport.getId() + ", variantName=" + variant.getName() + ")");
				
				if(!( definition.createParameterInstance().getClass().isAssignableFrom(instance.getClass()))){
					throw new ImportException("ParameterInstance and definitiontype do not match between variant and parent (parentId=" + parentReport.getId() + ", variantName=" + variant.getName() + ")");
				}
				
				instance.setDefinition(definition);
				handledParameters.add(definition.getId());
			}
			
			for(ParameterDefinition<?> def : parentReport.getParameterDefinitions()){
				if(handledParameters.contains(def.getId()))
					continue;
				
				ParameterInstance<?> instance = def.createParameterInstance();
				variant.addParameterInstance(instance);
			}
		}
	}
}
