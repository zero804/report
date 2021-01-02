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
// Copyright (C) 2005-2005 Julian Hyde
// Copyright (C) 2005-2009 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.recorder;

import mondrian3.olap.Util;

import java.io.PrintStream;

/**
 * Implementation of {@link MessageRecorder} simply writes messages to
 * PrintStreams.
 */
public class PrintStreamRecorder extends AbstractRecorder {
    private final PrintStream err;
    private final PrintStream out;

    public PrintStreamRecorder() {
        this(System.out, System.err);
    }

    public PrintStreamRecorder(final PrintStream out, final PrintStream err) {
        this.out = out;
        this.err = err;
    }

    protected void recordMessage(
        final String msg,
        final Object info,
        final MsgType msgType)
    {
        PrintStream ps;
        String prefix;
        switch (msgType) {
        case INFO:
            prefix = "INFO: ";
            ps = out;
            break;
        case WARN:
            prefix = "WARN: ";
            ps = out;
            break;
        case ERROR:
            prefix = "ERROR: ";
            ps = err;
            break;
        default:
            throw Util.unexpected(msgType);
        }
        String context = getContext();

        ps.print(prefix);
        ps.print(context);
        ps.print(": ");
        ps.println(msg);
    }
}

// End PrintStreamRecorder.java
