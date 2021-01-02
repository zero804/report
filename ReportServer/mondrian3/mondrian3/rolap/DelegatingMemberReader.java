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
// Copyright (C) 2003-2005 Julian Hyde
// Copyright (C) 2005-2012 Pentaho
// All Rights Reserved.
*/
package mondrian3.rolap;

import mondrian3.olap.Access;
import mondrian3.olap.Id;
import mondrian3.olap.Member;
import mondrian3.rolap.TupleReader.MemberBuilder;
import mondrian3.rolap.sql.MemberChildrenConstraint;
import mondrian3.rolap.sql.TupleConstraint;

import java.util.List;
import java.util.Map;

/**
 * A <code>DelegatingMemberReader</code> is a {@link MemberReader} which
 * redirects all method calls to an underlying {@link MemberReader}.
 *
 * @author jhyde
 * @since Feb 26, 2003
 */
class DelegatingMemberReader implements MemberReader {
    protected final MemberReader memberReader;

    DelegatingMemberReader(MemberReader memberReader) {
        this.memberReader = memberReader;
    }

    public RolapMember substitute(RolapMember member) {
        return memberReader.substitute(member);
    }

    public RolapMember desubstitute(RolapMember member) {
        return memberReader.desubstitute(member);
    }

    public RolapMember getMemberByKey(
        RolapLevel level, List<Comparable> keyValues)
    {
        return memberReader.getMemberByKey(level, keyValues);
    }

    public RolapMember getLeadMember(RolapMember member, int n) {
        return memberReader.getLeadMember(member, n);
    }

    public List<RolapMember> getMembersInLevel(
        RolapLevel level)
    {
        return memberReader.getMembersInLevel(level);
    }

    public void getMemberRange(
        RolapLevel level,
        RolapMember startMember,
        RolapMember endMember,
        List<RolapMember> list)
    {
        memberReader.getMemberRange(level, startMember, endMember, list);
    }

    public int compare(
        RolapMember m1,
        RolapMember m2,
        boolean siblingsAreEqual)
    {
        return memberReader.compare(m1, m2, siblingsAreEqual);
    }

    public RolapHierarchy getHierarchy() {
        return memberReader.getHierarchy();
    }

    public boolean setCache(MemberCache cache) {
        return memberReader.setCache(cache);
    }

    public List<RolapMember> getMembers() {
        return memberReader.getMembers();
    }

    public List<RolapMember> getRootMembers() {
        return memberReader.getRootMembers();
    }

    public void getMemberChildren(
        RolapMember parentMember,
        List<RolapMember> children)
    {
        getMemberChildren(parentMember, children, null);
    }

    public Map<? extends Member, Access> getMemberChildren(
        RolapMember parentMember,
        List<RolapMember> children,
        MemberChildrenConstraint constraint)
    {
        return memberReader.getMemberChildren(
            parentMember, children, constraint);
    }

    public void getMemberChildren(
        List<RolapMember> parentMembers,
        List<RolapMember> children)
    {
        memberReader.getMemberChildren(
            parentMembers, children);
    }

    public Map<? extends Member, Access> getMemberChildren(
        List<RolapMember> parentMembers,
        List<RolapMember> children,
        MemberChildrenConstraint constraint)
    {
        return memberReader.getMemberChildren(
            parentMembers, children, constraint);
    }

    public int getMemberCount() {
        return memberReader.getMemberCount();
    }

    public RolapMember lookupMember(
        List<Id.Segment> uniqueNameParts,
        boolean failIfNotFound)
    {
        return memberReader.lookupMember(uniqueNameParts, failIfNotFound);
    }

    public List<RolapMember> getMembersInLevel(
        RolapLevel level, TupleConstraint constraint)
    {
        return memberReader.getMembersInLevel(
            level, constraint);
    }

    public int getLevelMemberCount(RolapLevel level) {
        return memberReader.getLevelMemberCount(level);
    }

    public MemberBuilder getMemberBuilder() {
        return memberReader.getMemberBuilder();
    }

    public RolapMember getDefaultMember() {
        return memberReader.getDefaultMember();
    }

    public RolapMember getMemberParent(RolapMember member) {
        return memberReader.getMemberParent(member);
    }
}

// End DelegatingMemberReader.java
