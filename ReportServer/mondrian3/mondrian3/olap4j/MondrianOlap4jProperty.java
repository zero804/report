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
// Copyright (C) 2007-2012 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap4j;

import org.olap4j.impl.Named;
import org.olap4j.metadata.Datatype;
import org.olap4j.metadata.Property;

import java.util.*;

/**
 * Implementation of {@link org.olap4j.metadata.Property}
 * for the Mondrian OLAP engine,
 * as a wrapper around a mondrian
 * {@link mondrian3.olap.Property}.
 *
 * @author jhyde
 * @since Nov 12, 2007
 */
class MondrianOlap4jProperty implements Property, Named {
    /**
     * Map of member properties that are built into Mondrian but are not in the
     * olap4j standard.
     */
    static final Map<String, MondrianOlap4jProperty> MEMBER_EXTENSIONS =
        new LinkedHashMap<String, MondrianOlap4jProperty>();

    /**
     * Map of cell properties that are built into Mondrian but are not in the
     * olap4j standard.
     */
    static final Map<String, MondrianOlap4jProperty> CELL_EXTENSIONS =
        new LinkedHashMap<String, MondrianOlap4jProperty>();

    static {
        // Build set of names of olap4j standard member properties.
        final Set<String> memberNames = new HashSet<String>();
        for (Property property : Property.StandardMemberProperty.values()) {
            memberNames.add(property.getName());
        }

        final Set<String> cellNames = new HashSet<String>();
        for (Property property : StandardCellProperty.values()) {
            cellNames.add(property.getName());
        }

        for (mondrian3.olap.Property o
            : mondrian3.olap.Property.enumeration.getValuesSortedByName())
        {
            if (o.isMemberProperty()
                && !memberNames.contains(o.getName()))
            {
                MEMBER_EXTENSIONS.put(
                    o.getName(),
                    new MondrianOlap4jProperty(o));
            }
            if (o.isCellProperty()
                && !cellNames.contains(o.getName()))
            {
                CELL_EXTENSIONS.put(
                    o.getName(),
                    new MondrianOlap4jProperty(o));
            }
        }
    }

    final mondrian3.olap.Property property;

    MondrianOlap4jProperty(mondrian3.olap.Property property) {
        this.property = property;
    }

    public Datatype getDatatype() {
        switch (property.getType()) {
        case TYPE_BOOLEAN:
            return Datatype.BOOLEAN;
        case TYPE_NUMERIC:
            return Datatype.UNSIGNED_INTEGER;
        case TYPE_STRING:
            return Datatype.STRING;
        case TYPE_OTHER:
            return Datatype.VARIANT;
        default:
            throw new RuntimeException("unexpected: " + property.getType());
        }
    }

    public Set<TypeFlag> getType() {
        return property.isCellProperty()
            ? TypeFlag.CELL_TYPE_FLAG
            : TypeFlag.MEMBER_TYPE_FLAG;
    }

    public String getName() {
        return property.name;
    }

    public String getUniqueName() {
        return property.name;
    }

    public String getCaption() {
        // todo: i18n
        return property.getCaption();
    }

    public String getDescription() {
        // todo: i18n
        return property.getDescription();
    }

    public boolean isVisible() {
        return !property.isInternal();
    }

    public ContentType getContentType() {
        return ContentType.REGULAR;
    }
}

// End MondrianOlap4jProperty.java
