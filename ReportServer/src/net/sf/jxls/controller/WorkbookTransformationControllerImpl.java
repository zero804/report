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

import java.util.ArrayList;
import java.util.List;

import net.sf.jxls.transformer.Workbook;

/**
 * Simple implementation of {@link WorkbookTransformationController} based on the list of SheetTransformationControllers
 * @author Leonid Vysochyn
 */
public class WorkbookTransformationControllerImpl implements WorkbookTransformationController {
    List sheetTransformationControllers = new ArrayList();

    Workbook workbook;

    public WorkbookTransformationControllerImpl(Workbook hssfWorkbook) {
        this.workbook = hssfWorkbook;
    }

    public List getSheetTransformationControllers() {
        return sheetTransformationControllers;
    }

    public void setSheetTransformationControllers(List sheetTransformationControllers) {
        this.sheetTransformationControllers = sheetTransformationControllers;
    }

    public void addSheetTransformationController(SheetTransformationController sheetTransformationController) {
        sheetTransformationControllers.add( sheetTransformationController );
    }

    public void removeSheetTransformationController(SheetTransformationController sheetTransformationController) {
        sheetTransformationControllers.remove( sheetTransformationController );
    }

    public Workbook getWorkbook() {
        return workbook;
    }

}
