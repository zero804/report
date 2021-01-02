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

import net.sf.jxls.formula.ListRange;
import net.sf.jxls.parser.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents excel worksheet 
 * @author Leonid Vysochyn
 */
public class Sheet {

    Workbook workbook;

    /**
     * POI Excel workbook object
     */
    org.apache.poi.ss.usermodel.Workbook hssfWorkbook;

    /**
     * POI Excel sheet representation
     */
    org.apache.poi.ss.usermodel.Sheet hssfSheet;
    /**
     * This variable stores all list ranges found while processing template file
     */
    private Map listRanges = new HashMap();
    /**
     * Stores all named Cell objects
     */
    private Map namedCells = new HashMap();

    Configuration configuration = new Configuration();

    public Sheet() {
    }

    public Sheet(org.apache.poi.ss.usermodel.Workbook hssfWorkbook, org.apache.poi.ss.usermodel.Sheet hssfSheet, Configuration configuration) {
        this.hssfWorkbook = hssfWorkbook;
        this.hssfSheet = hssfSheet;
        this.configuration = configuration;
    }

    public Sheet(org.apache.poi.ss.usermodel.Workbook hssfWorkbook, org.apache.poi.ss.usermodel.Sheet hssfSheet) {
        this.hssfWorkbook = hssfWorkbook;
        this.hssfSheet = hssfSheet;
    }

    public String getSheetName(){
        return sheetName;
    }

    public void setSheetName(String sheetName){
        this.sheetName = sheetName; 
    }

    String sheetName;

    public void initSheetName(){
        for(int i = 0; i < hssfWorkbook.getNumberOfSheets(); i++){
            org.apache.poi.ss.usermodel.Sheet sheet = hssfWorkbook.getSheetAt( i );
            if( sheet == hssfSheet ){
                sheetName = hssfWorkbook.getSheetName( i );
                if( sheetName.indexOf(' ') >=0 ){
                    sheetName = "'" + sheetName + "'";
                }
            }
        }
    }

    public org.apache.poi.ss.usermodel.Workbook getPoiWorkbook() {
        return hssfWorkbook;
    }

    public void setPoiWorkbook(org.apache.poi.ss.usermodel.Workbook hssfWorkbook) {
        this.hssfWorkbook = hssfWorkbook;
    }

    public void setPoiSheet(org.apache.poi.ss.usermodel.Sheet hssfSheet) {
        this.hssfSheet = hssfSheet;
    }

    public org.apache.poi.ss.usermodel.Sheet getPoiSheet() {
        return hssfSheet;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Map getListRanges() {
        return listRanges;
    }

    public Map getNamedCells() {
        return namedCells;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public void addNamedCell(String label, Cell cell){
        namedCells.put( label, cell );
    }

    public void addListRange(String name, ListRange range){
        listRanges.put( name, range );
    }

    public int getMaxColNum(){
        int maxColNum = 0;
        for(int i = hssfSheet.getFirstRowNum(); i <= hssfSheet.getLastRowNum(); i++){
            Row hssfRow = hssfSheet.getRow( i );
            if( hssfRow != null && (hssfRow.getLastCellNum() > maxColNum)){
                    maxColNum = hssfRow.getLastCellNum();
            }
        }
        return maxColNum;
    }


}
