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
// Copyright (C) 2009-2009 Pentaho
// All Rights Reserved.
*/
package mondrian3.spi;

import javax.sql.DataSource;

/**
 * Plugin class that resolves data source name to {@link javax.sql.DataSource}
 * object.
 *
 * <p>The property
 * {@link mondrian3.olap.MondrianProperties#DataSourceResolverClass} determines
 * which class to use. The default implementation is
 * {@link mondrian3.spi.impl.JndiDataSourceResolver}.
 *
 * @author jhyde
 */
public interface DataSourceResolver {

    /**
     * Converts a data source name to a JDBC data source object.
     *
     * @param dataSourceName Data source name
     * @return JDBC data source, or null if not found
     * @throws Exception on error
     */
    DataSource lookup(String dataSourceName) throws Exception;

}

// End DataSourceResolver.java
