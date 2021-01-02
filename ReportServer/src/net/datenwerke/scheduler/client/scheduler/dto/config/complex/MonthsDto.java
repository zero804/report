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
 
 
package net.datenwerke.scheduler.client.scheduler.dto.config.complex;

import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.scheduler.client.scheduler.locale.SchedulerMessages;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public enum MonthsDto {

	JANUARY {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelJanuary();
		}
	},
	FEBRUARY {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelFebruary();
		}
	},
	MARCH {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelMarch();
		}
	},
	APRIL {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelApril();
		}
	},
	MAY {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelMay();
		}
	},
	JUNE {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelJune();
		}
	},
	JULY {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelJuly();
		}
	},
	AUGUST {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelAugust();
		}
	},
	SEPTEMBER {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelSeptember();
		}
	},
	OCTOBER {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelOctober();
		}
	},
	NOVEMBER {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelNovember();
		}
	},
	DECEMBER {
		public String toString(){
			return SchedulerMessages.INSTANCE.enumLabelDecember();
		}
	}

}