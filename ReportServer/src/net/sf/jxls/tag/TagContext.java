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

import net.sf.jxls.controller.SheetTransformationController;
import net.sf.jxls.transformer.Sheet;
import net.sf.jxls.transformer.SheetTransformer;

import java.util.Map;

/**
 * Contains tag related information
 * @author Leonid Vysochyn
 */
public class TagContext {
    Map beans;
    Block tagBody;

    Sheet sheet;
    SheetTransformationController stc;

    public TagContext(SheetTransformationController stc, SheetTransformer sheetTransformer, Sheet sheet, Block tagBody, Map beans) {
        this.stc = stc;
        this.sheet = sheet;
        this.tagBody = tagBody;
        this.beans = beans;
    }

    public TagContext(SheetTransformer sheetTransformer, Sheet sheet, Block tagBody, Map beans) {
        this.sheet = sheet;
        this.tagBody = tagBody;
        this.beans = beans;
    }

    public TagContext(Sheet sheet, Block tagBody, Map beans) {
        this.sheet = sheet;
        this.tagBody = tagBody;
        this.beans = beans;
    }


    public TagContext(Map beans, Block tagBody) {
        this.beans = beans;
        this.tagBody = tagBody;
    }

    public TagContext(Map beans) {
        this.beans = beans;
    }

    public Map getBeans() {
        return beans;
    }

    public void setBeans(Map beans) {
        this.beans = beans;
    }

    public String toString() {
        return "Beans: " + beans.toString() + ", Body: " + tagBody;
    }

    public Block getTagBody() {
        return tagBody;
    }

    public void setTagBody(Block tagBody) {
        this.tagBody = tagBody;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public SheetTransformationController getSheetTransformationController() {
        return stc;
    }

    public void setSheetTransformationController(SheetTransformationController stc) {
        this.stc = stc;
    }
}
