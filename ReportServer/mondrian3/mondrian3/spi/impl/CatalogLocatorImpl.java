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
// Copyright (C) 2005-2006 Pentaho
// All Rights Reserved.
*/
package mondrian3.spi.impl;

import mondrian3.spi.CatalogLocator;

/**
 * CatalogLocator which returns the catalog URI unchanged.
 *
 * @author jhyde
 * @since Dec 22, 2005
 */
public class CatalogLocatorImpl implements CatalogLocator {
    public static final CatalogLocator INSTANCE = new CatalogLocatorImpl();

    public String locate(String catalogPath) {
        return catalogPath;
    }
}

// End CatalogLocatorImpl.java
