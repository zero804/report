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
// Copyright (C) 2001-2005 Julian Hyde
// Copyright (C) 2005-2012 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap;

import mondrian3.resource.MondrianResource;
import mondrian3.spi.MemberFormatter;

/**
 * Skeleton implementation of {@link Level}.
 *
 * @author jhyde
 * @since 6 August, 2001
 */
public abstract class LevelBase
    extends OlapElementBase
    implements Level
{
    protected final Hierarchy hierarchy;
    protected final String name;
    protected final String uniqueName;
    protected final String description;
    protected final int depth;
    protected final LevelType levelType;
    protected MemberFormatter memberFormatter;
    protected int  approxRowCount;

    protected LevelBase(
        Hierarchy hierarchy,
        String name,
        String caption,
        boolean visible,
        String description,
        int depth,
        LevelType levelType)
    {
        this.hierarchy = hierarchy;
        this.name = name;
        this.caption = caption;
        this.visible = visible;
        this.description = description;
        this.uniqueName = Util.makeFqName(hierarchy, name);
        this.depth = depth;
        this.levelType = levelType;
    }

    /**
     * Sets the approximate number of members in this Level.
     * @see #getApproxRowCount()
     */
    public void setApproxRowCount(int approxRowCount) {
        this.approxRowCount = approxRowCount;
    }

    // from Element
    public String getQualifiedName() {
        return MondrianResource.instance().MdxLevelName.str(getUniqueName());
    }

    public LevelType getLevelType() {
        return levelType;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Hierarchy getHierarchy() {
        return hierarchy;
    }

    public Dimension getDimension() {
        return hierarchy.getDimension();
    }

    public int getDepth() {
        return depth;
    }

    public Level getChildLevel() {
        int childDepth = depth + 1;
        Level[] levels = hierarchy.getLevels();
        return (childDepth < levels.length)
            ? levels[childDepth]
            : null;
    }

    public Level getParentLevel() {
        int parentDepth = depth - 1;
        Level[] levels = hierarchy.getLevels();
        return (parentDepth >= 0)
            ? levels[parentDepth]
            : null;
    }

    public abstract boolean isAll();

    public boolean isMeasure() {
        return hierarchy.getName().equals("Measures");
    }

    public OlapElement lookupChild(
        SchemaReader schemaReader, Id.Segment s, MatchType matchType)
    {
        if (areMembersUnique()
            && s instanceof Id.NameSegment)
        {
            return Util.lookupHierarchyRootMember(
                schemaReader, hierarchy, ((Id.NameSegment) s), matchType);
        } else {
            return null;
        }
    }

    public MemberFormatter getMemberFormatter() {
        return memberFormatter;
    }
}


// End LevelBase.java
