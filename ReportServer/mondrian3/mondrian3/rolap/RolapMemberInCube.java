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
// Copyright (C) 2010-2010 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap;

/**
 * Extension to {@link RolapMember} that knows the current cube.
 *
 * <p>This is typical of members that occur in queries (where there is always a
 * current cube) as opposed to members that belong to caches. Members of shared
 * dimensions might occur in several different cubes, or even several times in
 * the same virtual cube.
 *
 * @author jhyde
 * @since 20 March, 2010
 */
public interface RolapMemberInCube extends RolapMember {
    /**
     * Returns the cube this cube member belongs to.
     *
     * <p>This method is not in the {@link RolapMember} interface, because
     * regular members may be shared, and therefore do not belong to a specific
     * cube.
     *
     * @return Cube this cube member belongs to, never null
     */
    RolapCube getCube();
}

// End RolapMemberInCube.java
