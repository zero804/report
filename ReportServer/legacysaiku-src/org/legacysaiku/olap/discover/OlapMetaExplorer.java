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
package org.legacysaiku.olap.discover;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.olap4j.OlapConnection;
import org.olap4j.OlapDatabaseMetaData;
import org.olap4j.OlapException;
import org.olap4j.mdx.IdentifierNode;
import org.olap4j.mdx.IdentifierSegment;
import org.olap4j.metadata.Catalog;
import org.olap4j.metadata.Cube;
import org.olap4j.metadata.Database;
import org.olap4j.metadata.Dimension;
import org.olap4j.metadata.Hierarchy;
import org.olap4j.metadata.Level;
import org.olap4j.metadata.Measure;
import org.olap4j.metadata.Member;
import org.olap4j.metadata.Schema;
import org.legacysaiku.datasources.connection.IConnectionManager;
import org.legacysaiku.olap.dto.SaikuCatalog;
import org.legacysaiku.olap.dto.SaikuConnection;
import org.legacysaiku.olap.dto.SaikuCube;
import org.legacysaiku.olap.dto.SaikuDimension;
import org.legacysaiku.olap.dto.SaikuHierarchy;
import org.legacysaiku.olap.dto.SaikuLevel;
import org.legacysaiku.olap.dto.SaikuMember;
import org.legacysaiku.olap.dto.SaikuSchema;
import org.legacysaiku.olap.util.ObjectUtil;
import org.legacysaiku.olap.util.SaikuCubeCaptionComparator;
import org.legacysaiku.olap.util.SaikuDimensionCaptionComparator;
import org.legacysaiku.olap.util.exception.SaikuOlapException;

public class OlapMetaExplorer {


	private IConnectionManager connections;

	public OlapMetaExplorer(IConnectionManager ic) {
		connections = ic;
	}

