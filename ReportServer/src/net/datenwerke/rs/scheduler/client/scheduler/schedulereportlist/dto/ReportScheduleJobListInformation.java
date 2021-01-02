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
 
 
package net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto;

import java.util.Date;

import net.datenwerke.gf.base.client.dtogenerator.RsDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownUser;

public class ReportScheduleJobListInformation extends RsDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2673814530254653060L;

	private Long reportId;
	private String reportName;
	private String reportDescription;
	private Date lastScheduled;
	private Date nextScheduled;
	private UserDto executor;
	private StrippedDownUser scheduledBy;
	private boolean active;
	
	private Long jobId;
	private boolean reportDeleted;
	private boolean executorDeleted;
	private boolean scheduledByDeleted;
	
	public Long getReportId() {
		return reportId;
	}
	
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportDescription() {
		return reportDescription;
	}

	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
	}

	public Date getLastScheduled() {
		return lastScheduled;
	}

	public void setLastScheduled(Date lastScheduled) {
		this.lastScheduled = lastScheduled;
	}

	public Date getNextScheduled() {
		return nextScheduled;
	}

	public void setNextScheduled(Date nextScheduled) {
		this.nextScheduled = nextScheduled;
	}

	public String getExecutorFirstName() {
		return executor.getFirstname();
	}

	public String getExecutorLastName() {
		return executor.getLastname();
	}

	public String getScheduledByFirstName() {
		return scheduledBy.getFirstname();
	}

	public String getScheduledByLastName() {
		return scheduledBy.getLastname();
	}
	
	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public boolean isReportDeleted() {
		return reportDeleted;
	}

	public void setReportDeleted(boolean deleted) {
		this.reportDeleted = deleted;
	}

	public String getExecutorName(){
		StringBuffer name = new StringBuffer();
		
		if(null != getExecutorFirstName())
			name.append(getExecutorFirstName()).append(" ");
		
		if(null != getExecutorLastName())
			name.append(getExecutorLastName());
		
		return name.toString();
	}
	
	public String getScheduledByName() {
		StringBuffer name = new StringBuffer();
		
		if(null != getScheduledByFirstName())
			name.append(getScheduledByFirstName()).append(" ");
		
		if(null != getScheduledByLastName())
			name.append(getScheduledByLastName());
		
		return name.toString();
	}

	public void setExecutorDeleted(boolean executorDeleted) {
		this.executorDeleted = executorDeleted;
	}
	
	public boolean isExecutorDeleted() {
		return executorDeleted;
	}

	public void setExecutor(UserDto executor) {
		this.executor = executor;
	}
	
	public UserDto getExecutor() {
		return executor;
	}
	
	public void setScheduledByDeleted(boolean scheduledByDeleted) {
		this.scheduledByDeleted = scheduledByDeleted;
	}
	
	public boolean isScheduledByDeleted() {
		return scheduledByDeleted;
	}

	public void setScheduledBy(StrippedDownUser scheduledBy) {
		this.scheduledBy = scheduledBy;
	}
	
	public StrippedDownUser getScheduledBy() {
		return scheduledBy;
	}
}
