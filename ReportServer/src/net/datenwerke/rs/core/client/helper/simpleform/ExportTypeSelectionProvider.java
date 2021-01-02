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
 
 
package net.datenwerke.rs.core.client.helper.simpleform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHookImpl;
import net.datenwerke.rs.core.client.helper.simpleform.config.SFFCExportTypeSelector;
import net.datenwerke.rs.core.client.reportexporter.ReportExporterUIService;
import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;
import net.datenwerke.rs.core.client.reportexporter.exporter.ReportExporter;
import net.datenwerke.rs.core.client.reportexporter.exporter.ReportExporter.ConfigurationFinishedCallback;
import net.datenwerke.rs.core.client.reportexporter.locale.ReportExporterMessages;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.ToggleGroup;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.Radio;

public class ExportTypeSelectionProvider extends FormFieldProviderHookImpl {

	private final ReportExporterUIService reportExporterService;
	
	private ToggleGroup exportTypeGroup;
	private Map<Radio, ReportExporter> exporterMap;
	
	private List<ReportExecutionConfigDto> exporterConfig;

	private Object model;

	private ValueProvider vp;
	

	@Inject
	public ExportTypeSelectionProvider(
		ReportExporterUIService reportExporterService	
		){
		
		/* store obejcts */
		this.reportExporterService = reportExporterService;
	}
	
	@Override
	public boolean doConsumes(Class<?> type,
			SimpleFormFieldConfiguration... configs) {
		return ExportTypeSelection.class.equals(type) && null != configs && configs.length > 0 && configs[0] instanceof SFFCExportTypeSelector;
	}
	
	@Override
	public Widget createFormField() {
		final SFFCExportTypeSelector config = (SFFCExportTypeSelector) configs[0];
		
		exportTypeGroup = new ToggleGroup();
		
		final DwTextButton formatConfigBtn = new DwTextButton(ReportExporterMessages.INSTANCE.formatConfigLabel());
		
		List<ReportExporter> exporters = reportExporterService.getCleanedUpAvailableExporters(config.getReport());
		exporterMap = new HashMap<Radio, ReportExporter>();
		boolean first = true;
		
		final HorizontalLayoutContainer radioContainer = new HorizontalLayoutContainer();
		
		for(final ReportExporter exporter : exporters){
			if(! exporter.canBeScheduled())
				continue;
			
			String name = exporter.getExportTitle();

			final Radio radio = new Radio();
			radio.setName("exportFormat"); //$NON-NLS-1$
			radio.setData("rs_outputFormat", exporter.getOutputFormat()); //$NON-NLS-1$
			radio.setBoxLabel(name);
			radio.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					if(radio.getValue()){
						if(exporter.hasConfiguration())
							formatConfigBtn.enable();
						else
							formatConfigBtn.disable();
					}
				}
			});
			
			exportTypeGroup.add(radio);
			radioContainer.add(radio);
			exporterMap.put(radio, exporter);
			
			if(first){
				first = false;
				radio.setValue(true, true);
			}
		}
		
		/* selection listener for extra config */
		formatConfigBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				Radio radio = (Radio) exportTypeGroup.getValue();
				if(null != radio && exporterMap.containsKey(radio)){
					final ReportExporter exporter = exporterMap.get(radio);
					exporter.displayConfiguration(config.getReport(), config.getExecuteReportToken(), false, new ConfigurationFinishedCallback() {
						
						@Override
						public void success() {
							exporterConfig = exporter.getConfiguration();
							
							updateModel();
						}

					});
				}
			}
		});
		
		VerticalLayoutContainer wrapper = new VerticalLayoutContainer();
		wrapper.add(radioContainer, new VerticalLayoutData(1,30));
		wrapper.add(formatConfigBtn);
		
		return wrapper;
	}

	@Override
	public void addFieldBindings(Object model, ValueProvider vp, Widget field) {
		this.model = model;
		this.vp = vp;
	}
	
	@Override
	public Object getValue(Widget field) {
		return getValue();
	}
	
	private Object getValue() {
		String outputFormat = ((Radio)exportTypeGroup.getValue()).getData("rs_outputFormat");
		
		ExportTypeSelection selection = new ExportTypeSelection();
		selection.setOutputFormat(outputFormat);
		if(null == exporterConfig) 
			selection.setExportConfiguration(new ArrayList<ReportExecutionConfigDto>());
		else 
			selection.setExportConfiguration(exporterConfig);
		
		ReportExporter exporter = exporterMap.get(exportTypeGroup.getValue());
		selection.setConfigured(exporter.isConfigured());
		
		return selection;
	}

	private void updateModel() {
		if(null != model && null != vp)
			vp.setValue(model, getValue());
	}
}
