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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea.helper.simpleform;

import java.util.HashSet;
import java.util.Set;

import net.datenwerke.gf.client.treedb.TreeDBUIService;
import net.datenwerke.gf.client.treedb.UiTreeFactory;
import net.datenwerke.gf.client.treedb.selection.SingleTreeSelectionField;
import net.datenwerke.gf.client.treedb.simpleform.TreeNodeDtoProvider;
import net.datenwerke.gf.client.treedb.stores.EnhancedTreeStore;
import net.datenwerke.gxtdto.client.clipboard.ClipboardUiService;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.TsDiskTreeLoaderDao;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.TsDiskUIService;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskReportReferenceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.posomap.TsDiskFolderDto2PosoMap;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.posomap.TsDiskRootDto2PosoMap;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.locale.TsFavoriteMessages;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.Style.SelectionMode;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwAlertMessageBox;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;

/**
 * 
 *
 */
public class TsReportReferenceProvider extends TreeNodeDtoProvider {
	
	private final TsDiskTreeLoaderDao treeLoaderDao;
	private final TreeDBUIService treeDBUIService;
	private final UiTreeFactory uiTreeFactory;
	private final TsDiskUIService tsDiskUIService;
	
	@Inject
	public TsReportReferenceProvider(
		ClipboardUiService clipboardService,
		TsDiskTreeLoaderDao treeLoaderDao, 
		TreeDBUIService treeDBUIService,
		UiTreeFactory uiTreeFactory,
		TsDiskUIService tsDiskUIService) {
		super(clipboardService);
		
		this.treeLoaderDao = treeLoaderDao;
		this.treeDBUIService = treeDBUIService;
		this.uiTreeFactory = uiTreeFactory;
		this.tsDiskUIService = tsDiskUIService;
	}

	@Override
	public boolean doConsumes(Class<?> type, SimpleFormFieldConfiguration... configs) {
		if(configs.length == 0 || !( configs[0] instanceof SFFCTsTeamSpaceSelector))
			return false;
		
		while(type != null){
			if(type.equals(TsDiskReportReferenceDto.class))
				return true;
			type = type.getSuperclass();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Widget createFormField() {
		final SFFCTsTeamSpaceSelector config = (SFFCTsTeamSpaceSelector) configs[0];
		
		final SingleTreeSelectionField ssf = new SingleTreeSelectionField(TsDiskReportReferenceDto.class);
		ssf.setIgnoreTriggerClick(true);
		ssf.addTriggerClickHandler(new TriggerClickHandler() {
			
			private TeamSpaceDto theTeamSpace;
			
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				TeamSpaceDto teamSpace = config.getTeamSpace();
				if(null == ssf.getTreePanel() || null == theTeamSpace || ! theTeamSpace.equals(teamSpace)){
					theTeamSpace = teamSpace;
					if(null == theTeamSpace){
						new DwAlertMessageBox (TsFavoriteMessages.INSTANCE.noTeamSpaceSelectedTitle(), TsFavoriteMessages.INSTANCE.noTeamSpaceSelectedMsg()).show();
					} else {
						treeLoaderDao.setState(theTeamSpace);

						Set<Dto2PosoMapper> filters = new HashSet<Dto2PosoMapper>();
						filters.add(new TsDiskRootDto2PosoMap());
						filters.add(new TsDiskFolderDto2PosoMap());
						EnhancedTreeStore treeStore = treeDBUIService.getUITreeStore(AbstractTsDiskNodeDto.class, treeLoaderDao, false, filters);

						ssf.setTreePanel(uiTreeFactory.create(treeStore));
						ssf.getTreePanel().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

						ssf.getTreePanel().setIconProvider(tsDiskUIService.getIconProvider());
						
						ssf.triggerClicked();
					}
				} else if(theTeamSpace.equals(teamSpace))
					ssf.triggerClicked();
					
			}
		});
		
		ssf.addValueChangeHandler(new ValueChangeHandler<AbstractNodeDto>() {
			@Override
			public void onValueChange(ValueChangeEvent<AbstractNodeDto> event) {
				ValueChangeEvent.fire(TsReportReferenceProvider.this, event.getValue());
			}
		});
		
		ssf.setName(name);
		
		return ssf;
	}

	
	
}
