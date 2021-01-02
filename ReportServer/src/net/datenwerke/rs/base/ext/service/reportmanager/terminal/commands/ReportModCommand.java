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
 
 
package net.datenwerke.rs.base.ext.service.reportmanager.terminal.commands;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.ext.service.reportmanager.hooks.ReportModSubCommandHook;
import net.datenwerke.rs.base.ext.service.reportmanager.locale.ReportManagerExtMessages;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.exceptions.TerminalException;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.hooks.SubCommand;
import net.datenwerke.rs.terminal.service.terminal.hooks.SubCommandContainerImpl;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;

import com.google.inject.Inject;


public class ReportModCommand extends SubCommandContainerImpl {

	public static final String BASE_COMMAND = "reportmod";
	
	private final HookHandlerService hookHandler;
	
	@Inject
	public ReportModCommand(
		HookHandlerService hookHandler
		){
		
		/* store objects */
		this.hookHandler = hookHandler;
	}
	
	@Override
	public String getBaseCommand() {
		return BASE_COMMAND;
	}

	@Override
	@CliHelpMessage(
		messageClass = ReportManagerExtMessages.class,
		name = BASE_COMMAND,
		description = "commandReportmod_description"
	)
	public CommandResult execute(CommandParser parser, TerminalSession session) throws TerminalException {
		return super.execute(parser,session);
	}

	@Override
	public List<SubCommand> getSubCommands() {
		List<ReportModSubCommandHook> list =  hookHandler.getHookers(ReportModSubCommandHook.class);
		return new ArrayList<SubCommand>(list);
	}




}
