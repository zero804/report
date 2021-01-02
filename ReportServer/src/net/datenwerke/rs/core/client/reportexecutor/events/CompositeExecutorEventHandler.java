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
 
 
package net.datenwerke.rs.core.client.reportexecutor.events;

import java.util.ArrayList;
import java.util.List;

public class CompositeExecutorEventHandler implements ExecutorEventHandler {

	private final List<ExecutorEventHandler> handlers;
	
	public CompositeExecutorEventHandler(ExecutorEventHandler... handlers){
		this.handlers = new ArrayList<ExecutorEventHandler>();
		for(ExecutorEventHandler handler:handlers)
			this.handlers.add(handler);
	}
	
	public void addHandler(ExecutorEventHandler handler) {
		handlers.add(handler);
	}
	
	@Override
	public void handleEvent(ReportExecutorEvent event) {
		for(ExecutorEventHandler handler:handlers)
			handler.handleEvent(event);
	}

}
