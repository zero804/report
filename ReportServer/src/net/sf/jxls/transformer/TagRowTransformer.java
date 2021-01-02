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

import net.sf.jxls.controller.SheetTransformationController;
import net.sf.jxls.parser.Cell;
import net.sf.jxls.tag.Block;
import net.sf.jxls.tag.Tag;
import net.sf.jxls.transformation.ResultTransformation;

import java.util.Map;

/**
 * Implementation of {@link RowTransformer} for transforming jx tags
 * @author Leonid Vysochyn
 */
public class TagRowTransformer extends BaseRowTransformer {

    Tag tag;

    private ResultTransformation resultTransformation;

    public TagRowTransformer(Row row, Cell cell) {
        this.row = row;
        this.tag = cell.getTag();
    }

    public ResultTransformation getTransformationResult() {
        return resultTransformation;
    }

    public ResultTransformation transform(SheetTransformationController stc, SheetTransformer sheetTransformer, Map beans, ResultTransformation previousTransformation) {
        tag.getTagContext().setSheetTransformationController( stc );
        resultTransformation = tag.process( sheetTransformer );
        return resultTransformation;
    }

    public Block getTransformationBlock() {
        return tag.getTagContext().getTagBody();
    }

    public void setTransformationBlock(Block block) {
        tag.getTagContext().setTagBody( block );
    }

}
