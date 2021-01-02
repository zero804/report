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

import org.apache.poi.ss.util.CellRangeAddress;


/**
 * Represents merged region
 * @author Leonid Vysochyn
 */
public class MergedRegion {
    private CellRangeAddress region;
    private RowCollection rowCollection;
    private int index;

    public MergedRegion(CellRangeAddress region, RowCollection rowCollection) {
        this.region = region;
        this.rowCollection = rowCollection;
    }

    public MergedRegion(CellRangeAddress region, int index) {
        this.region = region;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public CellRangeAddress getRegion() {
        return region;
    }

    public void setRegion(CellRangeAddress region) {
        this.region = region;
    }

    public RowCollection getRowCollection() {
        return rowCollection;
    }

    public void setRowCollection(RowCollection rowCollection) {
        this.rowCollection = rowCollection;
    }
}
