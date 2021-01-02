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
// Copyright (C) 2012-2012 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap;

import mondrian3.olap.Util;
import mondrian3.util.*;

/**
 * Globally unique identifier for the metadata content of a schema.
 *
 * <p>Two schemas have the same content if they have same schema XML.
 * But schemas are also deemed to have the same content if they are read from
 * the same URL (subject to rules about how often the contents of a URL change)
 * or if their content has the same MD5 hash.</p>
 *
 * @see SchemaKey
 *
 * @author jhyde
 */
class SchemaContentKey extends StringKey {
    private SchemaContentKey(String s) {
        super(s);
    }

    static SchemaContentKey create(
        final Util.PropertyList connectInfo,
        final String catalogUrl,
        final String catalogContents)
    {
        final String catalogContentProp =
            RolapConnectionProperties.CatalogContent.name();
        final String dynamicSchemaProp =
            RolapConnectionProperties.DynamicSchemaProcessor.name();

        StringBuilder buf = new StringBuilder();
        if (!Util.isEmpty(connectInfo.get(catalogContentProp))
            || !Util.isEmpty(connectInfo.get(dynamicSchemaProp)))
        {
            ConnectionKey.attributeValue(buf, "catalogStr", catalogContents);
        } else {
            ConnectionKey.attributeValue(buf, "catalog", catalogUrl);
        }
        return new SchemaContentKey(
            new ByteString(Util.digestMd5(buf.toString())).toString());
    }
}

// End SchemaContentKey.java
