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
// Copyright (C) 2005-2005 Julian Hyde
// Copyright (C) 2005-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap.type;

import mondrian3.olap.*;

/**
 * The type of an expression which represents a Cube or Virtual Cube.
 *
 * @author jhyde
 * @since Feb 17, 2005
 */
public class CubeType implements Type {
    private final Cube cube;

    /**
     * Creates a type representing a cube.
     */
    public CubeType(Cube cube) {
        this.cube = cube;
    }

    /**
     * Returns the cube.
     *
     * @return Cube
     */
    public Cube getCube() {
        return cube;
    }

    public boolean usesDimension(Dimension dimension, boolean definitely) {
        return false;
    }

    public boolean usesHierarchy(Hierarchy hierarchy, boolean definitely) {
        return false;
    }

    public Dimension getDimension() {
        return null;
    }

    public Hierarchy getHierarchy() {
        return null;
    }

    public Level getLevel() {
        return null;
    }

    public int hashCode() {
        return cube.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof CubeType) {
            CubeType that = (CubeType) obj;
            return this.cube.equals(that.cube);
        } else {
            return false;
        }
    }

    public Type computeCommonType(Type type, int[] conversionCount) {
        return this.equals(type)
            ? this
            : null;
    }

    public boolean isInstance(Object value) {
        return value instanceof Cube;
    }

    public int getArity() {
        // not meaningful; cube cannot be used in an expression
        throw new UnsupportedOperationException();
    }
}

// End CubeType.java
