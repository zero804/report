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
 
 
package net.datenwerke.rs.base.server.helpers.tempfile;

import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.datenwerke.gf.service.tempfile.TempFile;
import net.datenwerke.gf.service.tempfile.TempFileService;
import net.datenwerke.security.server.SecuredHttpServlet;
import net.datenwerke.security.service.authenticator.AuthenticatorService;

import org.apache.commons.io.FileUtils;

@Singleton
public class TempFileServlet extends SecuredHttpServlet {

	private static final long serialVersionUID = -3699726518482517393L;
	
	private final TempFileService tempFileService;

	private Provider<AuthenticatorService> authenticatorService;
	
	@Inject
	public TempFileServlet(TempFileService tempFileService, Provider<AuthenticatorService> authenticatorService) {
		this.tempFileService = tempFileService;
		this.authenticatorService = authenticatorService;

	}
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		String tempfileId = req.getParameter("id");
		TempFile tempFile = tempFileService.getTempFileById(tempfileId);
		

		if(null == tempFile){
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);	
		}

		if(!tempFile.isPermittedUser(authenticatorService.get().getCurrentUser())){
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		
		if(null != tempFile.getMimeType() && !tempFile.getMimeType().isEmpty()){
			resp.setContentType(tempFile.getMimeType());
		}
		
		OutputStream os = resp.getOutputStream();
		byte[] fileContents = FileUtils.readFileToByteArray(tempFile.getFile());
		os.write(fileContents);
		os.close();
	}

}
