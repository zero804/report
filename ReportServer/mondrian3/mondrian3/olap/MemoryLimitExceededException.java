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
package mondrian3.olap;

/**
 * Exception which indicates some resource limit was exceeded.
 * When a client receives a <code>MemoryLimitExceededException</code> the state
 * of the objects associated with the query execution can NOT be
 * counted on being correct - specifically data structures could be
 * in an inconsistent state or missing entirely. No attempt should be
 * make to access or use the result objects.
 */
public class MemoryLimitExceededException
    extends ResultLimitExceededException
{

    public MemoryLimitExceededException(String message) {
        super(message);
    }
}

// End MemoryLimitExceededException.java
