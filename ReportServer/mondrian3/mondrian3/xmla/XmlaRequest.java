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
// Copyright (C) 2005-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.xmla;

import org.olap4j.metadata.XmlaConstants;

import java.util.Map;

/**
 * XML/A request interface.
 *
 * @author Gang Chen
 */
public interface XmlaRequest {

    /**
     * Indicate DISCOVER or EXECUTE method.
     */
    XmlaConstants.Method getMethod();

    /**
     * Properties of XML/A request.
     */
    Map<String, String> getProperties();

    /**
     * Restrictions of DISCOVER method.
     *
     * <p>If the value is a list of strings, the restriction passes if the
     * column has one of the values.
     */
    Map<String, Object> getRestrictions();

    /**
     * Statement of EXECUTE method.
     */
    String getStatement();

    /**
     * Role name binds with this XML/A request. Maybe null.
     */
    String getRoleName();

    /**
     * Request type of DISCOVER method.
     */
    String getRequestType();

    /**
     * Indicate whether statement is a drill through statement of
     * EXECUTE method.
     */
    boolean isDrillThrough();

    /**
     * The username to use to open the underlying olap4j connection.
     * Can be null.
     */
    String getUsername();

    /**
     * The password to use to open the underlying olap4j connection.
     * Can be null.
     */
    String getPassword();

    /**
     * Returns the id of the session this request belongs to.
     *
     * <p>Not necessarily the same as the HTTP session: the SOAP request
     * contains its own session information.</p>
     *
     * <p>The session id is used to retrieve existing olap connections. And
     * username / password only need to be passed on the first request in a
     * session.</p>
     *
     * @return Id of the session
     */
    String getSessionId();
}

// End XmlaRequest.java
