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

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.Days;

@GenerateDto(
	dtoPackage="net.datenwerke.scheduler.client.scheduler.dto.config.complex"
)
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class WeeklyConfig extends DateTriggerConfig {

	
	/**
	 * Weekly, every weeklyN(3) Weeks on {weeklyDays}(Monday, Wednesday)
	 */
	@ExposeToClient
	private Integer weeklyN = 1;
	
	@JoinTable(name="WEEKLY_CONFIG_2_DAYS", joinColumns=@JoinColumn(name="weekly_config_id"))
	@ExposeToClient
	@ElementCollection(targetClass=Days.class, fetch=FetchType.EAGER)
	@Enumerated
	private Set<Days> weeklyDays;
	
	/**
	 * Defines the period with which the trigger should fire (every nth week).
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#WEEKLY}</p>
	 * @return
	 */
	public Integer getWeeklyN() {
		return weeklyN;
	}

	/**
 	 * Defines the period with which the trigger should fire (every nth week).
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#WEEKLY}</p>
	 * @param weeklyN
	 */
	public void setWeeklyN(Integer weeklyN) {
		if(null != weeklyN && weeklyN > 1000)
			throw new IllegalArgumentException("Are you sure that you want to schedule something every " + weeklyN + " weeks?");
		
		this.weeklyN = weeklyN;
	}
	
	/**
	 * Defines the days per week period with which the trigger should fire (every nth week on Monday, Wednesday and Friday).
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#WEEKLY}</p>
     *
	 * @return
	 */
	public Set<Days> getWeeklyDays() {
		return weeklyDays;
	}
	
	/**
     * Defines the days per week period with which the trigger should fire (every nth week on Monday, Wednesday and Friday).
	 * 
	 * <p>Only applies if {@link SeriesPattern} is set to {@link SeriesPattern#WEEKLY}</p>
     *
	 * @param weeklyDays
	 */
	public void setWeeklyDays(Set<Days> weeklyDays) {
		this.weeklyDays = weeklyDays;
	}
}
