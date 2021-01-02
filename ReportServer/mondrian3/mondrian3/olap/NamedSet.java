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
// Copyright (C) 2000-2005 Julian Hyde
// Copyright (C) 2005-2006 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap;

import mondrian3.olap.type.Type;

/**
 * A named set of members or tuples.
 *
 * <p>A set can be defined in a query, using a <code>WITH SET</code> clause,
 * or in a schema. Named sets in a schema can be defined against a particular
 * cube or virtual cube, or shared between all cubes.</p>
 *
 * @author jhyde
 * @since 6 August, 2001
 */
public interface NamedSet extends OlapElement, Annotated {
    /**
     * Sets the name of this named set.
     */
    void setName(String newName);

    /**
     * Returns the type of this named set.
     */
    Type getType();

    /**
     * Returns the expression used to derive this named set.
     */
    Exp getExp();

    NamedSet validate(Validator validator);

    /**
     * Returns a name for this set that is unique within the query.
     *
     * <p>This is necessary when there are several 'AS' expressions, or an 'AS'
     * expression overrides a named set defined using 'WITH MEMBER' clause or
     * against a cube.
     */
    String getNameUniqueWithinQuery();

    /**
     * Returns whether this named set is dynamic.
     *
     * <p>Evaluation rules:
     * <ul>
     * <li>A dynamic set is evaluated each time it is used, and inherits the
     *     context in which it is evaluated.
     * <li>A static set is evaluated only on first use, in the base context of
     *     the cube.
     * </ul>
     *
     * @return Whether this named set is dynamic
     */
    boolean isDynamic();
}

// End NamedSet.java
