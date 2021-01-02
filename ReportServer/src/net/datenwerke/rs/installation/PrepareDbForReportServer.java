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
 
 
package net.datenwerke.rs.installation;

import com.google.inject.Inject;

import net.datenwerke.gf.service.genrights.AdministrationViewSecurityTarget;
import net.datenwerke.rs.adminutils.service.su.genrights.SuSecurityTarget;
import net.datenwerke.rs.core.service.datasourcemanager.DatasourceService;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceFolder;
import net.datenwerke.rs.core.service.genrights.access.AccessRsSecurityTarget;
import net.datenwerke.rs.core.service.genrights.datasources.DatasourceManagerAdminViewSecurityTarget;
import net.datenwerke.rs.core.service.genrights.reportmanager.ReportManagerAdminViewSecurityTarget;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.ReportFolder;
import net.datenwerke.rs.dashboard.service.dashboard.DashboardManagerService;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DashboardFolder;
import net.datenwerke.rs.dashboard.service.dashboard.genrights.DashboardAdminSecurityTarget;
import net.datenwerke.rs.dashboard.service.dashboard.genrights.DashboardViewSecurityTarget;
import net.datenwerke.rs.eximport.service.genrights.ExportSecurityTarget;
import net.datenwerke.rs.eximport.service.genrights.ImportSecurityTarget;
import net.datenwerke.rs.fileserver.service.fileserver.FileServerService;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFolder;
import net.datenwerke.rs.fileserver.service.fileserver.genrights.FileServerManagerAdminViewSecurityTarget;
import net.datenwerke.rs.globalconstants.service.globalconstants.genrights.GlobalConstantsAdminViewSecurityTarget;
import net.datenwerke.rs.license.service.genrights.LicenseSecurityTarget;
import net.datenwerke.rs.remoteaccess.service.sftp.genrights.SftpSecurityTarget;
import net.datenwerke.rs.scheduler.service.scheduler.genrights.SchedulingAdminSecurityTarget;
import net.datenwerke.rs.scheduler.service.scheduler.genrights.SchedulingBasicSecurityTarget;
import net.datenwerke.rs.teamspace.service.teamspace.genrights.TeamSpaceSecurityTarget;
import net.datenwerke.rs.terminal.service.terminal.genrights.TerminalSecurityTarget;
import net.datenwerke.rs.uservariables.service.genrights.UserVariableAdminViewSecurityTarget;
import net.datenwerke.rs.utils.crypto.PasswordHasher;
import net.datenwerke.security.service.genrights.security.GenericSecurityTargetAdminViewSecurityTarget;
import net.datenwerke.security.service.genrights.usermanager.UserManagerAdminViewSecurityTarget;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.entities.Acl;
import net.datenwerke.security.service.security.entities.GenericSecurityTargetEntity;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.UserPropertiesService;
import net.datenwerke.security.service.usermanager.entities.OrganisationalUnit;
import net.datenwerke.security.service.usermanager.entities.User;

/**
 * Preps an empty database for report server. 
 *
 */
public class PrepareDbForReportServer implements DbInstallationTask {

	public static final String OU_NAME_ADMINISTRATION = "Administration";
	
	private final PasswordHasher passwordHasher;
	private final SecurityService securityService;
	private final UserManagerService userService;
	private final ReportService reportService;
	private final DatasourceService datasourceService;
	private final FileServerService fileServerService;

	private final UserPropertiesService userPropertiesService;

	private final DashboardManagerService dashboardService;
	
	@Inject
	public PrepareDbForReportServer(
		PasswordHasher passwordHasher,
		SecurityService securityService,
		UserManagerService userService,
		ReportService reportService,
		DatasourceService datasourceService,
		FileServerService fileServerService,
		UserPropertiesService userPropertiesService,
		DashboardManagerService dashboardService
		){
	
		this.passwordHasher = passwordHasher;
		this.securityService = securityService;
		this.userService = userService;
		this.reportService = reportService;
		this.datasourceService = datasourceService;
		this.fileServerService = fileServerService;
		this.userPropertiesService = userPropertiesService;
		this.dashboardService = dashboardService;
	}

	@Override
	public void executeOnFirstRun() {
		createRootUser();
		prepareReportTree();
		prepareDashboardTree();
		prepareDatasourceTree();
		prepareFileServerTree();
		prepareGenericSecurityTargets();
	}
	
