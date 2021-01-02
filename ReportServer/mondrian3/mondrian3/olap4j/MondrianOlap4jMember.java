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
// Copyright (C) 2007-2012 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap4j;

import mondrian3.olap.OlapElement;
import mondrian3.rolap.RolapConnection;
import mondrian3.rolap.RolapMeasure;
import mondrian3.server.Locus;

import org.olap4j.OlapException;
import org.olap4j.impl.AbstractNamedList;
import org.olap4j.impl.Named;
import org.olap4j.mdx.ParseTreeNode;
import org.olap4j.metadata.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link Member}
 * for the Mondrian OLAP engine,
 * as a wrapper around a mondrian
 * {@link mondrian3.olap.Member}.
 *
 * @author jhyde
 * @since May 25, 2007
 */
class MondrianOlap4jMember
    extends MondrianOlap4jMetadataElement
    implements Member, Named
{

    final mondrian3.olap.Member member;

    final MondrianOlap4jSchema olap4jSchema;

    MondrianOlap4jMember(
        MondrianOlap4jSchema olap4jSchema,
        mondrian3.olap.Member mondrianMember)
    {
        assert mondrianMember != null;
        assert mondrianMember instanceof RolapMeasure
            == this instanceof MondrianOlap4jMeasure;
        this.olap4jSchema = olap4jSchema;
        this.member = mondrianMember;
    }

    public boolean equals(Object obj) {
        return obj instanceof MondrianOlap4jMember
            && member.equals(((MondrianOlap4jMember) obj).member);
    }

    public int hashCode() {
        return member.hashCode();
    }

    public String toString() {
        return getUniqueName();
    }

    public NamedList<MondrianOlap4jMember> getChildMembers()
        throws OlapException
    {
        final RolapConnection conn =
            olap4jSchema.olap4jCatalog.olap4jDatabaseMetaData
                .olap4jConnection.getMondrianConnection();
        final List<mondrian3.olap.Member> children =
            Locus.execute(
                conn,
                "MondrianOlap4jMember.getChildMembers",
                new Locus.Action<List<mondrian3.olap.Member>>() {
                    public List<mondrian3.olap.Member> execute() {
                        return
                            conn.getSchemaReader()
                                .getMemberChildren(member);
                    }
                });
        return new AbstractNamedList<MondrianOlap4jMember>() {
            public String getName(Object member) {
                return ((MondrianOlap4jMember)member).getName();
            }

            public MondrianOlap4jMember get(int index) {
                return new MondrianOlap4jMember(
                    olap4jSchema, children.get(index));
            }

            public int size() {
                return children.size();
            }
        };
    }

    public int getChildMemberCount() throws OlapException {
        final RolapConnection conn =
            olap4jSchema.olap4jCatalog.olap4jDatabaseMetaData
                .olap4jConnection.getMondrianConnection();
        return
            Locus.execute(
                conn,
                "MondrianOlap4jMember.getChildMemberCount",
                new Locus.Action<Integer>() {
                    public Integer execute() {
                        return
                            conn.getSchemaReader()
                                .getMemberChildren(member).size();
                    }
                });
    }

    public MondrianOlap4jMember getParentMember() {
        final mondrian3.olap.Member parentMember = member.getParentMember();
        if (parentMember == null) {
            return null;
        }
        final RolapConnection conn =
            olap4jSchema.olap4jCatalog.olap4jDatabaseMetaData
                .olap4jConnection.getMondrianConnection2();
        final boolean isVisible =
            Locus.execute(
                conn,
                "MondrianOlap4jMember.getParentMember",
                new Locus.Action<Boolean>() {
                    public Boolean execute() {
                        return
                            conn.getSchemaReader()
                                .isVisible(parentMember);
                    }
                });
        if (!isVisible) {
            return null;
        }
        return new MondrianOlap4jMember(olap4jSchema, parentMember);
    }

    public Level getLevel() {
        return new MondrianOlap4jLevel(olap4jSchema, member.getLevel());
    }

    public Hierarchy getHierarchy() {
        return new MondrianOlap4jHierarchy(
            olap4jSchema, member.getHierarchy());
    }

    public Dimension getDimension() {
        return new MondrianOlap4jDimension(
            olap4jSchema, member.getDimension());
    }

    public Type getMemberType() {
        return Type.valueOf(member.getMemberType().name());
    }

    public boolean isAll() {
        return member.isAll();
    }

    public boolean isChildOrEqualTo(Member member) {
        throw new UnsupportedOperationException();
    }

    public boolean isCalculated() {
        return getMemberType() == Type.FORMULA;
    }

    public int getSolveOrder() {
        return member.getSolveOrder();
    }

    public ParseTreeNode getExpression() {
        throw new UnsupportedOperationException();
    }

    public List<Member> getAncestorMembers() {
        final List<Member> list = new ArrayList<Member>();
        MondrianOlap4jMember m = getParentMember();
        while (m != null) {
            list.add(m);
            m = m.getParentMember();
        }
        return list;
    }

    public boolean isCalculatedInQuery() {
        return member.isCalculatedInQuery();
    }

    public Object getPropertyValue(Property property) {
        return member.getPropertyValue(property.getName());
    }

    public String getPropertyFormattedValue(Property property) {
        return member.getPropertyFormattedValue(property.getName());
    }

    public void setProperty(Property property, Object value)
        throws OlapException
    {
        member.setProperty(property.getName(), value);
    }

    public NamedList<Property> getProperties() {
        return getLevel().getProperties();
    }

    public int getOrdinal() {
        final Number ordinal =
            (Number) member.getPropertyValue(
                Property.StandardMemberProperty.MEMBER_ORDINAL.getName());
        return ordinal.intValue();
    }

    public boolean isHidden() {
        return member.isHidden();
    }

    public int getDepth() {
        return member.getDepth();
    }

    public Member getDataMember() {
        final mondrian3.olap.Member dataMember = member.getDataMember();
        if (dataMember == null) {
            return null;
        }
        return new MondrianOlap4jMember(olap4jSchema, dataMember);
    }

    public String getName() {
        return member.getName();
    }

    public String getUniqueName() {
        return member.getUniqueName();
    }

    public String getCaption() {
        return member.getCaption();
    }

    public String getDescription() {
        return member.getDescription();
    }

    public boolean isVisible() {
        return (Boolean) member.getPropertyValue(
            mondrian3.olap.Property.VISIBLE.getName());
    }

    protected OlapElement getOlapElement() {
        return member;
    }
}

// End MondrianOlap4jMember.java
