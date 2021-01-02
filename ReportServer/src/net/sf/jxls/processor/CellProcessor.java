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
 
 
package net.sf.jxls.processor;

import java.util.Map;

import net.sf.jxls.parser.Cell;

/**
 * Allows dynamic processing of cell in excel workbook
 * @author Leonid Vysochyn
 */
public interface CellProcessor {
    /**
     * This method is invoked when cell is processed
     *
     * @param cell   {@link net.sf.jxls.parser.Cell} object with information about cell
     * @param namedCells Map with information about all named cells processed before
     */
    void processCell(final Cell cell, final Map namedCells);
}
