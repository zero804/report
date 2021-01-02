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
 
 
package net.datenwerke.rs.scheduler.service.scheduler.jobs;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.scheduler.service.scheduler.SchedulerService;
import net.datenwerke.scheduler.service.scheduler.jobs.BaseJob;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.entities.User;

/**
 * 
 *
 */
@MappedSuperclass
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class ReportServerJob extends BaseJob {

	@Transient
	@Inject protected Provider<AuthenticatorService> authenticatorServiceProvider;
	
	@Transient
	@Inject protected UserManagerService userService;
	
	@ManyToOne
	private User executor;
	
	@ManyToOne
	private User scheduledBy;
	
	public User getScheduledBy() {
		return scheduledBy;
	}
	
	public void setScheduledBy(User scheduledBy) {
		this.scheduledBy = scheduledBy;
	}
	
	public User getExecutor() {
		return executor;
	}

	/**
	 * If no executor is set, the currently authenticated use is used.
	 * @param executor
	 */
	public void setExecutor(User executor) {
		this.executor = executor;
	}
	
	public void setExecutor() {
		this.executor = authenticatorServiceProvider.get().getCurrentUser();
	}

}
