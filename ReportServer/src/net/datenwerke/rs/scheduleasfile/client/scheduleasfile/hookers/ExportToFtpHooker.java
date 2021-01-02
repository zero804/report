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
 
 
package net.datenwerke.rs.scheduleasfile.client.scheduleasfile.hookers;

import java.util.Collection;
import java.util.Map;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.DefaultInfoConfig;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.info.InfoConfig;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwAlertMessageBox;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCAllowBlank;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.servercommunication.callback.NotamCallback;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.helper.simpleform.ExportTypeSelection;
import net.datenwerke.rs.core.client.helper.simpleform.config.SFFCExportTypeSelector;
import net.datenwerke.rs.core.client.reportexecutor.hooks.PrepareReportModelForStorageOrExecutionHook;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportExecutorInformation;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportExecutorMainPanel;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportViewConfiguration;
import net.datenwerke.rs.core.client.reportexporter.hooks.ExportExternalEntryProviderHook;
import net.datenwerke.rs.core.client.reportexporter.locale.ReportExporterMessages;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.eximport.client.eximport.locale.ExImportMessages;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.ScheduleAsFileDao;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.StorageType;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.locale.ScheduleAsFileMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

public class ExportToFtpHooker implements ExportExternalEntryProviderHook {

	@Inject
	private ScheduleAsFileDao safDao;
	
	@Inject
	private HookHandlerService hookHandler;
	
	@Override
	public void getMenuEntry(final Menu menu, final ReportDto report,
			final ReportExecutorInformation info, final ReportExecutorMainPanel mainPanel) {
		safDao.getStorageEnabledConfigs(new AsyncCallback<Map<StorageType,Boolean>>() {

			@Override
			public void onSuccess(Map<StorageType, Boolean> result) {
				if (result.get(StorageType.FTP)) {
					MenuItem item = new DwMenuItem(ScheduleAsFileMessages.INSTANCE.exportToFtpLabel(), BaseIcon.GROUP_EDIT);
					menu.add(item);
					item.addSelectionHandler(new SelectionHandler<Item>() {
						
						@Override
						public void onSelection(SelectionEvent<Item> event) {
							displayExportDialog(report, info, mainPanel.getViewConfigs());
						}
					});
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}


	protected void displayExportDialog(final ReportDto report,
			final ReportExecutorInformation info, Collection<ReportViewConfiguration> configs) {
		final DwWindow window = new DwWindow();
		window.setHeaderIcon(BaseIcon.GROUP_EDIT);
		window.setHeading(ScheduleAsFileMessages.INSTANCE.exportToFtpLabel());
		window.setWidth(500);
		window.setHeight(310);
		
		/* configure form */
		final SimpleForm form = SimpleForm.getInlineInstance();
		
		final String formatKey = form.addField(ExportTypeSelection.class, ScheduleAsFileMessages.INSTANCE.exportTypeLabel(), new SFFCExportTypeSelector() {
			@Override
			public ReportDto getReport() {
				return report;
			}
			
			@Override
			public String getExecuteReportToken(){
				return info.getExecuteReportToken();
			}
		});
		
		final String folderKey = form.addField(String.class, ScheduleAsFileMessages.INSTANCE.folder(), new SFFCAllowBlank() {
			@Override
			public boolean allowBlank() {
				return false;
			}
		});

		final String nameKey = form.addField(String.class, ScheduleAsFileMessages.INSTANCE.nameLabel(),new SFFCAllowBlank() {
			@Override
			public boolean allowBlank() {
				return false;
			}
		});
		
		window.add(form, new MarginData(10));
		
		/* set properties */
		form.setValue(nameKey, report.getName());
		
		safDao.getFtpDefaultFolder(new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				form.setValue(folderKey, result);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});
		
		/* load fields */
		form.loadFields();
		
		window.addCancelButton();

		DwTextButton submitBtn = new DwTextButton(BaseMessages.INSTANCE.submit());
		submitBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if(! form.isValid())
					return;
				
				String name = ((String) form.getValue(nameKey)).trim();
				String folder = ((String) form.getValue(folderKey)).trim();
				
				ExportTypeSelection type = (ExportTypeSelection) form.getValue(formatKey);
				
				if(!type.isConfigured()){
					new DwAlertMessageBox(BaseMessages.INSTANCE.error(), ReportExporterMessages.INSTANCE.exportTypeNotConfigured()).show();
					return;
				}
				
				for(PrepareReportModelForStorageOrExecutionHook hooker : hookHandler.getHookers(PrepareReportModelForStorageOrExecutionHook.class)){
					if(hooker.consumes(report)){
						hooker.prepareForExecutionOrStorage(report, info.getExecuteReportToken());
					}
				}
				
				try{
					InfoConfig infoConfig = new DefaultInfoConfig(ExImportMessages.INSTANCE.quickExportProgressTitle(), ExImportMessages.INSTANCE.exportWait());
					infoConfig.setWidth(350);
					infoConfig.setDisplay(3500);
					Info.display(infoConfig);
				}catch(Exception e){}
				
				safDao.exportIntoFtp(report, info.getExecuteReportToken(), type.getOutputFormat(), type.getExportConfiguration(), name, folder, new NotamCallback<Void>(ScheduleAsFileMessages.INSTANCE.exportedIntoFtp()));
				
				window.hide();
			}
		});
		window.addButton(submitBtn);
		
		window.show();
	}

}
