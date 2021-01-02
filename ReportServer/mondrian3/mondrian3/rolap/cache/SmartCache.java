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
// Copyright (C) 2006-2012 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap.cache;

import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Defines a cache API. Implementations exist for hard and soft references.
 *
 * <p>To iterate over the contents of a cache, you must pass a
 * {@link #execute(SmartCacheTask)} instance. The code using the iterator
 * can be assured that it will be thread safe.
 *
 * <p>Implementations are responsible of enforcing thread safety.
 * @author av
 * @since Nov 21, 2005
 */
public interface SmartCache <K, V> {
    /**
     * Places a key/value pair into the cache.
     *
     * @param key Key
     * @param value Value
     * @return the previous value of <code>key</code> or null
     */
    V put(K key, V value);

    /**
     * Looks up and returns a cache value according to a given key.
     * If the cache does not correspond an entry corresponding to the key,
     * <code>null</code> is returned.
     */
    V get(K key);

    /**
     * Removes a key from the cache.
     *
     * @param key Key
     *
     * @return Previous value associated with the key
     */
    V remove(K key);

    /**
     * Clears the contents of this cache.
     */
    void clear();

    /**
     * Returns the number of elements in cache.
     */
    int size();

    /**
     * Executes a task over the contents of the cache and guarantees
     * exclusive write access while processing.
     * @param task The task to execute.
     */
    void execute(SmartCacheTask<K, V> task);

    /**
     * Defines a task to be run over the entries of the cache.
     * Used in conjunction with {@link #execute(Iterator)}.
     */
    public interface SmartCacheTask<K, V> {
        void execute(
            Iterator<Entry<K, V>> iterator);
    }
}

// End SmartCache.java
