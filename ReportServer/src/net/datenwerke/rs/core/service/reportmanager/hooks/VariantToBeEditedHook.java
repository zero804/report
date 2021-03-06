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
 
 
package net.datenwerke.rs.core.service.reportmanager.hooks;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;
import net.datenwerke.hookservices.annotations.HookConfig;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;

@HookConfig
public interface VariantToBeEditedHook extends Hook {

	/**
	 * Called just before the edits in a variant are going to be saved.
	 * 
	 * @param referenceVariant the original, not yet saved report. This shows the
	 *                         current state of the report in the database.
	 * @param reportDto        the report with the changes. This is going to be
	 *                         merged to {@paramref reportDto} after this hook is
	 *                         called.
	 * @param executorToken    the executor token.
	 * @throws ServerCallFailedException
	 */
	public void variantToBeEdited(Report referenceVariant, ReportDto reportDto, String executorToken) throws ServerCallFailedException;

}
