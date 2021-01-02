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
package mondrian3.calc;

import mondrian3.olap.Member;

import java.util.Iterator;
import java.util.List;

/**
 * Extension to {@link java.util.Iterator} that returns tuples.
 *
 * <p>Extends {@link TupleCursor} to support the standard Java iterator
 * API. For some implementations, using the iterator API (in particular the
 * {@link #next} and {@link #hasNext} methods) may be more expensive than using
 * cursor's {@link #forward} method.
 *
 * @author jhyde
 */
public interface TupleIterator extends Iterator<List<Member>>, TupleCursor {

}

// End TupleIterator.java
