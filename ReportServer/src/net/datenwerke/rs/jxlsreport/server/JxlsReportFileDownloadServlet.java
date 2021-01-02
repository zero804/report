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
 
 
package net.datenwerke.rs.jxlsreport.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.AbstractReportManagerNode;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.entities.JxlsReport;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.entities.JxlsReportFile;
import net.datenwerke.rs.utils.misc.HttpUtils;
import net.datenwerke.security.server.SecuredHttpServlet;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.treedb.actions.ReadAction;

@Singleton
public class JxlsReportFileDownloadServlet extends SecuredHttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1345346400745387435L;
	
	/**
	 * 
	 */
	private final Provider<ReportService> reportManagerProvider;
	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private final Provider<SecurityService> securityServiceProvider;
	private final Provider<HttpUtils> httpUtilsProvider;


	@Inject
	public JxlsReportFileDownloadServlet(
			Provider<ReportService> reportManagerProvider,
			Provider<AuthenticatorService> authenticatorServiceProvider,
			Provider<SecurityService> securityServiceProvider,
			Provider<HttpUtils> httpUtilsProvider
			) {
		
				this.reportManagerProvider = reportManagerProvider;
				this.authenticatorServiceProvider = authenticatorServiceProvider;
				this.securityServiceProvider = securityServiceProvider;
				this.httpUtilsProvider = httpUtilsProvider;
	}
	
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/* set utf-8 character encoding -> Ticket #691*/
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		Long reportId = Long.valueOf(request.getParameter("id")); //$NON-NLS-1$
		
		ReportService reportService = reportManagerProvider.get();
		AbstractReportManagerNode rmn = reportService.getNodeById(reportId);
		
		/* check rights */
		validateRequest(rmn);
		
		if(rmn instanceof JxlsReport){
			JxlsReport report = (JxlsReport)rmn;
			JxlsReportFile reportFile = report.getReportFile();
			
			/* set mime type */
			response.setContentType("application/xml"); //$NON-NLS-1$
			
			/* set header and encoding */
			response.setHeader(HttpUtils.CONTENT_DISPOSITION, httpUtilsProvider.get().makeContentDispositionHeader(true, reportFile.getName()));
			response.setCharacterEncoding("UTF-8"); //$NON-NLS-1$
			
			response.getOutputStream().write(reportFile.getContent());
		}
	}
	
	
	private void validateRequest(AbstractReportManagerNode report) {
		if(null == authenticatorServiceProvider.get().getCurrentUser())
			throw new ViolatedSecurityException();

		SecurityService securityService = securityServiceProvider.get();
		
		if(! securityService.checkActions(report, ReadAction.class))
			throw new ViolatedSecurityException();
		
	}
}
