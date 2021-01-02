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
 
 
package net.datenwerke.rs.compiledreportstore;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class CompiledReportStoreModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CompiledReportStoreService.class).to(CompiledReportStoreServiceImpl.class).in(Singleton.class);
		
		bind(CompiledReoprtStoreStartup.class).asEagerSingleton();
	}

}
