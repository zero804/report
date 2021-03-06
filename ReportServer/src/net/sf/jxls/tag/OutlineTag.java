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
 
 
package net.sf.jxls.tag;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformation.ResultTransformation;
import net.sf.jxls.transformer.SheetTransformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author Leonid Vysochyn
 */
public class OutlineTag extends BaseTag{
    protected static final Log log = LogFactory.getLog(OutlineTag.class);

    public static final String TAG_NAME = "outline";

    public OutlineTag() {
        name = TAG_NAME;
    }

    private boolean detail;

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public ResultTransformation process(SheetTransformer sheetTransformer) {
        if( log.isDebugEnabled() ){
            log.info("jx:outline tag processing..");
        }

        Block body = tagContext.getTagBody();
        if( body.getNumberOfRows()==1 ){
            log.warn("jx:outline for columns is not supported. Ignoring.");
        }
        int shiftNumber = 0;

        ResultTransformation shift = new ResultTransformation(0);
        tagContext.getSheetTransformationController().removeBorders(body);
        shiftNumber += -2;
        try {
            ResultTransformation processResult = sheetTransformer.processRows(tagContext.getSheetTransformationController(), tagContext.getSheet(), body.getStartRowNum(), body.getEndRowNum(), tagContext.getBeans(), null );
            if( body.getStartRowNum() <= body.getEndRowNum() + processResult.getLastRowShift() ){
                groupRows( body.getStartRowNum(), body.getEndRowNum() + processResult.getLastRowShift() );
            }
            shift.add( processResult );
        } catch (ParsePropertyException e) {
            log.error("Can't parse property ", e);
        }

        return shift.add( new ResultTransformation(0, shiftNumber) );
    }

    private void groupRows(int startRowNum, int endRowNum) {
        Sheet hssfSheet = tagContext.getSheet().getPoiSheet();
        hssfSheet.groupRow( startRowNum, endRowNum );
        hssfSheet.setRowGroupCollapsed( startRowNum, !detail);
    }
}
