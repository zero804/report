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

package org.legacysaiku.olap.query;

import java.util.Map;
import java.util.Properties;

import mondrian3.rolap.RolapConnection;

import org.olap4j.Axis;
import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.olap4j.OlapException;
import org.olap4j.OlapStatement;
import org.olap4j.Scenario;
import org.olap4j.mdx.SelectNode;
import org.olap4j.mdx.parser.MdxParser;
import org.olap4j.mdx.parser.MdxParserFactory;
import org.olap4j.mdx.parser.MdxValidator;
import org.olap4j.metadata.Catalog;
import org.olap4j.metadata.Cube;
import org.olap4j.metadata.Database;
import org.olap4j.metadata.Schema;
import org.olap4j.query.QueryAxis;
import org.olap4j.query.QueryDimension;
import org.olap4j.type.CubeType;
import org.legacysaiku.olap.dto.SaikuCube;
import org.legacysaiku.olap.dto.SaikuTag;
import org.legacysaiku.olap.util.exception.SaikuOlapException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdxQuery implements IQuery {

	private static final Logger log = LoggerFactory.getLogger(MdxQuery.class);

	private Properties properties = new Properties();
	private String mdx;
	private SaikuCube cube;
	private OlapConnection connection;
	private String name;
	private Scenario scenario;
	private CellSet cellset;
	private OlapStatement statement;

	public MdxQuery(OlapConnection con, SaikuCube cube, String name, String mdx) {
		this.cube = cube;
		this.connection = con;
		this.name = name;
		this.mdx = mdx;
	}

	public String getName() {
		return name;
	}

	public SaikuCube getSaikuCube() {
		try {
			Cube c = getCube();
			SaikuCube sc= new SaikuCube(
					cube.getConnectionName(),
					c.getUniqueName(), 
					c.getName(), 
					c.getCaption(),
					cube.getCatalogName(), 
					c.getSchema().getName());
			if (sc != null) {
				cube = sc;
			}
		} catch (Exception e) {
			// we tried, but it just doesn't work, so let's return the last working cube
		}
		return cube;
	}

	public String getMdx() {
		return mdx;
	}

	public void setMdx(String mdx) {
		this.mdx = mdx;
	}

	public void resetQuery() {
		this.mdx = "";
	}

	public void setProperties(Properties props) {
		this.properties.putAll(props);
	}

	public Properties getProperties() {
		Properties props = this.properties;
		props.put(QueryProperties.KEY_IS_DRILLTHROUGH, isDrillThroughEnabled().toString());
		props.put("org.legacysaiku.connection.scenario", Boolean.toString(false));
		try {
			props.put("org.legacysaiku.query.explain", Boolean.toString(connection.isWrapperFor(RolapConnection.class)));
		} catch (Exception e) {
			props.put("org.legacysaiku.query.explain", Boolean.toString(false));
		}
		return props;
	}

	public String toXml() {
		QuerySerializer qs = new QuerySerializer(this);
		return qs.createXML();
	}

	public Boolean isDrillThroughEnabled() {
		try {
			Cube cube = getCube();
			return (cube != null && cube.isDrillThroughEnabled());
		} catch (Exception e) {
			e.printStackTrace();
		};
		return false;
	}

	public CellSet execute() throws Exception {
		try {
			if (statement != null) {
				statement.close();
				statement = null;
			}

			OlapConnection con = connection;
			con.setCatalog(getSaikuCube().getCatalogName());
			OlapStatement stmt = con.createStatement();
			this.statement = stmt;
			CellSet cs = stmt.executeOlapQuery(mdx);
			
			return cs;
		} finally {
			if (statement != null) {
				statement.close();
				statement = null;
			}
		}
	}

	public QueryType getType() {
		return QueryType.MDX;
	}

	public void swapAxes() {
		throw new UnsupportedOperationException();
	}

	public Map<Axis, QueryAxis> getAxes() {
		throw new UnsupportedOperationException();
	}

	public QueryAxis getAxis(Axis axis) {
		throw new UnsupportedOperationException();
	}

	public QueryAxis getAxis(String name) throws SaikuOlapException {
		throw new UnsupportedOperationException();
	}

	public Cube getCube() {
		final MdxParserFactory parserFactory =
				connection.getParserFactory();
		MdxParser mdxParser =
				parserFactory.createMdxParser(connection);
		MdxValidator mdxValidator =
				parserFactory.createMdxValidator(connection);

		String mdx = getMdx();
		try {

			if (mdx != null && mdx.length() > 0 && mdx.toUpperCase().contains("FROM")) {
				SelectNode select =
						mdxParser.parseSelect(getMdx());
				select = mdxValidator.validateSelect(select);
				CubeType cubeType = (CubeType) select.getFrom().getType();
				return cubeType.getCube();
			}
		} catch (Exception e) {
			log.debug("Parsing MDX to get the Cube failed. Using fallback scenario.", e);
		} finally {
			mdxValidator = null;
		}
		try {
			// ok seems like we failed to get the cube, lets try it differently
			if (connection != null && mdx != null && mdx.length() > 0) {
				for (Database db : connection.getOlapDatabases()) {
					Catalog cat = db.getCatalogs().get(cube.getCatalogName());
					if (cat != null) {
						for (Schema schema : cat.getSchemas()) {
							for (Cube cub : schema.getCubes()) {
								if (cub.getName().equals(cube.getName()) || cub.getUniqueName().equals(cube.getName())) {
									return cub;
								}
							}
						}
					}
				}
			}
		} catch (OlapException e) {
			e.printStackTrace();
		}


		return null;
	}

	public QueryAxis getUnusedAxis() {
		throw new UnsupportedOperationException();
	}

	public void moveDimension(QueryDimension dimension, Axis axis) {
		throw new UnsupportedOperationException();
	}

	public void moveDimension(QueryDimension dimension, Axis axis, int position) {
		throw new UnsupportedOperationException();
	}

	public QueryDimension getDimension(String name) {
		throw new UnsupportedOperationException();
	}

	public void resetAxisSelections(QueryAxis axis) {
		throw new UnsupportedOperationException();
	}

	public void clearAllQuerySelections() {
		throw new UnsupportedOperationException();
	}

	public void clearAxis(String axisName) throws SaikuOlapException {
		throw new UnsupportedOperationException();
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setTag(SaikuTag tag) {
		throw new UnsupportedOperationException();		
	}

	public SaikuTag getTag() {
		return null;
	}

	public void removeTag() {
	}

	public void storeCellset(CellSet cs) {
		this.cellset = cs;

	}

	public CellSet getCellset() {
		return cellset;
	}

	public void setStatement(OlapStatement os) {
		this.statement = os;

	}

	public OlapStatement getStatement() {
		return this.statement;
	}

	public void cancel() throws Exception {
		if (this.statement != null && !this.statement.isClosed()) {
			statement.close();
		}
		this.statement = null;
	}
}
