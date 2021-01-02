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
// Copyright (C) 2004-2005 TONBELLER AG
// Copyright (C) 2005-2013 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap;

import mondrian3.calc.TupleList;
import mondrian3.calc.impl.DelegatingTupleList;
import mondrian3.calc.impl.UnaryTupleList;
import mondrian3.olap.*;
import mondrian3.olap.fun.FunUtil;
import mondrian3.rolap.sql.TupleConstraint;
import mondrian3.server.Locus;
import mondrian3.server.monitor.SqlStatementEvent;
import mondrian3.util.Pair;
import mondrian3.util.TraversalList;

import java.sql.SQLException;
import java.util.*;

import javax.sql.DataSource;

/**
 * Reads the members of a single level (level.members) or of multiple levels
 * (crossjoin).
 *
 * @deprecated Deprecated for Mondrian 4.0.
 * @author luis f. canals
 * @since Dec, 2007
 */
@Deprecated
public class HighCardSqlTupleReader extends SqlTupleReader {
    private ResultLoader resultLoader;
    private boolean moreRows;

    int maxRows = 0;

    public HighCardSqlTupleReader(final TupleConstraint constraint) {
        super(constraint);
    }

    public void addLevelMembers(
        final RolapLevel level,
        final MemberBuilder memberBuilder,
        final List<RolapMember> srcMembers)
    {
        targets.add(new Target(
            level, memberBuilder, srcMembers, constraint, this));
    }

    protected void prepareTuples(
        final DataSource dataSource,
        final TupleList partialResult,
        final List<List<RolapMember>> newPartialResult)
    {
        String message = "Populating member cache with members for " + targets;
        SqlStatement stmt = null;
        boolean execQuery = (partialResult == null);
        boolean success = false;
        try {
            if (execQuery) {
                // we're only reading tuples from the targets that are
                // non-enum targets
                List<TargetBase> partialTargets = new ArrayList<TargetBase>();
                for (TargetBase target : targets) {
                    if (target.getSrcMembers() == null) {
                        partialTargets.add(target);
                    }
                }
                final Pair<String, List<SqlStatement.Type>> pair =
                    makeLevelMembersSql(dataSource);
                String sql = pair.left;
                List<SqlStatement.Type> types = pair.right;
                stmt = RolapUtil.executeQuery(
                    dataSource, sql, types, maxRows, 0,
                    new SqlStatement.StatementLocus(
                        Locus.peek().execution,
                        "HighCardSqlTupleReader.readTuples " + partialTargets,
                        message,
                        SqlStatementEvent.Purpose.TUPLES, 0),
                    -1, -1, null);
            }

            for (TargetBase target : targets) {
                target.open();
            }

            // determine how many enum targets we have
            int enumTargetCount = getEnumTargetCount();

            int currPartialResultIdx = 0;
            if (execQuery) {
                this.moreRows = stmt.getResultSet().next();
                if (this.moreRows) {
                    ++stmt.rowCount;
                }
            } else {
                this.moreRows = currPartialResultIdx < partialResult.size();
            }

            this.resultLoader =
                new ResultLoader(
                    enumTargetCount,
                    targets, stmt, execQuery, partialResult,
                    newPartialResult);

            // Read first and second elements if exists (or marks
            // source as having "no more rows")
            readNextTuple();
            readNextTuple();
            success = true;
        } catch (SQLException sqle) {
            if (stmt != null) {
                throw stmt.handle(sqle);
            } else {
                throw Util.newError(sqle, message);
            }
        } finally {
            if (!moreRows || !success) {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
    }

    public TupleList readMembers(
        final DataSource dataSource,
        final TupleList partialResult,
        final List<List<RolapMember>> newPartialResult)
    {
        prepareTuples(dataSource, partialResult, newPartialResult);

        assert targets.size() == 1;

        return new UnaryTupleList(
            targets.get(0).close());
    }

    public TupleList readTuples(
        final DataSource jdbcConnection,
        final TupleList partialResult,
        final List<List<RolapMember>> newPartialResult)
    {
        prepareTuples(jdbcConnection, partialResult, newPartialResult);

        // List of tuples
        final int n = targets.size();
        @SuppressWarnings({"unchecked"})
        final List<Member>[] lists = new List[n];
        for (int i = 0; i < n; i++) {
            lists[i] = targets.get(i).close();
        }

        final List<List<Member>> list =
            new TraversalList<Member>(lists, Member.class);
        TupleList tupleList = new DelegatingTupleList(n, list);

        // need to hierarchize the columns from the enumerated targets
        // since we didn't necessarily add them in the order in which
        // they originally appeared in the cross product
        int enumTargetCount = getEnumTargetCount();
        if (enumTargetCount > 0) {
            tupleList = FunUtil.hierarchizeTupleList(tupleList, false);
        }
        return tupleList;
    }

    /**
     * Reads next tuple, notifying all internal targets.
     *
     * @return whether there are any more rows
     */
    public boolean readNextTuple() {
        if (!this.moreRows) {
            return false;
        }
        try {
            this.moreRows = this.resultLoader.loadResult();
        } catch (SQLException sqle) {
            this.moreRows = false;
            throw this.resultLoader.handle(sqle);
        }
        if (!this.moreRows) {
            this.resultLoader.close();
        }
        return this.moreRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public int getMaxRows() {
        return maxRows;
    }

    Collection<RolapCube> getBaseCubeCollection(final Query query) {
        return query.getBaseCubes();
    }
}
// End HighCardSqlTupleReader.java
