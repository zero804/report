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
// Copyright (C) 2010-2012 Pentaho
// All Rights Reserved.
*/
package mondrian3.server;

import mondrian3.olap.Util;

import java.io.IOException;

/**
* Implementation of {@link mondrian3.server.RepositoryContentFinder} that reads
 * from a URL.
 *
 * <p>The URL might be a string representation of a {@link java.net.URL}, such
 * as 'file:/foo/bar/datasources.xml' or 'http://server/datasources.xml', but
 * it might also be the mondrian-specific URL format 'inline:...'. The content
 * of an inline URL is the rest of the string after the 'inline:' prefix.
 *
 * @author Julian Hyde
*/
public class UrlRepositoryContentFinder
    implements RepositoryContentFinder
{
    protected final String url;

    /**
     * Creates a UrlRepositoryContentFinder.
     *
     * @param url URL of repository
     */
    public UrlRepositoryContentFinder(String url) {
        assert url != null;
        this.url = url;
    }

    public String getContent() {
        try {
            return Util.readURL(
                url, Util.toMap(System.getProperties()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown() {
        // nothing to do
    }
}

// End UrlRepositoryContentFinder.java
