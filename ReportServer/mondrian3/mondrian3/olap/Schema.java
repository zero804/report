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
// Copyright (C) 2006-2010 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap;

import java.util.Date;
import java.util.List;

/**
 * A <code>Schema</code> is a collection of cubes, shared dimensions, and roles.
 *
 * @author jhyde
 */
public interface Schema extends Annotated {

    /**
     * Returns the name of this schema.
     * @post return != null
     * @post return.length() > 0
     */
    String getName();

    /**
     * Returns the uniquely generated id of this schema.
     */
    String getId();

    /**
     * Finds a cube called <code>cube</code> in this schema; if no cube
     * exists, <code>failIfNotFound</code> controls whether to raise an error
     * or return <code>null</code>.
     */
    Cube lookupCube(String cube, boolean failIfNotFound);

    /**
     * Returns a list of all cubes in this schema.
     */
    Cube[] getCubes();

    /**
     * Returns a list of shared dimensions in this schema.
     */
    Hierarchy[] getSharedHierarchies();

    /**
     * Creates a dimension in the given cube by parsing an XML string. The XML
     * string must be either a &lt;Dimension&gt; or a &lt;DimensionUsage&gt;.
     * Returns the dimension created.
     */
    Dimension createDimension(Cube cube, String xml);

    /**
     * Creates a cube by parsing an XML string. Returns the cube created.
     */
    Cube createCube(String xml);

    /**
     * Removes a cube.
     *
     * @return Whether cube was removed
     */
    boolean removeCube(String cubeName);

    /**
     * Creates a {@link SchemaReader} without any access control.
     */
    SchemaReader getSchemaReader();

    /**
     * Finds a role with a given name in the current catalog, or returns
     * <code>null</code> if no such role exists.
     */
    Role lookupRole(String role);

    /**
     * Returns this schema's function table.
     */
    FunTable getFunTable();

    /**
     * Returns this schema's parameters.
     */
    Parameter[] getParameters();

    /**
     * Returns when this schema was last loaded.
     *
     * @return Date and time when this schema was last loaded
     */
    Date getSchemaLoadDate();

    /**
     * Returns a list of warnings and errors that occurred while loading this
     * schema.
     *
     * @return list of warnings
     */
    List<Exception> getWarnings();
}

// End Schema.java
