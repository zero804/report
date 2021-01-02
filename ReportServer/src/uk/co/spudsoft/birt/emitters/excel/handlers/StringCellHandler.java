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
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.content.IAutoTextContent;
import org.eclipse.birt.report.engine.content.ICellContent;
import org.eclipse.birt.report.engine.content.IContainerContent;
import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.content.IDataContent;
import org.eclipse.birt.report.engine.content.IForeignContent;
import org.eclipse.birt.report.engine.content.IImageContent;
import org.eclipse.birt.report.engine.content.ILabelContent;
import org.eclipse.birt.report.engine.content.ITableContent;
import org.eclipse.birt.report.engine.content.ITextContent;
import org.eclipse.birt.report.engine.css.engine.StyleConstants;
import org.eclipse.birt.report.engine.emitter.IContentEmitter;
import org.eclipse.birt.report.engine.layout.pdf.util.HTML2Content;

import uk.co.spudsoft.birt.emitters.excel.Area;
import uk.co.spudsoft.birt.emitters.excel.HandlerState;
import uk.co.spudsoft.birt.emitters.excel.framework.Logger;

public class StringCellHandler extends CellContentHandler {

	private String result = null;
	
	public StringCellHandler(IContentEmitter emitter, Logger log, IHandler parent, ICellContent cell) {
		super(emitter, log, parent, cell);
	}

	@Override
	public void endCell(HandlerState state, ICellContent cell) throws BirtException {
		endCellContent(state, cell, lastElement, null, null );
	}
	
	@Override
	protected void endCellContent(HandlerState state, ICellContent birtCell, IContent element, Cell cell, Area area) {
		if( lastValue != null ) {
			if( lastValue instanceof String ) {
				result = (String)lastValue;
			} else {
				result = lastValue.toString();
			}
		}
	}

	public void visit( Object obj ) throws BirtException {
		if( obj instanceof ICellContent ) {
			contentVisitor.visitCell( (ICellContent)obj, null );
		} else if( obj instanceof ILabelContent ) {
			contentVisitor.visitLabel( (ILabelContent)obj, null );
		} else if( obj instanceof ITextContent ) {
			contentVisitor.visitText( (ITextContent)obj, null );
		}  else if( obj instanceof IAutoTextContent ) {
			contentVisitor.visitAutoText( (IAutoTextContent)obj, null );
		}  else if( obj instanceof IForeignContent ) {
			contentVisitor.visitForeign( (IForeignContent)obj, null );
		} else {
			log.warn(0, "Not visiting " + obj.getClass(), null);
		}
	}

	public String getString() {
		if( result == null ) {
			if( lastValue != null ) {
				if( lastValue instanceof String ) {
					result = (String)lastValue;
				} else {
					result = lastValue.toString();
				}
			}
		} 
		if( result == null ) {
			result = "";
		}
		return result;
	}
	

	@Override
	public void startTable(HandlerState state, ITableContent table) throws BirtException {
		state.setHandler(new FlattenedTableHandler(this, log, this, table));
		state.getHandler().startTable(state, table);
	}

	@Override
	public void emitText(HandlerState state, ITextContent text) throws BirtException {
		String textText = text.getText();
		log.debug( "text:", textText );
		emitContent(state,text,textText, ( ! "inline".equals( getStyleProperty(text, StyleConstants.STYLE_DISPLAY, "block") ) ) );
	}

	@Override
	public void emitData(HandlerState state, IDataContent data) throws BirtException {
		emitContent(state,data,data.getValue(), ( ! "inline".equals( getStyleProperty(data, StyleConstants.STYLE_DISPLAY, "block") ) ) );
	}

	@Override
	public void emitLabel(HandlerState state, ILabelContent label) throws BirtException {
		String labelText = ( label.getLabelText() != null ) ? label.getLabelText() : label.getText();
		log.debug( "labelText:" + labelText );
		emitContent(state,label,labelText, ( ! "inline".equals( getStyleProperty(label, StyleConstants.STYLE_DISPLAY, "block") ) ));
	}

	@Override
	public void emitAutoText(HandlerState state, IAutoTextContent autoText) throws BirtException {
		emitContent(state,autoText,autoText.getText(), ( ! "inline".equals( getStyleProperty(autoText, StyleConstants.STYLE_DISPLAY, "block") ) ) );
	}

	@Override
	public void emitForeign(HandlerState state, IForeignContent foreign) throws BirtException {

		log.debug( "Handling foreign content of type " + foreign.getRawType() );
		if ( IForeignContent.HTML_TYPE.equalsIgnoreCase( foreign.getRawType( ) ) )
		{
			HTML2Content.html2Content( foreign );
			contentVisitor.visitChildren( foreign, null );			
		}
	}

	@Override
	public void emitImage(HandlerState state, IImageContent image) throws BirtException {
	}

	@Override
	public void endContainer(HandlerState state, IContainerContent container) throws BirtException {
		lastCellContentsWasBlock = ( ! "inline".equals( getStyleProperty(container, StyleConstants.STYLE_DISPLAY, "block") ) );
	}
			
	
	
}
