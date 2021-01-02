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
 
 
package net.datenwerke.rs.terminal.service.terminal.vfs.commands;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.rs.base.service.reportengines.table.output.object.RSStringTableRow;
import net.datenwerke.rs.base.service.reportengines.table.output.object.RSTableModel;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.Argument;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.NonOptArgument;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocationInfo;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSObjectInfo;
import net.datenwerke.rs.terminal.service.terminal.vfs.VirtualFileSystemDeamon;
import net.datenwerke.rs.terminal.service.terminal.vfs.exceptions.VFSException;
import net.datenwerke.rs.terminal.service.terminal.vfs.locale.VfsMessages;

public class VfsCommandLs implements TerminalCommandHook  {

	public static final String BASE_COMMAND = "ls";
	
	
	@Override
	public boolean consumes(CommandParser parser, TerminalSession session) {
		return BASE_COMMAND.equals(parser.getBaseCommand());
	}

	@CliHelpMessage(
		messageClass = VfsMessages.class,
		name = BASE_COMMAND,
		description = "commandLs_description",
		args = {
			@Argument(flag="l", description="commandLs_lArgument")
		},
		nonOptArgs = {
			@NonOptArgument(name="dir", description="commandLs_dirArgument")
		}
	)
	@Override
	public CommandResult execute(CommandParser parser, TerminalSession session) {
		VirtualFileSystemDeamon vfs = session.getFileSystem();
		
		try {
			List<String> arguments = parser.getNonOptionArguments();
			String path = arguments.isEmpty() ? "" : arguments.get(0);
			
			VFSLocation location  = null;
			if(null == path || "".equals(path))
				location = vfs.getCurrentLocation();
			else
				location = vfs.getLocation(path);
			
			VFSLocationInfo info = vfs.getLocationInfo(location);
			if(info.getChildInfos().isEmpty())
				return CommandResult.createEmptyInstance();
			
			if(parser.hasOption("l")){
				RSTableModel table = new RSTableModel();
				for(VFSObjectInfo child : info.getChildInfos())
					table.addDataRow(new RSStringTableRow(child.getId(), child.getName(), child.getType().getSimpleName()));
				
				return new CommandResult(table);
			} else {
				List<String> childList = new ArrayList<String>();
				for(VFSObjectInfo child : info.getChildInfos())
					childList.add(child.getName());
				
				return new CommandResult(childList);
			}
		} catch (VFSException e) {
			return new CommandResult(e.getMessage());
		}
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
		autocompleteHelper.autocompleteBaseCommand(BASE_COMMAND);
	}

}
