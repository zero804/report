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
// Copyright (C) 1999-2005 Julian Hyde
// Copyright (C) 2005-2009 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap;

/**
 * A <code>Dimension</code> represents a dimension of a cube.
 *
 * @author jhyde, 1 March, 1999
 */
public interface Dimension extends OlapElement, Annotated {
    final String MEASURES_UNIQUE_NAME = "[Measures]";
    final String MEASURES_NAME = "Measures";

    /**
     * Returns an array of the hierarchies which belong to this dimension.
     */
    Hierarchy[] getHierarchies();

    /**
     * Returns whether this is the <code>[Measures]</code> dimension.
     */
    boolean isMeasures();

    /**
     * Returns the type of this dimension
     * ({@link DimensionType#StandardDimension} or
     * {@link DimensionType#TimeDimension}
     */
    DimensionType getDimensionType();

    /**
     * Returns the schema this dimension belongs to.
     */
    Schema getSchema();

    /**
     * Returns whether the dimension should be considered as a "high
     * cardinality" or "low cardinality" according to cube definition.
     *
     * Mondrian tends to evaluate high cardinality dimensions using
     * iterators rather than lists, avoiding instantiating the dimension in
     * memory.
     *
     * @return whether this dimension is high-cardinality
     */
    boolean isHighCardinality();
}

// End Dimension.java
