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
 
 
package net.datenwerke.rs.birt.service.reportengine.output.generator;

import org.eclipse.birt.report.engine.api.RenderOption;

import com.google.inject.Inject;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.birt.service.reportengine.hooks.AdaptBirtRenderOptionsHook;

public abstract class BirtOutputGeneratorImpl implements BirtOutputGenerator {

	@Inject
	protected HookHandlerService hookHandler;
	
	@Override
	public boolean isCatchAll() {
		return false;
	}
	
	protected RenderOption adapt(RenderOption options) {
		for(AdaptBirtRenderOptionsHook adapter : hookHandler.getHookers(AdaptBirtRenderOptionsHook.class)){
			RenderOption newOptions = adapter.adapt(options);
			if(null != newOptions)
				options = newOptions;
				
		}
		
		return options;
	}

}
