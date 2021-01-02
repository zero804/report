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

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.actions.ShowHideFieldAction;
import net.datenwerke.gxtdto.client.forms.simpleform.conditions.FieldEquals;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCAllowBlank;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCBoolean;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportViewConfiguration;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.ScheduleAsFileDao;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.dto.ScheduleAsFtpFileInformation;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.locale.ScheduleAsFileMessages;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinition;
import net.datenwerke.rs.scheduler.client.scheduler.hooks.ScheduleExportSnippetProviderHook;

public class FtpExportSnippetProvider implements
		ScheduleExportSnippetProviderHook {


	private String isExportAsFileKey;
	private String folderKey;
	private String nameKey;
	
	@Inject
	private ScheduleAsFileDao safDao;
	
	@Override
	public boolean appliesFor(ReportDto report,
			Collection<ReportViewConfiguration> configs) {
		return true;
	}
	
	@Override
	public void configureSimpleForm(final SimpleForm xform, ReportDto report, Collection<ReportViewConfiguration> configs) {
		
		xform.setLabelWidth(0);
		xform.setLabelAlign(LabelAlign.LEFT);
		isExportAsFileKey = xform.addField(Boolean.class, "", new SFFCBoolean() {
			@Override
			public String getBoxLabel() {
				return ScheduleAsFileMessages.INSTANCE.exportToFtpLabel();
			}
		});
		
		xform.setLabelAlign(LabelAlign.TOP);
		xform.beginRow();
		
		folderKey = xform.addField(String.class, ScheduleAsFileMessages.INSTANCE.folder(), new SFFCAllowBlank() {
			@Override
			public boolean allowBlank() {
				return false;
			}
		});
		
		xform.setLabelAlign(LabelAlign.TOP);
		nameKey = xform.addField(String.class, BaseMessages.INSTANCE.propertyName(), new SFFCAllowBlank() {
			@Override
			public boolean allowBlank() {
				return false;
			}
		});
		
		xform.addCondition(isExportAsFileKey, new FieldEquals(true), new ShowHideFieldAction(folderKey));
		xform.addCondition(isExportAsFileKey, new FieldEquals(true), new ShowHideFieldAction(nameKey));
		
	}

	@Override
	public boolean isValid(SimpleForm simpleForm) {
		String folder = (String) simpleForm.getValue(folderKey);
		String name = (String) simpleForm.getValue(nameKey);
		
		return (null != folder && !folder.trim().isEmpty() && null != name && !name.trim().isEmpty());
	}

	@Override
	public void configureConfig(ReportScheduleDefinition configDto, SimpleForm form) {
		if(! isActive(form))
			return;

		ScheduleAsFtpFileInformation info = new ScheduleAsFtpFileInformation();
		info.setName((String) form.getValue(nameKey));
		info.setFolder((String) form.getValue(folderKey));
		
		configDto.addAdditionalInfo(info);
	}

	@Override
	public boolean isActive(SimpleForm simpleForm) {
		return (Boolean) simpleForm.getValue(isExportAsFileKey);
	}

	@Override
	public void loadFields(final SimpleForm form, ReportScheduleDefinition definition, ReportDto report) {
		form.setValue(nameKey, "${now} - " + report.getName());

		if (null != definition) {
			ScheduleAsFtpFileInformation info = definition.getAdditionalInfo(ScheduleAsFtpFileInformation.class);
			if(null != info){
				form.setValue(isExportAsFileKey, true);
				form.setValue(nameKey, info.getName());
				form.setValue(folderKey, info.getFolder());
			} else {
				safDao.getFtpDefaultFolder(new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String result) {
						form.setValue(folderKey, result);
					}
					
					@Override
					public void onFailure(Throwable caught) {
					}
				});
			}
			
		} else {
			safDao.getFtpDefaultFolder(new AsyncCallback<String>() {
				
				@Override
				public void onSuccess(String result) {
					form.setValue(folderKey, result);
				}
				
				@Override
				public void onFailure(Throwable caught) {
				}
			});
		}
		
		form.loadFields();
	}

}
