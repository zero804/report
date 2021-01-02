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
 
 
package net.sf.jxls.parser;

import net.sf.jxls.formula.Formula;
import net.sf.jxls.tag.Tag;
import net.sf.jxls.transformer.Row;
import net.sf.jxls.transformer.RowCollection;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents excel cell
 *
 * @author Leonid Vysochyn
 */
public class Cell {

    private Row row;
    private Property collectionProperty;
    private org.apache.poi.ss.usermodel.Cell hssfCell;

    private Formula formula;
    private String label;
    private int dependentRowNumber;

    private String collectionName;

    private String hssfCellValue;
    private boolean empty;
    private String stringCellValue;
    private String metaInfo;

    private CellRangeAddress mergedRegion;

    private List expressions = new ArrayList();

    private Tag tag;

    private RowCollection rowCollection;


    public Cell(org.apache.poi.ss.usermodel.Cell hssfCell, Row row) {
        this.setPoiCell(hssfCell);
        this.setRow(row);
    }


    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public List getExpressions() {
        return expressions;
    }

    public void setExpressions(List expressions) {
        this.expressions = expressions;
    }

    public CellRangeAddress getMergedRegion() {
        return mergedRegion;
    }

    public int getDependentRowNumber() {
        return dependentRowNumber;
    }


    public RowCollection getRowCollection() {
        return rowCollection;
    }

    public void setRowCollection(RowCollection rowCollection) {
        this.rowCollection = rowCollection;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Property getCollectionProperty() {
        return collectionProperty;
    }

    public void setCollectionProperty(Property collectionProperty) {
        this.collectionProperty = collectionProperty;
    }

    public org.apache.poi.ss.usermodel.Cell getPoiCell() {
        return hssfCell;
    }

    public void setPoiCell(org.apache.poi.ss.usermodel.Cell hssfCell) {
        this.hssfCell = hssfCell;
        figureEmpty();
    }

    public void replaceCellWithNewShiftedBy(int shift){
        org.apache.poi.ss.usermodel.Cell newCell = row.getPoiRow().getCell(hssfCell.getColumnIndex() + shift);
        this.hssfCell = newCell;
        figureEmpty();
    }

    public String toCellName() {
        CellReference cellRef = new CellReference(getRow().getPoiRow().getRowNum(), getPoiCell().getColumnIndex(), false, false);
        return cellRef.formatAsString();
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public boolean isFormula() {
        return getFormula() != null;
    }


    public String getPoiCellValue() {
        return hssfCellValue;
    }

    public String getStringCellValue() {
        return stringCellValue;
    }

    public String getMetaInfo() {
        return metaInfo;
    }

    private void figureEmpty() {
        empty = getPoiCellValue() == null || getPoiCellValue().length() == 0 || getPoiCell().getCellType() == CellType.BLANK;
    }

    public boolean isEmpty() {
        return empty;
    }

    public boolean isNull() {
        return getPoiCell() == null;
    }

    public String toString() {
        return getPoiCellValue();
    }

    public void setDependentRowNumber(int dependentRowNumber) {
        this.dependentRowNumber = dependentRowNumber;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void setPoiCellValue(String hssfCellValue) {
        this.hssfCellValue = hssfCellValue;
        figureEmpty();
    }

    public void setStringCellValue(String stringCellValue) {
        this.stringCellValue = stringCellValue;
    }

    public void setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
    }

    public void setMergedRegion(CellRangeAddress mergedRegion) {
        this.mergedRegion = mergedRegion;
    }
}
