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
// Copyright (C) 2008-2008 TASecurity Group Spain
// Copyright (C) 2008-2012 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap;

import mondrian3.rolap.sql.MemberChildrenConstraint;
import mondrian3.rolap.sql.TupleConstraint;
import mondrian3.spi.DataSourceChangeListener;

import java.util.List;

/**
 * Encapsulation of member caching for no caching.
 *
 * @author Luis F. Canals (lcanals@tasecurity.net)
 * @version 1.0
 */
public class MemberNoCacheHelper extends MemberCacheHelper {
    DataSourceChangeListener changeListener;

    public MemberNoCacheHelper() {
        super(null);
    }

    // implement MemberCache
    public RolapMember getMember(
        Object key,
        boolean mustCheckCacheStatus)
    {
        return null;
    }


    // implement MemberCache
    public Object putMember(Object key, RolapMember value) {
        return value;
    }

    // implement MemberCache
    public Object makeKey(RolapMember parent, Object key) {
        return new MemberKey(parent, key);
    }

    // implement MemberCache
    // synchronization: Must synchronize, because modifies mapKeyToMember
    public synchronized RolapMember getMember(Object key) {
        return getMember(key, true);
    }

    public void checkCacheStatus() {
    }

    public void putLevelMembersInCache(
        RolapLevel level,
        TupleConstraint constraint,
        List<RolapMember> members)
    {
    }

    public List<RolapMember> getChildrenFromCache(
        RolapMember member,
        MemberChildrenConstraint constraint)
    {
        return null;
    }

    public void putChildren(
        RolapMember member,
        MemberChildrenConstraint constraint,
        List<RolapMember> children)
    {
    }

    public List<RolapMember> getLevelMembersFromCache(
        RolapLevel level,
        TupleConstraint constraint)
    {
        return null;
    }

    public DataSourceChangeListener getChangeListener() {
        return changeListener;
    }

    public void setChangeListener(DataSourceChangeListener listener) {
        changeListener = listener;
    }

    public boolean isMutable() {
        return true;
    }

    public RolapMember removeMember(Object key) {
        return null;
    }

    public RolapMember removeMemberAndDescendants(Object key) {
        return null;
    }
}

// End MemberNoCacheHelper.java
