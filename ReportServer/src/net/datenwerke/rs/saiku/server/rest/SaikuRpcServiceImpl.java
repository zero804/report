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
 
 
package net.datenwerke.rs.saiku.server.rest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.name.Named;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoadResultBean;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.core.service.reportmanager.ReportDtoService;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.saiku.client.datasource.dto.MondrianDatasourceDto;
import net.datenwerke.rs.saiku.client.saiku.SaikuRpcService;
import net.datenwerke.rs.saiku.client.saiku.dto.SaikuReportDto;
import net.datenwerke.rs.saiku.service.datasource.MondrianDatasource;
import net.datenwerke.rs.saiku.service.locale.SaikuMessages;
import net.datenwerke.rs.saiku.service.saiku.OlapUtilService;
import net.datenwerke.rs.saiku.service.saiku.SaikuSessionContainer;
import net.datenwerke.rs.saiku.service.saiku.entities.SaikuReport;
import net.datenwerke.rs.utils.jpa.EntityUtils;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.annotation.ArgumentVerification;
import net.datenwerke.security.service.security.annotation.RightsVerification;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Execute;
import net.datenwerke.security.service.security.rights.Read;

@Singleton
public class SaikuRpcServiceImpl extends SecuredRemoteServiceServlet implements SaikuRpcService {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private static final long serialVersionUID = -4933426943425132953L;
	
	private final Provider<SecurityService> securityService;
	private final Provider<SaikuSessionContainer> sessionContainer;
	private final ReportDtoService reportDtoService;
	private final DtoService dtoService;
	private final OlapUtilService olapService;
	private final EntityUtils entityUtils;
	
	@Inject
	public SaikuRpcServiceImpl(
			Provider<SecurityService> securityService,
			Provider<SaikuSessionContainer> sessionContainer, 
			OlapUtilService olapService,
			ReportDtoService reportDtoService,
			DtoService dtoService,
			EntityUtils entityUtils
			) {
		this.securityService = securityService;
		this.sessionContainer = sessionContainer;
		this.olapService = olapService;
		this.reportDtoService = reportDtoService;
		this.dtoService = dtoService;
		this.entityUtils = entityUtils;
	}

	@SecurityChecked
	@Override
	public void stashReport(String token, @Named("node") SaikuReportDto reportDto) throws ServerCallFailedException, ExpectedException{
		Report report = reportDtoService.getReport(reportDto);
		
		if(!securityService.get().checkRights(report, Execute.class))
			throw new ViolatedSecurityException();
		
		/* create variant */
		Report adjustedReport = (Report) dtoService.createUnmanagedPoso(reportDto);
		report = report.createTemporaryVariant(adjustedReport);
		
		if(report instanceof SaikuReport){
			/*
			* We load data we know we need before putting it into the session.
			*/
			try {
				entityUtils.deepHibernateUnproxy(report);
				sessionContainer.get().putReport(token, (SaikuReport) report);
			} catch (Exception e) {
				throw new ServerCallFailedException(e);
			}
		}else{
			throw new IllegalArgumentException("invalid report type: was: " + report.getClass() + " expected SaikuReport");
		}
	}

	@SecurityChecked(
			argumentVerification = {
				@ArgumentVerification(
					name = "node",
					isDto = true,
					verify = @RightsVerification(rights=Read.class)
				),
				@ArgumentVerification(
						name = "report",
						isDto = true,
						verify = @RightsVerification(rights={Read.class})
					)
			}
		)
	@Override
	public ListLoadResult<String> loadCubesFor(
			@Named("node") MondrianDatasourceDto datasourceDto, @Named("report") SaikuReportDto saikuReportDto)
			throws ServerCallFailedException {
		MondrianDatasource datasource = (MondrianDatasource) dtoService.loadPoso(datasourceDto);
		SaikuReport saikuReport = (SaikuReport) dtoService.loadPoso(saikuReportDto);
		try {
			List<String> cubes = olapService.getCubes(datasource, saikuReport);
			return new ListLoadResultBean<String>(cubes);
		} catch (Exception e) {
			throw new ServerCallFailedException(e);
		}
	}
	
	@Override
	public void clearCache(
			@Named("node") MondrianDatasourceDto datasourceDto) 
					throws ServerCallFailedException {
		MondrianDatasource datasource = (MondrianDatasource) dtoService.loadPoso(datasourceDto);
		String url = datasource.getUrl();
		if (url.trim().startsWith("jdbc:xmla:")) {
			throw new ExpectedException(SaikuMessages.INSTANCE.errorClearCacheXMLA());
		}
		olapService.flushCache(datasource);
		try {

		} catch (Exception e) {
			throw new ServerCallFailedException(e);
		}
	}

	
}
