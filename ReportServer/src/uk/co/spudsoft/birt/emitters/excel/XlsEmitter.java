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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * XlsEmitter is the leaf class for implementing the ExcelEmitter with HSSFWorkbook.
 * @author Jim Talbut
 *
 */
public class XlsEmitter extends ExcelEmitter {

	/**
	 */
	public XlsEmitter() {
		super(StyleManagerHUtils.getFactory());
		log.debug("Constructed XlsEmitter");
	}
	
	public String getOutputFormat() {
		return "xls";
	}

	protected Workbook createWorkbook() {
		return new HSSFWorkbook();
	}
	
	protected Workbook openWorkbook( File templateFile ) throws IOException {
		InputStream stream = new FileInputStream( templateFile );
		try {
			return new HSSFWorkbook( stream );
		} finally {
			stream.close();
		}
	}
	
}
