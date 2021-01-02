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
 
 
package net.datenwerke.rs.base.client.reportengines.table.dto;

import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.rs.base.client.reportengines.table.locale.EnumMessages;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public enum AggregateFunctionDto {

	AVG {
		public String toString(){
			return EnumMessages.INSTANCE.avg();
		}
	},
	COUNT {
		public String toString(){
			return EnumMessages.INSTANCE.count();
		}
	},
	MAX {
		public String toString(){
			return EnumMessages.INSTANCE.max();
		}
	},
	MIN {
		public String toString(){
			return EnumMessages.INSTANCE.min();
		}
	},
	SUM {
		public String toString(){
			return EnumMessages.INSTANCE.sum();
		}
	},
	VARIANCE {
		public String toString(){
			return EnumMessages.INSTANCE.variance();
		}
	},
	COUNT_DISTINCT {
		public String toString(){
			return EnumMessages.INSTANCE.countDistinct();
		}
	}

}
