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
 
 
package net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.handlers;

import java.util.Collection;

import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.ExpressionToken;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.ExpressionTokenizer;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.handlers.tokens.VariableExpressionToken;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.hooks.ExpressionTokenHandlerHook;

public class VariableHandler implements ExpressionTokenHandlerHook{

	private Collection<String> variables;

	@Override
	public ExpressionToken generateToken(String strToken, ExpressionTokenizer expressionTokenizer, String lookaheadChar) {
		boolean greedy = null == lookaheadChar || "".equals(lookaheadChar.trim());
		strToken = strToken.trim();
		
		for(String variable : variables)
			if(strToken.equalsIgnoreCase(variable))
				return new VariableExpressionToken(variable, greedy);
			
		return null;
	}

	public void initVariables(Collection<String> variables) {
		this.variables  = variables;
	}

}
