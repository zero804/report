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
 
 
package net.datenwerke.rs.condition.service.condition.hookers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.pdf.PdfReader;

import net.datenwerke.rs.base.service.reportengines.table.output.object.CompiledTableReport;
import net.datenwerke.rs.condition.client.condition.dto.SimpleCondition;
import net.datenwerke.rs.condition.service.condition.hooks.ConditionProviderHook;
import net.datenwerke.rs.condition.service.condition.locale.ConditionMessages;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.basereports.HasPages;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob;
import net.datenwerke.security.service.usermanager.entities.User;

public class NotIsEmptySchedulerCondition implements ConditionProviderHook {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private static final String KEY = "__rs_cond_not_empty";
	
	@Override
	public SimpleCondition provideConditionFor(Report report) {
		SimpleCondition cond = new SimpleCondition();
		cond.setKey(KEY);
		cond.setName(ConditionMessages.INSTANCE.notIsEmptyCondition());
		cond.setDescription(ConditionMessages.INSTANCE.notIsEmptyConditionDesc());
		return cond;
	}

	@Override
	public boolean consumes(String key) {
		return KEY.equals(key);
	}

	@Override
	public boolean execute(String key, String expression, User user, ReportExecuteJob rJob) {
		CompiledReport report = rJob.getExecutedReport();
		if(report instanceof CompiledTableReport)
			return ((CompiledTableReport)report).hasData();
		else if(report instanceof HasPages)
			return ((HasPages)report).getPages() > 0;
		else if(null != report.getFileExtension() && "pdf".equals(report.getFileExtension().toLowerCase()) || "application/pdf".equals(report.getMimeType())){
			try{
				PdfReader reader = new PdfReader((byte[])report.getReport());
				int pages = reader.getNumberOfPages();
				return pages > 0;
			}catch(Exception e){
				logger.warn("Could not read pdf to determine page count", e);
			}
		}
		return true;
	}

	@Override
	public boolean isBeforeActions() {
		return true;
	}

	@Override
	public boolean isBeforeJob() {
		return false;
	}

	

}