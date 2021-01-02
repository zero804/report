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
// Copyright (C) 2004-2005 TONBELLER AG
// Copyright (C) 2005-2005 Julian Hyde
// Copyright (C) 2005-2007 Pentaho
// All Rights Reserved.
*/
package mondrian3.spi;

import mondrian3.olap.Util;

/**
 * A dynamic schema processor is used to dynamically change
 * a Mondrian schema at runtime.
 *
 * <p>Mondrian loads a DynamicSchemaProcessor when it sees the
 * {@link mondrian3.rolap.RolapConnectionProperties#DynamicSchemaProcessor}
 * keyword in a connect string. The value of that property must be a class
 * which implements this interface. Rather than loading the schema directly,
 * Mondrian instantiates the class and calls the
 * {@link #processSchema(String, mondrian3.olap.Util.PropertyList)} method
 * with the catalog URL and connection properties specified in the connect
 * string.
 *
 * <p>By default, mondrian uses Apache VFS (virtual file system) to resolve
 * catalog URLs. We recommend that implementations of DynamicSchemaProcessor
 * do the same.
 *
 * <p>If you are writing an implementation of this class, we recommend that
 * you use {@link mondrian3.spi.impl.FilterDynamicSchemaProcessor} as a
 * base class.
 *
 * @author hhaas
 */
public interface DynamicSchemaProcessor {

    /**
     * Modifies a Mondrian schema.
     *
     * <p>An implementation should generally interpret the URL string as
     * an Apache VFS (virtual file system) URL.
     *
     * @param schemaUrl the URL of the catalog
     * @param connectInfo Connection properties
     * @return the modified schema
     * @throws Exception if an error occurs
     */
    public String processSchema(
        String schemaUrl,
        Util.PropertyList connectInfo) throws Exception;
}

// End DynamicSchemaProcessor.java

