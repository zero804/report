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
 
 
package net.datenwerke.gf.client.dispatcher;



public interface DispatcherService {
	
	public static final String REPORTSERVER_EVENT_BEFORE_STARTUP = "REPORTSERVER_EVENT_BEFORE_STARTUP"; //$NON-NLS-1$
	public static final String REPORTSERVER_EVENT_DISPATCHING_STARTED = "REPORTSERVER_EVENT_DISPATCHING_STARTED"; //$NON-NLS-1$
	public static final String REPORTSERVER_EVENT_USER_LOGGED_IN_APPLICATION_LOADED = "REPORTSERVER_EVENT_USER_LOGGED_IN_APPLICATION_LOADED"; //$NON-NLS-1$
	
	public void dispatch();

	public boolean isWarnOnExit();
	public void setWarnOnExit(boolean warnOnExit);

	public void redirect(String url, boolean disableExitWarning);

}
