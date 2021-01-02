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
 
 
package net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.handlers.tokens;

import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.ExpressionToken;

public class ParameterExpressionToken implements ExpressionToken {

	private final String parameter;

	public ParameterExpressionToken(String parameter) {
		super();
		this.parameter = parameter;
	}

	public String getParameter() {
		return parameter;
	}
	
	@Override
	public int hashCode() {
		return parameter.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof ParameterExpressionToken))
			return false;
		return parameter.equals(((ParameterExpressionToken)obj).parameter);
	}
	
	@Override
	public boolean isGreedy() {
		return true;
	}
}