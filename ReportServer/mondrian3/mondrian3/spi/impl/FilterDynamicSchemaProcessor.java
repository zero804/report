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
// Copyright (C) 2007-2009 Pentaho
// All Rights Reserved.
*/
package mondrian3.spi.impl;

import mondrian3.olap.Util;
import mondrian3.spi.DynamicSchemaProcessor;

import java.io.*;

/**
 * Implementation of {@link DynamicSchemaProcessor} which allows a derived class
 * to easily process a schema file.
 *
 * <p>Mondrian's default mechanism for loading schema files, if no
 * DynamicSchemaProcessor is specified, is to use Apache VFS (virtual file
 * system) to resolve the URL to a stream, and to read the contents of the
 * stream into a string.
 *
 * <p>FilterDynamicSchemaProcessor implements exactly the
 * same mechanism, but makes it easy for a derived class to override the
 * mechanism. For example:<ul>
 *
 * <li>To redirect to a different URL, override the
 * {@link #processSchema(String, mondrian3.olap.Util.PropertyList)} method.
 *
 * <li>To process the contents of the URL, override the
 * {@link #filter(String, mondrian3.olap.Util.PropertyList, java.io.InputStream)}
 * method.
 *
 * </ul>
 *
 * @author jhyde
 * @since Mar 30, 2007
 */
public class FilterDynamicSchemaProcessor implements DynamicSchemaProcessor {

    /**
     * {@inheritDoc}
     *
     * <p>FilterDynamicSchemaProcessor's implementation of this method reads
     * from the URL supplied (that is, it does not perform URL translation)
     * and passes it through the {@link #filter} method.
     */
    public String processSchema(
        String schemaUrl,
        Util.PropertyList connectInfo) throws Exception
    {
        InputStream in = Util.readVirtualFile(schemaUrl);
        return filter(schemaUrl, connectInfo, in);
    }

    /**
     * Reads the contents of a schema as a stream and returns the result as
     * a string.
     *
     * <p>The default implementation returns the contents of the schema
     * unchanged.
     *
     * @param schemaUrl the URL of the catalog
     * @param connectInfo Connection properties
     * @param stream Schema contents represented as a stream
     * @return the modified schema
     * @throws Exception if an error occurs
     */
    protected String filter(
        String schemaUrl,
        Util.PropertyList connectInfo,
        InputStream stream)
        throws Exception
    {
        BufferedReader in =
            new BufferedReader(
                new InputStreamReader(stream));
        try {
            StringBuilder builder = new StringBuilder();
            char[] buf = new char[2048];
            int readCount;
            while ((readCount = in.read(buf, 0, buf.length)) >= 0) {
                builder.append(buf, 0, readCount);
            }
            return builder.toString();
        } finally {
            in.close();
        }
    }
}

// End FilterDynamicSchemaProcessor.java
