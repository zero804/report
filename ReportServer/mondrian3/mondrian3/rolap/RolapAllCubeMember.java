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
// Copyright (C) 2005-2010 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap;

import mondrian3.olap.Util;

/**
 * The 'All' member of a {@link mondrian3.rolap.RolapCubeHierarchy}.
 *
 * <p>A minor extension to {@link mondrian3.rolap.RolapCubeMember} because the
 * naming rules are different.
 *
 * @author Will Gorman, 19 October 2007
 */
class RolapAllCubeMember
    extends RolapCubeMember
{
    protected final String name;
    private final String uniqueName;

    /**
     * Creates a RolapAllCubeMember.
     *
     * @param member Member of underlying (non-cube) hierarchy
     * @param cubeLevel Level
     */
    public RolapAllCubeMember(RolapMember member, RolapCubeLevel cubeLevel)
    {
        super(null, member, cubeLevel);
        assert member.isAll();

        // replace hierarchy name portion of all member with new name
        if (member.getHierarchy().getName().equals(getHierarchy().getName())) {
            name = member.getName();
        } else {
            // special case if we're dealing with a closure
            String replacement =
                getHierarchy().getName().replaceAll("\\$", "\\\\\\$");

            // convert string to regular expression
            String memberLevelName =
                member.getHierarchy().getName().replaceAll("\\.", "\\\\.");

            name = member.getName().replaceAll(memberLevelName, replacement);
        }

        // Assign unique name. We use a kludge to ensure that calc members are
        // called [Measures].[Foo] not [Measures].[Measures].[Foo]. We can
        // remove this code when we revisit the scheme to generate member unique
        // names.
        if (getHierarchy().getName().equals(getDimension().getName())) {
            this.uniqueName = Util.makeFqName(getDimension(), name);
        } else {
            this.uniqueName = Util.makeFqName(getHierarchy(), name);
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String getUniqueName() {
        return uniqueName;
    }
}

// End RolapAllCubeMember.java
