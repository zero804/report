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
 * The type of an expression which represents a Dimension.
 *
 * @author jhyde
 * @since Feb 17, 2005
 */
public class DimensionType implements Type {
    private final Dimension dimension;
    private final String digest;

    public static final DimensionType Unknown = new DimensionType(null);

    /**
     * Creates a type representing a dimension.
     *
     * @param dimension Dimension that values of this type must belong to, or
     *   null if the dimension is unknown
     */
    public DimensionType(Dimension dimension) {
        this.dimension = dimension;
        StringBuilder buf = new StringBuilder("DimensionType<");
        if (dimension != null) {
            buf.append("dimension=").append(dimension.getUniqueName());
        }
        buf.append(">");
        this.digest = buf.toString();
    }

    public static DimensionType forDimension(Dimension dimension) {
        return new DimensionType(dimension);
    }

    public static DimensionType forType(Type type) {
        return new DimensionType(type.getDimension());
    }

    public boolean usesDimension(Dimension dimension, boolean definitely) {
        // REVIEW: Should be '!definitely'?
        return this.dimension == dimension
            || (definitely && this.dimension == null);
    }

    public boolean usesHierarchy(Hierarchy hierarchy, boolean definitely) {
        // If hierarchy belongs to this type's dimension, we might use it.
        return hierarchy.getDimension() == this.dimension
            && !definitely;
    }

    public Hierarchy getHierarchy() {
        return dimension == null
            ? null
            : dimension.getHierarchies().length > 1
            ? null
            : dimension.getHierarchies()[0];
    }

    public Level getLevel() {
        return null;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public int hashCode() {
        return digest.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof DimensionType) {
            DimensionType that = (DimensionType) obj;
            return Util.equals(this.getDimension(), that.getDimension());
        }
        return false;
    }

    public String toString() {
        return digest;
    }

    public Type computeCommonType(Type type, int[] conversionCount) {
        if (conversionCount != null && type instanceof HierarchyType) {
            HierarchyType hierarchyType = (HierarchyType) type;
            if (Util.equals(hierarchyType.getDimension(), dimension)) {
                ++conversionCount[0];
                return this;
            }
            return null;
        }
        if (!(type instanceof DimensionType)) {
            return null;
        }
        DimensionType that = (DimensionType) type;
        if (this.getDimension() != null
            && this.getDimension().equals(that.getDimension()))
        {
            return new DimensionType(
                this.getDimension());
        }
        return DimensionType.Unknown;
    }

    public boolean isInstance(Object value) {
        return value instanceof Dimension
            && (dimension == null
                || value.equals(dimension));
    }

    public int getArity() {
        return 1;
    }
}

// End DimensionType.java
