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
// Copyright (C) 2012-2012 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap;

import mondrian3.olap.*;
import mondrian3.rolap.RolapHierarchy.LimitedRollupMember;
import mondrian3.rolap.sql.*;

import java.util.*;
import java.util.concurrent.locks.*;

/**
 * A {@link SmartRestrictedMemberReader} is a subclass of
 * {@link RestrictedMemberReader} which caches the access rights
 * per children's list. We place them in this throw-away object
 * to speed up partial rollup calculations.
 *
 * <p>The speed improvement is noticeable when dealing with very
 * big dimensions with a lot of branches (like a parent-child
 * hierarchy) because the 'partial' rollup policy forces us to
 * navigate the tree and find the lowest level to rollup to and
 * then figure out all of the children on which to constraint
 * the SQL query.
 */
class SmartRestrictedMemberReader extends RestrictedMemberReader {

    SmartRestrictedMemberReader(
        final MemberReader memberReader,
        final Role role)
    {
        // We want to extend a RestrictedMemberReader with access details
        // that we cache.
        super(memberReader, role);
    }

    // Our little ad-hoc cache.
    final Map<RolapMember, AccessAwareMemberList>
        memberToChildren =
            new WeakHashMap<RolapMember, AccessAwareMemberList>();

    // The lock for cache access.
    final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public Map<? extends Member, Access> getMemberChildren(
        RolapMember member,
        List<RolapMember> children,
        MemberChildrenConstraint constraint)
    {
        // Strip off the rollup wrapper.
        if (member instanceof LimitedRollupMember) {
            member = ((LimitedRollupMember)member).member;
        }
        try {
            // Get the read lock.
            lock.readLock().lock();

            AccessAwareMemberList memberList =
                memberToChildren.get(member);

            if (memberList != null) {
                // Sadly, we need to do a hard cast here,
                // but since we know what it is, it's fine.
                children.addAll(
                    memberList.children);

                return memberList.accessMap;
            }
        } finally {
            lock.readLock().unlock();
        }

        // No cache data.
        try {
            // Get a write lock.
            lock.writeLock().lock();

            Map<? extends Member, Access> membersWithAccessDetails =
                super.getMemberChildren(
                    member,
                    children,
                    constraint);

            memberToChildren.put(
                member,
                new AccessAwareMemberList(
                    membersWithAccessDetails,
                    new ArrayList(membersWithAccessDetails.keySet())));

            return membersWithAccessDetails;
        } finally {
            lock.writeLock().unlock();
        }
    }

    private static class AccessAwareMemberList {
        private final Map<? extends Member, Access> accessMap;
        private final Collection<RolapMember> children;
        public AccessAwareMemberList(
            Map<? extends Member, Access> accessMap,
            Collection<RolapMember> children)
        {
            this.accessMap = accessMap;
            this.children = children;
        }
    }
}

// End SmartRestrictedMemberReader.java