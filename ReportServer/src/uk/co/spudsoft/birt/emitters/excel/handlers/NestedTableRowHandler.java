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
import org.eclipse.birt.report.engine.content.ICellContent;
import org.eclipse.birt.report.engine.content.IRowContent;

import uk.co.spudsoft.birt.emitters.excel.HandlerState;
import uk.co.spudsoft.birt.emitters.excel.framework.Logger;

public class NestedTableRowHandler extends AbstractRealTableRowHandler {

	public NestedTableRowHandler(Logger log, IHandler parent, IRowContent row, int startCol) {
		super(log, parent, row, startCol);
	}

	@Override
	public void startRow(HandlerState state, IRowContent row) throws BirtException {
		log.debug( "startRow called with colOffset = ", startCol );
		super.startRow(state, row);
	}

	@Override
	public void startCell(HandlerState state, ICellContent cell) throws BirtException {
		log.debug( "startCell called with colOffset = ", startCol );
		state.setHandler(new NestedTableCellHandler(state.getEmitter(), log, this, cell, startCol));
		state.getHandler().startCell(state, cell);
	}

	@Override
	public void endRow(HandlerState state, IRowContent row) throws BirtException {
		super.endRow(state, row);
	}

	@Override
	protected boolean isNested() {
		return true;
	}
	
}
