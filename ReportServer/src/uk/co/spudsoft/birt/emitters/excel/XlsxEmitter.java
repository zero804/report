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

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * XlsxEmitter is the leaf class for implementing the ExcelEmitter with XSSFWorkbook.
 * @author Jim Talbut
 *
 */
public class XlsxEmitter extends ExcelEmitter {
	
	/**
	 */
	public XlsxEmitter() {
		super(StyleManagerXUtils.getFactory());
		log.debug("Constructed XlsxEmitter");
	}

	public String getOutputFormat() {
		return "xlsx";
	}

	protected Workbook createWorkbook() {
		return new XSSFWorkbook();
	}
	
	protected Workbook openWorkbook( File templateFile ) throws IOException {
		InputStream stream = new FileInputStream( templateFile );
		try {
			return new XSSFWorkbook( stream );
		} finally {
			stream.close();
		}
	}

}
