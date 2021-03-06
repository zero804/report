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
// Copyright (C) 2006-2009 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap.sql;

/**
 * Restricts the members that are fetched by SqlMemberSource.
 *
 * @see mondrian3.rolap.SqlMemberSource
 *
 * @author av
 * @since Nov 2, 2005
 */
public interface SqlConstraint {

   /**
    * Returns a key that becomes part of the key for caching the
    * result of the SQL query. So SqlConstraint instances that
    * produce the same SQL resultset must return equal keys
    * in terms of equal() and hashCode().
    * @return valid key or null to prevent the result from being cached
    */
    Object getCacheKey();
}

// End SqlConstraint.java
