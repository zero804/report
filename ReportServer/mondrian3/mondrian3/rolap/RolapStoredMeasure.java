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
// Copyright (C) 2005-2007 Pentaho and others
// All Rights Reserved.
//
// jhyde, 10 August, 2001
*/
package mondrian3.rolap;

import mondrian3.olap.MondrianDef;

/**
 * A measure which is implemented by a SQL column or SQL expression (as opposed
 * to a {@link RolapCalculatedMember}.
 *
 * <p>Implemented by {@link RolapBaseCubeMeasure} and
 * {@link RolapVirtualCubeMeasure}.
 *
 * @author jhyde
 * @since 10 August, 2001
 */
public interface RolapStoredMeasure extends RolapMeasure {
    /**
     * Returns the cube this measure belongs to.
     */
    RolapCube getCube();

    /**
     * Returns the column which holds the value of the measure.
     */
    MondrianDef.Expression getMondrianDefExpression();

    /**
     * Returns the aggregation function which rolls up this measure: "SUM",
     * "COUNT", etc.
     */
    RolapAggregator getAggregator();

    /**
     * Returns the {@link mondrian3.rolap.RolapStar.Measure} from which this
     * member is computed. Untyped, because another implementation might store
     * it somewhere else.
     */
    Object getStarMeasure();
}

// End RolapStoredMeasure.java
