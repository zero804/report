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
import java.util.Iterator;
import java.util.List;

/**
 * Base class for {@link Transformation} interface implementations
 * @author Leonid Vysochyn
 */
public abstract class BaseTransformation implements Transformation{
    int firstRowNum;
    int lastRowNum;

    List transformations = new ArrayList();

    protected BaseTransformation() {
    }

    protected BaseTransformation(int firstRowNum, int lastRowNum) {
        this.firstRowNum = firstRowNum;
        this.lastRowNum = lastRowNum;
    }


    public void addTransformation( Transformation transformation ){
        transformations.add( transformation );
    }

    public int getFirstRowNum() {
        return firstRowNum;
    }

    public void setFirstRowNum(int firstRowNum) {
        this.firstRowNum = firstRowNum;
    }

    public int getLastRowNum() {
        return lastRowNum;
    }

    public void setLastRowNum(int lastRowNum) {
        this.lastRowNum = lastRowNum;
    }

    public List getTransformations() {
        return transformations;
    }

    public int getShiftNumber() {
        int shiftNumber = 0;
        for (Iterator iterator = transformations.iterator(); iterator.hasNext();) {
            Transformation transformation = (Transformation) iterator.next();
            shiftNumber += transformation.getShiftNumber();
        }
        return shiftNumber + lastRowNum - firstRowNum;
    }

    public int getNextRowShiftNumber() {
        int shiftNumber = 0;
        for (Iterator iterator = transformations.iterator(); iterator.hasNext();) {
            Transformation transformation = (Transformation) iterator.next();
            shiftNumber += transformation.getNextRowShiftNumber();
        }
        return shiftNumber;
    }

}
