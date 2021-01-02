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

import org.olap4j.mdx.CallNode;
import org.olap4j.mdx.HierarchyNode;
import org.olap4j.mdx.LiteralNode;
import org.olap4j.mdx.ParseTreeNode;
import org.olap4j.mdx.Syntax;
import org.olap4j.mdx.parser.MdxParser;
import org.olap4j.metadata.Hierarchy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NameFilter extends AbstractFilterFunction {

	private String operator = " = ";
	private String op;
	private List<String> filterExpression = new ArrayList<String>();
	private MdxFunctionType type;
	private Hierarchy hierarchy;

	public NameFilter(Hierarchy hierarchy, String... matchingExpression) {
		List<String> expressions = Arrays.asList(matchingExpression);
		this.filterExpression.addAll(expressions);
		this.hierarchy = hierarchy;
		this.type = MdxFunctionType.Filter;
	}
	
	public NameFilter(Hierarchy hierarchy, List<String> matchingExpression) {
		this.hierarchy = hierarchy;
		this.filterExpression.addAll(matchingExpression);
		this.type = MdxFunctionType.Filter;
	}

	public NameFilter(Hierarchy hierarchy, List<String> matchingExpression, String operator) {
		this.hierarchy = hierarchy;
		this.filterExpression.addAll(matchingExpression);
		this.type = MdxFunctionType.Filter;
		this.op = operator;
		if(operator!=null && operator.equals("NOTEQUAL")){
			this.operator = " <> ";
		}
	}

	@Override
	public List<ParseTreeNode> getArguments(MdxParser parser) {
		List<ParseTreeNode> filters = new ArrayList<ParseTreeNode>();
		List<ParseTreeNode> arguments = new ArrayList<ParseTreeNode>();
		ParseTreeNode h =  new HierarchyNode(null, hierarchy);
		for (int i = 0; i< filterExpression.size(); i++) {
			String filter = filterExpression.get(i);

			String o = operator;
			if(filterExpression.size()>1 && i == 0){
				o = " = ";
			}

			ParseTreeNode filterExp =  LiteralNode.createString(null, filter);
			CallNode currentMemberNode =
					new CallNode(
							null,
							"CurrentMember",
							Syntax.Property,
							h);
			CallNode currentMemberNameNode =
					new CallNode(
							null,
							"Name",
							Syntax.Property,
							currentMemberNode);

			CallNode filterNode = 
					new CallNode(
							null,
							o,
							Syntax.Infix,
							currentMemberNameNode,
							filterExp);
			
			filters.add(filterNode);			
		}
		if (filters.size() == 1) {
			arguments.addAll(filters);
		} else if (filters.size() > 1) {
			ParseTreeNode allfilter = filters.get(0);
			for (int i = 1; i< filters.size(); i++) {
				allfilter = 
						new CallNode(
								null,
								" OR ",
								Syntax.Infix,
								allfilter,
								filters.get(i));
			}
			arguments.add(allfilter);
		}
		return arguments;
	}

	@Override
	public MdxFunctionType getFunctionType() {
		return type;
	}

	/**
	 * @return the filterExpression
	 */
	public List<String> getFilterExpression() {
		return filterExpression;
	}

	/**
	 * @return the hierarchy
	 */
	public Hierarchy getHierarchy() {
		return hierarchy;
	}

	public String getOp() {
		return op;
	}
}
