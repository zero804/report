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
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 1998-2005 Julian Hyde
// Copyright (C) 2005-2012 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap;

import java.util.List;

/**
 * Represents Cell Property.
 *
 * @author Shishir
 * @since 08 May, 2007
 */

public class CellProperty extends QueryPart {
    private String name;

    public CellProperty(List<Id.Segment> segments) {
        this.name = Util.implode(segments);
    }

    /**
     * checks whether cell property is equals to passed parameter.
     * It adds '[' and ']' before and after the propertyName before comparing.
     * The comparison is case insensitive.
     */
    public boolean isNameEquals(String propertyName) {
        return name.equalsIgnoreCase(Util.quoteMdxIdentifier(propertyName));
    }

    public String toString() {
        return name;
    }
}

// End CellProperty.java
