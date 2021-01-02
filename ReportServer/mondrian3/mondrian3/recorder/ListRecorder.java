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

import org.apache.log4j.Logger;

import java.util.*;

/**
 * Implementation of {@link MessageRecorder} that records each message
 * in a {@link List}. The calling code can then access the list and take
 * actions as needed.
 */
public class ListRecorder extends AbstractRecorder {

    private final List<Entry> errorList;
    private final List<Entry> warnList;
    private final List<Entry> infoList;

    public ListRecorder() {
        errorList = new ArrayList<Entry>();
        warnList = new ArrayList<Entry>();
        infoList = new ArrayList<Entry>();
    }

    public void clear() {
        super.clear();
        errorList.clear();
        warnList.clear();
        infoList.clear();
    }

    public Iterator<Entry> getErrorEntries() {
        return errorList.iterator();
    }

    public Iterator<Entry> getWarnEntries() {
        return warnList.iterator();
    }

    public Iterator<Entry> getInfoEntries() {
        return infoList.iterator();
    }

    protected void recordMessage(
        final String msg,
        final Object info,
        final MsgType msgType)
    {
        String context = getContext();

        Entry e = new Entry(context, msg, msgType, info);
        switch (msgType) {
        case INFO:
            infoList.add(e);
            break;
        case WARN:
            warnList.add(e);
            break;
        case ERROR:
            errorList.add(e);
            break;
        default:
            e = new Entry(
                context,
                "Unknown message type enum \"" + msgType
                + "\" for message: " + msg,
                MsgType.WARN,
                info);
            warnList.add(e);
        }
    }

    public void logInfoMessage(final Logger logger) {
        if (hasInformation()) {
            logMessage(getInfoEntries(), logger);
        }
    }

    public void logWarningMessage(final Logger logger) {
        if (hasWarnings()) {
            logMessage(getWarnEntries(), logger);
        }
    }

    public void logErrorMessage(final Logger logger) {
        if (hasErrors()) {
            logMessage(getErrorEntries(), logger);
        }
    }

    static void logMessage(Iterator<Entry> it, Logger logger) {
        while (it.hasNext()) {
            Entry e = it.next();
            logMessage(e, logger);
        }
    }

    static void logMessage(
        final Entry e,
        final Logger logger)
    {
        logMessage(e.getContext(), e.getMessage(), e.getMsgType(), logger);
    }

    /**
     * Entry is a Info, Warning or Error message. This is the object stored
     * in the Lists MessageRecorder's info, warning and error message lists.
     */
    public static class Entry {
        private final String context;
        private final String msg;
        private final MsgType msgType;
        private final Object info;

        private Entry(
            final String context,
            final String msg,
            final MsgType msgType,
            final Object info)
        {
            this.context = context;
            this.msg = msg;
            this.msgType = msgType;
            this.info = info;
        }

        public String getContext() {
            return context;
        }

        public String getMessage() {
            return msg;
        }

        public MsgType getMsgType() {
            return msgType;
        }

        public Object getInfo() {
            return info;
        }
    }
}

// End ListRecorder.java
