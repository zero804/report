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
 
 
package net.datenwerke.usermanager.ext.service.terminal.commands;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.NonOptArgument;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.objresolver.ObjectResolverDeamon;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.security.service.security.rights.Read;
import net.datenwerke.security.service.security.rights.Write;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.UserPropertiesService;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.security.service.usermanager.locale.UserManagerMessages;
import net.datenwerke.usermanager.ext.service.hooks.UserModSubCommandHook;

import com.google.inject.Inject;


public class SetUserPropertySubCommand implements UserModSubCommandHook {

	public static final String BASE_COMMAND = "setproperty";
	
	private final UserManagerService userService;

	private final UserPropertiesService userPropertiesService;

	@Inject
	public SetUserPropertySubCommand(
		UserManagerService userService, 
		UserPropertiesService userPropertiesService
		){
		
		/* store objects */
		this.userService = userService;
		this.userPropertiesService = userPropertiesService;
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
		messageClass = UserManagerMessages.class,
		name = BASE_COMMAND,
		description = "commandUsermod_sub_setProperty_description",
		nonOptArgs = {
			@NonOptArgument(name="property", description="commandUsermod_sub_setProperty_arg1", mandatory=true),
			@NonOptArgument(name="value", description="commandUsermod_sub_setProperty_arg2", mandatory=true),
			@NonOptArgument(name="users", description="commandUsermod_sub_setProperty_arg3", mandatory=true, varArgs=true)
		}
	)
	public CommandResult execute(CommandParser parser, TerminalSession session) throws ObjectResolverException {
		List<String> arguments = parser.getNonOptionArguments();
		if(3 > arguments.size())
			throw new IllegalArgumentException();
		
		String property = arguments.remove(0);
		String value = arguments.remove(0);
		
		ObjectResolverDeamon objectResolver = session.getObjectResolver();
		
		Set<User> userList = new HashSet<User>();
		for(String locationStr : arguments){
			Collection<Object> objectList = objectResolver.getObjects(locationStr, Read.class, Write.class);
			if(objectList.isEmpty())
				throw new IllegalArgumentException("No users selected");
			
			for(Object obj : objectList){
				if(! (obj instanceof AbstractUserManagerNode))
					throw new IllegalArgumentException("Found unknown objects in object selection: " + obj.getClass());
				if(obj instanceof User)
					userList.add((User) obj);
			}
		}
		
		for(User user : userList){
			userPropertiesService.setPropertyValue(user, property, value);
			userService.merge(user);
		}
		
		return new CommandResult();
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
	}

}
