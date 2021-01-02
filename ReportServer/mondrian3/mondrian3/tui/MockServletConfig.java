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
// Copyright (C) 2005-2009 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.tui;

import java.util.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * This is a partial implementation of the ServletConfig where just
 * enough is present to allow for communication between Mondrian's
 * XMLA code and other code in the same JVM.
 * Currently it is used in both the CmdRunner and in XMLA JUnit tests.
 * <p>
 * If you need to add to this implementation, please do so.
 *
 * @author <a>Richard M. Emberson</a>
 */
public class MockServletConfig implements ServletConfig {
    private String servletName;
    private Map<String, String> initParams;
    private ServletContext servletContext;

    public MockServletConfig() {
        this(null);
    }
    public MockServletConfig(ServletContext servletContext) {
        this.initParams = new HashMap<String, String>();
        this.servletContext = servletContext;
    }

    /**
     * Returns the name of this servlet instance.
     *
     */
    public String getServletName() {
        return servletName;
    }

    /**
     * Returns a reference to the ServletContext in which the servlet is
     * executing.
     *
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * Returns a String containing the value of the named initialization
     * parameter, or null if the parameter does not exist.
     *
     */
    public String getInitParameter(String key) {
        return initParams.get(key);
    }

    /**
     *  Returns the names of the servlet's initialization parameters as an
     *  Enumeration of String objects, or an empty Enumeration if the servlet
     *  has no initialization parameters.
     *
     */
    public Enumeration getInitParameterNames() {
        return Collections.enumeration(initParams.keySet());
    }

    /////////////////////////////////////////////////////////////////////////
    //
    // implementation access
    //
    /////////////////////////////////////////////////////////////////////////
    public void setServletName(String servletName) {
        this.servletName = servletName;
    }
    public void addInitParameter(String key, String value) {
        if (value != null) {
            this.initParams.put(key, value);
        }
    }
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}

// End MockServletConfig.java
