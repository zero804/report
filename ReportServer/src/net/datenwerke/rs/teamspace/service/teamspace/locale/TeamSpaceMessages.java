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
 
 
package net.datenwerke.rs.teamspace.service.teamspace.locale;

import net.datenwerke.rs.utils.localization.Messages;


public interface TeamSpaceMessages extends Messages{

	String teamSpaceSecureeName();

	String rightTeamSpaceAdminAbbreviation();

	String rightTeamSpaceAdminDescription();

	String commandTeamspacemod_description();
	
	String commandTeamspacemod_sub_memberadd_description();
	String commandTeamspacemod_sub_memberadd_cflag();
	String commandTeamspacemod_sub_memberadd_arg_teamspaceid();
	String commandTeamspacemod_sub_memberadd_arg_members();
	
	String commandTeamspacemod_sub_setrole_description();
	String commandTeamspacemod_sub_setrole_arg1();
	String commandTeamspacemod_sub_setrole_arg2();
	String commandTeamspacemod_sub_setrole_arg3();
}

