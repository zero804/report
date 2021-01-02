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
// Copyright (C) 2012-2012 Pentaho
// All Rights Reserved.
*/
package mondrian3.rolap.sql;

import mondrian3.olap.Evaluator;
import mondrian3.olap.MondrianDef;
import mondrian3.rolap.*;
import mondrian3.rolap.aggmatcher.AggStar;
import mondrian3.spi.Dialect;
import mondrian3.util.Pair;

import java.util.List;

/**
 * Restricts the SQL result set to members where particular columns have
 * particular values.
 *
 * @version $Id$
 */
public class MemberKeyConstraint
    implements TupleConstraint
{
    private final Pair<List<MondrianDef.Expression>, List<Comparable>> cacheKey;
    private final List<MondrianDef.Expression> columnList;
    private final List<Dialect.Datatype> datatypeList;
    private final List<Comparable> valueList;

    public MemberKeyConstraint(
        List<MondrianDef.Expression> columnList,
        List<Dialect.Datatype> datatypeList,
        List<Comparable> valueList)
    {
        this.columnList = columnList;
        this.datatypeList = datatypeList;
        this.valueList = valueList;
        cacheKey = Pair.of(columnList, valueList);
    }

    public void addConstraint(
        SqlQuery sqlQuery, RolapCube baseCube, AggStar aggStar)
    {
        for (int i = 0; i < columnList.size(); i++) {
            MondrianDef.Expression expression = columnList.get(i);
            final Comparable value = valueList.get(i);
            final Dialect.Datatype datatype = datatypeList.get(i);
            sqlQuery.addWhere(
                SqlConstraintUtils.constrainLevel2(
                    sqlQuery,
                    expression,
                    datatype,
                    value));
        }
    }

    public void addLevelConstraint(
        SqlQuery sqlQuery,
        RolapCube baseCube,
        AggStar aggStar,
        RolapLevel level)
    {
    }

    public MemberChildrenConstraint getMemberChildrenConstraint(
        RolapMember parent)
    {
        return null;
    }

    public String toString() {
        return "MemberKeyConstraint";
    }


    public Object getCacheKey() {
        return cacheKey;
    }

    public Evaluator getEvaluator() {
        return null;
    }
}

// End MemberKeyConstraint.java
