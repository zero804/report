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
 
 
package net.datenwerke.rs.base.client.reportengines.table.columnfilter.hookers;

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwToggleButton;
import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.client.eventbus.events.ObjectChangedEvent;
import net.datenwerke.gxtdto.client.eventbus.handlers.ObjectChangedEventHandler;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.hooks.FilterViewEnhanceToolbarHook;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.locale.FilterMessages;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.propertywidgets.FilterView;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.decorator.TableReportDtoDec;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.inject.Inject;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonArrowAlign;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonScale;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class ToolbarEnhancerEditFilter implements FilterViewEnhanceToolbarHook {

	private final ToolbarService toolbarService;

	private FilterView filterView;
	private TableReportDto report;
	
	@Inject
	public ToolbarEnhancerEditFilter(
		ToolbarService toolbarService	
		){
		
		/* store obejcts */
		this.toolbarService = toolbarService;
	}
	
	@Override
	public void initialize(FilterView filterView, TableReportDto report) {
		this.filterView = filterView;
		this.report = report;
	}
	
	@Override
	public void enhanceToolbarLeft(ToolBar toolbar) {
		/* */
		DwTextButton editFormat = toolbarService.createSmallButtonLeft(FilterMessages.INSTANCE.editColumnFormat(), BaseIcon.FORMAT);
		editFormat.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				filterView.displayColumnFormatDialog();
			}
		});
		toolbar.add(editFormat);
		
		
		/* display filter dialog */
		DwTextButton editFilter = toolbarService.createSmallButtonLeft(FilterMessages.INSTANCE.editFilter(), BaseIcon.FILTER);
		editFilter.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				filterView.displayFilterDialog();
			}
		});
		toolbar.add(editFilter);

		
		/* set distinct */
		final CheckBox distinctCheckBox = new CheckBox();
		distinctCheckBox.setBoxLabel(FilterMessages.INSTANCE.distinctFilter());
		distinctCheckBox.setValue(report.isDistinctFlag());
		distinctCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				report.setDistinctFlag(distinctCheckBox.getValue());
			}
		});

		if(((TableReportDtoDec)report).hasAggregateColumn())
			distinctCheckBox.disable();

		toolbar.add(distinctCheckBox);
		
		/* */
		final DwToggleButton editSubtotals = new DwToggleButton(FilterMessages.INSTANCE.editSubtotals());
		editSubtotals.setIcon(BaseIcon.SUBTOTALS);
		editSubtotals.setIconAlign(IconAlign.LEFT);
		editSubtotals.setArrowAlign(ButtonArrowAlign.RIGHT);
		editSubtotals.setScale(ButtonScale.SMALL);
		editSubtotals.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				filterView.displaySubtotalsDialog();
			}
		});
		if(report.isEnableSubtotals())
			editSubtotals.setValue(true);
		toolbar.add(editSubtotals);
		
		report.addInstanceChangedHandler(new ObjectChangedEventHandler<Dto>() {
			
			@Override
			public void onObjectChangedEvent(ObjectChangedEvent<Dto> event) {
				if(((TableReportDtoDec)report).hasAggregateColumn()){
					distinctCheckBox.setValue(false, true);
					if(report.isDistinctFlag())
						report.setDistinctFlag(false);
					distinctCheckBox.disable();
				} else
					distinctCheckBox.enable();
				
				boolean silent = report.isSilenceEvents();
				report.silenceEvents(true);
				if(((TableReportDtoDec)report).hasSubtotalGroupColumn()){
					report.setEnableSubtotals(true);
					editSubtotals.setValue(true, true);
				} else {
					report.setEnableSubtotals(false);
					editSubtotals.setValue(false, true);
				}
				report.silenceEvents(silent);
			}
		});
		
	}

	@Override
	public void enhanceToolbarRight(ToolBar toolbar) {

	}

}
