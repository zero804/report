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
 
 
package net.sf.jxls.util;

import java.util.List;
import java.util.Map;

import net.sf.jxls.formula.Formula;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author Leonid Vysochyn
 */
public class FormulaUtil {

    /**
     * This method updates formula cells
     *
     * @param sheet            Sheet to update
     * @param ignoreUnresolved Flag indicating should unresolved formulas be removed or just ignored
     */
    public static void updateFormulas(Sheet sheet, List formulas, Map listRanges, Map namedCells, boolean ignoreUnresolved) {
        for (int i = 0, c = formulas.size(); i < c; i++) {
            Formula formula = (Formula) formulas.get(i);
            String formulaString = formula.getAppliedFormula( listRanges, namedCells );
            Row hssfRow = sheet.getRow(formula.getRowNum().intValue());
            Cell hssfCell = hssfRow.getCell(formula.getCellNum().shortValue());
            if (formulaString != null) {
                hssfCell.setCellFormula(formulaString);
            } else {
                if (!ignoreUnresolved) {
                    hssfCell.setCellValue(hssfCell.getSheet().getWorkbook().getCreationHelper().createRichTextString(""));
                    formulas.remove(i--);
                }
            }
        }
    }

    /**
     * @param formulas {@link List} of {@link Formula} objects
     * @param formula {@link Formula} object to check
     * @return true if given {@link Formula} already exists in formulas list
     */
    static boolean formulaExists(List formulas, Formula formula) {
        for (int i = 0, c = formulas.size(); i < c; i++) {
            Formula cur = (Formula) formulas.get(i);
            if (cur.getFormula().equals(formula.getFormula())) {
                return true;
            }
        }
        return false;
    }


}
