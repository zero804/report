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
// Copyright (C) 2007-2013 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap4j;

import mondrian3.olap.OlapElement;

import org.olap4j.OlapException;
import org.olap4j.impl.*;
import org.olap4j.metadata.*;

import java.util.List;

/**
 * Implementation of {@link org.olap4j.metadata.Hierarchy}
 * for the Mondrian OLAP engine.
 *
 * @author jhyde
 * @since May 25, 2007
 */
class MondrianOlap4jHierarchy
    extends MondrianOlap4jMetadataElement
    implements Hierarchy, Named
{
    final MondrianOlap4jSchema olap4jSchema;
    final mondrian3.olap.Hierarchy hierarchy;

    MondrianOlap4jHierarchy(
        MondrianOlap4jSchema olap4jSchema,
        mondrian3.olap.Hierarchy hierarchy)
    {
        this.olap4jSchema = olap4jSchema;
        this.hierarchy = hierarchy;
    }

    public boolean equals(Object obj) {
        return obj instanceof MondrianOlap4jHierarchy
            && hierarchy.equals(((MondrianOlap4jHierarchy) obj).hierarchy);
    }

    public int hashCode() {
        return hierarchy.hashCode();
    }

    public Dimension getDimension() {
        return new MondrianOlap4jDimension(
            olap4jSchema, hierarchy.getDimension());
    }

    public NamedList<Level> getLevels() {
        final NamedList<MondrianOlap4jLevel> list =
            new NamedListImpl<MondrianOlap4jLevel>();
        final MondrianOlap4jConnection olap4jConnection =
            olap4jSchema.olap4jCatalog.olap4jDatabaseMetaData.olap4jConnection;
        final mondrian3.olap.SchemaReader schemaReader =
            olap4jConnection.getMondrianConnection2().getSchemaReader()
                .withLocus();
        for (mondrian3.olap.Level level
            : schemaReader.getHierarchyLevels(hierarchy))
        {
            list.add(olap4jConnection.toOlap4j(level));
        }
        return Olap4jUtil.cast(list);
    }

    public boolean hasAll() {
        return hierarchy.hasAll();
    }

    public Member getDefaultMember() throws OlapException {
        final MondrianOlap4jConnection olap4jConnection =
            olap4jSchema.olap4jCatalog.olap4jDatabaseMetaData.olap4jConnection;
        final mondrian3.olap.SchemaReader schemaReader =
            olap4jConnection.getMondrianConnection()
                .getSchemaReader().withLocus();
        return
            olap4jConnection.toOlap4j(
                schemaReader.getHierarchyDefaultMember(hierarchy));
    }

    public NamedList<Member> getRootMembers() throws OlapException {
        final MondrianOlap4jConnection olap4jConnection =
            olap4jSchema.olap4jCatalog.olap4jDatabaseMetaData.olap4jConnection;
        final List<mondrian3.olap.Member> levelMembers =
            olap4jConnection.getMondrianConnection().getSchemaReader()
                .withLocus()
                .getLevelMembers(
                    hierarchy.getLevels()[0], true);

        return new AbstractNamedList<Member>() {
            public String getName(Object member) {
                return ((Member)member).getName();
            }

            public Member get(int index) {
                return olap4jConnection.toOlap4j(levelMembers.get(index));
            }

            public int size() {
                return levelMembers.size();
            }
        };
    }

    public String getName() {
        return hierarchy.getName();
    }

    public String getUniqueName() {
        return hierarchy.getUniqueName();
    }

    public String getCaption() {
        return hierarchy.getLocalized(
            OlapElement.LocalizedProperty.CAPTION,
            olap4jSchema.getLocale());
    }

    public String getDescription() {
        return hierarchy.getLocalized(
            OlapElement.LocalizedProperty.DESCRIPTION,
            olap4jSchema.getLocale());
    }

    public boolean isVisible() {
        return hierarchy.isVisible();
    }

    protected OlapElement getOlapElement() {
        return hierarchy;
    }
}

// End MondrianOlap4jHierarchy.java