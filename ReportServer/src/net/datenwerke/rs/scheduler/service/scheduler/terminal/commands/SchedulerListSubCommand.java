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
 
 
package net.datenwerke.rs.scheduler.service.scheduler.terminal.commands;

import java.text.DateFormat;
import java.util.Arrays;

import net.datenwerke.rs.base.service.reportengines.table.output.object.RSStringTableRow;
import net.datenwerke.rs.base.service.reportengines.table.output.object.RSTableModel;
import net.datenwerke.rs.base.service.reportengines.table.output.object.TableDefinition;
import net.datenwerke.rs.scheduler.service.scheduler.locale.SchedulerMessages;
import net.datenwerke.rs.scheduler.service.scheduler.terminal.hooks.SchedulerSubCommandHook;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.scheduler.service.scheduler.SchedulerService;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.stores.jpa.filter.JobFilterConfiguration;

import com.google.inject.Inject;


public class SchedulerListSubCommand implements SchedulerSubCommandHook{

	public static final String BASE_COMMAND = "list";
	
	private final SchedulerService schedulerService;

	@Inject
	public SchedulerListSubCommand(
		SchedulerService schedulerService
		){
		
		/* store objects */
		this.schedulerService = schedulerService;
	}
	
	@Override
	public String getBaseCommand() {
		return BASE_COMMAND;
	}
	
	@Override
	public boolean consumes(CommandParser parser, TerminalSession session) {
		return BASE_COMMAND.equals(parser.getBaseCommand());
	}

	@Override
	@CliHelpMessage(
		messageClass = SchedulerMessages.class,
		name = BASE_COMMAND,
		description = "commandScheduler_sub_list_description"
	)
	public CommandResult execute(CommandParser parser, TerminalSession session) throws ObjectResolverException {
		JobFilterConfiguration filter = new JobFilterConfiguration();
		
		if(parser.hasOption("i")){
			filter.setActive(false);
			filter.setInActive(true);
		} else {
			filter.setActive(true);
			filter.setInActive(false);
		}
		
		RSTableModel table = new RSTableModel();
		table.setTableDefinition(new TableDefinition(Arrays.asList(new String[]{"id", "type", "next firetime"})));
		for (AbstractJob job : schedulerService.getJobsBy(filter)){
			table.addDataRow(new RSStringTableRow(
				String.valueOf(job.getId()), 
				job.getClass().getSimpleName(),
				null != job.getTrigger().getNextScheduledFireTime() ?
						DateFormat.getDateTimeInstance().format(job.getTrigger().getNextScheduledFireTime()) : ""
			));
		}
		
		return new CommandResult(table);
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
	}

}
