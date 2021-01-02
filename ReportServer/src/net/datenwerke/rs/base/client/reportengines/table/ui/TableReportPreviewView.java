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
 
 
package net.datenwerke.rs.base.client.reportengines.table.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.PromptMessageBox;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent.CellDoubleClickHandler;
import com.sencha.gxt.widget.core.client.event.CellMouseDownEvent;
import com.sencha.gxt.widget.core.client.event.CellMouseDownEvent.CellMouseDownHandler;
import com.sencha.gxt.widget.core.client.event.ColumnMoveEvent;
import com.sencha.gxt.widget.core.client.event.ColumnMoveEvent.ColumnMoveHandler;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent;
import com.sencha.gxt.widget.core.client.event.ColumnWidthChangeEvent.ColumnWidthChangeHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.HeaderMouseDownEvent;
import com.sencha.gxt.widget.core.client.event.HeaderMouseDownEvent.HeaderMouseDownHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.error.DefaultEditorError;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.menu.SeparatorMenuItem;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwAlertMessageBox;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwPromptMessageBox;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.forms.locale.FormsMessages;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.model.DwModel;
import net.datenwerke.gxtdto.client.utils.SqlTypes;
import net.datenwerke.gxtdto.client.utils.modelkeyprovider.BasicObjectModelKeyProvider;
import net.datenwerke.gxtdto.client.utils.valueprovider.ObjectArrayValueProvider;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.client.datasources.statementmanager.StatementManagerDao;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.locale.FilterMessages;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.NullHandlingDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.OrderDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.decorator.ColumnDtoDec;
import net.datenwerke.rs.base.client.reportengines.table.dto.decorator.TableReportDtoDec;
import net.datenwerke.rs.base.client.reportengines.table.hooks.TableReportPreviewCellEnhancerHook;
import net.datenwerke.rs.base.client.reportengines.table.locale.TableMessages;
import net.datenwerke.rs.base.client.reportengines.table.ui.model.TableReportPreviewConfig;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorDao;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorUIService;
import net.datenwerke.rs.core.client.reportexecutor.dto.PreviewModel;
import net.datenwerke.rs.core.client.reportexecutor.locale.ReportexecutorMessages;
import net.datenwerke.rs.core.client.reportexecutor.ui.preview.AbstractReportPreviewView;
import net.datenwerke.rs.core.client.reportexecutor.ui.preview.PageablePreviewView;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.theme.client.icon.BaseIcon;


/**
 * The reportoutputUi used to display previews of jasperReports
 * 
 *
 */
public class TableReportPreviewView extends AbstractReportPreviewView implements PageablePreviewView {

	private final Resources resources = GWT.create(Resources.class);

	interface Resources extends ClientBundle {
		@Source("preview.gss")
		Style css();
	}

	interface Style extends CssResource {
		@ClassName("rs-dynamic-list-preview-label")
		String previewLabel();

		@ClassName("rs-dynamic-list-preview-value")
		String previewValue();

		@ClassName("rs-dynamic-list-preview-row")
		String previewRow();
	}

	private DwContentPanel wrapper;

	private int currentPageNumber = 1;

	private Menu menu;

	private List<ColumnConfig<String[], ?>> columns;

	private StatementManagerDao statementManager;

	private Grid<String[]> grid;
	
	private ReportExecutorUIService reportExecutorUIService;

	@Inject
	public TableReportPreviewView(
			ReportExecutorDao rexDao,
			HookHandlerService hookHandler, 
			StatementManagerDao statementManager,
			ReportExecutorUIService reportExecutorUIService
			) {
		super(rexDao, hookHandler);
		this.statementManager = statementManager;
		this.reportExecutorUIService = reportExecutorUIService;

		wrapper = DwContentPanel.newInlineInstance();

		resources.css().ensureInjected();
	}

