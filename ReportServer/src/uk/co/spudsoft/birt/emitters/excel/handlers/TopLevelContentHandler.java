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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.content.IDataContent;
import org.eclipse.birt.report.engine.content.IForeignContent;
import org.eclipse.birt.report.engine.content.IImageContent;
import org.eclipse.birt.report.engine.content.ILabelContent;
import org.eclipse.birt.report.engine.content.ITextContent;
import org.eclipse.birt.report.engine.css.engine.StyleConstants;
import org.eclipse.birt.report.engine.emitter.IContentEmitter;
import org.eclipse.birt.report.engine.layout.pdf.util.HTML2Content;

import uk.co.spudsoft.birt.emitters.excel.Coordinate;
import uk.co.spudsoft.birt.emitters.excel.HandlerState;
import uk.co.spudsoft.birt.emitters.excel.framework.Logger;

public class TopLevelContentHandler extends CellContentHandler {

	public TopLevelContentHandler(IContentEmitter emitter, Logger log, IHandler parent) {
		super(emitter, log, parent, null);
	}
	
	
	@Override
	public void emitText(HandlerState state, ITextContent text) throws BirtException {
		log.debug( "Creating row ", state.rowNum, " for text" );
		state.currentSheet.createRow( state.rowNum );

		emitContent(state, text, text.getText(), ( ! "inline".equals( getStyleProperty(text, StyleConstants.STYLE_DISPLAY, "block") ) ) );

		Cell currentCell = state.currentSheet.getRow(state.rowNum).createCell( 0 );
		currentCell.setCellType(CellType.BLANK);
				
		endCellContent(state, null, text, currentCell, null);

		++state.rowNum;
		state.setHandler(parent);
	}

	@Override
	public void emitData(HandlerState state, IDataContent data) throws BirtException {
		log.debug( "Creating row ", state.rowNum, " for data" );
		state.currentSheet.createRow( state.rowNum );

		emitContent(state, data, data.getValue(), ( ! "inline".equals( getStyleProperty(data, StyleConstants.STYLE_DISPLAY, "block") ) ) );

		Cell currentCell = state.currentSheet.getRow(state.rowNum).createCell( 0 );
		currentCell.setCellType(CellType.BLANK);
				
		endCellContent(state, null, data, currentCell, null);

		++state.rowNum;
		state.setHandler(parent);
	}

	@Override
	public void emitLabel(HandlerState state, ILabelContent label) throws BirtException {
		log.debug( "Creating row ", state.rowNum, " for label" );
		state.currentSheet.createRow( state.rowNum );

		String labelText = ( label.getLabelText() != null ) ? label.getLabelText() : label.getText();
		emitContent(state,label,labelText, ( ! "inline".equals( getStyleProperty(label, StyleConstants.STYLE_DISPLAY, "block") ) ));

		Cell currentCell = state.currentSheet.getRow(state.rowNum).createCell( 0 );
		currentCell.setCellType(CellType.BLANK);
				
		endCellContent(state, null, label, currentCell, null);

		++state.rowNum;
		state.setHandler(parent);
	}

	@Override
	public void emitForeign(HandlerState state, IForeignContent foreign) throws BirtException {
		
		log.debug( "Handling foreign content of type ", foreign.getRawType() );
		if ( IForeignContent.HTML_TYPE.equalsIgnoreCase( foreign.getRawType( ) ) )
		{
			HTML2Content.html2Content( foreign );
			contentVisitor.visitChildren( foreign, null );			
		}
		
		state.setHandler(parent);
	}

	@Override
	public void emitImage(HandlerState state, IImageContent image) throws BirtException {
		log.debug( "Creating row ", state.rowNum, " for image" );
		state.currentSheet.createRow( state.rowNum );

		recordImage(state, new Coordinate( state.rowNum, 0 ), image, true);
		Cell currentCell = state.currentSheet.getRow(state.rowNum).createCell( 0 );
		currentCell.setCellType(CellType.BLANK);
				
		endCellContent(state, null, image, currentCell, null);

		++state.rowNum;
		state.setHandler(parent);
	}
	

	
}
