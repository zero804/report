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
 
 
package net.datenwerke.rs.computedcolumns.client.computedcolumns.locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface ComputedColumnsMessages extends Messages {

	public static final ComputedColumnsMessages INSTANCE = GWT.create(ComputedColumnsMessages.class);
	
	String computedColumnsHeading();

	String removeAllConfirmHeading();
	String removeAllConfirmText();
	
	String addColumn();
	
	String expressionLabel();
	String editColumnHeading();
	String validateData();
	
	String newName();
	String newDescription();
	String expressionNotEmpty();
	
	String duplicateColumnLabel();
	
	String selectColBtnLabel();

	String importColumnConflictTitle();
	String importColumnConflictMsg(String name);
	
	
}