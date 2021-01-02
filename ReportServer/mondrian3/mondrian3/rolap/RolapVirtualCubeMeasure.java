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
// Copyright (C) 2006-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.rolap;

import mondrian3.olap.Annotation;
import mondrian3.olap.MondrianDef;

import java.util.Map;

/**
 * Measure which is defined in a virtual cube, and based on a stored measure
 * in one of the virtual cube's base cubes.
 *
 * @author jhyde
 * @since Aug 18, 2006
 */
public class RolapVirtualCubeMeasure
    extends RolapMemberBase
    implements RolapStoredMeasure
{
    /**
     * The measure in the underlying cube.
     */
    private final RolapStoredMeasure cubeMeasure;
    private final Map<String, Annotation> annotationMap;

    public RolapVirtualCubeMeasure(
        RolapMember parentMember,
        RolapLevel level,
        RolapStoredMeasure cubeMeasure,
        Map<String, Annotation> annotationMap)
    {
        super(parentMember, level, cubeMeasure.getName());
        this.cubeMeasure = cubeMeasure;
        this.annotationMap = annotationMap;
    }

    public Object getPropertyValue(String propertyName, boolean matchCase) {
        // Look first in this member (against the virtual cube), then
        // fallback on the base measure.
        // This allows, for instance, a measure to be invisible in a virtual
        // cube but visible in its base cube.
        Object value = super.getPropertyValue(propertyName, matchCase);
        if (value == null) {
            value = cubeMeasure.getPropertyValue(propertyName, matchCase);
        }
        return value;
    }

    public RolapCube getCube() {
        return cubeMeasure.getCube();
    }

    public Object getStarMeasure() {
        return cubeMeasure.getStarMeasure();
    }

    public MondrianDef.Expression getMondrianDefExpression() {
        return cubeMeasure.getMondrianDefExpression();
    }

    public RolapAggregator getAggregator() {
        return cubeMeasure.getAggregator();
    }

    public RolapResult.ValueFormatter getFormatter() {
        return cubeMeasure.getFormatter();
    }

    public Map<String, Annotation> getAnnotationMap() {
        return annotationMap;
    }
}

// End RolapVirtualCubeMeasure.java
