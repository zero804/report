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
package mondrian3.spi;


import mondrian3.rolap.RolapHierarchy;
import mondrian3.rolap.agg.AggregationKey;


/**
 * Definition of a data source change listener.
 *
 * A change listener can be specified in the connection string.  It is used
 * to ask what is changed in the datasource (e.g. database).
 *
 * Everytime mondrian has to decide whether it will use data from cache, it
 * will call the change listener.  When the change listener tells mondrian
 * the datasource has changed for a dimension, cube, ... then mondrian will
 * flush the cache and read from database again.
 *
 * It is specified in the connection string, like this :
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
 * @deprecated Will be removed with Mondrian 4.0.
 * @author Bart Pappyn
 * @since Dec 12, 2006
 */
@Deprecated
public interface DataSourceChangeListener {

    /**
     * Checks if the given hierarchy has changed since the previous
     * time this function was called.
     *
     * The first time, this function will be called when the cache
     * is still empty.  This is because the plugin is able to register
     * the first timestamp the function was accessed.
     *
     * It is highly recommended to optimize the plugin and minimize
     * the time needed to evaluate this function, because this plugin
     * is called many times for each mondrian query.
     */
    public boolean isHierarchyChanged(RolapHierarchy hierarchy);

    /**
     * Checks if the given aggregation has changed since the previous
     * time this function was called.
     *
     * The first time, this function will be called when the cache
     * is still empty.  This is because the plugin is able to register
     * the first timestamp the function was accessed.
     */
    public boolean isAggregationChanged(AggregationKey aggregation);
}

// End DataSourceChangeListener.java
