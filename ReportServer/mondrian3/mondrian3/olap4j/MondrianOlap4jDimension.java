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

import mondrian3.olap.*;

import org.olap4j.OlapException;
import org.olap4j.impl.*;
import org.olap4j.metadata.Dimension;
import org.olap4j.metadata.Hierarchy;
import org.olap4j.metadata.*;

/**
 * Implementation of {@link org.olap4j.metadata.Dimension}
 * for the Mondrian OLAP engine.
 *
 * @author jhyde
 * @since May 24, 2007
 */
class MondrianOlap4jDimension
    extends MondrianOlap4jMetadataElement
    implements Dimension, Named
{
    private final MondrianOlap4jSchema olap4jSchema;
    private final mondrian3.olap.Dimension dimension;

    MondrianOlap4jDimension(
        MondrianOlap4jSchema olap4jSchema,
        mondrian3.olap.Dimension dimension)
    {
        this.olap4jSchema = olap4jSchema;
        this.dimension = dimension;
    }

    public boolean equals(Object obj) {
        return obj instanceof MondrianOlap4jDimension
            && dimension.equals(((MondrianOlap4jDimension) obj).dimension);
    }

    public int hashCode() {
        return dimension.hashCode();
    }

    public NamedList<Hierarchy> getHierarchies() {
        final NamedList<MondrianOlap4jHierarchy> list =
            new NamedListImpl<MondrianOlap4jHierarchy>();
        final MondrianOlap4jConnection olap4jConnection =
            olap4jSchema.olap4jCatalog.olap4jDatabaseMetaData.olap4jConnection;
        final mondrian3.olap.SchemaReader schemaReader =
            olap4jConnection.getMondrianConnection2().getSchemaReader()
            .withLocus();
        for (mondrian3.olap.Hierarchy hierarchy
            : schemaReader.getDimensionHierarchies(dimension))
        {
            list.add(olap4jConnection.toOlap4j(hierarchy));
        }
        return Olap4jUtil.cast(list);
    }

    public Hierarchy getDefaultHierarchy() {
        return getHierarchies().get(0);
    }

    public Type getDimensionType() throws OlapException {
        final DimensionType dimensionType = dimension.getDimensionType();
        switch (dimensionType) {
        case StandardDimension:
            return Type.OTHER;
        case MeasuresDimension:
            return Type.MEASURE;
        case TimeDimension:
            return Type.TIME;
        default:
            throw Util.unexpected(dimensionType);
        }
    }

    public String getName() {
        return dimension.getName();
    }

    public String getUniqueName() {
        return dimension.getUniqueName();
    }

    public String getCaption() {
        return dimension.getLocalized(
            OlapElement.LocalizedProperty.CAPTION,
            olap4jSchema.getLocale());
    }

    public String getDescription() {
        return dimension.getLocalized(
            OlapElement.LocalizedProperty.DESCRIPTION,
            olap4jSchema.getLocale());
    }

    public boolean isVisible() {
        return dimension.isVisible();
    }

    protected OlapElement getOlapElement() {
        return dimension;
    }
}

// End MondrianOlap4jDimension.java
