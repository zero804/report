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
 
 
package net.datenwerke.treedb.ext.service.eximport.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.datenwerke.eximport.ExportDataAnalyzerService;
import net.datenwerke.eximport.ExportService;
import net.datenwerke.eximport.ex.Exporter;
import net.datenwerke.eximport.obj.ExportedItem;
import net.datenwerke.eximport.obj.ReferenceItemProperty;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.eximport.service.eximport.im.http.HttpImportService;
import net.datenwerke.treedb.ext.client.eximport.im.dto.ImportTreeModel;

import com.google.inject.Provider;

public class HttpTreeImportServiceImpl implements HttpTreeImportService {

	private final DtoService dtoService;
	private final ExportDataAnalyzerService analizerService;
	private final Provider<HttpImportService> httpImportService;
	private final ExportService exportService;
	
	@Inject
	public HttpTreeImportServiceImpl(DtoService dtoService,
			ExportDataAnalyzerService analizerService,
			Provider<HttpImportService> httpImportService,
			ExportService exportService) {
		
		/* store objects */
		this.dtoService = dtoService;
		this.analizerService = analizerService;
		this.httpImportService = httpImportService;
		this.exportService = exportService;
	}

	@Override
	public List<ImportTreeModel> loadTreeDto(Class<? extends Exporter> exporterType) {
		/* get items for exporter */
		List<ExportedItem> items = new ArrayList<ExportedItem>(); 
		try {
			items.addAll(
				analizerService.getExportedItemsFor(httpImportService.get().getCurrentConfig().getImportData(), exporterType)
			);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		/* prepare return list */
		Map<String, ImportTreeModel> lookupMap = new HashMap<String, ImportTreeModel>();
		
		for(ExportedItem item : items){
			ImportTreeModel model = new ImportTreeModel();
			
			model.setId(item.getId());
			Class<?> type = item.getType();
			Object dto = dtoService.instantiateDto(type);
			if(null != dto)
				model.setType(dto.getClass().getName());
			else
				model.setType(type.getName());
			
			model.setName(exportService.getExporterFor(exporterType).getDisplayNameFor(item));
			
			ReferenceItemProperty parentProperty = (ReferenceItemProperty) item.getPropertyByName("parent");
			if(null != parentProperty){
				String parentId = parentProperty.getReferenceId();
				model.setParentId(parentId);
			}
			
			lookupMap.put(model.getId(), model);
		}
		
		/* build tree */
		List<ImportTreeModel> tree = new ArrayList<ImportTreeModel>();
		
		/* get roots and build tree*/
		for(ImportTreeModel model : lookupMap.values()){
			String parentId = model.getParentId();
			if(null == parentId || "".equals(parentId) || ! lookupMap.containsKey(parentId))
				tree.add(model);
			else {
				ImportTreeModel parent = lookupMap.get(parentId);
				parent.addChild(model);
			}
		}
		
		return tree;
	}
}
