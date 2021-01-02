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
 
 
package net.sf.jxls.formula;

import net.sf.jxls.tag.Point;
import net.sf.jxls.transformation.BlockTransformation;
import net.sf.jxls.transformation.DuplicateTransformation;
import net.sf.jxls.transformer.Workbook;
import net.sf.jxls.util.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;

import java.util.*;

/**
 * @author Leonid Vysochyn
 */
public class FormulaControllerImpl implements FormulaController {
    protected static final Log log = LogFactory.getLog(FormulaControllerImpl.class);

    protected Map sheetFormulasMap;

    public FormulaControllerImpl(Workbook workbook) {
        sheetFormulasMap = workbook.createFormulaSheetMap();
    }

    public void updateWorkbookFormulas(BlockTransformation transformation) {
        Set sheetNames = sheetFormulasMap.keySet();
        Formula formula, newFormula;
        Set cellRefs, newCellRefs;
        CellRef cellRef, newCellRef;
        List resultCells;
        String newCell;
        Point point, newPoint;
        Set cellRefsToRemove = new HashSet();
        Set formulasToRemove = new HashSet();
        for (Iterator iterator = sheetNames.iterator(); iterator.hasNext(); ) {
            String sheetName = (String) iterator.next();
            List formulas = (List) sheetFormulasMap.get(sheetName);
            formulasToRemove.clear();
            for (int i = 0, size = formulas.size(); i < size; i++) {
                formula = (Formula) formulas.get(i);
                List formulaPoints = null;
                Point formulaPoint = null;
                boolean transformFormula = false;
                if (formula.getSheet().getSheetName().equals(transformation.getBlock().getSheet().getSheetName())) {
                    transformFormula = true;
                    formulaPoint = new Point(formula.getRowNum().intValue(), formula.getCellNum().shortValue());
                    formulaPoints = transformation.transformCell(formulaPoint);
                }
                if (!transformFormula || (formulaPoints != null && !formulaPoints.isEmpty())) {
                    cellRefs = formula.getCellRefs();
                    cellRefsToRemove.clear();
                    for (Iterator iter = cellRefs.iterator(); iter.hasNext(); ) {
                        cellRef = (CellRef) iter.next();
                        if (!(transformation instanceof DuplicateTransformation && transformation.getBlock().contains(cellRef) &&
                                transformation.getBlock().contains(formula))) {
                            resultCells = transformation.transformCell(sheetName, cellRef);
                            if (resultCells != null) {
                                int len = resultCells.size();
                                if (len == 0) ;
                                else if (len == 1) {
                                    newCell = (String) resultCells.get(0);
                                    cellRef.update(newCell);
                                }
                                else if (len > 1) {
                                    cellRef.update(resultCells);
                                }
                            }
                            else {
                                cellRefsToRemove.add(cellRef);
                            }
                        }
                    }
//                cellRefs.removeAll( cellRefsToRemove );
                if( !cellRefsToRemove.isEmpty() ){
                    formula.removeCellRefs( cellRefsToRemove );
                }
                if (formula.updateReplacedRefCellsCollection()) {
                    formula.updateCellRefs();
                }
                    if(formulaPoints != null && !formulaPoints.isEmpty()){
                        if(formulaPoints.size() == 1){
                            newPoint = (Point) formulaPoints.get(0);
                            formula.setRowNum( newPoint.getRow() );
                            formula.setCellNum( (int)newPoint.getCol() );
                        }else{
                            List sheetFormulas = (List) sheetFormulasMap.get( formula.getSheet().getSheetName() );
                            for (int j = 1, num = formulaPoints.size(); j < num; j++) {
                                point = (Point) formulaPoints.get(j);
                                newFormula = new Formula( formula );
                                newFormula.setRowNum( point.getRow() );
                                newFormula.setCellNum((int) point.getCol());
                                newCellRefs = newFormula.getCellRefs();
                                for (Iterator iterator1 = newCellRefs.iterator(); iterator1.hasNext();) {
                                    newCellRef =  (CellRef) iterator1.next();
                                    if( transformation.getBlock().contains( newCellRef ) && transformation.getBlock().contains( formulaPoint ) ){
                                        newCellRef.update(transformation.getDuplicatedCellRef( sheetName, newCellRef.toString(), j));
                                    }
                                }
                                sheetFormulas.add( newFormula );
                            }
                        }
                    }
                }else{
                    if( formulaPoints == null ){
                        // remove formula
                        formulasToRemove.add( formula );
                    }
                }
            }
            formulas.removeAll( formulasToRemove );
        }
    }

    public Map getSheetFormulasMap() {
        return sheetFormulasMap;
    }

    public void writeFormulas(FormulaResolver formulaResolver) {
        Set sheetNames = sheetFormulasMap.keySet();
        for (Iterator iterator = sheetNames.iterator(); iterator.hasNext(); ) {
            String sheetName = (String) iterator.next();
            List formulas = (List) sheetFormulasMap.get(sheetName);
            for (int i = 0; i < formulas.size(); i++) {
                Formula formula = (Formula) formulas.get(i);
                String formulaString = formulaResolver.resolve(formula, null);
                if (formulaString != null) {
                    Cell hssfCell = Util.getOrCreateCell(formula.getSheet().getPoiSheet(), formula.getRowNum(), formula.getCellNum());
                    try {
                        hssfCell.setCellFormula(formulaString);
                    }
                    catch (RuntimeException e) {
                        log.error("Can't set formula: " + formulaString, e);
//                        hssfCell.setCellType( Cell.CELL_TYPE_BLANK );
                        throw new RuntimeException("Can't set formula: " + formulaString, e);
                    }
                }
            }
        }
    }

}
