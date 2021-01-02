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
// Copyright (C) 2005-2009 Pentaho
// All Rights Reserved.
*/
package mondrian3.spi.impl;

import mondrian3.olap.MondrianDef;
import mondrian3.rolap.RolapHierarchy;
import mondrian3.rolap.agg.AggregationKey;
import mondrian3.spi.DataSourceChangeListener;

import java.util.Random;


/**
 * Default implementation of a data source change listener
 * that always returns that the datasource is changed.
 *
 * A change listener can be specified in the connection string.  It is used
 * to ask what is changed in the datasource (e.g. database).
 *
 * Everytime mondrian has to decide whether it will use data from cache, it
 * will call the change listener.  When the change listener tells mondrian
 * the datasource has changed for a dimension, cube, ... then mondrian will
 * flush the cache and read from database again.
 *
 * It is specified in the connection string, like this:
 *
 * <blockquote><code>
 * Jdbc=jdbc:odbc:MondrianFoodMart; JdbcUser=ziggy; JdbcPassword=stardust;
 * DataSourceChangeListener=com.acme.MyChangeListener;
 * </code></blockquote>
 *
 * This class should be called in mondrian before any data is read, so
 * even before cache is build.  This way, the plugin is able to register
 * the first timestamp mondrian tries to read the datasource.
 *
 * @author Bart Pappyn
 * @since Dec 12, 2006
 */

public class DataSourceChangeListenerImpl4 implements DataSourceChangeListener {
    private int flushInverseFrequencyHierarchy;
    private int flushInverseFrequencyAggregation;
    final Random random = new Random(123456);

    /** Creates a new instance of DataSourceChangeListenerImpl2 */
    public DataSourceChangeListenerImpl4() {
        this(0, 0);
    }

    public DataSourceChangeListenerImpl4(
        int flushInverseFrequencyHierarchy,
        int flushInverseFrequencyAggregation)
    {
        this.flushInverseFrequencyHierarchy = flushInverseFrequencyHierarchy;
        this.flushInverseFrequencyAggregation =
            flushInverseFrequencyAggregation;
    }

    public synchronized boolean isHierarchyChanged(RolapHierarchy hierarchy) {
        if (flushInverseFrequencyHierarchy != 0) {
            if (random.nextInt(flushInverseFrequencyHierarchy) == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public synchronized boolean isAggregationChanged(
        AggregationKey aggregation)
    {
        if (flushInverseFrequencyAggregation != 0) {
            if (random.nextInt(flushInverseFrequencyAggregation) == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public String getTableName(RolapHierarchy hierarchy) {
        MondrianDef.RelationOrJoin relation = hierarchy.getRelation();
        if (relation instanceof MondrianDef.Table) {
            MondrianDef.Table tableRelation = (MondrianDef.Table)relation;
            return tableRelation.name;
        } else {
            return null;
        }
    }
}

// End DataSourceChangeListenerImpl4.java
