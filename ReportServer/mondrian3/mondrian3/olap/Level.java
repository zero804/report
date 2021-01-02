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
// Copyright (C) 2005-2011 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap;

import mondrian3.spi.MemberFormatter;

/**
 * A <code>Level</code> is a group of {@link Member}s in a {@link Hierarchy},
 * all with the same attributes and at the same depth in the hierarchy.
 *
 * @author jhyde, 1 March, 1999
 */
public interface Level extends OlapElement, Annotated {

    /**
     * Returns the depth of this level.
     *
     * <p>Note #1: In an access-controlled context, the first visible level of
     * a hierarchy (as returned by {@link SchemaReader#getHierarchyLevels}) may
     * not have a depth of 0.</p>
     *
     * <p>Note #2: In a parent-child hierarchy, the depth of a member (as
     * returned by {@link SchemaReader#getMemberDepth}) may not be the same as
     * the depth of its level.
     */
    int getDepth();
    Hierarchy getHierarchy();

    Level getChildLevel();
    Level getParentLevel();
    boolean isAll();
    boolean areMembersUnique();
    LevelType getLevelType();

    /** Returns properties defined against this level. */
    Property[] getProperties();
    /** Returns properties defined against this level and parent levels. */
    Property[] getInheritedProperties();

    /**
      * Returns the object that is used to format members of this level.
      */
    MemberFormatter getMemberFormatter();

    /**
     * Returns the approximate number of members in this level, or
     * {@link Integer#MIN_VALUE} if no approximation is known.
     */
    int getApproxRowCount();
}

// End Level.java
