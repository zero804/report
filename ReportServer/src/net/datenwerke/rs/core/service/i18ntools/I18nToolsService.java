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
 
 
package net.datenwerke.rs.core.service.i18ntools;

import java.text.NumberFormat;

import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.ImplementedBy;

@ImplementedBy(I18nToolsServiceImpl.class)
public interface I18nToolsService {

	String getUserDecimalSeparator(User u);
	void setUserDecimalSeparator(User u, String separator);
	
	NumberFormat getUserNumberFormatter(User user);
	NumberFormat getUserNumberFormatter();
	
	NumberFormat getSystemNumberFormatter();
	String getUserDecimalSeparator();

	String getSystemDecimalSeparator();
	
	String translateNumberFromUserToSystem(String fe);
	String translateNumberFromSystemToUser(String fe);
	String translateNumberFromSystemToUser(String valueOf, User user);
	
	void validateSystemNumber(String number);
	
	String getDefaultDateFormat();
	FormatPatterns getFormatPatterns();
	
}
