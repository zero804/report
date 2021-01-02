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
// Copyright (C) 2011-2012 Pentaho
// All Rights Reserved.
*/
package mondrian3.server.monitor;

/**
 * Visitor for events.
 */
public interface Visitor<T> {
    T visit(ConnectionStartEvent event);
    T visit(ConnectionEndEvent event);
    T visit(StatementStartEvent event);
    T visit(StatementEndEvent event);
    T visit(ExecutionStartEvent event);
    T visit(ExecutionPhaseEvent event);
    T visit(ExecutionEndEvent event);
    T visit(SqlStatementStartEvent event);
    T visit(SqlStatementExecuteEvent event);
    T visit(SqlStatementEndEvent event);
    T visit(CellCacheSegmentCreateEvent event);
    T visit(CellCacheSegmentDeleteEvent event);
}

// End Visitor.java
