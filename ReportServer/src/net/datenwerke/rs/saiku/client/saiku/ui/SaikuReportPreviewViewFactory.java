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
 
 
package net.datenwerke.rs.saiku.client.saiku.ui;

import java.util.Collection;

import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportExecutorMainPanelView;
import net.datenwerke.rs.core.client.reportexecutor.ui.ReportViewConfiguration;
import net.datenwerke.rs.core.client.reportexecutor.ui.preview.AbstractReportPreviewView;
import net.datenwerke.rs.core.client.reportexecutor.ui.preview.PreviewViewFactory;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.saiku.client.datasource.dto.MondrianDatasourceDto;
import net.datenwerke.rs.saiku.client.saiku.dto.SaikuReportDto;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SaikuReportPreviewViewFactory extends PreviewViewFactory {
	
	private final Provider<SaikuReportPreviewView> brpvProvider;

	@Inject
	public SaikuReportPreviewViewFactory(
			Provider<SaikuReportPreviewView> brpvProvider 
	) {
		this.brpvProvider = brpvProvider;
	}
	
	@Override
	public ReportExecutorMainPanelView newInstance(ReportDto report, Collection<? extends ReportViewConfiguration> configs) {
		AbstractReportPreviewView view = brpvProvider.get();
		view.setReport(report);
		
		return view;
	}

	@Override
	public boolean consumes(ReportDto report) {
		if (! (report instanceof SaikuReportDto) ) 
			return false;
		
		DatasourceContainerDto datasourceContainer = report.getDatasourceContainer();
		return !((MondrianDatasourceDto)datasourceContainer.getDatasource()).isMondrian3();
	}

}
