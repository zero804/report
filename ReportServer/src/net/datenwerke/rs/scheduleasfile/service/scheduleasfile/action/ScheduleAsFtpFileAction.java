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
 
 
package net.datenwerke.rs.scheduleasfile.service.scheduleasfile.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.FtpService;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob;
import net.datenwerke.rs.utils.juel.SimpleJuel;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractAction;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.exceptions.ActionExecutionException;

@Entity
@Table(name="SCHED_ACTION_AS_FTP_FILE")
@Inheritance(strategy=InheritanceType.JOINED)
public class ScheduleAsFtpFileAction extends AbstractAction {

	@Transient @Inject private Provider<SimpleJuel> simpleJuelProvider;
	@Transient @Inject private FtpService ftpService;
	
	@Transient private Report report;
	
	@Transient private String filename;
	
	private String name;
	private String folder;
	
	@Override
	public void execute(AbstractJob job) throws ActionExecutionException {
		if(! (job instanceof ReportExecuteJob))
			throw new ActionExecutionException("No idea what job that is");
		
		ReportExecuteJob rJob = (ReportExecuteJob) job;
		
		/* did everything go as planned ?*/
		if(null == rJob.getExecutedReport())
			return;
		
		if (! ftpService.isFtpEnabled() || ! ftpService.isFtpSchedulingEnabled())
			throw new ActionExecutionException("ftp scheduling is disabled");
		
		CompiledReport compiledReport = rJob.getExecutedReport();
		report = rJob.getReport();
		
		SimpleJuel juel = simpleJuelProvider.get();
		juel.addReplacement("now", new SimpleDateFormat("yyyyMMddhhmm").format(Calendar.getInstance().getTime()));
		filename = null == name ? "" : juel.parse(name);
		
		filename += "." + compiledReport.getFileExtension();
		
		if(null == name || name.trim().isEmpty())
			throw new ActionExecutionException("name is empty");
		
		if(null == folder || folder.trim().isEmpty())
			throw new ActionExecutionException("folder is empty");
		
		try {
			ftpService.sendToFtpServer(compiledReport.getReport(), filename, folder);
		} catch (Exception e) {
			throw new ActionExecutionException("report could not be sent to FTP", e);
		}
		
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public Report getReport() {
		return report;
	}
}
