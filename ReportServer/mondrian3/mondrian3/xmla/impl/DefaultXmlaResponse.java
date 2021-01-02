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
// Copyright (C) 2005-2010 Pentaho
// All Rights Reserved.
*/
package mondrian3.xmla.impl;

import mondrian3.olap.Util;
import mondrian3.xmla.*;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Default implementation of {@link mondrian3.xmla.XmlaResponse}.
 *
 * @author Gang Chen
 */
public class DefaultXmlaResponse implements XmlaResponse  {

    // TODO: add a msg to MondrianResource for this.
    private static final String MSG_ENCODING_ERROR = "Encoding unsupported: ";

    private final SaxWriter writer;

    public DefaultXmlaResponse(
        OutputStream outputStream,
        String encoding,
        Enumeration.ResponseMimeType responseMimeType)
    {
        try {
            switch (responseMimeType) {
            case JSON:
                writer = new JsonSaxWriter(outputStream);
                break;
            case SOAP:
            default:
                writer = new DefaultSaxWriter(outputStream, encoding);
                break;
            }
        } catch (UnsupportedEncodingException uee) {
            throw Util.newError(uee, MSG_ENCODING_ERROR + encoding);
        }
    }

    public SaxWriter getWriter() {
        return writer;
    }

    public void error(Throwable t) {
        writer.completeBeforeElement("root");
        @SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
        Throwable throwable = XmlaUtil.rootThrowable(t);
        writer.startElement("Messages");
        writer.startElement(
            "Error",
            "ErrorCode", throwable.getClass().getName(),
            "Description", throwable.getMessage(),
            "Source", "Mondrian",
            "Help", "");
        writer.endElement(); // </Messages>
        writer.endElement(); // </Error>
    }
}

// End DefaultXmlaResponse.java
