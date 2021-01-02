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
// Copyright (C) 2006-2011 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap;

import mondrian3.calc.TupleList;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

/**
 * Describes the public methods of {@link mondrian3.rolap.SqlTupleReader}.
 *
 * @author av
 * @since Nov 21, 2005
 */
public interface TupleReader {
    /**
     * Factory to create new members for a
     * hierarchy from SQL result.
     *
     * @author av
     * @since Nov 11, 2005
     */
    public interface MemberBuilder {

        /**
         * Returns the <code>MemberCache</code> to look up members before
         * creating them.
         *
         * @return member cache
         */
        MemberCache getMemberCache();

        /**
         * Returns the object which acts as the member cache
         * synchronization lock.
         *
         * @return Object to lock
         */
        Object getMemberCacheLock();


        /**
         * Creates a new member (together with its properties).
         *
         * @param parentMember Parent member
         * @param childLevel Child level
         * @param value Member value
         * @param captionValue Caption
         * @param parentChild Whether a parent-child hierarchy
         * @param stmt SQL statement
         * @param key Member key
         * @param column Column ordinal (0-based)
         * @return new member
         * @throws java.sql.SQLException on error
         */
        RolapMember makeMember(
            RolapMember parentMember,
            RolapLevel childLevel,
            Object value,
            Object captionValue,
            boolean parentChild,
            SqlStatement stmt,
            Object key,
            int column)
            throws SQLException;

        /**
         * Returns the 'all' member of the hierarchy.
         *
         * @return The 'all' member
         */
        RolapMember allMember();
    }

    /**
     * Adds a hierarchy to retrieve members from.
     *
     * @param level level that the members correspond to
     * @param memberBuilder used to build new members for this level
     * @param srcMembers if set, array of enumerated members that make up
     *     this level
     */
    void addLevelMembers(
        RolapLevel level,
        MemberBuilder memberBuilder,
        List<RolapMember> srcMembers);

    /**
     * Performs the read.
     *
     * @param dataSource Data source
     * @param partialResult List of rows from previous pass
     * @param newPartialResult Populated with a new list of rows
     *
     * @return a list of tuples
     */
    TupleList readTuples(
        DataSource dataSource,
        TupleList partialResult,
        List<List<RolapMember>> newPartialResult);

    /**
     * Performs the read.
     *
     * @param dataSource source for reading tuples
     * @param partialResult partially cached result that should be used
     * instead of executing sql query
     * @param newPartialResult if non-null, return the result of the read;
     * note that this is a subset of the full return list

     * @return a list of RolapMember
     */
    TupleList readMembers(
        DataSource dataSource,
        TupleList partialResult,
        List<List<RolapMember>> newPartialResult);

    /**
     * Returns an object that uniquely identifies the Result that this
     * {@link TupleReader} would return. Clients may use this as a key for
     * caching the result.
     *
     * @return Cache key
     */
    Object getCacheKey();

}

// End TupleReader.java
