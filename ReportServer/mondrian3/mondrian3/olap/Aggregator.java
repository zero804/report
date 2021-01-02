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
package mondrian3.olap;

import mondrian3.calc.Calc;
import mondrian3.calc.TupleList;
import mondrian3.spi.Dialect.Datatype;
import mondrian3.spi.SegmentBody;

import java.util.List;

/**
 * Describes an aggregation operator, such as "sum" or "count".
 *
 * @see FunDef
 * @see Evaluator
 *
 * @author jhyde$
 * @since Jul 9, 2003$
 */
public interface Aggregator {
    /**
     * Returns the aggregator used to combine sub-totals into a grand-total.
     *
     * @return aggregator used to combine sub-totals into a grand-total
     */
    Aggregator getRollup();

    /**
     * Applies this aggregator to an expression over a set of members and
     * returns the result.
     *
     * @param evaluator Evaluation context
     * @param members List of members, not null
     * @param calc Expression to evaluate
     *
     * @return result of applying this aggregator to a set of members/tuples
     */
    Object aggregate(Evaluator evaluator, TupleList members, Calc calc);

    /**
     * Tells Mondrian if this aggregator can perform fast aggregation
     * using only the raw data of a given object type. This will
     * determine if Mondrian will attempt to perform in-memory rollups
     * on raw segment data by invoking {@link #aggregate}.
     *
     * <p>This is only invoked for rollup operations.
     *
     * @param datatype The datatype of the object we would like to rollup.
     * @return Whether this aggregator supports fast aggregation
     */
    boolean supportsFastAggregates(Datatype datatype);

    /**
     * Applies this aggregator over a raw list of objects for a rollup
     * operation. This is useful when the values are already resolved
     * and we are dealing with a raw {@link SegmentBody} object.
     *
     * <p>Only gets called if
     * {@link #supportsFastAggregates(mondrian3.spi.Dialect.Datatype)} is true.
     *
     * <p>This is only invoked for rollup operations.
     *
     * @param rawData An array of values in its raw form, to be aggregated.
     * @return A rolled up value of the raw data.
     * if the object type is not supported.
     */
    Object aggregate(List<Object> rawData, Datatype datatype);
}

// End Aggregator.java