	@Override
	protected void cancelExecution(String executeToken) {
		statementManager.cancelStatement(executeToken);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doLoadReport(DwModel reportExecutionResult) {
		final TableReportDtoDec tReport = (TableReportDtoDec) report;

		if(reportExecutionResult instanceof PreviewModel){
			final PreviewModel model = (PreviewModel) reportExecutionResult;

			/* create data store */
			ListStore<String[]> store = new ListStore<String[]>(new BasicObjectModelKeyProvider<String[]>());
			store.addAll(model.getRows());

			/* configure columns */ 
			columns = new ArrayList<ColumnConfig<String[], ?>>();

			/* create table */
			for(int i = 0; i < model.getSize(); i++){
				SafeHtmlBuilder header = new SafeHtmlBuilder().appendEscaped(model.getColumnName(i));

				final ColumnDtoDec column = (ColumnDtoDec) tReport.getVisibleColumnByPos(i);
				if(null != column){
					header.appendHtmlConstant("&nbsp;");
					if (null != column.getOrder())
						if (column.getOrder().equals(OrderDto.ASC))
							header.append(BaseIcon.SORT_ALPHA_ASC.toSafeHtml());
						else
							header.append(BaseIcon.SORT_ALPHA_DESC.toSafeHtml());
					if (null != column.getAggregateFunction()) 
						header.append(BaseIcon.AGGREGATION.toSafeHtml());
					if(column.hasSomeFormat())
						header.append(BaseIcon.FORMAT.toSafeHtml());
					if(column.hasFilters())
						header.append(BaseIcon.FILTER.toSafeHtml());
					if (null != column.getNullHandling()) {
						if (column.getNullHandling().equals(NullHandlingDto.Exlude))
							header.append(BaseIcon.NULL_EXCLUDE.toSafeHtml());
						else
							header.append(BaseIcon.NULL_INCLUDE.toSafeHtml());
					}
				}

				/* name column */
				int colWidth = getColWidth(column, tReport);
			
				ColumnConfig<String[], String> colConfig = new ColumnConfig<String[], String>(new ObjectArrayValueProvider<String>(i), colWidth, header.toSafeHtml());
				
				colConfig.setMenuDisabled(true);
				colConfig.setSortable(false);

				if(SqlTypes.isNumerical(column.getType()))
					colConfig.setHorizontalAlignment(HorizontalAlignmentConstant.endOf(Direction.LTR));

				columns.add(colConfig);
			}

			/* create grid */
			grid = new Grid<String[]>(store, new ColumnModel<String[]>(columns));

			grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			grid.getView().setStripeRows(true);
			grid.setColumnReordering(true);
			grid.setContextMenu(new DwMenu());
			
			grid.addHeaderMouseDownHandler(new HeaderMouseDownHandler() {
				@Override
				public void onHeaderMouseDown(HeaderMouseDownEvent event) {
					if(event.getEvent().getButton() == NativeEvent.BUTTON_RIGHT){
						if (!tReport.isConfigurationProtected()) {
							configureHeaderMenu(model, event.getColumnIndex(), event.getEvent().getClientX(), event.getEvent().getClientY());
							grid.setContextMenu(menu);
						} else 
							grid.setContextMenu(null);
					}
				}
			});

			grid.addCellMouseDownHandler(new CellMouseDownHandler() {
				@Override
				public void onCellMouseDown(final CellMouseDownEvent event) {
					if(event.getEvent().getButton() == NativeEvent.BUTTON_RIGHT){
						int cellIndex = event.getCellIndex();
						if (!tReport.isConfigurationProtected()) {
							configureMenu(cellIndex, event.getRowIndex(), model, event);
							grid.setContextMenu(menu);
						} else 
							grid.setContextMenu(null);
					}
				}
			});

			grid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
				@Override
				public void onCellClick(CellDoubleClickEvent event) {
					previewRow(event.getRowIndex(), model);
				}
			});

			grid.getColumnModel().addColumnWidthChangeHandler(new ColumnWidthChangeHandler() {
				@Override
				public void onColumnWidthChange(ColumnWidthChangeEvent event) {
					((TableReportDtoDec)report).setPreviewColumnWidth(((TableReportDtoDec)report).getVisibleColumnByPos(event.getIndex()), 
							event.getColumnConfig().getWidth());
				}
			});

			grid.getColumnModel().addColumnMoveHandler(new ColumnMoveHandler() {
				@Override
				public void onColumnMove(ColumnMoveEvent event) {
					for(int i = 0; i < columns.size(); i++){
						if(columns.get(i).equals(event.getColumnConfig())){
							ColumnDto col = tReport.getVisibleColumnByPos(i);
							tReport.moveColumnToPos(col, event.getIndex());
							break;
						}
					}
					columns = grid.getColumnModel().getColumns();
				}
			});
			
			wrapper.clear();
			wrapper.setWidget(grid);
		}
	}

	protected int getColWidth(ColumnDtoDec column, TableReportDtoDec tReport) {
		Integer columnWidth = column.getPreviewWidth();
		if(null != columnWidth)
			return columnWidth;
		
		if (null != column.getDefaultPreviewWidth())
			if ( ! "".contentEquals(column.getDefaultPreviewWidth()))
				return Integer.valueOf(column.getDefaultPreviewWidth());
		
		Integer defaultColumnWidth = reportExecutorUIService.getDefaultColumnWidth();
		return defaultColumnWidth;
	}

	protected void configureHeaderMenu(PreviewModel model, final int colIndex, int posX, int posY) {
		menu = new DwMenu();

		MenuItem setColumnWidth = new DwMenuItem(TableMessages.INSTANCE.columnWidth());
		setColumnWidth.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				final PromptMessageBox mb = new DwPromptMessageBox(TableMessages.INSTANCE.columnWidth(), TableMessages.INSTANCE.setColumnWidthMsg());
				mb.addDialogHideHandler(new DialogHideHandler() {

					@Override
					public void onDialogHide(DialogHideEvent event) {
						if (event.getHideButton() == PredefinedButton.OK) {
							String value = mb.getTextField().getCurrentValue();
							if(null == value)
								return;
							try{
								int width = Math.max(10, Math.min(reportExecutorUIService.getMaxColumnWidth(), Integer.parseInt(value)));

								((TableReportDtoDec)report).setPreviewColumnWidth(((TableReportDtoDec)report).getVisibleColumnByPos(colIndex), width);

								grid.getColumnModel().getColumn(colIndex).setWidth(width);
								grid.getView().refresh(true);
								grid.fireEvent(new ColumnWidthChangeEvent(colIndex, grid.getColumnModel().getColumn(colIndex)));
							} catch(Exception e){}
						}
					}
				});

				mb.getTextField().setWidth(200);
				mb.getTextField().setValue("" + grid.getColumnModel().getColumn(colIndex).getWidth());

				mb.getTextField().addValidator(new Validator<String>() {
					@Override
					public List<EditorError> validate(Editor<String> editor,
							String value) {
						try{
							Integer width = Integer.parseInt(value);
							Integer max = reportExecutorUIService.getMaxColumnWidth().intValue();
							if (width.intValue() > max.intValue()) {
								List<EditorError> list = new ArrayList<EditorError>();
								list.add(new DefaultEditorError(editor, FilterMessages.INSTANCE.previewWidthWarningMsg(), max));
								return list;
							}
							return null;
						} catch(NumberFormatException e){
							List<EditorError> list = new ArrayList<EditorError>();
							list.add(new DefaultEditorError(editor, FormsMessages.INSTANCE.invalidInteger(), value));
							return list;
						}
					}
				});
				mb.show();
			}
		});
		menu.add(setColumnWidth);

		MenuItem setAllColumnWidth = new DwMenuItem(TableMessages.INSTANCE.allColumnWidth());
		setAllColumnWidth.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				final PromptMessageBox mb = new DwPromptMessageBox(TableMessages.INSTANCE.allColumnWidth(), TableMessages.INSTANCE.setColumnWidthMsg());
				mb.addDialogHideHandler(new DialogHideHandler() {

					@Override
					public void onDialogHide(DialogHideEvent event) {
						if (event.getHideButton() == PredefinedButton.OK) {
							String value = mb.getTextField().getCurrentValue();
							if(null == value)
								return;
							try{
								int width = Math.max(10, Math.min(reportExecutorUIService.getMaxColumnWidth(), Integer.parseInt(value)));

								for (int i = 0; i<= grid.getColumnModel().getColumnCount()-1; i++) 
									((TableReportDtoDec)report).setPreviewColumnWidth(((TableReportDtoDec)report).getVisibleColumnByPos(i), width);

								for(ColumnConfig<?,?> col : grid.getColumnModel().getColumns())
									col.setWidth(width);
								
								grid.getView().refresh(true);
								
								for (int i = 0; i<= grid.getColumnModel().getColumnCount()-1; i++) 
									grid.fireEvent(new ColumnWidthChangeEvent(i, grid.getColumnModel().getColumn(i)));
								
							} catch(Exception e){}
						}
					}
				});

				mb.getTextField().setWidth(200);
				mb.getTextField().setValue(reportExecutorUIService.getDefaultColumnWidth()+"");

				mb.getTextField().addValidator(new Validator<String>() {
					@Override
					public List<EditorError> validate(Editor<String> editor,
							String value) {
						try{
							Integer width = Integer.parseInt(value);
							Integer max = reportExecutorUIService.getMaxColumnWidth().intValue();
							if (width.intValue() > max.intValue()) {
								List<EditorError> list = new ArrayList<EditorError>();
								list.add(new DefaultEditorError(editor, FilterMessages.INSTANCE.previewWidthWarningMsg(), max));
								return list;
							}
							return null;
						} catch(NumberFormatException e){
							List<EditorError> list = new ArrayList<EditorError>();
							list.add(new DefaultEditorError(editor, FormsMessages.INSTANCE.invalidInteger(), value));
							return list;
						}
					}
				});
				mb.show();
			}
		});
		menu.add(setAllColumnWidth);

		MenuItem setOptimalColumnWidth = new DwMenuItem(TableMessages.INSTANCE.optimalColumnWidth());
		setOptimalColumnWidth.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				setOptimalColumnWidth();
			}
		});
		menu.add(setOptimalColumnWidth);
	}

	protected void setOptimalColumnWidth() {

		try{
			int cwidth = computeOptimalColumnWidth(((TableReportDtoDec)report));
			
			for (int i = 0; i<= grid.getColumnModel().getColumnCount()-1; i++) 
				((TableReportDtoDec)report).setPreviewColumnWidth(((TableReportDtoDec)report).getVisibleColumnByPos(i), cwidth);
			
			for(ColumnConfig<?,?> col : grid.getColumnModel().getColumns())
				col.setWidth(cwidth);
			
			grid.getView().refresh(true);
			
			for (int i = 0; i<= grid.getColumnModel().getColumnCount()-1; i++) 
				grid.fireEvent(new ColumnWidthChangeEvent(i, grid.getColumnModel().getColumn(i)));
			
		} catch(Exception e){}
	}

	protected int computeOptimalColumnWidth(TableReportDtoDec report) {
		int width = wrapper.getElement().getWidth(true);
		if(width > 500)
			width = width - 25; // scrollbar
		int cwidth = (int) Math.floor(width / (double) report.getVisibleColumnCount());
		return cwidth;
	}

	protected void previewRow(int rowIndex, final PreviewModel model) {
		final String[] values = model.getRows().get(rowIndex);

		final DwWindow window = new DwWindow();
		window.setSize(800, 640);
		window.setHeading(TableMessages.INSTANCE.rowPreviewHeader());
		window.setCollapsible(true);

		FlexTable table = new FlexTable();
		table.getColumnFormatter().setWidth(0, "33%");

		for(int i = 0; i < model.getColumnNames().size(); i++){
			table.setText(i, 0, model.getColumnName(i) + ":");
			table.setText(i, 1, values[i]);

			table.getCellFormatter().addStyleName(i, 0, resources.css().previewLabel());
			table.getCellFormatter().addStyleName(i, 1, resources.css().previewValue());

			table.getRowFormatter().addStyleName(i, resources.css().previewRow());
		}

		VerticalLayoutContainer wrapper = new VerticalLayoutContainer();
		wrapper.setScrollMode(ScrollMode.AUTOY);
		wrapper.add(table, new VerticalLayoutData(1,-1, new Margins(10)));
		window.add(wrapper);


		DwTextButton btn = new DwTextButton(BaseMessages.INSTANCE.close());
		btn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				window.hide();
			}
		});
		window.addButton(btn);

		window.show();
	}

	protected void configureMenu(int pos, final int row, PreviewModel model, CellMouseDownEvent event) {
		if(menu != null)
			menu.hide();

		String value = model.getRows().get(row)[pos];
		String rawValue = model.getRawData(row, pos);

		menu = new DwMenu();
		TableReportDtoDec tReport = (TableReportDtoDec) report;
		final ColumnDto column = tReport.getVisibleColumnByPos(pos);
		if(null == column)
			return;

		Iterator<TableReportPreviewCellEnhancerHook> iterator = hookHandler.getHookers(TableReportPreviewCellEnhancerHook.class).iterator();
		while(iterator.hasNext()){
			TableReportPreviewCellEnhancerHook enhancer = iterator.next();
			if(! enhancer.consumes(tReport, column, value, rawValue, model.getRows().get(row)))
				continue;

			boolean separator = enhancer.enhanceMenu(this, menu, tReport, column, value, rawValue, model.getRows().get(row));
			if(iterator.hasNext() && separator)
				menu.add(new SeparatorMenuItem());
		}
	}

	@Override
	public Widget doGetViewComponent() {
		return wrapper;
	}


	@Override
	public Request execute(ReportDto report, String executeToken, AsyncCallback<DwModel> callback) {
		TableReportPreviewConfig config = new TableReportPreviewConfig();
		config.setPagenumber(currentPageNumber);
		config.setPagesize(getPageSize());

		return reportExecutorDao.executeAs("TABLE_REPORT_PREVIEW", executeToken, report, config, callback);
	}

	@Override
	public void reload() {
		currentPageNumber = 1;
		super.reload();
	}

	@Override
	public void setPageNumber(int number) {
		currentPageNumber = Math.max(1, number);
		executeAndDisplayReport(false);
	}

	@Override
	public int getPageNumber() {
		return currentPageNumber;
	}

	@Override
	public void makeAwareOfSelection() {
		if(report instanceof TableReportDto){
			if(((TableReportDto) report).getColumns().isEmpty()){
				new DwAlertMessageBox(ReportexecutorMessages.INSTANCE.failed(), ReportexecutorMessages.INSTANCE.noColumnsSelected()).show();
				if(null != container)
					container.disable();
				return;
			}
		}

		currentPageNumber = 1;
		super.makeAwareOfSelection();
	}

	@Override
	public void cleanup() {
		statementManager.cancelStatement(getExecuteReportToken());
	}

	@Override
	public int getPageSize(){
		List<ColumnDto> cols = ((TableReportDto)report).getColumns();
		if(null == cols || cols.size() < 100)
			return 50;
		if(cols.size() < 250)
			return 25;
		if(cols.size() < 500)
			return 10;
		return 5;
	}

}
