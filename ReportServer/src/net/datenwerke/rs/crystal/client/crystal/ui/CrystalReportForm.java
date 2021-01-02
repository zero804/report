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
 
 
package net.datenwerke.rs.crystal.client.crystal.ui;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gf.client.upload.FileUploadUiService;
import net.datenwerke.gf.client.upload.dto.UploadProperties;
import net.datenwerke.rs.core.client.datasourcemanager.DatasourceUIService;
import net.datenwerke.rs.core.client.datasourcemanager.provider.annotations.DatasourceTreeNoMondrian;
import net.datenwerke.rs.core.client.reportmanager.ui.forms.AbstractReportForm;
import net.datenwerke.rs.crystal.client.crystal.CrystalUiModule;
import net.datenwerke.rs.crystal.client.crystal.dto.CrystalReportDto;
import net.datenwerke.rs.crystal.client.crystal.dto.CrystalReportFileDto;
import net.datenwerke.rs.crystal.client.crystal.locale.CrystalMessages;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.locale.JxlsReportMessages;

public class CrystalReportForm extends AbstractReportForm {
	
	private final FileUploadUiService fileUploadService;
	private final Provider<UITree> datasourceTreeProvider;
	
	private FieldLabel uploadLabel;
	
	@Inject
	public CrystalReportForm(
		DatasourceUIService datasourceService, 
		FileUploadUiService fileUploadService,
		@DatasourceTreeNoMondrian Provider<UITree> datasourceTreeProvider
	) {
		super(datasourceService, datasourceTreeProvider);
		this.fileUploadService = fileUploadService;
		this.datasourceTreeProvider = datasourceTreeProvider;
	}
	
	@Override
	protected void initializeForm(FormPanel form, VerticalLayoutContainer fieldWrapper) {
		super.initializeForm(form, fieldWrapper);
		CrystalReportFileDto reportfile = ((CrystalReportDto)getSelectedNode()).getReportFile();
		
		UploadProperties uploadProperties = new UploadProperties("crystalreport", CrystalUiModule.UPLOAD_HANDLER_ID);
		uploadProperties.addMetadata(CrystalUiModule.UPLOAD_REPORT_ID_FIELD, String.valueOf(getSelectedNode().getId()));
		
		Component uploadComponent = fileUploadService.addCombinedUploadField(uploadProperties);
		
		uploadLabel = new FieldLabel(uploadComponent, JxlsReportMessages.INSTANCE.templateUpload() + (null != reportfile ? " (" + reportfile.getName() + ")" : ""));
		fieldWrapper.add(uploadLabel);
		
		configureUnboundFields();
	}
	
	/**
	 * Set values for the fields not included in the formbinding
	 */
	private void configureUnboundFields(){
		CrystalReportFileDto file = ((CrystalReportDto)getSelectedNode()).getReportFile();
		if(null != file)
			uploadLabel.setText(file.getName());

	}
	
	@Override
	protected String getHeader() {
		return CrystalMessages.INSTANCE.editReport()  + " ("+getSelectedNode().getId()+")"; //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	
	@Override
	protected boolean isUploadForm() {
		return true;
	}
	
	@Override
	protected String getFormAction() {
		return fileUploadService.getFormAction();
	}

	@Override
	protected boolean isDisplayOptionalAdditionalConfigFieldsForDatasource() {
		return false;
	}

	@Override
	protected void addDatasourceField(Container container, boolean displayOptionalConfigFields) {
		datasourceFieldCreator = datasourceService.getSelectionField(container, displayOptionalConfigFields, datasourceTreeProvider);
		datasourceFieldCreator.addSelectionField();
		datasourceFieldCreator.addDisplayDefaultButton();
	}
}
