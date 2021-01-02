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
 
 
package net.datenwerke.scheduler.service.scheduler.locale;

import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.Days;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.Months;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.Nth;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.TimeUnits;

import com.google.gwt.core.client.GWT;

public class LocaliseDateHelper {

	public static SchedulerMessages messages;
	static{
		try{
			messages = GWT.create(SchedulerMessages.class);
		}catch(Exception e){
			/* this happens if called from server side code. 
			 * Because some enums use this class in their toString methods
			 * we need to provide a fallback for this case
			*/
		}
	}
	
	private LocaliseDateHelper() {
		// no instances, please
	}
	
	public static String localDay(Days day){
		if(null == messages)
			return day.name();
		
		switch(day){
		case MONDAY:	return messages.monday();
		case TUESDAY:	return messages.tuesday();
		case WEDNESDAY:	return messages.wednesday();
		case THURSDAY:	return messages.thursday();
		case FRIDAY:	return messages.friday();
		case SATURDAY:	return messages.saturday();
		case SUNDAY:	return messages.sunday();
		case DAY:		return messages.day();
		case WORKINGDAY:	return messages.workingday();
		case WEEKENDDAY:	return messages.weekendday();
		}
		return day.name();
	}
	
	public static String localMonth(Months month){
		if(null == messages)
			return month.name();
		
		switch(month){
		case JANUARY: 	return messages.january();
		case FEBRUARY:	return messages.february();
		case MARCH:		return messages.march();
		case APRIL:		return messages.april();
		case MAY:		return messages.may();
		case JUNE:		return messages.june();
		case JULY:		return messages.july();
		case AUGUST:	return messages.august();
		case SEPTEMBER:	return messages.september();
		case OCTOBER:	return messages.october();
		case NOVEMBER: 	return messages.november();
		case DECEMBER:	return messages.december();
		}
		return month.name();
	}

	public static String localNth(Nth nth){
		if(null == messages)
			return nth.name();
		
		switch(nth){
		case FIRST: 	return messages.first();
		case SECOND: 	return messages.second();
		case THIRD: 	return messages.third();
		case FOURTH: 	return messages.fourth();
		case LAST: 		return messages.last();
		}
		return nth.name();
	}

	public static String localTimeUnit(TimeUnits timeUnit) {
		if(null == messages)
			return timeUnit.name();
		
		switch(timeUnit){
		case HOURS: 	return messages.hours();
		case MINUTES:	return messages.minutes();
		}
		
		return timeUnit.name();
	}
}
