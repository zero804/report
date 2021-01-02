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
 
 
package net.sf.jxls.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.util.CellReference;

/**
 * Simple implementation of {@link net.sf.jxls.controller.WorkbookCellFinder} based on SheetCellFinder mapping to corresponding worksheets
 * @author Leonid Vysochyn
 */
public class WorkbookCellFinderImpl implements WorkbookCellFinder {

    Map sheetCellFinderMapping = new HashMap();

    public WorkbookCellFinderImpl() {
    }

    public WorkbookCellFinderImpl(Map sheetCellFinderMapping) {
        this.sheetCellFinderMapping = sheetCellFinderMapping;
    }

    public List findCell(String sheetName, String cellName) {
        CellReference cellReference = new CellReference( cellName );
        int colNum = cellReference.getCol();
        int rowNum = cellReference.getRow();
        return findCell( sheetName, rowNum, colNum );
    }

    public List findCell(String sheetName, int rowNum, int colNum) {
        if( !sheetCellFinderMapping.containsKey( sheetName ) ){
            throw new IllegalArgumentException("Can't find sheet with name " + sheetName + " used in formula cell reference");
        }
        return ((SheetCellFinder)sheetCellFinderMapping.get( sheetName )).findCell( rowNum, colNum );
    }

}
