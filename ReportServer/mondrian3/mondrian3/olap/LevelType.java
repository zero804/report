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
// Copyright (C) 2004-2005 Julian Hyde
// Copyright (C) 2005-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap;

/**
 * Enumerates the types of levels.
 *
 * @deprecated Will be replaced with {@link org.olap4j.metadata.Level.Type}
 * before mondrian-4.0.
 *
 * @author jhyde
 * @since 5 April, 2004
 */
public enum LevelType {

    /** Indicates that the level is not related to time. */
    Regular,

    /**
     * Indicates that a level refers to years.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TimeDimension}.
     */
    TimeYears,

    /**
     * Indicates that a level refers to half years.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TimeDimension}.
     */
    TimeHalfYears,

    /**
     * Indicates that a level refers to quarters.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TimeDimension}.
     */
    TimeQuarters,

    /**
     * Indicates that a level refers to months.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TimeDimension}.
     */
    TimeMonths,

    /**
     * Indicates that a level refers to weeks.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TimeDimension}.
     */
    TimeWeeks,

    /**
     * Indicates that a level refers to days.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TimeDimension}.
     */
    TimeDays,

    /**
     * Indicates that a level refers to hours.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TimeDimension}.
     */
    TimeHours,

    /**
     * Indicates that a level refers to minutes.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TimeDimension}.
     */
    TimeMinutes,

    /**
     * Indicates that a level refers to seconds.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TimeDimension}.
     */
    TimeSeconds,

    /**
     * Indicates that a level is an unspecified time period.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TimeDimension}.
     */
    TimeUndefined,

    /**
     * Indicates that a level holds the null member.
     */
    Null;

    /**
     * Returns whether this is a time level.
     *
     * @return Whether this is a time level.
     */
    public boolean isTime() {
        return ordinal() >= TimeYears.ordinal()
           && ordinal() <= TimeUndefined.ordinal();
    }
}

// End LevelType.java
