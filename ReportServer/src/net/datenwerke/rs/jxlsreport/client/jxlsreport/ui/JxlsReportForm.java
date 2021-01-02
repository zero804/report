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
 
 
package net.datenwerke.rs.jxlsreport.client.jxlsreport.ui;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gf.client.upload.FileUploadUiService;
import net.datenwerke.gf.client.upload.dto.UploadProperties;
import net.datenwerke.gf.client.upload.filter.FileUploadFilter;
import net.datenwerke.gxtdto.client.forms.binding.FormBinding;
import net.datenwerke.rs.base.client.reportengines.jasper.locale.JasperMessages;
import net.datenwerke.rs.core.client.datasourcemanager.DatasourceUIService;
import net.datenwerke.rs.core.client.datasourcemanager.provider.annotations.DatasourceTreeNoMondrian;
import net.datenwerke.rs.core.client.reportmanager.ui.forms.AbstractReportForm;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.JxlsReportUiModule;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.dto.JxlsReportDto;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.dto.JxlsReportFileDto;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.dto.pa.JxlsReportDtoPA;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.locale.JxlsReportMessages;

public class JxlsReportForm extends AbstractReportForm {
	
	private final FileUploadUiService fileUploadService;
	
	private final Provider<UITree> datasourceTreeProvider;
	
	private FieldLabel uploadLabel;
	private CheckBox jxlsLegacy;

	private static FileUploadFilter uploadFilter = new FileUploadFilter() {
		@Override
		public String doProcess(String name, long size, String base64) {
			boolean error = null == name || ! (name.toLowerCase().endsWith(".xlsx") || name.toLowerCase().endsWith(".xls") );
			return error ? JasperMessages.INSTANCE.fileMustBeJrxml() : null;
		}
	};
	
	@Inject
	public JxlsReportForm(
		DatasourceUIService datasourceService,
		FileUploadUiService fileUploadService,
		@DatasourceTreeNoMondrian Provider<UITree> datasourceTreeProvider
	) {
		super(datasourceService, datasourceTreeProvider);
		
		/* store objects */
		this.fileUploadService = fileUploadService;
		this.datasourceTreeProvider = datasourceTreeProvider;
	}
	
	@Override
	protected void initializeForm(FormPanel form,
			VerticalLayoutContainer fieldWrapper) {
		super.initializeForm(form, fieldWrapper);
		
		JxlsReportFileDto reportTemplate = ((JxlsReportDto)getSelectedNode()).getReportFile();
		
		UploadProperties uploadProperties = new UploadProperties("jxlsreport", JxlsReportUiModule.UPLOAD_HANDLER_ID);
		uploadProperties.addMetadata(JxlsReportUiModule.UPLOAD_REPORT_ID_FIELD, String.valueOf(getSelectedNode().getId()));
		uploadProperties.setFilter(uploadFilter);
		
		Component uploadComponent = fileUploadService.addCombinedUploadField(uploadProperties);
		
		uploadLabel = new FieldLabel(uploadComponent, JxlsReportMessages.INSTANCE.templateUpload() + (null != reportTemplate ? " (" + reportTemplate.getName() + ")" : ""));
		fieldWrapper.add(uploadLabel);
				
		jxlsLegacy = new CheckBox();
		jxlsLegacy.setName(JxlsReportDtoPA.INSTANCE.jxlsOne().getPath());
		jxlsLegacy.setBoxLabel(JxlsReportMessages.INSTANCE.useLegacyJxls());
		fieldWrapper.add(jxlsLegacy);
		
		configureUnboundFields();
	}
	
	@Override
	protected String getHeader() {
		return JxlsReportMessages.INSTANCE.editReport()  + " ("+getSelectedNode().getId()+")"; //$NON-NLS-1$ //$NON-NLS-2$
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
	protected void configureAutoBinding(FormBinding binding) {
//		binding.addIgnorePathsForAutoBinding(FileUploadUIModule.FIELDNAME_UPLOAD_ID, FileUploadUIModule.UPLOAD_FILE_PREFIX);
	}
	
	@Override
	protected void addDatasourceField(Container container,
			boolean displayConfigFields) {
		datasourceFieldCreator = datasourceService.getSelectionField(container, displayConfigFields, datasourceTreeProvider);
		datasourceFieldCreator.setShouldShowDefaultAdditionalConfig(true);
		datasourceFieldCreator.addSelectionField();
		datasourceFieldCreator.addDisplayDefaultButton();
	}
	
	/**
	 * Set values for the fields not included in the formbinding
	 */
	private void configureUnboundFields(){
		JxlsReportFileDto file = ((JxlsReportDto)getSelectedNode()).getReportFile();
		if(null != file)
			uploadLabel.setText(file.getName());

	}
	


}