	public SaikuConnection getConnection(String connectionName) throws SaikuOlapException {
		OlapConnection olapcon = connections.getOlapConnection(connectionName);
		SaikuConnection connection = null;
		if (olapcon != null) {
			List<SaikuCatalog> catalogs = new ArrayList<SaikuCatalog>();
			try {
					for (Catalog cat : olapcon.getOlapCatalogs()) {
						List<SaikuSchema> schemas = new ArrayList<SaikuSchema>();
						for (Schema schem : cat.getSchemas()) {
							List<SaikuCube> cubes = new ArrayList<SaikuCube>();
							for (Cube cub : schem.getCubes()) {
								cubes.add(new SaikuCube(connectionName, cub.getUniqueName(), cub.getName(), cub.getCaption(), cat.getName(), schem.getName(), cub.isVisible()));
							}
							Collections.sort(cubes, new SaikuCubeCaptionComparator());
							schemas.add(new SaikuSchema(schem.getName(),cubes));
						}
						if (schemas.size() == 0) {
							OlapDatabaseMetaData olapDbMeta = olapcon.getMetaData();
                            ResultSet cubesResult = olapDbMeta.getCubes(cat.getName(), null, null);

							try {
								List<SaikuCube> cubes = new ArrayList<SaikuCube>();
								while(cubesResult.next()) {

									cubes.add(new SaikuCube(connectionName, cubesResult.getString("CUBE_NAME"),cubesResult.getString("CUBE_NAME"),
											cubesResult.getString("CUBE_NAME"), cubesResult.getString("CATALOG_NAME"),cubesResult.getString("SCHEMA_NAME")));

								}
								Collections.sort(cubes, new SaikuCubeCaptionComparator());
								schemas.add(new SaikuSchema("",cubes));
							} catch (SQLException e) {
								throw new OlapException(e.getMessage(),e);
							} finally {
							    try {
                                    cubesResult.close();
                                } catch (SQLException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
							}

						}
						Collections.sort(schemas);
						catalogs.add(new SaikuCatalog(cat.getName(),schemas));
					}
			} catch (OlapException e) {
				throw new SaikuOlapException("Error getting objects of connection (" + connectionName + ")" ,e);
			}
			Collections.sort(catalogs);
			connection = new SaikuConnection(connectionName,catalogs);
			return connection;
		}
		throw new SaikuOlapException("Cannot find connection: (" + connectionName + ")");
	}

	public List<SaikuConnection> getConnections(List<String> connectionNames) throws SaikuOlapException {
		List<SaikuConnection> connectionList = new ArrayList<SaikuConnection>();
		for (String connectionName : connectionNames) {
			connectionList.add(getConnection(connectionName));
		}
		return connectionList;
	}

	public List<SaikuConnection> getAllConnections() throws SaikuOlapException {
		List<SaikuConnection> cubesList = new ArrayList<SaikuConnection>();
		for (String connectionName : connections.getAllOlapConnections().keySet()) {
			cubesList.add(getConnection(connectionName));
		}
		Collections.sort(cubesList);
		return cubesList;
	}


	public List<SaikuCube> getCubes(String connectionName) {
		OlapConnection olapcon = connections.getOlapConnection(connectionName);
		List<SaikuCube> cubes = new ArrayList<SaikuCube>();
		if (olapcon != null) {
			try {
				for (Catalog cat : olapcon.getOlapCatalogs()) {
					for (Schema schem : cat.getSchemas()) {
						for (Cube cub : schem.getCubes()) {
							cubes.add(new SaikuCube(connectionName, cub.getUniqueName(), cub.getName(), cub.getCaption(), cat.getName(), schem.getName(), cub.isVisible()));
						}
					}
				}
			} catch (OlapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Collections.sort(cubes, new SaikuCubeCaptionComparator());
		return cubes;

	}

	public List<SaikuCube> getCubes(List<String> connectionNames) {
		List<SaikuCube> cubesList = new ArrayList<SaikuCube>();
		for (String connectionName : connectionNames) {
			cubesList.addAll(getCubes(connectionName));
		}
		Collections.sort(cubesList, new SaikuCubeCaptionComparator());
		return cubesList;
	}

	public List<SaikuCube> getAllCubes() {
		List<SaikuCube> cubes = new ArrayList<SaikuCube>();
		for (String connectionName : connections.getAllOlapConnections().keySet()) {
			cubes.addAll(getCubes(connectionName));
		}
		Collections.sort(cubes, new SaikuCubeCaptionComparator());
		return cubes;
	}

	public Cube getNativeCube(SaikuCube cube) throws SaikuOlapException {
		try {
			OlapConnection con = connections.getOlapConnection(cube.getConnectionName());
			if (con != null ) {
				for (Database db : con.getOlapDatabases()) {
					Catalog cat = db.getCatalogs().get(cube.getCatalogName());
					if (cat != null) {
						for (Schema schema : cat.getSchemas()) {
							if (schema.getName().equals(cube.getSchemaName())) {
								for (Cube cub : schema.getCubes()) {
									if (cub.getName().equals(cube.getName()) || cub.getUniqueName().equals(cube.getUniqueName())) {
										return cub;
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new SaikuOlapException("Cannot get native cube for ( " + cube+ " )",e);
		}
		throw new SaikuOlapException("Cannot get native cube for ( " + cube+ " )");
	}

	public OlapConnection getNativeConnection(String name) throws SaikuOlapException {
		try {
			OlapConnection con = connections.getOlapConnection(name);
			if (con != null ) {
				return con;
			}
		} catch (Exception e) {
			throw new SaikuOlapException("Cannot get native connection for ( " + name + " )",e);
		}
		return null;
	}

	public List<SaikuDimension> getAllDimensions(SaikuCube cube) throws SaikuOlapException {
		Cube nativeCube = getNativeCube(cube);
		List<SaikuDimension> dimensions = ObjectUtil.convertDimensions(nativeCube.getDimensions());
		for (int i=0; i < dimensions.size();i++) {
			SaikuDimension dim = dimensions.get(i);
			if (dim.getName().equals("Measures") || dim.getUniqueName().equals("[Measures]")) {
				dimensions.remove(i);
				break;
			}
		}
		Collections.sort(dimensions, new SaikuDimensionCaptionComparator());
		return dimensions;
	}

	public SaikuDimension getDimension(SaikuCube cube, String dimensionName) throws SaikuOlapException {
		Cube nativeCube = getNativeCube(cube);
		Dimension dim = nativeCube.getDimensions().get(dimensionName);
		if (dim != null) {
			return ObjectUtil.convert(dim);
		}
		return null;
	}

	public List<SaikuHierarchy> getAllHierarchies(SaikuCube cube) throws SaikuOlapException {
		Cube nativeCube = getNativeCube(cube);
		return ObjectUtil.convertHierarchies(nativeCube.getHierarchies());
	}

	public SaikuHierarchy getHierarchy(SaikuCube cube, String hierarchyName) throws SaikuOlapException {
		Cube nativeCube = getNativeCube(cube);
		Hierarchy h = nativeCube.getHierarchies().get(hierarchyName);
		if (h != null) {
			return ObjectUtil.convert(h);
		}
		return null;
	}

	public List<SaikuMember> getHierarchyRootMembers(SaikuCube cube, String hierarchyName) throws SaikuOlapException {
		Cube nativeCube = getNativeCube(cube);
		List<SaikuMember> members = new ArrayList<SaikuMember>();
		Hierarchy h = nativeCube.getHierarchies().get(hierarchyName);

		if (h == null) {
			for (Hierarchy hlist : nativeCube.getHierarchies()) {
				if (hlist.getUniqueName().equals(hierarchyName) || hlist.getName().equals(hierarchyName)) {
					h = hlist;
				}
			}
		}
		if (h!= null) {
			try {
				members = (ObjectUtil.convertMembers(h.getRootMembers()));
			} catch (OlapException e) {
				throw new SaikuOlapException("Cannot retrieve root members of hierarchy: " + hierarchyName,e);
			}
		}

		return members;
	}


	public List<SaikuLevel> getAllLevels(SaikuCube cube, String dimension, String hierarchy) throws SaikuOlapException {
		Cube nativeCube = getNativeCube(cube);
		Dimension dim = nativeCube.getDimensions().get(dimension);
		if (dim != null) {
			Hierarchy h = dim.getHierarchies().get(hierarchy);
			if (h == null) {
				for (Hierarchy hlist : dim.getHierarchies()) {
					if (hlist.getUniqueName().equals(hierarchy) || hlist.getName().equals(hierarchy)) {
						h = hlist;
					}
				}
			}

			if (h!= null) {
				List<SaikuLevel> levels = (ObjectUtil.convertLevels(h.getLevels()));
				return levels;
			}
		}
		return new ArrayList<SaikuLevel>();

	}

	public List<SaikuMember> getAllMembers(SaikuCube cube, String dimension, String hierarchy, String level) throws SaikuOlapException {
		try {
			Cube nativeCube = getNativeCube(cube);
			Dimension dim = nativeCube.getDimensions().get(dimension);
			if (dim != null) {
				Hierarchy h = dim.getHierarchies().get(hierarchy);
				if (h == null) {
					for (Hierarchy hlist : dim.getHierarchies()) {
						if (hlist.getUniqueName().equals(hierarchy) || hlist.getName().equals(hierarchy)) {
							h = hlist;
						}
					}
				}

				if (h!= null) {
					Level l = h.getLevels().get(level);
					if (l == null) {
						for (Level lvl : h.getLevels()) {
							if (lvl.getUniqueName().equals(level) || lvl.getName().equals(level)) {
								return (ObjectUtil.convertMembers(lvl.getMembers()));
							}
						}
					} else {
						return (ObjectUtil.convertMembers(l.getMembers()));
					}

				}
			}
		} catch (OlapException e) {
			throw new SaikuOlapException("Cannot get all members",e);
		}

		return new ArrayList<SaikuMember>();

	}

	public List<SaikuMember> getMemberChildren(SaikuCube cube, String uniqueMemberName) throws SaikuOlapException {
		List<SaikuMember> members = new ArrayList<SaikuMember>();
		try {
			Cube nativeCube = getNativeCube(cube);
			List<IdentifierSegment> memberList = IdentifierNode.parseIdentifier(uniqueMemberName).getSegmentList();
			Member m = nativeCube.lookupMember(memberList);
			if (m != null) {
				for (Member c :  m.getChildMembers()) {
					SaikuMember sm = ObjectUtil.convert(c);
					members.add(sm);
				}
			}
		} catch (OlapException e) {
			throw new SaikuOlapException("Cannot get child members of member:" + uniqueMemberName,e);
		}

		return members;
	}

	public List<SaikuMember> getAllMeasures(SaikuCube cube) throws SaikuOlapException {
		List<SaikuMember> measures = new ArrayList<SaikuMember>();
		try {
			Cube nativeCube = getNativeCube(cube);
			for (Measure measure : nativeCube.getMeasures()) {
				if(measure.isVisible()) {
					measures.add(ObjectUtil.convertMeasure(measure));
				}
			}
			if (measures.size() == 0) {
				Hierarchy hierarchy = nativeCube.getDimensions().get("Measures").getDefaultHierarchy();
				measures = (ObjectUtil.convertMembers(hierarchy.getRootMembers()));
			}
		} catch (OlapException e) {
			throw new SaikuOlapException("Cannot get measures for cube:"+cube.getName(),e);
		}
		
//		Collections.sort(measures, new SaikuMemberCaptionComparator());
		return measures;
	}

	public SaikuMember getMember(SaikuCube cube, String uniqueMemberName) throws SaikuOlapException {
		try {
			Cube nativeCube = getNativeCube(cube);
			Member m = nativeCube.lookupMember(IdentifierNode.parseIdentifier(uniqueMemberName).getSegmentList());
			if (m != null) {
				return ObjectUtil.convert(m);
			}
			return null;
		} catch (Exception e) {
			throw new SaikuOlapException("Cannot find member: " + uniqueMemberName + " in cube:"+cube.getName(),e);
		}
	}

}
