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
 
 
package net.datenwerke.scheduler.service.scheduler.triggers.complex.config;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.DailyRepeatType;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.EndTypes;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.TimeUnits;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DateTriggerConfig.class)
public abstract class DateTriggerConfig_ {

	public static volatile SingularAttribute<DateTriggerConfig, Time> atTime;
	public static volatile SingularAttribute<DateTriggerConfig, Time> timeRangeStart;
	public static volatile SingularAttribute<DateTriggerConfig, EndTypes> endType;
	public static volatile SingularAttribute<DateTriggerConfig, Time> timeRangeEnd;
	public static volatile SingularAttribute<DateTriggerConfig, Date> lastExecution;
	public static volatile SingularAttribute<DateTriggerConfig, DailyRepeatType> dailyRepeatType;
	public static volatile SingularAttribute<DateTriggerConfig, Integer> timeRangeInterval;
	public static volatile SingularAttribute<DateTriggerConfig, Integer> numberOfExecutions;
	public static volatile SingularAttribute<DateTriggerConfig, Long> id;
	public static volatile SingularAttribute<DateTriggerConfig, Long> version;
	public static volatile SingularAttribute<DateTriggerConfig, Date> firstExecution;
	public static volatile SingularAttribute<DateTriggerConfig, TimeUnits> timeRangeUnit;

}

