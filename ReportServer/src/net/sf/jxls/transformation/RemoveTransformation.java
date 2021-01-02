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
 
 
package net.sf.jxls.transformation;

import java.util.ArrayList;
import java.util.List;

import net.sf.jxls.formula.CellRef;
import net.sf.jxls.tag.Block;
import net.sf.jxls.tag.Point;

/**
 * Remove transformation
 * @author Leonid Vysochyn
 */
public class RemoveTransformation extends BlockTransformation {

    public RemoveTransformation(Block block) {
        super(block);
    }

    public Block getBlockAfterTransformation() {
        return null;
    }


    public List transformCell(Point p) {
        List cells = null;
        if( !block.contains( p ) ){
            cells = new ArrayList(1);
            cells.add(p);
        }
        return cells;
    }

    public List transformCell(String sheetName, CellRef cellRef) {
        List cells = null;
        String refSheetName = cellRef.getSheetName();
        if( block.getSheet().getSheetName().equalsIgnoreCase( refSheetName ) || (cellRef.getSheetName() == null && block.getSheet().getSheetName().equalsIgnoreCase( sheetName ))){
            if( !block.contains( cellRef.getRowNum(), cellRef.getColNum() ) ){
                cells = new ArrayList();
                cells.add( cellRef.toString() );
            }
        }else{
            cells = new ArrayList(1);
            cells.add( cellRef.toString() );
        }
        return cells;
    }

    public String toString() {
        return "RemoveTransformation: {" + super.toString() + "}";
    }
}
