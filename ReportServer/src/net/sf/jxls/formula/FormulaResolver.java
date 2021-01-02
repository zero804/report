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

import net.sf.jxls.controller.WorkbookCellFinder;

/**
 * An interface to resolve coded formulas into real Excel formulas
 * @author Leonid Vysochyn
 */
public interface FormulaResolver {
    /**
     * This method resolves original formula coded in XLS template file into the real Excel formula
     * @param sourceFormula - {@link Formula} object representing coded formula found in XLS template file
     * @param cellFinder    - {@link net.sf.jxls.controller.WorkbookCellFinder}
     * @return Real Excel formula to be placed instead of the coded one
     */
    String resolve(Formula sourceFormula, WorkbookCellFinder cellFinder);
}
