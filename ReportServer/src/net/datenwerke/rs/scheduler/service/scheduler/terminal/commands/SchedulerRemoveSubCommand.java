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

import java.util.List;

import net.datenwerke.rs.scheduler.service.scheduler.locale.SchedulerMessages;
import net.datenwerke.rs.scheduler.service.scheduler.terminal.hooks.SchedulerSubCommandHook;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.NonOptArgument;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.scheduler.service.scheduler.SchedulerService;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;

import com.google.inject.Inject;


public class SchedulerRemoveSubCommand implements SchedulerSubCommandHook{

	public static final String BASE_COMMAND = "remove";
	
	private final SchedulerService schedulerService;

	@Inject
	public SchedulerRemoveSubCommand(
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
		description = "commandScheduler_sub_remove_description",
		nonOptArgs = {
			@NonOptArgument(name="jobId", description="commandScheduler_sub_remove_arg1", mandatory=true)
		}
	)
	public CommandResult execute(CommandParser parser, TerminalSession session) throws ObjectResolverException {
		List<String> args = parser.getNonOptionArguments();
		if(args.size() != 1)
			throw new IllegalArgumentException("Expected exactly one argument");
		
		Long id = Long.valueOf(args.get(0));
		AbstractJob job = schedulerService.getJobById(id);
		if(null == job)
			return new CommandResult("Could not find job with id: " + id);
		
		schedulerService.remove(job);
		
		return new CommandResult("removed job: " + id);
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
	}

}
