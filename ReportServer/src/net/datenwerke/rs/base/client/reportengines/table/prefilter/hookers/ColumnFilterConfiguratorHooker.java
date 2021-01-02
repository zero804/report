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
 
 
package net.datenwerke.rs.base.client.reportengines.table.prefilter.hookers;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.forms.selection.SelectionMode;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.base.client.reportengines.table.TableReportUtilityDao;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnFilterDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.FilterSpecDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.decorator.ColumnDtoDec;
import net.datenwerke.rs.base.client.reportengines.table.dto.decorator.ColumnFilterDtoDec;
import net.datenwerke.rs.base.client.reportengines.table.helpers.ColumnFilterWindow;
import net.datenwerke.rs.base.client.reportengines.table.helpers.ColumnSelector;
import net.datenwerke.rs.base.client.reportengines.table.prefilter.hooks.PreFilterConfiguratorHook;
import net.datenwerke.rs.base.client.reportengines.table.prefilter.locale.PreFilterMessages;
import net.datenwerke.rs.base.client.reportengines.table.prefilter.propertywidgets.PreFilterView;
import net.datenwerke.rs.base.client.reportengines.table.prefilter.propertywidgets.PreFilterView.EditPreFilterCallback;
import net.datenwerke.rs.base.client.reportengines.table.prefilter.propertywidgets.PreFilterView.InstantiatePreFilterCallback;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;

public class ColumnFilterConfiguratorHooker implements
		PreFilterConfiguratorHook {

	class ExtendedColumnSelector extends ColumnSelector{
		
		private InstantiatePreFilterCallback callback;
		private ColumnFilterDto filter;
		
		public ExtendedColumnSelector(
				TableReportUtilityDao tableReportUtilityDao,
				TableReportDto report, 
				String executeToken
				) {
			super(tableReportUtilityDao, report, executeToken);
		}
		
		public void setCallback(InstantiatePreFilterCallback callback){
			this.callback = callback;
		}
		
		public void setFilter(ColumnFilterDto filter){
			this.filter = filter;
		}
		
		@Override
		protected void itemsSelected(List<ColumnDto> selectedItems) {
			if(null == selectedItems || selectedItems.isEmpty()){
				callback.filterInstantiated(null);
				return;
			}
			
			filter.setColumn(((ColumnDtoDec)selectedItems.get(0)).cloneColumnForSelection());
			callback.filterInstantiated(filter);
		}
		
		@Override
		protected void cancelSelected() {
			super.cancelSelected();
			callback.filterInstantiated(null);
		}
		
	};
	
	private final TableReportUtilityDao tableReportUtilityDao;
	private ExtendedColumnSelector selector;
	
	@Inject
	public ColumnFilterConfiguratorHooker(
		TableReportUtilityDao tableReportUtilityDao	
		){
	
		/* store objects */
		this.tableReportUtilityDao = tableReportUtilityDao;
	}
	
	@Override
	public String getHeadline() {
		return PreFilterMessages.INSTANCE.columnFilterHeadline();
	}

	@Override
	public ImageResource getIcon() {
		return BaseIcon.FILTER.toImageResource();
	}

	@Override
	public void instantiateFilter(TableReportDto report, String executeToken, final PreFilterView.InstantiatePreFilterCallback callback) {
		final ColumnFilterDto filter =  new ColumnFilterDtoDec();
		
		if(null == selector){
			selector = new ExtendedColumnSelector(tableReportUtilityDao, report, executeToken);
			selector.setSelectionMode(SelectionMode.SINGLE);
			selector.loadData();
		} 

		selector.setOnEsc(false);
		selector.setSelectedItems(new ArrayList<ColumnDto>());
		selector.setCallback(callback);
		selector.setFilter(filter);
		
		selector.show();
	}

	@Override
	public boolean consumes(FilterSpecDto item) {
		return item instanceof ColumnFilterDto;
	}

	@Override
	public void displayFilter(TableReportDto report, FilterSpecDto filter, String executeToken, final EditPreFilterCallback callback) {
		ColumnFilterWindow mfilter = new ColumnFilterWindow(report, ((ColumnFilterDto)filter).getColumn(), executeToken, false, false){
			protected void onHide() {
				super.onHide();
				callback.editDone();
			}
		};
		mfilter.show();
	}
	
	@Override
	public void filterInstantiated(TableReportDto report, FilterSpecDto filter,
			String executeToken, EditPreFilterCallback callback) {
		displayFilter(report, filter, executeToken, callback);
	}

}
