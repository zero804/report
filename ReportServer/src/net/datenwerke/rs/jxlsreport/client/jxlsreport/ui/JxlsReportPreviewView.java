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

import java.util.List;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.model.DwModel;
import net.datenwerke.gxtdto.client.model.SuccessIndicatorBaseModel;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorDao;
import net.datenwerke.rs.core.client.reportexecutor.ui.preview.AbstractReportPreviewView;
import net.datenwerke.rs.core.client.reportexporter.ReportExporterUIService;
import net.datenwerke.rs.core.client.reportexporter.exporter.ReportExporter;
import net.datenwerke.rs.core.client.reportexporter.exporter.generic.Export2HTML;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.container.MarginData;

public class JxlsReportPreviewView extends AbstractReportPreviewView {

	private ReportExporterUIService exporterUIService;
	
	private DwContentPanel wrapper;
	
	@Inject
	public JxlsReportPreviewView(ReportExecutorDao rexService, HookHandlerService hookHandler,
			ReportExporterUIService exporterUIService) {
		super(rexService, hookHandler);
		this.exporterUIService = exporterUIService;
		
		wrapper = DwContentPanel.newInlineInstance();
	}
	
	@Override
	protected boolean isCreateStatusBar() {
		return false;
	}

	@Override
	protected void doLoadReport(DwModel reportExecutionResult) {
		if(((SuccessIndicatorBaseModel)reportExecutionResult).isSuccess()){
			wrapper.clear();

			DwContentPanel frame = new DwContentPanel();
			frame.setHeaderVisible(false);
			frame.setUrl(exporterUIService.getExportServletPath() + "&tid=" + getExecuteReportToken() + "&download=false");
			
			wrapper.add(frame, new MarginData(0));
		}
	}

	
	@Override
	protected void cancelExecution(String executeToken) {
	}

	
	@Override
	public Request execute(ReportDto report, String executeToken, final AsyncCallback<DwModel> callback) {
		ReportExporter exporter = null;
		List<ReportExporter> exporters = exporterUIService.getAvailableExporters(report);
		for(ReportExporter rex : exporters){
			if(rex instanceof Export2HTML){
				exporter = rex;
				break;
			}
		}
		
		if(null == exporter)
			throw new RuntimeException("No HTML Exporter for " + report.getClass().getName());
		
		return exporter.prepareExportForPreview(report, getExecuteReportToken(), new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				callback.onFailure(caught);	
			}
			@Override
			public void onSuccess(Void result) {
				SuccessIndicatorBaseModel model = new SuccessIndicatorBaseModel();
				model.setSuccess(true);
				callback.onSuccess(model);
			}
		});
	}

	@Override
	public Widget doGetViewComponent() {
		return wrapper;
	}


}
