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

import java.util.List;

import net.sf.jxls.formula.CellRef;
import net.sf.jxls.tag.Block;
import net.sf.jxls.tag.Point;

/**
 * This class defines common {@link net.sf.jxls.tag.Block} transformation behaviour
 * @author Leonid Vysochyn
 */
public abstract class BlockTransformation {
    protected Block block;

    /**
     * @param block - defines transformation {@link net.sf.jxls.tag.Block}
     */
    protected BlockTransformation(Block block) {
        this.block = block;
    }


    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    boolean contains(int row, int col){
        return block.contains( row, col);
    }

    boolean contains(Point p){
        return block.contains( p );
    }


    public abstract Block getBlockAfterTransformation();

    /**
     * Transforms given spreadsheet cell
     * @param p - {@link net.sf.jxls.tag.Point} representing spreadsheet cell to transform
     * @return {@link List} of {@link Point} objects which are result of source cell transformation
     */
    public abstract List transformCell(Point p);

    /**
     * Transforms given spreadsheet cell
     * @param sheetName - indicates sheet containing cell to transform
     * @param cellRef - {@link CellRef} object representing spreadsheet cell to transform
     * @return {@link List} of cell names which are result of source cell transformation
     */
    public abstract List transformCell(String sheetName, CellRef cellRef);

    public String getDuplicatedCellRef(String sheetName, String cell, int duplicateBlock){
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object obj) {
        if( obj != null && obj instanceof BlockTransformation ){
            BlockTransformation bt = (BlockTransformation) obj;
            return ((block!=null && block.equals( bt.block )) || (block == null && bt.block == null));
        }
        return false;
    }

    public int hashCode() {
        return (block != null ? block.hashCode() : 0);
    }

    public String toString() {
        if( block != null ){
            return block.toString();
        }
        return "";
    }
}
