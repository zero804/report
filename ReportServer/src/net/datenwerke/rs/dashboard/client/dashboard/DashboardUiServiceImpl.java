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
 
 
package net.datenwerke.rs.dashboard.client.dashboard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.widget.core.client.button.IconButton;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.ShowEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent.ShowHandler;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.DwWindow.OnButtonClickHandler;
import net.datenwerke.gxtdto.client.baseex.widget.layout.DwFlowContainer;
import net.datenwerke.gxtdto.client.baseex.widget.tool.DwToolButton;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.stores.LoadableListStore;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.core.client.parameters.propertywidgets.ParameterView;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.decorator.DashboardDtoDec;
import net.datenwerke.rs.dashboard.client.dashboard.dto.pa.DashboardDtoPA;
import net.datenwerke.rs.dashboard.client.dashboard.hooks.DadgetProcessorHook;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DadgetPanel;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DashboardContainer.ConfigType;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;

@Singleton
public class DashboardUiServiceImpl implements DashboardUiService {

	private final DashboardDao dashboardDao;

	private static DashboardDtoPA dashboardPA = GWT.create(DashboardDtoPA.class);
	
	@Inject
	public DashboardUiServiceImpl(DashboardDao dashboardDao) {
		super();
		this.dashboardDao = dashboardDao;
	}
	
	@Override
	public void showHideParameterToolButton(DadgetPanel panel, ReportDto report) {
		/* if it does not have parameter instances, no filter */
		if(null != panel.getHeader() && panel.getHeader().getToolCount()>0){
			if(null == report || report.getParameterInstances().isEmpty())
				panel.getHeader().getTool(0).asWidget().setVisible(false);
			else
				panel.getHeader().getTool(0).asWidget().setVisible(true);
		}
	}

	@Override
	public IconButton addParameterToolButtonTo(final DadgetPanel dadgetPanel, final DadgetProcessorHook dadgetProcessor) {
		IconButton editParamBtn = new ToolButton(DwToolButton.FILTER);
		editParamBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if(! dadgetProcessor.readyToDisplayParameters(dadgetPanel))
					return;
				
				dashboardDao.getDadgetParameterInstances(dadgetPanel.getDadget(), new RsAsyncCallback<Map<String, ParameterInstanceDto>>(){

					@Override
					public void onSuccess(final Map<String, ParameterInstanceDto> result) {
						final List<ParameterDefinitionDto> definitions = new ArrayList<>();
						final Set<ParameterInstanceDto> instances = new HashSet<>();

						for(ParameterInstanceDto inst : result.values()){
							definitions.add(inst.getDefinition());
							instances.add(inst);
						}

						ParameterView pv = new ParameterView(definitions, instances);
						final DwWindow dialog = new DwWindow();
						
						DwFlowContainer wrapper = new DwFlowContainer();
						wrapper.setScrollMode(ScrollMode.AUTO);
						dialog.add(wrapper);
						
						DwContentPanel panelWrapper = DwContentPanel.newInlineInstance(pv.getParameterContainer());
						panelWrapper.setLightDarkStyle();
						
						wrapper.add(panelWrapper, new MarginData(10));
						
						
						dialog.addShowHandler(new ShowHandler() {
							@Override
							public void onShow(ShowEvent event) {
								try{
									int width = dialog.getElement().getWidth(true);
									int height= dialog.getElement().getHeight(true);
									if(width > 1200 || height > 1000)
										dialog.setSize(800, 600);
								}catch(Exception e){}
							}
						});
						
						
						dialog.setHeading(pv.getComponentHeader());
						dialog.addCancelButton();
						dialog.addSubmitButton(new OnButtonClickHandler() {
							@Override
							public void onClick() {
								dashboardDao.setDadgetParameterInstances(dadgetPanel.getDadget(),instances, new RsAsyncCallback<DadgetDto>(){
									public void onSuccess(DadgetDto result) {
										DadgetDto oldDadget = dadgetPanel.getDadget();
										dadgetPanel.updateDadget(result);
										
										/* update also dashboard */
										DashboardDto dashboard = dadgetPanel.getView().getDashboard();
										if(null != dashboard)
											((DashboardDtoDec)dashboard).updateDadget(oldDadget, result);
										
										Scheduler.get().scheduleDeferred(new ScheduledCommand() {
											@Override
											public void execute() {
												dadgetPanel.getView().dadgetConfigured(dadgetPanel, ConfigType.MISC);
											}
										});
									};
								});
							}
						});

						dialog.show();
					}
				});
			}
		});
		dadgetPanel.addTool(editParamBtn);
		return editParamBtn;
	}
	
	@Override
	public ListLoader<ListLoadConfig, ListLoadResult<DashboardDto>> getAllDashboardsLoader(){
		/* create store */
		RpcProxy<ListLoadConfig, ListLoadResult<DashboardDto>> proxy = new RpcProxy<ListLoadConfig, ListLoadResult<DashboardDto>>() {
			
			@Override
			public void load(ListLoadConfig loadConfig,
					AsyncCallback<ListLoadResult<DashboardDto>> callback) {
				dashboardDao.loadAllDashboards(callback);
			}

		};
		return new ListLoader<ListLoadConfig, ListLoadResult<DashboardDto>>(proxy);
	}

	@Override
	public ListStore<DashboardDto> getAllDashboardsStore() {
		return new LoadableListStore<ListLoadConfig, DashboardDto, ListLoadResult<DashboardDto>>(dashboardPA.dtoId(), getAllDashboardsLoader());
	}

	@Override
	public ListStore<DashboardDto> getDashboardStore() {
		return new LoadableListStore<ListLoadConfig, DashboardDto, ListLoadResult<DashboardDto>>(dashboardPA.dtoId(), getDashboardsLoader());
	}

	@Override
	public ListLoader<ListLoadConfig, ListLoadResult<DashboardDto>> getDashboardsLoader(){
		/* create store */
		RpcProxy<ListLoadConfig, ListLoadResult<DashboardDto>> proxy = new RpcProxy<ListLoadConfig, ListLoadResult<DashboardDto>>() {
			@Override
			public void load(ListLoadConfig loadConfig,
					AsyncCallback<ListLoadResult<DashboardDto>> callback) {
				dashboardDao.loadDashboards(callback);
			}
		};

		return new ListLoader<ListLoadConfig, ListLoadResult<DashboardDto>>(proxy);
	}
}
