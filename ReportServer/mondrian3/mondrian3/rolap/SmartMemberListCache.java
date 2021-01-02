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
// Copyright (C) 2006-2010 Pentaho
// All Rights Reserved.
*/
package mondrian3.rolap;

import mondrian3.rolap.cache.SmartCache;
import mondrian3.rolap.cache.SoftSmartCache;
import mondrian3.rolap.sql.SqlConstraint;
import mondrian3.util.Pair;

/**
 * Uses a {@link mondrian3.rolap.cache.SmartCache} to store lists of members,
 * where the key depends on a {@link mondrian3.rolap.sql.SqlConstraint}.
 *
 * <p>Example 1:
 *
 * <pre>
 *   select ...
 *   [Customer].[Name].members on rows
 *   ...
 * </pre>
 *
 * <p>Example 2:
 * <pre>
 *   select ...
 *   NON EMPTY [Customer].[Name].members on rows
 *   ...
 *   WHERE ([Store#14], [Product].[Product#1])
 * </pre>
 *
 * <p>The first set, <em>all</em> customers are computed, in the second only
 * those, who have bought Product#1 in Store#14. We want to put both results
 * into the cache. Then the key for the cache entry is the Level that the
 * members belong to <em>plus</em> the costraint that restricted the amount of
 * members fetched. For Level.Members the key consists of the Level and the
 * cacheKey of the {@link mondrian3.rolap.sql.SqlConstraint}.
 *
 * @see mondrian3.rolap.sql.SqlConstraint#getCacheKey
 *
 * @author av
 * @since Nov 21, 2005
 */
public class SmartMemberListCache <K, V> {
    SmartCache<Pair<K, Object>, V> cache;

    public SmartMemberListCache() {
        cache = new SoftSmartCache<Pair<K, Object>, V>();
    }

    public Object put(K key, SqlConstraint constraint, V value) {
        Object cacheKey = constraint.getCacheKey();
        if (cacheKey == null) {
            return null;
        }
        Pair<K, Object> key2 = new Pair<K, Object>(key, cacheKey);
        return cache.put(key2, value);
    }

    public V get(K key, SqlConstraint constraint) {
        Pair<K, Object> key2 =
            new Pair<K, Object>(key, constraint.getCacheKey());
        return cache.get(key2);
    }

    public void clear() {
        cache.clear();
    }

    SmartCache<Pair<K, Object>, V> getCache() {
        return cache;
    }

    void setCache(SmartCache<Pair<K, Object>, V> cache) {
        this.cache = cache;
    }
}

// End SmartMemberListCache.java
