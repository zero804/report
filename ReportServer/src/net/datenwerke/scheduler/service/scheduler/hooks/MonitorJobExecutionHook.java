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
 
 
package net.datenwerke.scheduler.service.scheduler.hooks;

import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;
import net.datenwerke.hookservices.annotations.HookConfig;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;

@HookConfig
public interface MonitorJobExecutionHook extends Hook {

	void notifyOfExecution(AbstractJob reportExecuteJob);

	void jobExecutedSuccessfully(AbstractJob reportExecuteJob);

	/***
	 * 
	 * be careful. if the action ended abnormally the transaction will be rolled back. so if you plan
	 * to write something into the database, make sure to get a new transaction.
	 * @param action
	 * @param e
	 */
	void jobExecutionFailed(AbstractJob reportExecuteJob,
			Exception e);

}
