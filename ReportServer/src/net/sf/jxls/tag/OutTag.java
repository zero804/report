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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import net.sf.jxls.parser.Expression;
import net.sf.jxls.transformation.ResultTransformation;
import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.Sheet;
import net.sf.jxls.transformer.SheetTransformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class OutTag extends BaseTag {
    
    protected static final Log log = LogFactory.getLog(OutTag.class);
    
    public static final String TAG_NAME = "out";
    
    private Configuration configuration = new Configuration();
    private TagContext tagContext;
    private String expr;
    private String formula;
    private String label;

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return TAG_NAME;
    }

    public TagContext getTagContext() {
        return tagContext;
    }

    public void init(TagContext context) {
        this.tagContext = context;
    }

    public ResultTransformation process(SheetTransformer sheetTransformer) {
        
        ResultTransformation resultTransformation = new ResultTransformation(0);

        if (expr != null) {
            
            // process expression cell

            try {
                Block block = getTagContext().getTagBody();
                int rowNum = block.getStartRowNum();
                int cellNum = block.getStartCellNum();
                
                Sheet jxlsSheet = getTagContext().getSheet();
                if (jxlsSheet != null) {
                    org.apache.poi.ss.usermodel.Sheet sheet = jxlsSheet.getPoiSheet();
                    if (sheet != null) {
                        Row row = sheet.getRow(rowNum);
                        if (row != null) {
                            Cell cell = row.getCell((short) cellNum);
                            if (cell != null) {
                                
                                Object value = new Expression(expr, tagContext.getBeans(), configuration).evaluate();
                                if (value == null) {
                                    cell.setCellValue(sheet.getWorkbook().getCreationHelper().createRichTextString(""));
                                } else if (value instanceof Double) {
                                    cell.setCellValue(((Double) value).doubleValue());
                                } else if (value instanceof BigDecimal) {
                                    cell.setCellValue(((BigDecimal) value).doubleValue());
                                } else if (value instanceof Date) {
                                    cell.setCellValue((Date) value);
                                }else if (value instanceof Calendar) {
                                    cell.setCellValue((Calendar) value);
                                } else if (value instanceof Integer) {
                                    cell.setCellValue(((Integer) value).intValue());
                                }else if (value instanceof Long) {
                                    cell.setCellValue(((Long) value).longValue());
                                } else {
                                    // fixing possible CR/LF problem
                                    String fixedValue = value.toString();
                                    if (fixedValue != null) {
                                        fixedValue = fixedValue.replaceAll("\r\n", "\n");
                                    }
                                    cell.setCellValue(sheet.getWorkbook().getCreationHelper().createRichTextString(fixedValue));
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Cell expression evaluation has failed.", e);
            }
        }
        
        return resultTransformation;
    }
}
