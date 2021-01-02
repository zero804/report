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

import javax.sql.DataSource;

/**
 * Globally unique identifier for the definition of a JDBC database connection.
 *
 * <p>Two connections should have the same connection key if and only if their
 * databases have the same content.</p>
 *
 * @see RolapConnectionProperties#JdbcConnectionUuid
 *
 * @author jhyde
 */
class ConnectionKey extends StringKey {
    private ConnectionKey(String s) {
        super(s);
    }

    static ConnectionKey create(
        final String connectionUuidStr,
        final DataSource dataSource,
        final String catalogUrl,
        final String connectionKey,
        final String jdbcUser,
        final String dataSourceStr)
    {
        String s;
        if (connectionUuidStr != null
            && connectionUuidStr.length() != 0)
        {
            s = connectionUuidStr;
        } else {
            final StringBuilder buf = new StringBuilder(100);
            if (dataSource != null) {
                attributeValue(buf, "jvm", Util.JVM_INSTANCE_UUID);
                attributeValue(
                    buf, "dataSource", System.identityHashCode(dataSource));
            } else {
                attributeValue(buf, "connectionKey", connectionKey);
                attributeValue(buf, "catalogUrl", catalogUrl);
                attributeValue(buf, "jdbcUser", jdbcUser);
                attributeValue(buf, "dataSourceStr", dataSourceStr);
            }
            s = new ByteString(Util.digestMd5(buf.toString())).toString();
        }
        return new ConnectionKey(s);
    }

    static void attributeValue(
        StringBuilder buf, String attribute, Object value)
    {
        if (value == null) {
            return;
        }
        if (buf.length() > 0) {
            buf.append(';');
        }
        buf.append(attribute)
            .append('=');
        Util.quoteForMdx(buf, value.toString());
    }
}

// End ConnectionKey.java