	@Override
	public void executeOnStartup() {
		
	}
	
	private void prepareGenericSecurityTargets() {
		/* administration */
		GenericSecurityTargetEntity adminEntity = securityService.createGenericSecurityTargetEntity(AdministrationViewSecurityTarget.class);
		
		Acl adminACL = new Acl();
		adminEntity.setAcl(adminACL);
		securityService.merge(adminEntity);
		
		/* global constants */
		GenericSecurityTargetEntity globalConstantsEntity = securityService.createGenericSecurityTargetEntity(GlobalConstantsAdminViewSecurityTarget.class);
		
		Acl globalConstantsACL = new Acl();
		globalConstantsEntity.setAcl(globalConstantsACL);
		securityService.merge(globalConstantsEntity);	

		/* reportmanager */
		GenericSecurityTargetEntity reportManagerEntity = securityService.createGenericSecurityTargetEntity(ReportManagerAdminViewSecurityTarget.class);
		
		Acl reportManagerACL = new Acl();
		reportManagerEntity.setAcl(reportManagerACL);
		securityService.merge(reportManagerEntity);	
		
		/* scheduling */
		GenericSecurityTargetEntity schedulingAdminEntity = securityService.createGenericSecurityTargetEntity(SchedulingAdminSecurityTarget.class);
		
		Acl schedulingAdminACL = new Acl();
		schedulingAdminEntity.setAcl(schedulingAdminACL);
		securityService.merge(schedulingAdminEntity);
		
		
		GenericSecurityTargetEntity schedulingBasicViewEntity = securityService.createGenericSecurityTargetEntity(SchedulingBasicSecurityTarget.class);
		
		Acl schedulingBasicACL = new Acl();
		schedulingBasicViewEntity.setAcl(schedulingBasicACL);
		securityService.merge(schedulingBasicViewEntity);
		
		/* security */
		GenericSecurityTargetEntity genericSecurityEntity = securityService.createGenericSecurityTargetEntity(GenericSecurityTargetAdminViewSecurityTarget.class);
		
		Acl genericSecurityACL = new Acl();
		genericSecurityEntity.setAcl(genericSecurityACL);
		securityService.merge(genericSecurityEntity);	
		
		/* usermanager */
		GenericSecurityTargetEntity userManagerEntity = securityService.createGenericSecurityTargetEntity(UserManagerAdminViewSecurityTarget.class);
		
		Acl userManagerACL = new Acl();
		userManagerEntity.setAcl(userManagerACL);
		securityService.merge(userManagerEntity);
		
		/* uservariable */
		GenericSecurityTargetEntity userVariableEntity = securityService.createGenericSecurityTargetEntity(UserVariableAdminViewSecurityTarget.class);
		
		Acl userVariableACL = new Acl();
		userVariableEntity.setAcl(userVariableACL);
		securityService.merge(userVariableEntity);
		
		/* datasources */
		GenericSecurityTargetEntity datasourcesEntity = securityService.createGenericSecurityTargetEntity(DatasourceManagerAdminViewSecurityTarget.class);
		
		Acl datasourceACL = new Acl();
		datasourcesEntity.setAcl(datasourceACL);
		securityService.merge(datasourcesEntity);
		
		/* fileServer */
		GenericSecurityTargetEntity fileServerEntity = securityService.createGenericSecurityTargetEntity(FileServerManagerAdminViewSecurityTarget.class);
		
		Acl fileServerACL = new Acl();
		fileServerEntity.setAcl(fileServerACL);
		securityService.merge(fileServerEntity);
		
		/* teamspaces */
		GenericSecurityTargetEntity teamspacesEntity = securityService.createGenericSecurityTargetEntity(TeamSpaceSecurityTarget.class);
		
		Acl teamspaceACL = new Acl();
		teamspacesEntity.setAcl(teamspaceACL);
		securityService.merge(teamspacesEntity);
		
		/* export*/
		GenericSecurityTargetEntity exportEntity = securityService.createGenericSecurityTargetEntity(ExportSecurityTarget.class);
		
		Acl exportACL = new Acl();
		exportEntity.setAcl(exportACL);
		securityService.merge(exportEntity);	
		
		/* import*/
		GenericSecurityTargetEntity importEntity = securityService.createGenericSecurityTargetEntity(ImportSecurityTarget.class);
		
		Acl importACL = new Acl();
		importEntity.setAcl(importACL);
		securityService.merge(importEntity);
		
		/* su */
		GenericSecurityTargetEntity suEntity = securityService.createGenericSecurityTargetEntity(SuSecurityTarget.class);
		
		Acl suACL = new Acl();
		suEntity.setAcl(suACL);
		securityService.merge(suEntity);
		
		/* terminal */
		GenericSecurityTargetEntity terminalEntity = securityService.createGenericSecurityTargetEntity(TerminalSecurityTarget.class);
		
		Acl terminalACL = new Acl();
		terminalEntity.setAcl(terminalACL);
		securityService.merge(terminalEntity);
		
		/* license */
		GenericSecurityTargetEntity licenseEntity = securityService.createGenericSecurityTargetEntity(LicenseSecurityTarget.class);
		
		Acl licenseACL = new Acl();
		licenseEntity.setAcl(licenseACL);
		securityService.merge(licenseEntity);
		
		/* dashboard client */
		GenericSecurityTargetEntity dashboardEntity = securityService.createGenericSecurityTargetEntity(DashboardViewSecurityTarget.class);
		
		Acl dashboardACL = new Acl();
		dashboardEntity.setAcl(dashboardACL);
		securityService.merge(dashboardEntity);
		
		/* dashboard client */
		GenericSecurityTargetEntity dashboardAdminEntity = securityService.createGenericSecurityTargetEntity(DashboardAdminSecurityTarget.class);
		
		Acl dashboardAdminACL = new Acl();
		dashboardAdminEntity.setAcl(dashboardAdminACL);
		securityService.merge(dashboardAdminEntity);
		
		/* access rs client */
		GenericSecurityTargetEntity accessRsEntity = securityService.createGenericSecurityTargetEntity(AccessRsSecurityTarget.class);
		
		Acl accessRsACL = new Acl();
		accessRsEntity.setAcl(accessRsACL);
		securityService.merge(accessRsEntity);
		
		/* sftp access */
		GenericSecurityTargetEntity sftpEntity = securityService.createGenericSecurityTargetEntity(SftpSecurityTarget.class);

		Acl sftpACL= new Acl();
		sftpEntity.setAcl(sftpACL);
		securityService.merge(sftpEntity);
	}

