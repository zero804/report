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
 
 
package net.datenwerke.rs.birt.service.reportengine;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.datenwerke.rs.birt.service.BirtModule;
import net.datenwerke.rs.fileserver.service.fileserver.FileServerService;
import net.datenwerke.rs.fileserver.service.fileserver.entities.AbstractFileServerNode;
import net.datenwerke.rs.utils.config.ConfigService;

import org.eclipse.birt.report.model.api.IResourceLocator;
import org.eclipse.birt.report.model.api.ModuleHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class FileServerServiceResourceLocator implements IResourceLocator {
	
	private static final String CONFIG_FILE = "reportengines/reportengines.cf";

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private final FileServerService fileServerService; 
	private final Provider<HttpServletRequest> requestProvider;

	private ConfigService configService;
	
	@Inject
	public FileServerServiceResourceLocator(
			FileServerService fileServerService,
			ConfigService configService,
			Provider<HttpServletRequest> requestProvider
			) {
		
		this.fileServerService = fileServerService;
		this.configService = configService;
		this.requestProvider = requestProvider;
	}

	@Override
	public URL findResource(ModuleHandle moduleHandle, String filename, int type) {
		return findResource(filename);
	}

	@Override
	public URL findResource(ModuleHandle moduleHandle, String filename, int type, Map appContext) {
		return findResource(filename);
	}
	
	public URL findResource(String filename){
		if(filename.startsWith("http://") || filename.startsWith("https://") || filename.startsWith("rsfs://"))
			try {
				return new URL(filename);
			} catch (MalformedURLException e1) {
				logger.info( "resource locator error", e1);
			}
		
		AbstractFileServerNode basefolder = null;
		Long baseFolderId =  configService.getConfigFailsafe(CONFIG_FILE).getLong(BirtModule.BIRT_LIBRARY_BASE_FOLDER_ID_PROPERETY_NAME, null);
		if(null != baseFolderId)
			basefolder = fileServerService.getNodeById(baseFolderId);
		else {
			String baseFolderPath = configService.getConfigFailsafe(CONFIG_FILE).getString(BirtModule.BIRT_LIBRARY_BASE_FOLDER_PATH_PROPERETY_NAME, null); 
			if(null == baseFolderPath)
				throw new IllegalStateException("Could not find library base folder. Please set either property " + BirtModule.BIRT_LIBRARY_BASE_FOLDER_ID_PROPERETY_NAME + " or " + BirtModule.BIRT_LIBRARY_BASE_FOLDER_PATH_PROPERETY_NAME );
			basefolder = fileServerService.getNodeByPath(baseFolderPath);
		}
		
		if(null == basefolder){
			logger.warn("Birt library folder not found");
			return null;
		}
		
		String basePath = "";
		for(AbstractFileServerNode node: basefolder.getRootLine()){
			if(!node.isRoot())
				basePath = node.getName() + "/" + basePath;
		}
				
		basePath = "/" + basePath + basefolder.getName() + "/";
		
		AbstractFileServerNode resolvedNode = fileServerService.getNodeByPath(basePath + filename);
		if(null != resolvedNode && !resolvedNode.getRootLine().contains(basefolder))
			throw new RuntimeException("Library not in permitted location " + filename);
		
//		HttpServletRequest request = requestProvider.get();
//		String baseurl = request.getRequestURL().toString();
//		baseurl = baseurl.substring(0, baseurl.lastIndexOf("/") + 1);
		
		try {
//			URL res = new URL(baseurl + FileServerUiModule.FILE_ACCESS_SERVLET + "?" + FileServerAccessServlet.KEY_ID + "=" + String.valueOf(resolvedNode.getId()));
			URL res = new URL("rsfs://fileserver" + basePath + filename);
			return res;
		} catch (MalformedURLException e) {
			return null;
		}
	}

}
