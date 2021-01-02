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
 
 
package net.datenwerke.rs.jxlsreport.client.jxlsreport.hookers;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import net.datenwerke.gf.client.fileselection.locale.FileSelectionMessages;
import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewToolbarConfiguratorHook;
import net.datenwerke.gf.client.managerhelper.mainpanel.FormView;
import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.gf.client.uiutils.ClientDownloadHelper;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwAlertMessageBox;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.utilityservices.UtilsUIService;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.dto.JxlsReportDto;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

public class JxlsReportFileDownloadToolbarConfiguratorHooker implements	MainPanelViewToolbarConfiguratorHook {
	
	private final ToolbarService toolbarUtils;
	private final UtilsUIService utilsUIService;

	@Inject
	public JxlsReportFileDownloadToolbarConfiguratorHooker(
			ToolbarService toolbarUtils,
			UtilsUIService utilsUIService
		) {
			this.toolbarUtils = toolbarUtils;
			this.utilsUIService = utilsUIService;
	}
	
	@Override
	public void mainPanelViewToolbarConfiguratorHook_addLeft(
			MainPanelView view, ToolBar toolbar, AbstractNodeDto selectedNode) {
		if(! (selectedNode instanceof JxlsReportDto))
			return;
		if(! (view instanceof FormView))
			return;

		final JxlsReportDto report = (JxlsReportDto) selectedNode;
		
		   
		/* add parameter */
		DwTextButton createPreviewBtn = toolbarUtils.createSmallButtonLeft(BaseMessages.INSTANCE.download(), BaseIcon.FILE_PICTURE_O); 
		createPreviewBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if(null == report.getReportFile()){
					new DwAlertMessageBox(BaseMessages.INSTANCE.warning(), FileSelectionMessages.INSTANCE.noFileUploaded()).show();
					return;
				}
				
				String id = String.valueOf(report.getId());
				String url = GWT.getModuleBaseURL() + "jxlsReportDownload?id=" + id; //$NON-NLS-1$
				ClientDownloadHelper.triggerDownload(url);
			}
		});

		
		toolbar.add(createPreviewBtn);
	}

	@Override
	public void mainPanelViewToolbarConfiguratorHook_addRight(
			MainPanelView view, ToolBar toolbar, AbstractNodeDto selectedNode) {

	}

}
