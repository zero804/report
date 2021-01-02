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
 
 
package org.saiku.olap.query2.filter;

import java.util.ArrayList;
import java.util.List;

public class ThinFilter {
	
	private FilterFlavour flavour;
	private FilterOperator operator;
	private FilterFunction function;
	private List<String> expressions = new ArrayList<>();

	public enum FilterFlavour {
		Generic, Measure, Name, NameLike, N
	}
	
	public enum FilterOperator {
		EQUALS, NOTEQUAL, GREATER, GREATER_EQUALS, SMALLER, SMALLER_EQUALS, LIKE
	}

	public enum FilterFunction {
		Filter, TopCount, TopPercent, TopSum, BottomCount, BottomPercent, BottomSum
	}

	public ThinFilter() {}

  public ThinFilter(
			FilterFlavour flavour, 
			FilterOperator operator,
			FilterFunction function, 
			List<String> expressions) 
	{
		this.flavour = flavour;
		this.operator = operator;
		this.function = function;
		this.expressions = expressions;
	}

	/**
	 * @return the flavour
	 */
	public FilterFlavour getFlavour() {
		return flavour;
	}

	/**
	 * @return the operator
	 */
	public FilterOperator getOperator() {
		return operator;
	}

	/**
	 * @return the function
	 */
	public FilterFunction getFunction() {
		return function;
	}

	/**
	 * @return the expressions
	 */
	public List<String> getExpressions() {
		return expressions;
	}
	
	

}
