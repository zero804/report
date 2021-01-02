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
 
 
package net.datenwerke.rs.base.client.reportengines.table.previewenhancer;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.http.client.URL;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.utilityservices.UtilsUIService;
import net.datenwerke.gxtdto.client.utils.StringEscapeUtils;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.decorator.TableReportDtoDec;
import net.datenwerke.rs.base.client.reportengines.table.hooks.TableReportPreviewCellEnhancerHook;
import net.datenwerke.rs.base.client.reportengines.table.ui.TableReportPreviewView;
import net.datenwerke.rs.core.client.reportexecutor.locale.ReportexecutorMessages;

public class LinkToEnhancer implements TableReportPreviewCellEnhancerHook {

	private final UtilsUIService utilsService;
	
	@Inject
	public LinkToEnhancer(
		UtilsUIService utilsService
		){
		
		/* store objects */
		this.utilsService = utilsService;
	}
	
	@Override
	public boolean consumes(TableReportDto report, ColumnDto column, String value, String rawValue, String[] rowValues) {
		return null != column.getSemanticType() && column.getSemanticType().toLowerCase().startsWith("linkto|");
	}

	@Override
	public boolean enhanceMenu(TableReportPreviewView tableReportPreviewView, Menu menu, TableReportDto report, final ColumnDto column, final String value, String rawValue, String[] rowValues) {
		MenuItem lookupItem = new DwMenuItem(ReportexecutorMessages.INSTANCE.linktomsg());
		menu.add(lookupItem);
		
		String semanticType = column.getSemanticType();
		String url = semanticType.substring(7, semanticType.length());
		url = StringEscapeUtils.unescapeXml(url);
		
		url = replaceValues(report, url, value, rowValues);
		
		final String target = url;
		
		lookupItem.addSelectionHandler(new SelectionHandler<Item>() {
			
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				utilsService.redirectInPopup(target);
			}
		});
		
		return false;
	}

	private String replaceValues(TableReportDto report, String url, String value, String[] rowValues) {
		String replacementUrl = url.replace("${_value}", URL.encode(value));
		
		int startIndex = url.indexOf("${");
		int endIndex = url.indexOf("}");
		while(startIndex >= 0) {
		     String colName = url.substring(startIndex+2, endIndex);
		     ColumnDto col = ((TableReportDtoDec)report).getVisibleColumnByName(colName);
		     if (null != col) {
		    	 int colIndex = ((TableReportDtoDec)report).getVisibleColumnPositionByName(colName);
		    	 replacementUrl = replacementUrl.replace("${" + colName + "}", URL.encode(rowValues[colIndex]));
		     } 
		     startIndex = url.indexOf("${", endIndex+1);
		     endIndex = url.indexOf("}", endIndex+1);
		}
		
		return replacementUrl;
	}

}
