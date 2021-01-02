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
// Copyright (C) 2012-2012 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap.cache;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.locks.*;

/**
 * A base implementation of the {@link SmartCache} interface which
 * enforces synchronization with a ReadWrite lock.
 */
public abstract class SmartCacheImpl<K, V>
    implements SmartCache<K, V>
{
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Must provide an iterator on the contents of the cache.
     * It does not need to be thread safe because we will handle
     * that in {@link SmartCacheImpl}.
     */
    protected abstract Iterator<Entry<K, V>> iteratorImpl();

    protected abstract V putImpl(K key, V value);
    protected abstract V getImpl(K key);
    protected abstract V removeImpl(K key);
    protected abstract void clearImpl();
    protected abstract int sizeImpl();

    public V put(K key, V value) {
        lock.writeLock().lock();
        try {
            return putImpl(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public V get(K key) {
        lock.readLock().lock();
        try {
            return getImpl(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    public V remove(K key) {
        lock.writeLock().lock();
        try {
            return removeImpl(key);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void clear() {
        lock.writeLock().lock();
        try {
            clearImpl();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int size() {
        lock.readLock().lock();
        try {
            return sizeImpl();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void execute(SmartCache.SmartCacheTask<K, V> task) {
        lock.writeLock().lock();
        try {
            task.execute(iteratorImpl());
        } finally {
            lock.writeLock().unlock();
        }
    }
}
// End SmartCacheImpl.java