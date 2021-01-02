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
 * Base class for types which represent scalar values.
 *
 * <p>An instance of this class means a scalar value of unknown type.
 * Usually one of the derived classes {@link NumericType},
 * {@link StringType}, {@link BooleanType} is used instead.
 *
 * @author jhyde
 * @since Feb 17, 2005
 */
public class ScalarType implements Type {
    private final String digest;

    /**
     * Creates a ScalarType.
     */
    public ScalarType() {
        this("SCALAR");
    }

    /**
     * Creates a ScalarType (or subtype) with a given digest.
     *
     * <p>The digest is used for {@link #toString()} and {@link #hashCode()}.
     *
     * @param digest Description of this type
     */
    protected ScalarType(String digest) {
        this.digest = digest;
    }

    public int hashCode() {
        return digest.hashCode();
    }

    public boolean equals(Object obj) {
        return obj != null
            && obj.getClass() == ScalarType.class;
    }

    public String toString() {
        return digest;
    }

    public boolean usesDimension(Dimension dimension, boolean definitely) {
        return false;
    }

    public boolean usesHierarchy(Hierarchy hierarchy, boolean definitely) {
        return false;
    }

    public Hierarchy getHierarchy() {
        return null;
    }

    public Level getLevel() {
        return null;
    }

    public Type computeCommonType(Type type, int[] conversionCount) {
        if (this.equals(type)) {
            return this;
        } else if (type instanceof NullType) {
            return this;
        } else if (this instanceof NullType
            && type instanceof ScalarType)
        {
            return type;
        } else if (this.getClass() == ScalarType.class
            && type instanceof ScalarType)
        {
            return this;
        } else if (type.getClass() == ScalarType.class) {
            return type;
        } else if (type instanceof ScalarType) {
            return new ScalarType();
        } else if (type instanceof MemberType) {
            return computeCommonType(
                ((MemberType) type).getValueType(),
                conversionCount);
        } else if (type instanceof TupleType) {
            return computeCommonType(
                ((TupleType) type).getValueType(),
                conversionCount);
        } else {
            return null;
        }
    }

    public Dimension getDimension() {
        return null;
    }

    public boolean isInstance(Object value) {
        // Somewhat pessimistic.
        return false;
    }

    public int getArity() {
        return 1;
    }
}

// End ScalarType.java
