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
 
 
package net.datenwerke.rs.uservariables.service;

import net.datenwerke.eximport.hooks.ExporterProviderHook;
import net.datenwerke.eximport.hooks.ImporterProviderHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.reportmanager.hooks.ParameterProviderHook;
import net.datenwerke.rs.eximport.service.eximport.hooks.ExportAllHook;
import net.datenwerke.rs.eximport.service.eximport.hooks.ImportAllHook;
import net.datenwerke.rs.uservariables.service.eventhandler.HandleUserNodeRemoveEvents;
import net.datenwerke.rs.uservariables.service.eventhandler.HandleUserVarForceRemoveEvents;
import net.datenwerke.rs.uservariables.service.eventhandler.HandleUserVarRemoveEvents;
import net.datenwerke.rs.uservariables.service.parameters.hookers.UserVariableParameterProviderHooker;
import net.datenwerke.rs.uservariables.service.uservariables.entities.UserVariableDefinition;
import net.datenwerke.rs.uservariables.service.uservariables.eximport.UserVariableExporter;
import net.datenwerke.rs.uservariables.service.uservariables.eximport.UserVariableImporter;
import net.datenwerke.rs.uservariables.service.uservariables.eximport.hookers.ExportAllUserVariablesHooker;
import net.datenwerke.rs.uservariables.service.uservariables.eximport.hookers.ImportAllUserVariablesHooker;
import net.datenwerke.rs.uservariables.service.uservariables.hookers.BaseUserVariableProvider;
import net.datenwerke.rs.uservariables.service.uservariables.hooks.UserVariableProviderHook;
import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.security.service.eventlogger.jpa.ForceRemoveEntityEvent;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserVariableStartup {

	@Inject
	public UserVariableStartup(
			HookHandlerService hookHandler,
			EventBus eventBus,
			
			Provider<UserVariableExporter> exporterProvider,
			Provider<UserVariableImporter> importerProvider,
			Provider<ExportAllUserVariablesHooker> exportAllUserVars,
			Provider<ImportAllUserVariablesHooker> importAllUserVars,
			
			Provider<BaseUserVariableProvider> baseVariableProvider,
			
			Provider<UserVariableParameterProviderHooker> parameterProvider,
			
			HandleUserVarRemoveEvents handleRemove,
			HandleUserVarForceRemoveEvents handleForceRemove,
			
			HandleUserNodeRemoveEvents handleFolkRemove
			
		){
		
		eventBus.attachObjectEventHandler(RemoveEntityEvent.class, UserVariableDefinition.class, handleRemove);
		eventBus.attachObjectEventHandler(ForceRemoveEntityEvent.class, UserVariableDefinition.class, handleForceRemove);
		
		eventBus.attachObjectEventHandler(RemoveEntityEvent.class, AbstractUserManagerNode.class, handleFolkRemove);
		
		/* eximport */
		hookHandler.attachHooker(ExporterProviderHook.class, new ExporterProviderHook(exporterProvider));
		hookHandler.attachHooker(ImporterProviderHook.class, new ImporterProviderHook(importerProvider));
		hookHandler.attachHooker(ExportAllHook.class, exportAllUserVars);
		hookHandler.attachHooker(ImportAllHook.class, importAllUserVars);
		
		
		hookHandler.attachHooker(UserVariableProviderHook.class, baseVariableProvider);
		
		hookHandler.attachHooker(ParameterProviderHook.class, parameterProvider);
	}
}
