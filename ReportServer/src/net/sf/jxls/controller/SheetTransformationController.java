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

import net.sf.jxls.tag.Block;
import net.sf.jxls.transformer.RowCollection;
import net.sf.jxls.transformer.Sheet;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 * Interface for controlling all excel sheet transformations
 * @author Leonid Vysochyn
 */
public interface SheetTransformationController {
    /**
     * @return {@link net.sf.jxls.transformer.Sheet} corresponding worksheet object
     */
    Sheet getSheet();

    /**
     * This method duplicates given block to the right
     * @param block - {@link Block} to process
     * @param n - number of times to duplicate given block
     * @return shift number based on number of affected rows
     */
    int duplicateRight(Block block, int n);

    /**
     * This method duplicates given block down
     * @param block - {@link Block} to process
     * @param n - number of times to duplicate given block
     * @return shift number based on number of affected rows
     */
    int duplicateDown( Block block, int n );

    /**
     * This method removes borders around given block shifting all other rows
     * @param block - {@link Block} to process
     */
    void removeBorders(Block block);

    /**
     * This method removes left and right borders for the block
     * @param block - {@link Block} to process
     */
    void removeLeftRightBorders(Block block);

    /**
     * Clears row cells in a given range
     * @param row {@link Row} to process
     * @param startCellNum - start cell number to clear
     * @param endCellNum   - end cell number to clear
     */
    void removeRowCells(Row row, int startCellNum, int endCellNum);

    /**
     * Deletes the body of the block
     * @param block {@link Block} to process
     */
    void removeBodyRows(Block block);

    /**
     * Duplicates given row cells according to passed {@link RowCollection} object
     * @param rowCollection - {@link RowCollection} object defining duplicate numbers and cell ranges
     */
    void duplicateRow(RowCollection rowCollection);

    /**
     * @return list of all transformation applied by this controller
     */
    List getTransformations();

}
