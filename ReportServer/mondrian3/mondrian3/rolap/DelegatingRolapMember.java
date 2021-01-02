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
package mondrian3.rolap;

import mondrian3.olap.*;

import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link mondrian3.rolap.RolapMember} that delegates all calls
 * to an underlying member.
 *
 * @author jhyde
 * @since Mar 16, 2010
 */
public class DelegatingRolapMember extends RolapMemberBase {
    public final RolapMember member;

    protected DelegatingRolapMember(RolapMember member) {
        super();
        this.member = member;
    }

    public RolapLevel getLevel() {
        return member.getLevel();
    }

    public Object getKey() {
        return member.getKey();
    }

    public RolapMember getParentMember() {
        return member.getParentMember();
    }

    public RolapHierarchy getHierarchy() {
        return member.getHierarchy();
    }

    public String getParentUniqueName() {
        return member.getParentUniqueName();
    }

    public MemberType getMemberType() {
        return member.getMemberType();
    }

    public boolean isParentChildLeaf() {
        return member.isParentChildLeaf();
    }

    public void setName(String name) {
        member.setName(name);
    }

    public boolean isAll() {
        return member.isAll();
    }

    public boolean isMeasure() {
        return member.isMeasure();
    }

    public boolean isNull() {
        return member.isNull();
    }

    public boolean isChildOrEqualTo(Member member2) {
        return member.isChildOrEqualTo(member2);
    }

    public boolean isCalculated() {
        return member.isCalculated();
    }

    public boolean isEvaluated() {
        return member.isEvaluated();
    }

    public int getSolveOrder() {
        return member.getSolveOrder();
    }

    public Exp getExpression() {
        return member.getExpression();
    }

    public List<Member> getAncestorMembers() {
        return member.getAncestorMembers();
    }

    public boolean isCalculatedInQuery() {
        return member.isCalculatedInQuery();
    }

    public Object getPropertyValue(String propertyName) {
        return member.getPropertyValue(propertyName);
    }

    public Object getPropertyValue(String propertyName, boolean matchCase) {
        return member.getPropertyValue(propertyName, matchCase);
    }

    public String getPropertyFormattedValue(String propertyName) {
        return member.getPropertyFormattedValue(propertyName);
    }

    public void setProperty(String name, Object value) {
        member.setProperty(name, value);
    }

    public Property[] getProperties() {
        return member.getProperties();
    }

    public int getOrdinal() {
        return member.getOrdinal();
    }

    public Comparable getOrderKey() {
        return member.getOrderKey();
    }

    public boolean isHidden() {
        return member.isHidden();
    }

    public int getDepth() {
        return member.getDepth();
    }

    public Member getDataMember() {
        return member.getDataMember();
    }

    @SuppressWarnings({"unchecked"})
    public int compareTo(Object o) {
        return member.compareTo(o);
    }

    public String getUniqueName() {
        return member.getUniqueName();
    }

    public String getName() {
        return member.getName();
    }

    public String getDescription() {
        return member.getDescription();
    }

    public OlapElement lookupChild(
        SchemaReader schemaReader, Id.Segment s, MatchType matchType)
    {
        return member.lookupChild(schemaReader, s, matchType);
    }

    public Map<String, Annotation> getAnnotationMap() {
        return member.getAnnotationMap();
    }

    public String getQualifiedName() {
        return member.getQualifiedName();
    }

    public String getCaption() {
        return member.getCaption();
    }

    public Dimension getDimension() {
        return member.getDimension();
    }

    public boolean isAllMember() {
        return member.isAllMember();
    }
}

// End DelegatingRolapMember.java
