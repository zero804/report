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

import mondrian3.olap.OlapElement;

import org.olap4j.impl.Named;
import org.olap4j.mdx.ParseTreeNode;
import org.olap4j.metadata.Cube;
import org.olap4j.metadata.NamedSet;

/**
 * Implementation of {@link org.olap4j.metadata.NamedSet}
 * for the Mondrian OLAP engine.
 *
 * @author jhyde
 * @since Nov 12, 2007
 */
class MondrianOlap4jNamedSet
    extends MondrianOlap4jMetadataElement
    implements NamedSet, Named
{
    private final MondrianOlap4jCube olap4jCube;
    private mondrian3.olap.NamedSet namedSet;

    MondrianOlap4jNamedSet(
        MondrianOlap4jCube olap4jCube,
        mondrian3.olap.NamedSet namedSet)
    {
        this.olap4jCube = olap4jCube;
        this.namedSet = namedSet;
    }

    public Cube getCube() {
        return olap4jCube;
    }

    public ParseTreeNode getExpression() {
        final MondrianOlap4jConnection olap4jConnection =
            olap4jCube.olap4jSchema.olap4jCatalog.olap4jDatabaseMetaData
                .olap4jConnection;
        return olap4jConnection.toOlap4j(namedSet.getExp());
    }

    public String getName() {
        return namedSet.getName();
    }

    public String getUniqueName() {
        return namedSet.getUniqueName();
    }

    public String getCaption() {
        return namedSet.getLocalized(
            OlapElement.LocalizedProperty.CAPTION,
            olap4jCube.olap4jSchema.getLocale());
    }

    public String getDescription() {
        return namedSet.getLocalized(
            OlapElement.LocalizedProperty.DESCRIPTION,
            olap4jCube.olap4jSchema.getLocale());
    }

    public boolean isVisible() {
        return namedSet.isVisible();
    }

    protected OlapElement getOlapElement() {
        return namedSet;
    }
}

// End MondrianOlap4jNamedSet.java
