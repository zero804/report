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
 
 
package net.datenwerke.rs.configservice.service.configservice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Provider;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.configservice.service.configservice.hooks.ConfigStoreHook;
import net.datenwerke.rs.configservice.service.configservice.hooks.ReloadConfigNotificationHook;
import net.datenwerke.rs.fileserver.service.fileserver.FileServerService;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.terminal.service.terminal.TerminalService;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.rs.utils.config.ConfigFileNotFoundException;
import net.datenwerke.rs.utils.config.ConfigService;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.json.JSONException;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ConfigServiceImpl implements ConfigService{

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private final Map<String, Configuration> cache = new ConcurrentHashMap<String, Configuration>(); 
	
	private final FileServerService fileService;
	private final TerminalService terminalService;
	private final HookHandlerService hookHandler;
	
	@Inject
	public ConfigServiceImpl(
		FileServerService fileService,	
		TerminalService terminalService,
		HookHandlerService hookHandler
		) {

		/* store objects */
		this.fileService = fileService;
		this.terminalService = terminalService;
		this.hookHandler = hookHandler;
	}

	
	public Provider<Configuration> getConfigProvider(final String identifier){
		return new Provider<Configuration>() {
			@Override
			public Configuration get() {
				return getConfig(identifier);
			}
		};
	}
	
	
	@Override 
	public Configuration getConfigFailsafe(String identifier){
		try{
			 Configuration config = getConfig(identifier);
			 
			 if(null == config)
				 return new BaseConfiguration();
			 
			 return config;
		}catch(Exception e){
			logger.warn( "Configfile " + identifier + " could not be loaded. Default values are in effect.");
			return new BaseConfiguration();
		}
	}
		
	@Override
	public Configuration getConfig(String identifier) {
		return getConfig(identifier, true);
	}
	
	@Override
	public String getConfigAsJson(String identifier) {
		try{
			Object object = terminalService.getObjectByLocation("/fileserver/etc/" + identifier, false);
			if(null == object || ! (object instanceof FileServerFile))
				throw new ConfigFileNotFoundException("Could not find config for " + identifier);
			
			FileServerFile file = (FileServerFile) object;
			
			byte[] data = file.getData();
			if(null == data)
				throw new ConfigFileNotFoundException("config file is empty " + identifier);
			
			return XML.toJSONObject(new String(data)).toString();
		} catch (ObjectResolverException e) {
			throw new ConfigFileNotFoundException("Could not find config for " + identifier, e);
		} catch (JSONException e) {
			throw new ConfigFileNotFoundException("Could not parse config for " + identifier + " to json", e);
		} 
	}
	
	@Override
	public String getConfigAsJsonFailsafe(String identifier) {
		try{
			Object object = terminalService.getObjectByLocation("/fileserver/etc/" + identifier, false);
			if(null == object || ! (object instanceof FileServerFile))
				throw new ConfigFileNotFoundException("Could not find config for " + identifier);
			
			FileServerFile file = (FileServerFile) object;
			
			byte[] data = file.getData();
			if(null == data)
				throw new ConfigFileNotFoundException("config file is empty " + identifier);
			
			return XML.toJSONObject(new String(data)).toString();
		} catch (Exception e) {
			logger.warn( "Configfile " + identifier + " could not be loaded. Default values are in effect.");
			return "";
		}
	}

	@Override
	public Configuration getConfig(String identifier, boolean useCache) {
		try {
			if(useCache && cache.containsKey(identifier))
				return cache.get(identifier);
			
			/* try to load config from configstores */
			HierarchicalConfiguration config = null;
			for(ConfigStoreHook confstore : hookHandler.getHookers(ConfigStoreHook.class)){
				config = confstore.getObject().loadConfig(identifier);
				if(null != config)
					break;
			}
			if(null == config)
				throw new ConfigFileNotFoundException("Could not find config for " + identifier);
				
			cache.put(identifier, config);
			
			return config;
		} catch (ConfigurationException e) {
			throw new IllegalArgumentException("Failed processing config from " + identifier + ": " + e.getMessage(), e);
		}
	}

	@Override
	public Configuration newConfig() {
		 XMLConfiguration config = new XMLConfiguration();
		 config.setDelimiterParsingDisabled(true);
		 
		 return config;
	}
	
	@Override
	public void storeConfig(Configuration config, String identifier) {
		if(! (config instanceof XMLConfiguration))
			throw new IllegalArgumentException();
		try {
			Object object = null;
			try{
				object = terminalService.getObjectByLocation("/fileserver/etc/" + identifier, false);
			} catch (ObjectResolverException e) {}
			if(null == object){
				object = fileService.createFileAtLocation("/fileserver/etc/" + identifier, false);
			} else if(null != object && ! (object instanceof FileServerFile))
				throw new IllegalArgumentException("Could not find config for " + identifier);
			
			FileServerFile file = (FileServerFile) object;
			file.setContentType("application/xml");
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			((XMLConfiguration)config).save(out);
			file.setData(out.toByteArray());
			fileService.merge(file);
			
			/* remove from cache */
			if(cache.containsKey(identifier))
				cache.remove(identifier);
			
			for(ReloadConfigNotificationHook hooker : hookHandler.getHookers(ReloadConfigNotificationHook.class))
				hooker.reloadConfig(identifier);
		} catch (ConfigurationException e) {
			throw new IllegalArgumentException("Could not find config for " + identifier, e);
		}
	}

	@Override
	public void clearCache(String identifier) {
		cache.remove(identifier);
		
		for(ReloadConfigNotificationHook hooker : hookHandler.getHookers(ReloadConfigNotificationHook.class))
			hooker.reloadConfig(identifier);
	}
	
	@Override
	public void clearCache() {
		cache.clear();
		
		for(ReloadConfigNotificationHook hooker : hookHandler.getHookers(ReloadConfigNotificationHook.class))
			hooker.reloadConfig();
	}
}
