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

import org.olap4j.Axis;
import org.olap4j.CellSet;
import org.olap4j.OlapStatement;
import org.olap4j.Scenario;
import org.olap4j.metadata.Cube;
import org.olap4j.query.QueryAxis;
import org.olap4j.query.QueryDimension;
import org.legacysaiku.olap.dto.SaikuCube;
import org.legacysaiku.olap.dto.SaikuTag;
import org.legacysaiku.olap.util.exception.SaikuOlapException;

public interface IQuery {

	public enum QueryType { MDX, QM };
	public String getName();
	public SaikuCube getSaikuCube();
	public CellSet execute() throws Exception;
	public String getMdx();
	public void resetQuery();
	public void setProperties(Properties props);
	public Properties getProperties();
	public String toXml();
	public Boolean isDrillThroughEnabled();
	public QueryType getType();

	public void swapAxes();
	public Map<Axis, QueryAxis> getAxes();
	public QueryAxis getAxis(Axis axis);
	public QueryAxis getAxis(String name) throws SaikuOlapException;
	public Cube getCube();			
	public QueryAxis getUnusedAxis();
	public void moveDimension(QueryDimension dimension, Axis axis);
	public void moveDimension(QueryDimension dimension, Axis axis, int position);
	public QueryDimension getDimension(String name);
	public void resetAxisSelections(QueryAxis axis);
	public void clearAllQuerySelections();
	public void setMdx(String mdx);
	public void setScenario(Scenario scenario);
	public Scenario getScenario();
	public void setTag(SaikuTag tag);
	public SaikuTag getTag();
	public void removeTag();
	public void storeCellset(CellSet cs);
	public CellSet getCellset();
	public void setStatement(OlapStatement os);
	public OlapStatement getStatement();
	public void cancel() throws Exception;
	public void clearAxis(String axisName) throws SaikuOlapException;
	

}
