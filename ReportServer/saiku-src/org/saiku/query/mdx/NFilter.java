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
 *   Copyright 2014 Paul Stoellberger
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
package org.saiku.query.mdx;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.olap4j.mdx.LiteralNode;
import org.olap4j.mdx.ParseTreeNode;
import org.olap4j.mdx.parser.MdxParser;

public class NFilter extends AbstractFilterFunction {

	private String filterExpression;
	private int n;
	private MdxFunctionType type;

	public NFilter(MdxFunctionType type, int n,  String filterExpression) {
		if (MdxFunctionType.Filter.equals(type)) {
			throw new IllegalArgumentException("Cannot use Filter() as TopN Filter");
		}
		this.filterExpression = filterExpression;
		this.n = n;
		this.type = type;
	}
	
	public int getN() {
		return n;
	}

	public String getFilterExpression() {
		return filterExpression;
	}

	@Override
	public List<ParseTreeNode> getArguments(MdxParser parser) {
		List<ParseTreeNode> arguments = new ArrayList<ParseTreeNode>();
		ParseTreeNode nfilter =  LiteralNode.createNumeric(null, new BigDecimal(n), false);
		arguments.add(nfilter);
		if (filterExpression != null) {
			ParseTreeNode topn =  parser.parseExpression(filterExpression);
			arguments.add(topn);
		}
		
		return arguments;
	}

	@Override
	public MdxFunctionType getFunctionType() {
		return type;
	}
}
