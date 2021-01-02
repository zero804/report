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

import net.sf.jxls.transformation.ResultTransformation;
import net.sf.jxls.transformer.SheetTransformer;

/**
 * Defines an interface for a general jx tag
 * @author Leonid Vysochyn
 */
public interface Tag {
    /**
     * @return number of rows to shift
     * @param sheetTransformer
     */
    public ResultTransformation process(SheetTransformer sheetTransformer);

    /**
     * @return tag name
     */
    public String getName();

    /**
     * This method is invoked after all tag attributes are set
     * @param tagContext
     */
    void init(TagContext tagContext);

    /**
     * @return {@link TagContext} for this tag
     */
    TagContext getTagContext();
}
