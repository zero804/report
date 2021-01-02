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
// Copyright (C) 2005-2009 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.web.taglib;

import java.io.InputStream;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;

/**
 * Holds compiled stylesheets.
 *
 * @author Andreas Voss, 22 March, 2002
 */
public class ApplResources implements Listener.ApplicationContext {

    private static final String ATTRNAME = "mondrian3.web.taglib.ApplResources";
    private ServletContext context;

    /**
     * Creates a <code>ApplResources</code>. Only {@link Listener} calls this;
     * you should probably call {@link #getInstance}.
     */
    public ApplResources() {
    }

    /**
     * Retrieves the one and only instance of <code>ApplResources</code> in
     * this servlet's context.
     */
    public static ApplResources getInstance(ServletContext context) {
        return (ApplResources)context.getAttribute(ATTRNAME);
    }

    private HashMap templatesCache = new HashMap();
    public Transformer getTransformer(String xsltURI, boolean useCache) {
        try {
            Templates templates = null;
            if (useCache) {
                templates = (Templates)templatesCache.get(xsltURI);
            }
            if (templates == null) {
                TransformerFactory tf = TransformerFactory.newInstance();
                InputStream input = context.getResourceAsStream(xsltURI);
                templates = tf.newTemplates(new StreamSource(input));
                if (useCache) {
                    templatesCache.put(xsltURI, templates);
                }
            }
            return templates.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            throw new RuntimeException(e.toString());
        }
    }

    // implement ApplicationContext
    public void init(ServletContextEvent event) {
        this.context = event.getServletContext();
        context.setAttribute(ATTRNAME, this);
    }

    public void destroy(ServletContextEvent ev) {
    }


}

// End ApplResources.java
