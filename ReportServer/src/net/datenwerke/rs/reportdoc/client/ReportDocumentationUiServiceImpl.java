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
 
 
package net.datenwerke.rs.reportdoc.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;

import net.datenwerke.gf.client.uiutils.ClientDownloadHelper;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

public class ReportDocumentationUiServiceImpl implements ReportDocumentationUiService {

	@Override
	public void openDocumentationForopen(ReportDto report) {
		int nonce = Random.nextInt();
		String url = GWT.getModuleBaseURL() + ReportDocumentationUIModule.SERVLET + "?nonce=" + nonce + "&id=" + report.getId() + "&format=PDF&download=true";
		ClientDownloadHelper.triggerDownload(url);
	}
	
	@Override
	public void openDeployAnalyzeForopen(ReportDto leftReport, ReportDto rightReport) {
		int nonce = Random.nextInt();
		String url = GWT.getModuleBaseURL() + ReportDocumentationUIModule.SERVLET + "?nonce=" + nonce + "&leftRepId=" + leftReport.getId() + "&rightRepId=" + rightReport.getId() + "&report=deployanalyze" + "&format=PDF&download=true";
		ClientDownloadHelper.triggerDownload(url);
	}

}