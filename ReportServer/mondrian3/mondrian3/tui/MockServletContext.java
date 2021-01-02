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
// Copyright (C) 2005-2007 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.tui;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import javax.servlet.*;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.descriptor.JspConfigDescriptor;

/**
 * Partial implementation of the {@link ServletContext} where just
 * enough is present to allow for communication between Mondrian's
 * XMLA code and other code in the same JVM.
 *
 * <p>Currently it is used in both the CmdRunner and in XMLA JUnit tests.
 * If you need to add to this implementation, please do so.
 *
 * @author Richard M. Emberson
 */
public class MockServletContext implements ServletContext {

    public static final String PARAM_DATASOURCES_CONFIG = "DataSourcesConfig";
    public static final String PARAM_CHAR_ENCODING = "CharacterEncoding";
    public static final String PARAM_CALLBACKS = "Callbacks";

    private Map<String, URL> resources;
    private Map<String, Object> attributes;
    private int majorVersion;
    private int minorVersion;
    private Properties parameters;

    public MockServletContext() {
        this.majorVersion = 1;
        this.minorVersion = 1;
        this.resources = Collections.emptyMap();
        this.attributes = Collections.emptyMap();
        this.parameters = new Properties();
    }


    /**
     * Returns a ServletContext object that corresponds to a specified URL on
     * the server.
     *
     */
    public ServletContext getContext(String s) {
        // TODO
        return null;
    }

    /**
     * Returns the major version of the Java Servlet API that this servlet
     * container supports.
     *
     */
    public int getMajorVersion() {
        return this.majorVersion;
    }

    /**
     * Returns the minor version of the Servlet API that this servlet container
     * supports.
     *
     */
    public int getMinorVersion() {
        return this.minorVersion;
    }

    /**
     * Returns the MIME type of the specified file, or null if the MIME type is
     * not known.
     *
     */
    public String getMimeType(String s) {
        // TODO
        return null;
    }

    /**
     *
     *
     */
    public Set getResourcePaths(String s) {
        // TODO
        return null;
    }

    /**
     * Returns a URL to the resource that is mapped to a specified path.
     */
    public URL getResource(String name) throws MalformedURLException {
        if (!resources.containsKey(name)) {
            addResource(name, new URL("file://" + name));
        }
        return resources.get(name);
    }

    /**
     *  Returns the resource located at the named path as an InputStream object.
     *
     */
    public InputStream getResourceAsStream(String s) {
        // TODO
        return null;
    }

    /**
     *  Returns a RequestDispatcher object that acts as a wrapper for the
     *  resource located at the given path.
     *
     */
    public RequestDispatcher getRequestDispatcher(String s) {
        // TODO
        return null;
    }

    /**
     * Returns a RequestDispatcher object that acts as a wrapper for the named
     * servlet.
     *
     */
    public RequestDispatcher getNamedDispatcher(String s) {
        // TODO
        return null;
    }

    public Servlet getServlet(String s) throws ServletException {
        // method is deprecated as of Servlet API 2.1
        return null;
    }

    public Enumeration getServlets() {
        // method is deprecated as of Servlet API 2.1
        return null;
    }

    public Enumeration getServletNames() {
        // method is deprecated as of Servlet API 2.1
        return null;
    }

    /**
     * Writes the specified message to a servlet log file, usually an event log.
     *
     */
    public void log(String s) {
        // TODO
    }

    /**
     * Deprecated. As of Java Servlet API 2.1, use log(String message, Throwable
     * throwable) instead.
     *
     * This method was originally defined to write an exception's stack trace
     * and an explanatory error message to the servlet log file.
     *
     * @deprecated Method log is deprecated
     */
    public void log(Exception exception, String s) {
        log(s, exception);
    }

    /**
     *  Writes an explanatory message and a stack trace for a given Throwable
     *  exception to the servlet log file.
     *
     */
    public void log(String s, Throwable throwable) {
        // TODO
    }

    /**
     * Returns a String containing the real path for a given virtual path.
     *
     */
    public String getRealPath(String path) {
        return path;
    }

    /**
     * Returns the name and version of the servlet container on which the
     * servlet is running.
     *
     */
    public String getServerInfo() {
        // TODO
        return null;
    }

    /**
     * Returns a String containing the value of the named context-wide
     * initialization parameter, or null if the parameter does not exist.
     *
     */
    public String getInitParameter(String name) {
        return parameters.getProperty(name);
    }

    /**
     * Returns the names of the context's initialization parameters as an
     * Enumeration of String objects, or an empty Enumeration if the context has
     * no initialization parameters.
     *
     */
    public Enumeration getInitParameterNames() {
        return parameters.propertyNames();
    }

    /**
     *
     *
     */
    public Object getAttribute(String s) {
        return this.attributes.get(s);
    }

    /**
     * Returns an Enumeration containing the attribute names available within
     * this servlet context.
     *
     */
    public Enumeration getAttributeNames() {
        // TODO
        return Collections.enumeration(this.attributes.keySet());
    }

    /**
     *  Binds an object to a given attribute name in this servlet context.
     *
     */
    public void setAttribute(String s, Object obj) {
        if (this.attributes == Collections.EMPTY_MAP) {
            this.attributes = new HashMap<String, Object>();
        }
        this.attributes.put(s, obj);
    }

    /**
     *  Removes the attribute with the given name from the servlet context.
     *
     */
    public void removeAttribute(String s) {
        this.attributes.remove(s);
    }

    /**
     *
     *
     */
    public String getServletContextName() {
        // TODO
        return null;
    }




    /////////////////////////////////////////////////////////////////////////
    //
    // implementation access
    //
    /////////////////////////////////////////////////////////////////////////
    public void setMajorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
    }
    public void setMinorVersion(int minorVersion) {
        this.minorVersion = minorVersion;
    }
    public void addResource(String name, URL url) {
        if (this.resources == Collections.EMPTY_MAP) {
            this.resources = new HashMap<String, URL>();
        }
        this.resources.put(name, url);
    }
    public void addInitParameter(String name, String value) {
        if (value != null) {
            this.parameters.setProperty(name, value);
        }
    }


	@Override
	public Dynamic addFilter(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Dynamic addFilter(String arg0, Filter arg1) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Dynamic addFilter(String arg0, Class<? extends Filter> arg1) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void addListener(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public <T extends EventListener> void addListener(T arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addListener(Class<? extends EventListener> arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, Servlet arg1) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, Class<? extends Servlet> arg1) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <T extends Filter> T createFilter(Class<T> arg0) throws ServletException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <T extends EventListener> T createListener(Class<T> arg0) throws ServletException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <T extends Servlet> T createServlet(Class<T> arg0) throws ServletException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void declareRoles(String... arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ClassLoader getClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getEffectiveMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getEffectiveMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public FilterRegistration getFilterRegistration(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JspConfigDescriptor getJspConfigDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ServletRegistration getServletRegistration(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map<String, ? extends ServletRegistration> getServletRegistrations() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SessionCookieConfig getSessionCookieConfig() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean setInitParameter(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void setSessionTrackingModes(Set<SessionTrackingMode> arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getVirtualServerName() {
		// TODO Auto-generated method stub
		return null;
	}
}

// End MockServletContext.java
