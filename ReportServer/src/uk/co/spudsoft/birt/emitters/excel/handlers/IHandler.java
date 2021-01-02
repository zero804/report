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
 
 
/*************************************************************************************
 * Copyright (c) 2011, 2012, 2013 James Talbut.
 *  jim-emitters@spudsoft.co.uk
 *  
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     James Talbut - Initial implementation.
 ************************************************************************************/

package uk.co.spudsoft.birt.emitters.excel.handlers;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.content.IAutoTextContent;
import org.eclipse.birt.report.engine.content.ICellContent;
import org.eclipse.birt.report.engine.content.IContainerContent;
import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.content.IDataContent;
import org.eclipse.birt.report.engine.content.IForeignContent;
import org.eclipse.birt.report.engine.content.IGroupContent;
import org.eclipse.birt.report.engine.content.IImageContent;
import org.eclipse.birt.report.engine.content.ILabelContent;
import org.eclipse.birt.report.engine.content.IListBandContent;
import org.eclipse.birt.report.engine.content.IListContent;
import org.eclipse.birt.report.engine.content.IListGroupContent;
import org.eclipse.birt.report.engine.content.IPageContent;
import org.eclipse.birt.report.engine.content.IRowContent;
import org.eclipse.birt.report.engine.content.ITableBandContent;
import org.eclipse.birt.report.engine.content.ITableContent;
import org.eclipse.birt.report.engine.content.ITableGroupContent;
import org.eclipse.birt.report.engine.content.ITextContent;
import org.w3c.dom.css.CSSValue;

import uk.co.spudsoft.birt.emitters.excel.HandlerState;

public interface IHandler {
	
	public IHandler getParent();
	public <T extends IHandler> T getAncestor(Class<T> clazz);
	public CSSValue getBackgroundColour();
	
	public String getPath();
	public void notifyHandler(HandlerState state);
	
	public void startPage(HandlerState state, IPageContent page) throws BirtException;
	public void endPage(HandlerState state, IPageContent page) throws BirtException;

	public void startTable(HandlerState state, ITableContent table) throws BirtException;
	public void endTable(HandlerState state, ITableContent table) throws BirtException;

	public void startTableBand(HandlerState state, ITableBandContent band) throws BirtException;
	public void endTableBand(HandlerState state, ITableBandContent band) throws BirtException;

	public void startRow(HandlerState state, IRowContent row) throws BirtException;
	public void endRow(HandlerState state, IRowContent row) throws BirtException;

	public void startCell(HandlerState state, ICellContent cell) throws BirtException;
	public void endCell(HandlerState state, ICellContent cell) throws BirtException;

	public void startList(HandlerState state, IListContent list) throws BirtException;
	public void endList(HandlerState state, IListContent list) throws BirtException;

	public void startListBand(HandlerState state, IListBandContent listBand) throws BirtException;
	public void endListBand(HandlerState state, IListBandContent listBand) throws BirtException;

	public void startContainer(HandlerState state, IContainerContent container) throws BirtException;
	public void endContainer(HandlerState state, IContainerContent container) throws BirtException;

	public void startContent(HandlerState state, IContent content) throws BirtException;
	public void endContent(HandlerState state, IContent content) throws BirtException;

	public void startGroup(HandlerState state, IGroupContent group) throws BirtException;
	public void endGroup(HandlerState state, IGroupContent group) throws BirtException;

	public void startTableGroup(HandlerState state, ITableGroupContent group) throws BirtException;
	public void endTableGroup(HandlerState state, ITableGroupContent group) throws BirtException;

	public void startListGroup(HandlerState state, IListGroupContent group) throws BirtException;
	public void endListGroup(HandlerState state, IListGroupContent group) throws BirtException;

	public void emitText(HandlerState state, ITextContent text) throws BirtException;

	public void emitData(HandlerState state, IDataContent data) throws BirtException;

	public void emitLabel(HandlerState state, ILabelContent label) throws BirtException;

	public void emitAutoText(HandlerState state, IAutoTextContent autoText) throws BirtException;

	public void emitForeign(HandlerState state, IForeignContent foreign) throws BirtException;

	public void emitImage(HandlerState state, IImageContent image) throws BirtException;

}
