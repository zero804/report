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
// Copyright (C) 2005-2009 Pentaho and others
// All Rights Reserved.
//
// jhyde, 22 December, 2001
*/
package mondrian3.rolap;

import mondrian3.olap.Id;
import mondrian3.olap.Util;
import mondrian3.resource.MondrianResource;

import java.util.Collections;
import java.util.List;

/**
 * <code>ArrayMemberSource</code> implements a flat, static hierarchy. There is
 * no root member, and all members are siblings.
 *
 * @author jhyde
 * @since 22 December, 2001
 */
abstract class ArrayMemberSource implements MemberSource {

    protected final RolapHierarchy hierarchy;
    protected final List<RolapMember> members;

    ArrayMemberSource(RolapHierarchy hierarchy, List<RolapMember> members) {
        this.hierarchy = hierarchy;
        this.members = members;
    }
    public RolapHierarchy getHierarchy() {
        return hierarchy;
    }
    public boolean setCache(MemberCache cache) {
        return false; // we do not support cache writeback
    }
    public List<RolapMember> getMembers() {
        return members;
    }
    public int getMemberCount() {
        return members.size();
    }

    public List<RolapMember> getRootMembers() {
        return Collections.emptyList();
    }

    public void getMemberChildren(
        RolapMember parentMember,
        List<RolapMember> children)
    {
        // there are no children
    }

    public void getMemberChildren(
        List<RolapMember> parentMembers,
        List<RolapMember> children)
    {
        // there are no children
    }

    public RolapMember lookupMember(
        List<Id.Segment> uniqueNameParts,
        boolean failIfNotFound)
    {
        String uniqueName = Util.implode(uniqueNameParts);
        for (RolapMember member : members) {
            if (member.getUniqueName().equals(uniqueName)) {
                return member;
            }
        }
        if (failIfNotFound) {
            throw MondrianResource.instance().MdxCantFindMember.ex(uniqueName);
        } else {
            return null;
        }
    }
}

// End ArrayMemberSource.java
