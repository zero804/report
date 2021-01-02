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
 
 
package net.datenwerke.rs.crystal.client.crystal.hookers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewToolbarConfiguratorHook;
import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.window.SimpleDialogWindow;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.ui.helper.grid.DeletableRowsGrid;
import net.datenwerke.gxtdto.client.ui.helper.wrapper.ToolbarWrapperPanel;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.core.client.parameters.ParameterUIService;
import net.datenwerke.rs.core.client.parameters.backend.ParameterView;
import net.datenwerke.rs.core.client.parameters.config.ParameterConfigurator;
import net.datenwerke.rs.core.client.parameters.config.ParameterConfigurator.ParameterType;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.crystal.client.crystal.CrystalUtilsDao;
import net.datenwerke.rs.crystal.client.crystal.dto.CrystalParameterProposalDto;
import net.datenwerke.rs.crystal.client.crystal.dto.CrystalReportDto;
import net.datenwerke.rs.crystal.client.crystal.dto.pa.CrystalParameterProposalDtoPA;
import net.datenwerke.rs.crystal.client.crystal.locale.CrystalMessages;
import net.datenwerke.rs.incubator.client.jasperutils.locale.JasperMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.Converter;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.StringLabelProvider;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwAlertMessageBox;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class CrystalReportParameterProposerToolbarConfiguratorHooker implements MainPanelViewToolbarConfiguratorHook {
	
	private static CrystalParameterProposalDtoPA crystalPa = GWT.create(CrystalParameterProposalDtoPA.class);
	
	private ToolbarService toolbarUtils;
	private CrystalUtilsDao crystalUtilsDao;
	private ParameterUIService parameterService;

	@Inject
	public CrystalReportParameterProposerToolbarConfiguratorHooker(
			ToolbarService toolbarUtils, 
			CrystalUtilsDao crystalUtilsDao, 
			ParameterUIService parameterService) {
		
		this.toolbarUtils = toolbarUtils;
		this.crystalUtilsDao = crystalUtilsDao;
		this.parameterService = parameterService;
	}

	@Override
	public void mainPanelViewToolbarConfiguratorHook_addLeft(final MainPanelView view, ToolBar toolbar, AbstractNodeDto selectedNode) {
		
		if(! (selectedNode instanceof CrystalReportDto))
			return;
		if(! (view instanceof ParameterView))
			return;
		
		final CrystalReportDto report = (CrystalReportDto) selectedNode;


		/* add parameter */
		DwTextButton createPreviewBtn = toolbarUtils.createSmallButtonLeft(CrystalMessages.INSTANCE.parameterProposalBtn(), BaseIcon.LIGHTBULB);
		createPreviewBtn.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				crystalUtilsDao.proposeParametersFor(report, new RsAsyncCallback<List<CrystalParameterProposalDto>>() {

					@Override
					public void onSuccess(List<CrystalParameterProposalDto> result) {
						if(null != result && ! result.isEmpty()){
							displayResults((ParameterView) view, report, result);
						}else{ 
							new DwAlertMessageBox(CrystalMessages.INSTANCE.noProposalsFoundTitle(), CrystalMessages.INSTANCE.noProposalsFoundText()).show();
						}
					}
				});
			}
		});
		toolbar.add(createPreviewBtn);
		
	}

	@Override
	public void mainPanelViewToolbarConfiguratorHook_addRight(
			MainPanelView view, ToolBar toolbar, AbstractNodeDto selectedNode) {
		// TODO Auto-generated method stub

	}
	
	protected void displayResults(final ParameterView view, final CrystalReportDto report, List<CrystalParameterProposalDto> proposals) {
		/* create store */
		final ListStore<CrystalParameterProposalDto> proposalStore = new ListStore<CrystalParameterProposalDto>(crystalPa.dtoId());
		proposalStore.setAutoCommit(true);
		for(CrystalParameterProposalDto proposal : proposals){
			for(ParameterConfigurator configurator : parameterService.getAvailableParameterConfigurators()){
				if(configurator.canHandle(proposal)){
					proposal.setParameterProposal(configurator.getNewDto(proposal, report));
					break;
				}
			}
			proposalStore.add(proposal);
		}
		
		/* configure columns */ 
		List<ColumnConfig<CrystalParameterProposalDto,?>> columns = new ArrayList<ColumnConfig<CrystalParameterProposalDto,?>>();
		
		/* Name column */
		ColumnConfig<CrystalParameterProposalDto,String> nameConfig = new ColumnConfig<CrystalParameterProposalDto,String>(crystalPa.name(), 200, JasperMessages.INSTANCE.name()); 
		columns.add(nameConfig);
		
		/* Key column */
		ColumnConfig<CrystalParameterProposalDto,String> keyConfig = new ColumnConfig<CrystalParameterProposalDto,String>(crystalPa.key(), 200, JasperMessages.INSTANCE.key()); 
		columns.add(keyConfig);
		
		/* Proposal column */
	    final SimpleComboBox<ParameterConfigurator> proposalCombo = new SimpleComboBox<ParameterConfigurator>(new StringLabelProvider<ParameterConfigurator>(){
	    	@Override
	    	public String getLabel(ParameterConfigurator item) {
	    		return item.getName();
	    	}
	    });  
	    proposalCombo.setForceSelection(true);
	    proposalCombo.setAllowBlank(true);
	    proposalCombo.setTriggerAction(TriggerAction.ALL);
	    for(ParameterConfigurator configurator : parameterService.getAvailableParameterConfigurators())
	    	if(ParameterType.Normal == configurator.getType())
	    		proposalCombo.add(configurator);
	  
		ColumnConfig<CrystalParameterProposalDto,ParameterDefinitionDto> proposalConfig = new ColumnConfig<CrystalParameterProposalDto,ParameterDefinitionDto>(crystalPa.parameterProposal(), 200, JasperMessages.INSTANCE.proposal());
		proposalConfig.setCell(new AbstractCell<ParameterDefinitionDto>() {
			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context,
					ParameterDefinitionDto model, SafeHtmlBuilder sb) {
				if(null != model)
					sb.appendEscaped(parameterService.getConfigurator(model).getName());
			}
		});
		columns.add(proposalConfig);
		
		/* create grid */
		final DeletableRowsGrid<CrystalParameterProposalDto> grid = new DeletableRowsGrid<CrystalParameterProposalDto>(proposalStore, new ColumnModel<CrystalParameterProposalDto>(columns));
		
		grid.setSelectionModel(new GridSelectionModel<CrystalParameterProposalDto>());
		grid.getSelectionModel().setSelectionMode(SelectionMode.MULTI);
		grid.getView().setShowDirtyCells(false);
		grid.setHeight(20);
		
		// edit //
		GridEditing<CrystalParameterProposalDto> editing = new GridInlineEditing<CrystalParameterProposalDto>(grid);
		editing.addEditor(nameConfig, new TextField());
		editing.addEditor(keyConfig, new TextField());
		
		editing.addEditor(proposalConfig, new Converter<ParameterDefinitionDto, ParameterConfigurator>() {
			@Override
			public ParameterDefinitionDto convertFieldValue(ParameterConfigurator object) {
				if(null == object)
		    		  return null;
		    	  
				return object.getNewDto(report);
			}

			@Override
			public ParameterConfigurator convertModelValue(ParameterDefinitionDto object) {
				if(null == object)
		    		  return null;
				
				return parameterService.getConfigurator(object);
			}
		}, proposalCombo);
		
		/* create toolbar wrapper */
		ToolbarWrapperPanel wrapper = new ToolbarWrapperPanel(grid){

			@Override
			protected void removeAllButtonClicked(com.google.gwt.event.logical.shared.SelectionEvent<com.sencha.gxt.widget.core.client.menu.Item> event) {
				proposalStore.clear();
			}
			
			@Override
			protected void removeButtonClicked(SelectEvent ce) {
				for(CrystalParameterProposalDto proposal : grid.getSelectionModel().getSelectedItems())
					proposalStore.remove(proposal);
			}
		};
		wrapper.addRemoveButtons();
		
		/* create window */
		DwWindow window = new SimpleDialogWindow(){
			@Override
			protected void submitButtonClicked() {
				addParametersFor(view, report, proposalStore.getAll());
				super.submitButtonClicked();
			}
		};
		window.setHeading(JasperMessages.INSTANCE.windowTitle());
		window.setWidth(800);
		window.setHeight(600);
		window.add(wrapper);
		window.show();
	}


	protected void addParametersFor(final ParameterView view, CrystalReportDto report, List<CrystalParameterProposalDto> proposalDtos) {
		crystalUtilsDao.addParametersFor(report, proposalDtos, new RsAsyncCallback<CrystalReportDto>() {
			@Override
			public void onSuccess(CrystalReportDto result) {
				if(null != result)
					view.updateStore(result);
			}
		});
	}

}
