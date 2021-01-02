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
// Copyright (C) 2002-2005 Julian Hyde
// Copyright (C) 2005-2007 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.web.taglib;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <code>Listener</code> creates and destroys a {@link ApplResources} at the
 * appropriate times in the servlet's life-cycle.
 *
 * <p>NOTE: This class must not depend upon any non-standard packages (such as
 * <code>javax.transform</code>) because it is loaded when Tomcat starts, not
 * when the servlet is loaded. (This might be a bug in Tomcat 4.0.3, because
 * it worked in 4.0.1. But anyway.)
 */
public class Listener implements ServletContextListener {

    ApplicationContext applicationContext;

    public Listener() {
    }

    public void contextInitialized(ServletContextEvent event) {
        Class clazz;
        try {
            clazz = Class.forName("mondrian3.web.taglib.ApplResources");
        } catch (ClassNotFoundException e) {
            throw new Error(
                "Received [" + e.toString() + "] while initializing servlet");
        }
        Object o = null;
        try {
            o = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new Error(
                "Received [" + e.toString() + "] while initializing servlet");
        } catch (IllegalAccessException e) {
            throw new Error(
                "Received [" + e.toString() + "] while initializing servlet");
        }
        applicationContext = (ApplicationContext) o;
        applicationContext.init(event);
    }

    public void contextDestroyed(ServletContextEvent event) {
        if (applicationContext != null) {
            applicationContext.destroy(event);
        }
    }

    interface ApplicationContext {
        void init(ServletContextEvent event);
        void destroy(ServletContextEvent event);
    }
}

// End Listener.java
