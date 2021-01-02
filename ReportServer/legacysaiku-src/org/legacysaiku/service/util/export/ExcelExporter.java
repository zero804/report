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
 
 
/*  
 *   Copyright 2012 OSBI Ltd
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.legacysaiku.service.util.export;

import java.util.List;

import org.olap4j.CellSet;
import org.legacysaiku.olap.dto.SaikuDimensionSelection;
import org.legacysaiku.olap.dto.resultset.CellDataSet;
import org.legacysaiku.olap.util.OlapResultSetUtil;
import org.legacysaiku.olap.util.formatter.HierarchicalCellSetFormatter;
import org.legacysaiku.olap.util.formatter.ICellSetFormatter;
import org.legacysaiku.service.util.export.excel.ExcelWorksheetBuilder;

public class ExcelExporter {

	public static byte[] exportExcel(CellSet cellSet, List<SaikuDimensionSelection> filters) {
		return exportExcel(cellSet, new HierarchicalCellSetFormatter(), filters);
	}

	public static byte[] exportExcel(CellSet cellSet,
                                     ICellSetFormatter formatter,
                                     List<SaikuDimensionSelection> filters) {
		CellDataSet table = OlapResultSetUtil.cellSet2Matrix(cellSet, formatter);
		return getExcel(table, filters);
	}

	private static byte[] getExcel(CellDataSet table, List<SaikuDimensionSelection> filters) {
        // TBD Sheet name is parametric. Useful for future ideas or improvements
        ExcelWorksheetBuilder worksheetBuilder = new ExcelWorksheetBuilder(table, filters, "Sheet 1");
        return worksheetBuilder.build();
	}
}
