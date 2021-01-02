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

import java.util.List;
import java.util.Map;

import net.sf.jxls.controller.SheetTransformationController;
import net.sf.jxls.processor.RowProcessor;
import net.sf.jxls.tag.Block;
import net.sf.jxls.transformation.ResultTransformation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Controls a list of transformers
 * @author Leonid Vysochyn
 */
public class ChainTransformer{
    protected static final Log log = LogFactory.getLog(ChainTransformer.class);

    List transformers;
    Sheet sheet;
    List rowProcessors;
    Row parentRow;

    public ChainTransformer(List transformers, Sheet sheet, List rowProcessors, Row parentRow) {
        this.transformers = transformers;
        this.sheet = sheet;
        this.rowProcessors = rowProcessors;
        this.parentRow = parentRow;
    }

    public ChainTransformer(List transformers, List rowProcessors, Row parentRow) {
        this.transformers = transformers;
        this.rowProcessors = rowProcessors;
        this.parentRow = parentRow;
    }

    public ChainTransformer(List transformers, List rowProcessors) {
        this.transformers = transformers;
        this.rowProcessors = rowProcessors;
    }

    public ChainTransformer(List transformers) {
        this.transformers = transformers;
    }



    ResultTransformation transform(SheetTransformationController stc, SheetTransformer sheetTransformer, Map beans){
        ResultTransformation resultTransformation = new ResultTransformation();
        for (int i = 0, c=transformers.size(); i < c; i++) {
            RowTransformer rowTransformer = (RowTransformer) transformers.get(i);
            Block transformationBlock = rowTransformer.getTransformationBlock();
            transformationBlock = resultTransformation.transformBlock( transformationBlock );
            rowTransformer.setTransformationBlock( transformationBlock );
            log.debug(rowTransformer.getClass().getName() + ", " + rowTransformer.getTransformationBlock());
            //rowTransformer
            Row row = rowTransformer.getRow();
            row.setParentRow( parentRow );
            applyRowProcessors(row );
//            Util.writeToFile("beforeTransformBlock.xls", sheet.getPoiWorkbook());
            resultTransformation.add( rowTransformer.transform(stc, sheetTransformer, beans, resultTransformation) );
//            Util.writeToFile("afterTransformBlock.xls", sheet.getPoiWorkbook());

        }
        return resultTransformation;
    }

    /**
     * Applies all registered RowProcessors to a row
     * @param row - {@link Row} object with row information
     */
    private void applyRowProcessors(Row row) {
        for (int i = 0, c=rowProcessors.size(); i < c; i++) {
            RowProcessor rowProcessor = (RowProcessor) rowProcessors.get(i);
            rowProcessor.processRow(row, sheet.getNamedCells());
        }
    }


}
