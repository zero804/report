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
// Copyright (C) 2007-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap4j;

import mondrian3.olap.Property;
import mondrian3.rolap.*;

import org.olap4j.metadata.Datatype;
import org.olap4j.metadata.Measure;

/**
 * Implementation of {@link org.olap4j.metadata.Measure}
 * for the Mondrian OLAP engine,
 * as a wrapper around a mondrian
 * {@link mondrian3.rolap.RolapStoredMeasure}.
 *
 * @author jhyde
 * @since Dec 10, 2007
 */
class MondrianOlap4jMeasure
    extends MondrianOlap4jMember
    implements Measure
{
    MondrianOlap4jMeasure(
        MondrianOlap4jSchema olap4jSchema,
        RolapMeasure measure)
    {
        super(olap4jSchema, measure);
    }

    public Aggregator getAggregator() {
        if (!(member instanceof RolapStoredMeasure)) {
            return Aggregator.UNKNOWN;
        }
        final RolapAggregator aggregator =
            ((RolapStoredMeasure) member).getAggregator();
        if (aggregator == RolapAggregator.Avg) {
            return Aggregator.AVG;
        } else if (aggregator == RolapAggregator.Count) {
            return Aggregator.COUNT;
        } else if (aggregator == RolapAggregator.DistinctCount) {
            return Aggregator.UNKNOWN;
        } else if (aggregator == RolapAggregator.Max) {
            return Aggregator.MAX;
        } else if (aggregator == RolapAggregator.Min) {
            return Aggregator.MIN;
        } else if (aggregator == RolapAggregator.Sum) {
            return Aggregator.SUM;
        } else {
            return Aggregator.UNKNOWN;
        }
    }

    public Datatype getDatatype() {
        final String datatype =
            (String) member.getPropertyValue(Property.DATATYPE.getName());
        if (datatype != null) {
            if (datatype.equals("Integer")) {
                return Datatype.INTEGER;
            } else if (datatype.equals("Numeric")) {
                return Datatype.DOUBLE;
            }
        }
        return Datatype.STRING;
    }
}

// End MondrianOlap4jMeasure.java
