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
// Copyright (C) 2009-2011 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap;

import mondrian3.olap.Member;
import mondrian3.rolap.sql.TupleConstraint;

import java.sql.SQLException;
import java.util.List;
import java.util.RandomAccess;

/**
 * Base helper class for the SQL tuple readers
 * {@link mondrian3.rolap.HighCardSqlTupleReader} and
 * {@link mondrian3.rolap.SqlTupleReader}.
 *
 * <p>Keeps track of target levels and constraints for adding to the SQL query.
 * The real work is done in the extending classes,
 * {@link Target} and
 * {@link mondrian3.rolap.SqlTupleReader.Target}.
 *
 * @author Kurtis Walker
 * @since July 23, 2009
 */
public abstract class TargetBase {
    final List<RolapMember> srcMembers;
    final RolapLevel level;
    private RolapMember currMember;
    private List<RolapMember> list;
    final Object cacheLock;
    final TupleReader.MemberBuilder memberBuilder;

    public TargetBase(
        List<RolapMember> srcMembers,
        RolapLevel level,
        TupleReader.MemberBuilder memberBuilder)
    {
        this.srcMembers = srcMembers;
        this.level = level;
        cacheLock = memberBuilder.getMemberCacheLock();
        this.memberBuilder = memberBuilder;
    }

    public void setList(final List<RolapMember> list) {
        assert list instanceof RandomAccess;
        this.list = list;
    }

    public List<RolapMember> getSrcMembers() {
        return srcMembers;
    }

    public RolapLevel getLevel() {
        return level;
    }

    public RolapMember getCurrMember() {
        return this.currMember;
    }

    public void removeCurrMember() {
        this.currMember = null;
    }

    public void setCurrMember(final RolapMember m) {
        this.currMember = m;
    }

    public List<RolapMember> getList() {
        return list;
    }

    public String toString() {
        return level.getUniqueName();
    }

    /**
     * Adds a row to the collection.
     *
     * @param stmt Statement
     * @param column Column ordinal (0-based)
     * @return Ordinal of next unconsumed column
     * @throws SQLException On error
     */
    public final int addRow(SqlStatement stmt, int column) throws SQLException {
        synchronized (cacheLock) {
            return internalAddRow(stmt, column);
        }
    }

    public abstract void open();

    public abstract List<Member> close();

    abstract int internalAddRow(SqlStatement stmt, int column)
        throws SQLException;

    public void add(final RolapMember member) {
        this.getList().add(member);
    }

    RolapNativeCrossJoin.NonEmptyCrossJoinConstraint
    castToNonEmptyCJConstraint(TupleConstraint constraint) {
        return (RolapNativeCrossJoin.NonEmptyCrossJoinConstraint) constraint;
    }
}

// End TargetBase.java
