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
 
 
/*  
 *   Copyright 2012 OSBI Ltd
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.legacysaiku.olap.dto.resultset;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The Class CellInfo.
 */
public class DataCell extends AbstractBaseCell implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double rawNumber = null;

    private List<Integer> coordinates = null;

    private String formatString = null; // Definition of the property which holds the format string
                                        // used to format cell values.

    private Map<String,String> properties = new HashMap<String, String>();

    /**
     * 
     * Blank constructor for serialization purposes, don't use it.
     * 
     */
    public DataCell() {
        super();
    }

    /**
     * 
     * Construct a Data Cell containing olap data.
     * 
     */
    public DataCell(final boolean right, final boolean sameAsPrev, List<Integer> coordinates) {
        super();
        this.right = right;
        this.sameAsPrev = sameAsPrev;
        this.coordinates = coordinates;
    }

    public String getFormatString() {
        return formatString;
    }

    public void setFormatString(String formatString) {
        this.formatString = formatString;
    }

    public Number getRawNumber() {
        return rawNumber;
    }

    public void setRawNumber(final Double rawNumber) {
        this.rawNumber = rawNumber;
    }

    public List<Integer> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Integer> coordinates) {
        this.coordinates = coordinates;
    }

    public void setProperty(String name, String value){
        properties.put(name, value);
    }

    public void setProperties(Map<String, String> props) {
        properties.putAll(props);
    }

    public Map<String, String> getProperties(){
        return properties;
    }

    public String getProperty(String name){
        return properties.get(name);
    }



    @Override
    public String toString() {
        return "DataCell{" +
                ", rawNumber=" + rawNumber +
                ", coordinates=" + coordinates +
                ", formatString='" + formatString +
                '}';
    }
}
