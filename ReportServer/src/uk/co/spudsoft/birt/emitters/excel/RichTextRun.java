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

package uk.co.spudsoft.birt.emitters.excel;

import org.apache.poi.ss.usermodel.Font;

/**
 * <p>
 * Class to capture the RichText information needed for nested (and HTML) cells.
 * </p><p>
 * In theory this information could be captured using the RichTextString class from POI, but
 * experiments found that to produce NullPoiiunterExceptions and multiple entries in the XLSX files.
 * </p> 
 * @author jtalbut
 *
 */
public class RichTextRun {
	/**
	 * The index of the first character to be formatted using this font.
	 */
	public int startIndex;
	/**
	 * The font to apply to characters following this.
	 */
	public Font font;
	
	public RichTextRun(int startIndex, Font font) {
		super();
		this.startIndex = startIndex;
		this.font = font;
	}
	/**
	 * For debug purposes.
	 */
	@Override
	public String toString() {
		return "RichTextRun [" + startIndex + ", " + font.toString().replaceAll("\n", "") + "]";
	}
	
}
