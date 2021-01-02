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
 
 
package net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.handlers.sql;

import java.util.Iterator;

import net.datenwerke.rs.computedcolumns.service.computedcolumns.hookers.ExpressionTokenToSqlHook;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.ExpressionToken;
import net.datenwerke.rs.computedcolumns.service.computedcolumns.tokenizer.handlers.tokens.RelationExpressionToken;

public class RelationToSql implements ExpressionTokenToSqlHook {

	@Override
	public boolean consumes(ExpressionToken token) {
		return token instanceof RelationExpressionToken;
	}

	@Override
	public String handleToken(ExpressionToken token,
			Iterator<ExpressionToken> tokenIt) {
		switch (((RelationExpressionToken)token).getType()){
		case LESS:
			return "<";
		case LESS_OR_EQUAL:
			return "<=";
		case EQUAL:
			return "=";
		case GREATER:
			return ">";
		case NOTEQUAL:
			return "<>";
		case GREATER_OR_EQUAL:
			return ">=";
		case LIKE:
			return "LIKE";
		case BETWEEN:
			return "BETWEEN";
		case IS_NULL:
			return "IS NULL";
		case IS_NOT_NULL:
			return "IS NOT NULL";
		default:
			throw new IllegalArgumentException();
		}
	}

}
