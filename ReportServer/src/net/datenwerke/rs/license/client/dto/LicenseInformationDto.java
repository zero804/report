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
 
 
package net.datenwerke.rs.license.client.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class LicenseInformationDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String rsVersion;
	private String serverId;
	private Date installationDate;
	private String licenseType;
	private Date expirationDate;
	private String name;
	private HashMap<String, String> additionalLicenseProperties;
	private Date licenseIssueDate;
	private Date upgradesUntil;
	private String javaVersion;
	private String vmArguments;
	private String applicationServer;
	private String osVersion;
	private String browserName;
	private String browserVersion;

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String name) {
		this.browserName = name;
	}
	
	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getJavaVersion() {
		return javaVersion;
	}

	public void setJavaVersion(String javaVersion) {
		this.javaVersion = javaVersion;
	}
	
	public String getVmArguments() {
		return vmArguments;
	}

	public void setVmArguments(String vmArguments) {
		this.vmArguments = vmArguments;
	}

	public String getApplicationServer() {
		return applicationServer;
	}

	public void setApplicationServer(String applicationServer) {
		this.applicationServer = applicationServer;
	}
	
	public LicenseInformationDto() {
	}
	
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public Date getInstallationDate() {
		return installationDate;
	}
	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}
	
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public HashMap<String, String> getAdditionalLicenseProperties() {
		return additionalLicenseProperties;
	}
	
	public void setAdditionalLicenseProperties(HashMap<String, String> additionalLicenseProperties) {
		this.additionalLicenseProperties = additionalLicenseProperties;
	}
	
	public void setLicenseIssueDate(Date licenseIssueDate) {
		this.licenseIssueDate = licenseIssueDate;
	}
	
	public Date getLicenseIssueDate() {
		return licenseIssueDate;
	}
	
	public Date getUpgradesUntil() {
		return upgradesUntil;
	}
	
	public void setUpgradesUntil(Date upgradesUntil) {
		this.upgradesUntil = upgradesUntil;
	}

	public String getRsVersion() {
		return rsVersion;
	}

	public void setRsVersion(String rsVersion) {
		this.rsVersion = rsVersion;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}
}