	private void prepareDatasourceTree() {
		DatasourceFolder dsRoot = new DatasourceFolder();
		dsRoot.setName("Datasource Root"); //$NON-NLS-1$
		datasourceService.persist(dsRoot);
	}
	
	private void prepareFileServerTree() {
		FileServerFolder rootoot = new FileServerFolder();
		rootoot.setName("FileServer Root"); //$NON-NLS-1$
		fileServerService.persist(rootoot);
	}


	private void prepareReportTree() {
		/* create root folder for datasources */
		ReportFolder root = new ReportFolder();
		root.setName("Report Root"); //$NON-NLS-1$
		reportService.persist(root);
	}
	
	private void prepareDashboardTree() {
		/* create root folder for datasources */
		DashboardFolder root = new DashboardFolder();
		root.setName("Dashboard Root"); //$NON-NLS-1$
		dashboardService.persist(root);
	}

	private void createRootUser() {
		OrganisationalUnit rootFolder = new OrganisationalUnit();
		rootFolder.setName("User Root"); //$NON-NLS-1$
		
		OrganisationalUnit adminFolder = new OrganisationalUnit();
		adminFolder.setName(OU_NAME_ADMINISTRATION);
		rootFolder.addChild(adminFolder);
		userService.persist(rootFolder);
		userService.persist(adminFolder);
		
		/* create Root */
		User root = new User();
		root.setSuperUser(true);
		root.setFirstname("root"); //$NON-NLS-1$
		root.setLastname("root"); //$NON-NLS-1$
		root.setUsername("root"); //$NON-NLS-1$
		root.setPassword("root", passwordHasher); //$NON-NLS-1$
		root.setEmail("nobody@datenwerke.net");
		adminFolder.addChild(root);
		userService.persist(root);
		
		/* make sure roots password has not expired yet */
		userPropertiesService.setPropertyValue(root, "bsiPasswordPolicy:lastChangedPassword", Long.toString(System.currentTimeMillis()));
		userService.merge(root);
	}

}
