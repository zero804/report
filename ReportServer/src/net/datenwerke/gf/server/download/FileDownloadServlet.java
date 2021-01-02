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
 
 
package net.datenwerke.gf.server.download;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.datenwerke.gf.client.download.FileDownloadUiModule;
import net.datenwerke.gf.service.download.FileDownloadService;
import net.datenwerke.security.server.SecuredHttpServlet;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class FileDownloadServlet extends SecuredHttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7745426896682214945L;
	
	private final Provider<FileDownloadService> fileDownloadServiceProvider;

	@Inject
	public FileDownloadServlet(
			Provider<FileDownloadService> fileDownloadServiceProvider
			){
				this.fileDownloadServiceProvider = fileDownloadServiceProvider;

	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
		/* grep handler and id */
		String id = request.getParameter("id");
		String handler = request.getParameter("handler");
		
		/* get metadata */
		Map<String,String> metadata = new HashMap<String, String>();
		
		Enumeration names = request.getParameterNames();
		while(names.hasMoreElements()){
			Object el = names.nextElement();
			if(el instanceof String){
				String name = (String) el;
				
				if(name.startsWith(FileDownloadUiModule.META_FIELD_PREFIX) && name.length() > FileDownloadUiModule.META_FIELD_PREFIX.length()){
					String metaname = name.substring(FileDownloadUiModule.META_FIELD_PREFIX.length());
					String value = request.getParameter(name);
					
					metadata.put(metaname,value);
				}
				
			}
		}
		
		FileDownloadService fileDownloadService = fileDownloadServiceProvider.get();
		
		fileDownloadService.processDownload(id, handler, metadata, response);
	}
}
