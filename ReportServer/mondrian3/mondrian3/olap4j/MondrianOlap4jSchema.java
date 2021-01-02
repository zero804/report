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

import mondrian3.olap.Hierarchy;
import mondrian3.olap.OlapElement;
import mondrian3.olap.Role;

import org.olap4j.OlapException;
import org.olap4j.impl.*;
import org.olap4j.metadata.*;

import java.util.*;

/**
 * Implementation of {@link org.olap4j.metadata.Schema}
 * for the Mondrian OLAP engine.
 *
 * @author jhyde
 * @since May 24, 2007
 */
class MondrianOlap4jSchema
    extends MondrianOlap4jMetadataElement
    implements Schema, Named
{
    final MondrianOlap4jCatalog olap4jCatalog;
    final String schemaName;
    final mondrian3.olap.Schema schema;

    /**
     * Creates a MondrianOlap4jSchema.
     *
     * <p>The name of the schema is not necessarily the same as
     * schema.getName(). If schema was loaded in a datasources.xml file, the
     * name it was given there (in the &lt;Catalog&gt; element) trumps the name
     * in the catalog.xml file.
     *
     * @param olap4jCatalog Catalog containing schema
     * @param schemaName Name of schema
     * @param schema Mondrian schema
     */
    MondrianOlap4jSchema(
        MondrianOlap4jCatalog olap4jCatalog,
        String schemaName,
        mondrian3.olap.Schema schema)
    {
        this.olap4jCatalog = olap4jCatalog;
        this.schemaName = schemaName;
        this.schema = schema;
    }

    public Catalog getCatalog() {
        return olap4jCatalog;
    }

    public NamedList<Cube> getCubes() throws OlapException {
        NamedList<MondrianOlap4jCube> list =
            new NamedListImpl<MondrianOlap4jCube>();
        final MondrianOlap4jConnection olap4jConnection =
            olap4jCatalog.olap4jDatabaseMetaData.olap4jConnection;
        for (mondrian3.olap.Cube cube
            : olap4jConnection.getMondrianConnection()
                .getSchemaReader().getCubes())
        {
            list.add(olap4jConnection.toOlap4j(cube));
        }
        return Olap4jUtil.cast(list);
    }

    public NamedList<Dimension> getSharedDimensions() throws OlapException {
        final MondrianOlap4jConnection olap4jConnection =
            olap4jCatalog.olap4jDatabaseMetaData.olap4jConnection;
        final SortedSet<MondrianOlap4jDimension> dimensions =
            new TreeSet<MondrianOlap4jDimension>(
                new Comparator<MondrianOlap4jDimension>() {
                    public int compare(
                        MondrianOlap4jDimension o1,
                        MondrianOlap4jDimension o2)
                    {
                        return o1.getName().compareTo(o2.getName());
                    }
                }
            );
        final Role role = olap4jConnection.getMondrianConnection().getRole();
        for (Hierarchy hierarchy : schema.getSharedHierarchies()) {
            if (role.canAccess(hierarchy)) {
                dimensions.add(
                    olap4jConnection.toOlap4j(hierarchy.getDimension()));
            }
        }
        NamedList<MondrianOlap4jDimension> list =
            new NamedListImpl<MondrianOlap4jDimension>();
        list.addAll(dimensions);
        return Olap4jUtil.cast(list);
    }

    public Collection<Locale> getSupportedLocales() throws OlapException {
        return Collections.emptyList();
    }

    public String getName() {
        return schemaName;
    }

    /**
     * Shorthand for catalog.database.connection.getLocale().
     * Not part of the olap4j api; do not make public.
     *
     * @return Locale of current connection
     */
    final Locale getLocale() {
        return olap4jCatalog.olap4jDatabase.getOlapConnection().getLocale();
    }

    protected OlapElement getOlapElement() {
        return null;
    }
}

// End MondrianOlap4jSchema.java
