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
 *   Copyright 2012 OSBI Ltd
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.legacysaiku.service.olap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.olap4j.OlapConnection;
import org.olap4j.metadata.Cube;
import org.legacysaiku.olap.discover.OlapMetaExplorer;
import org.legacysaiku.olap.dto.SaikuConnection;
import org.legacysaiku.olap.dto.SaikuCube;
import org.legacysaiku.olap.dto.SaikuDimension;
import org.legacysaiku.olap.dto.SaikuHierarchy;
import org.legacysaiku.olap.dto.SaikuLevel;
import org.legacysaiku.olap.dto.SaikuMember;
import org.legacysaiku.olap.util.exception.SaikuOlapException;
import org.legacysaiku.service.datasource.DatasourceService;
import org.legacysaiku.service.util.exception.SaikuServiceException;

public class OlapDiscoverService implements Serializable {
	
	/**
	 *  SerialVersionUID
	 */
	private static final long serialVersionUID = 884682532600907574L;
	
	private DatasourceService datasourceService;
	private OlapMetaExplorer metaExplorer;
	
	public void setDatasourceService(DatasourceService ds) {
		datasourceService = ds;
		metaExplorer = new OlapMetaExplorer(ds.getConnectionManager());
	}
	
	public List<SaikuCube> getAllCubes() {
		return metaExplorer.getAllCubes();
	}

	public List<SaikuConnection> getAllConnections() throws SaikuServiceException {
		try {
			return metaExplorer.getAllConnections();
		} catch (SaikuOlapException e) {
			throw new SaikuServiceException("Cannot retrieve all connections", e);
		}
	}
	

	public List<SaikuConnection> getConnection(String connectionName) {
		List<SaikuConnection> connections = new ArrayList<SaikuConnection>();
		try {
			 SaikuConnection c = metaExplorer.getConnection(connectionName);
			 connections.add(c);
			 return connections;
		} catch (SaikuOlapException e) {
			throw new SaikuServiceException("Cannot retrieve all connections", e);
		}
	}

	
	public void refreshAllConnections() throws SaikuServiceException {
		try {
			datasourceService.getConnectionManager().refreshAllConnections();
		} catch (Exception e) {
			throw new SaikuServiceException("Cannot refresh all connections", e);
		}
	}
	
	public void refreshConnection(String name) throws SaikuServiceException {
		try {
			datasourceService.getConnectionManager().refreshConnection(name);
		} catch (Exception e) {
			throw new SaikuServiceException("Cannot refresh all connections", e);
		}
	}
	
	public Cube getNativeCube(SaikuCube cube) throws SaikuServiceException {
		try {
			return metaExplorer.getNativeCube(cube);
		} catch (SaikuOlapException e) {
			throw new SaikuServiceException("Cannot get native cube for cube ( " + cube + " )", e);
		}
	}

	public OlapConnection getNativeConnection(String name) throws SaikuServiceException {
		try {
			return metaExplorer.getNativeConnection(name);
		} catch (SaikuOlapException e) {
			throw new SaikuServiceException("Cannot get native connection for cube ( " + name + " )", e);
		}
	}

	public List<SaikuDimension> getAllDimensions(SaikuCube cube) throws SaikuServiceException {
		try {
			return metaExplorer.getAllDimensions(cube);
		} catch (SaikuOlapException e) {
			throw new SaikuServiceException("Cannot get all dimensions for cube ( " + cube + " )", e);
		}
	}
	
	public SaikuDimension getDimension(SaikuCube cube, String dimensionName) throws SaikuServiceException {
		try {
			return metaExplorer.getDimension(cube, dimensionName);
		} catch (SaikuOlapException e) {
			throw new SaikuServiceException("Cannot get dimension (" + dimensionName + " ) for cube ( " + cube + " )", e);
		}
	}
	
	public List<SaikuHierarchy> getAllHierarchies(SaikuCube cube) throws SaikuServiceException {
		try {
			return metaExplorer.getAllHierarchies(cube);
		} catch (SaikuOlapException e) {
			throw new SaikuServiceException("Cannot get all hierarchies for cube ( " + cube + " )", e);	
		}
	}
	
	public List<SaikuHierarchy> getAllDimensionHierarchies(SaikuCube cube, String dimensionName) {
		try {
			SaikuDimension dim = metaExplorer.getDimension(cube, dimensionName);
			if (dim == null) {
				throw new SaikuServiceException("Cannot find dimension ( "+ dimensionName + ") for cube ( " + cube + " )");
			}
			return dim.getHierarchies();

		} catch (SaikuOlapException e) {
			throw new SaikuServiceException("Cannot get all hierarchies for cube ( " + cube + " ) dimension ( " + dimensionName + " )", e);
		}
	}

	public List<SaikuLevel> getAllHierarchyLevels(SaikuCube cube, String dimensionName, String hierarchyName) {
		try {
			return  metaExplorer.getAllLevels(cube, dimensionName, hierarchyName);
		} catch (SaikuOlapException e) {
			throw new SaikuServiceException("Cannot get all levels for cube ( " + cube 
					+ " ) dimension ( " + dimensionName + " ) hierarchy ( " + hierarchyName + " )", e);

		}
	}

	public List<SaikuMember> getLevelMembers(SaikuCube cube, String dimensionName, String hierarchyName, String levelName) {
		try {
			return  metaExplorer.getAllMembers(cube, dimensionName, hierarchyName, levelName);
		} catch (SaikuOlapException e) {
			throw new SaikuServiceException("Cannot get all members for cube ( " + cube 
					+ " ) dimension ( " + dimensionName + " ) hierarchy ( " + hierarchyName + " )", e);
		}
	}
	
	public List<SaikuMember> getMeasures(SaikuCube cube) {
		try {
			return metaExplorer.getAllMeasures(cube);
		} catch (SaikuOlapException e) {
			throw new SaikuServiceException("Cannot get all measures for cube ( " + cube + " )", e);
		}
	}

	public List<SaikuMember> getHierarchyRootMembers(SaikuCube cube, String hierarchyName) {
		try {
			return metaExplorer.getHierarchyRootMembers(cube, hierarchyName);
		} catch (SaikuOlapException e) {
			throw new SaikuServiceException(e);
		}	
	}
	
	public List<SaikuMember> getMemberChildren(SaikuCube cube, String uniqueMemberName) {
		try {
			return metaExplorer.getMemberChildren(cube, uniqueMemberName);
		} catch (SaikuOlapException e) {
			throw new SaikuServiceException(e);
		}	
	}

	public SaikuMember getMember(SaikuCube cube, String uniqueMemberName) {
		try {
			return metaExplorer.getMember(cube, uniqueMemberName);
		} catch (SaikuOlapException e) {
			throw new SaikuServiceException(e);
		}
	}
}
