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
// Copyright (C) 2011-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * View of an object as a map. Each attribute name is an key, and the value of
 * the attribute is the value.
 *
 * <p>Currently only returns public fields, not methods.</p>
 */
public class BeanMap extends AbstractMap<String, Object> {
    private final Object o;
    private final Info info;
    private static final Map<Class, Info> INFO_MAP =
        new WeakHashMap<Class, Info>();

    /**
     * Creates a map view onto an object.
     *
     * @param o Object
     */
    public BeanMap(Object o) {
        this.o = o;
        this.info = getInfo(o.getClass());
    }

    private static Info getInfo(Class<?> clazz) {
        synchronized (INFO_MAP) {
            Info info = INFO_MAP.get(clazz);
            if (info == null) {
                info = new Info(clazz);
                INFO_MAP.put(clazz, info);
            }
            return info;
        }
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return new AbstractSet<Entry<String, Object>>() {
            @Override
            public Iterator<Entry<String, Object>> iterator() {
                final Iterator<BeanField> fieldIterator =
                    info.fields.iterator();
                return new Iterator<Entry<String, Object>>() {
                    public boolean hasNext() {
                        return fieldIterator.hasNext();
                    }

                    public Entry<String, Object> next() {
                        final BeanField field = fieldIterator.next();
                        return Pair.of(field.name(), field.value(o));
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

            @Override
            public int size() {
                return info.fields.size();
            }
        };
    }

    public static String xxx(Object o) {
        return o.getClass().getSimpleName() + new BeanMap(o);
    }

    private static class Info {
        private List<BeanField> fields = new ArrayList<BeanField>();

        public Info(Class<? extends Object> clazz) {
            for (final Field field : clazz.getDeclaredFields()) {
                final int mod = field.getModifiers();
                if (Modifier.isStatic(mod)
                    || !Modifier.isPublic(mod))
                {
                    continue;
                }
                fields.add(
                    new BeanField() {
                        public String name() {
                            return field.getName();
                        }

                        public Object value(Object o) {
                            try {
                                return field.get(o);
                            } catch (IllegalAccessException e) {
                                return null;
                            }
                        }
                    }
                );
            }
        }
    }

    private interface BeanField {
        String name();
        Object value(Object o);
    }
}

// End BeanMap.java
