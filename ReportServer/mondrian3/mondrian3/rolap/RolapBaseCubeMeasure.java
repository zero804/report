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
// Copyright (C) 2006-2012 Pentaho
// All Rights Reserved.
*/
package mondrian3.rolap;

import mondrian3.olap.*;
import mondrian3.resource.MondrianResource;
import mondrian3.spi.CellFormatter;
import mondrian3.spi.Dialect;

import java.util.*;

/**
 * Measure which is computed from a SQL column (or expression) and which is
 * defined in a non-virtual cube.
 *
 * @see RolapVirtualCubeMeasure
 *
 * @author jhyde
 * @since 24 August, 2006
 */
public class RolapBaseCubeMeasure
    extends RolapMemberBase
    implements RolapStoredMeasure
{
    static enum DataType {
        Integer,
        Numeric,
        String
    }

    /**
     * For SQL generator. Column which holds the value of the measure.
     */
    private final MondrianDef.Expression expression;

    /**
     * For SQL generator. Has values "SUM", "COUNT", etc.
     */
    private final RolapAggregator aggregator;

    private final RolapCube cube;
    private final Map<String, Annotation> annotationMap;

    /**
     * Holds the {@link mondrian3.rolap.RolapStar.Measure} from which this
     * member is computed. Untyped, because another implementation might store
     * it somewhere else.
     */
    private Object starMeasure;

    private RolapResult.ValueFormatter formatter;

    /**
     * Creates a RolapBaseCubeMeasure.
     *
     * @param cube Cube
     * @param parentMember Parent member
     * @param level Level this member belongs to
     * @param name Name of this member
     * @param caption Caption
     * @param description Description
     * @param formatString Format string
     * @param expression Expression
     * @param aggregatorName Aggregator
     * @param datatype Data type
     * @param annotationMap Annotations
     */
    RolapBaseCubeMeasure(
        RolapCube cube,
        RolapMember parentMember,
        RolapLevel level,
        String name,
        String caption,
        String description,
        String formatString,
        MondrianDef.Expression expression,
        String aggregatorName,
        String datatype,
        Map<String, Annotation> annotationMap)
    {
        super(parentMember, level, name, null, MemberType.MEASURE);
        assert annotationMap != null;
        this.cube = cube;
        this.annotationMap = annotationMap;
        this.caption = caption;
        this.expression = expression;
        if (description != null) {
            setProperty(
                Property.DESCRIPTION.name,
                description);
        }
        if (formatString == null) {
            formatString = "";
        } else {
            setProperty(
                Property.FORMAT_STRING.name,
                formatString);
        }
        setProperty(
            Property.FORMAT_EXP_PARSED.name,
            Literal.createString(formatString));
        setProperty(
            Property.FORMAT_EXP.name,
            formatString);

        // Validate aggregator.
        this.aggregator =
            RolapAggregator.enumeration.getValue(aggregatorName, false);
        if (this.aggregator == null) {
            StringBuilder buf = new StringBuilder();
            for (String aggName : RolapAggregator.enumeration.getNames()) {
                if (buf.length() > 0) {
                    buf.append(", ");
                }
                buf.append('\'');
                buf.append(aggName);
                buf.append('\'');
            }
            throw MondrianResource.instance().UnknownAggregator.ex(
                aggregatorName,
                buf.toString());
        }

        setProperty(Property.AGGREGATION_TYPE.name, aggregator);
        if (datatype == null) {
            if (aggregator == RolapAggregator.Count
                || aggregator == RolapAggregator.DistinctCount)
            {
                datatype = "Integer";
            } else {
                datatype = "Numeric";
            }
        }
        if (RolapBaseCubeMeasure.DataType.valueOf(datatype) == null) {
            throw MondrianResource.instance().CastInvalidType.ex(datatype);
        }
        setProperty(Property.DATATYPE.name, datatype);
    }

    public MondrianDef.Expression getMondrianDefExpression() {
        return expression;
    }

    public RolapAggregator getAggregator() {
        return aggregator;
    }

    public RolapCube getCube() {
        return cube;
    }

    public RolapResult.ValueFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(CellFormatter cellFormatter) {
        this.formatter =
            new RolapResult.CellFormatterValueFormatter(cellFormatter);
    }

    public Object getStarMeasure() {
        return starMeasure;
    }

    void setStarMeasure(Object starMeasure) {
        this.starMeasure = starMeasure;
    }

    @Override
    public Map<String, Annotation> getAnnotationMap() {
        return annotationMap;
    }

    public Dialect.Datatype getDatatype() {
        Object datatype = getPropertyValue(Property.DATATYPE.name);
        try {
            return Dialect.Datatype.valueOf((String) datatype);
        } catch (ClassCastException e) {
            return Dialect.Datatype.String;
        } catch (IllegalArgumentException e) {
            return Dialect.Datatype.String;
        }
    }
}

// End RolapBaseCubeMeasure.java
