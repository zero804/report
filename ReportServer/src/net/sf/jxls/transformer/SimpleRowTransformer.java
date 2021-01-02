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
 
 
package net.sf.jxls.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.jxls.controller.SheetTransformationController;
import net.sf.jxls.parser.Cell;
import net.sf.jxls.processor.CellProcessor;
import net.sf.jxls.transformation.ResultTransformation;

/**
 * @author Leonid Vysochyn
 */
public class SimpleRowTransformer extends BaseRowTransformer {

    Configuration configuration;
    List cellProcessors;
    List cells = new ArrayList();

    private ResultTransformation resultTransformation;

    public SimpleRowTransformer(Row row, List cellProcessors, Configuration configuration) {
        this.row = row;
        this.cellProcessors = cellProcessors;
        this.configuration = configuration;
    }

    public void addCell(Cell cell){
        if( !cell.isEmpty() ){
            cells.add( cell );
        }
    }

    public ResultTransformation getTransformationResult() {
        return resultTransformation;
    }

    public List getCells() {
        return cells;
    }


    public ResultTransformation transform(SheetTransformationController stc, SheetTransformer sheetTransformer, Map beans, ResultTransformation previousTransformation){
        CellTransformer cellTransformer = new CellTransformer( configuration );
        if( cells.isEmpty() ){
//            throw new RuntimeException("Don't expect to execute this code");
            for (int j = 0, c = row.getCells().size(); j < c; j++) {
                Cell cell = (Cell) row.getCells().get(j);
                if (configuration.getCellKeyName() != null) {
                    beans.put(configuration.getCellKeyName(), cell.getPoiCell() );
                }                
                applyCellProcessors(row.getSheet(), cell );
                cellTransformer.transform( cell );
            }
        }else{
            for (int i = 0, c = cells.size(); i < c; i++) {
                Cell cell = (Cell) cells.get(i);
                if (configuration.getCellKeyName() != null) {
                    beans.put(configuration.getCellKeyName(), cell.getPoiCell() );
                }                

                if( previousTransformation != null && cell.getPoiCell().getColumnIndex()>= previousTransformation.getStartCellShift()
                        && previousTransformation.getStartCellShift() != 0){
                    cell.replaceCellWithNewShiftedBy(previousTransformation.getLastCellShift());
                }
                applyCellProcessors( row.getSheet(), cell );
                cellTransformer.transform( cell );
            }
        }
        resultTransformation = new ResultTransformation();
        return resultTransformation;
    }

    /**
     * Applies all registered CellProcessors to a cell
     * @param sheet - {@link Sheet} to apply Cell Processors to
     * @param cell - {@link net.sf.jxls.parser.Cell} object with cell information
     */
    private void applyCellProcessors(Sheet sheet, Cell cell) {
        for (int i = 0, c = cellProcessors.size(); i < c; i++) {
            CellProcessor cellProcessor = (CellProcessor) cellProcessors.get(i);
            cellProcessor.processCell(cell, sheet.getNamedCells());
        }
    }




}
