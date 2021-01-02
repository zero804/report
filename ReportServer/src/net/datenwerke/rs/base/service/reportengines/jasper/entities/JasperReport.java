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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.base.client.reportengines.jasper.locale.JasperMessages;
import net.datenwerke.rs.base.service.reportengines.jasper.util.JasperUtilsService;
import net.datenwerke.rs.base.service.reportengines.locale.ReportEnginesMessages;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;
import net.datenwerke.rs.utils.instancedescription.annotations.InstanceDescription;
import net.datenwerke.treedb.service.treedb.annotation.TreeDBAllowedChildren;

import org.hibernate.envers.Audited;

import net.datenwerke.gf.base.service.annotations.Indexed;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * 
 *
 */
@Entity
@Table(name="JASPER_REPORT")
@Audited
@Indexed
@TreeDBAllowedChildren({
	JasperReportVariant.class
})
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.reportengines.jasper.dto",
	createDecorator=true,
	typeDescriptionMsg=JasperMessages.class,
	typeDescriptionKey="reportTypeName",
	icon="jpg"
)
@InstanceDescription(
	msgLocation=ReportEnginesMessages.class,
	objNameKey="jasperReportTypeName",	
	icon = "file"
)
public class JasperReport extends Report {
    
	@Inject
	private static JasperUtilsService jasperUtilsService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8956063000457003892L;

	@EnclosedEntity
	@ExposeToClient
	@OneToOne(cascade={CascadeType.ALL})
	private JasperReportJRXMLFile masterFile;

	@JoinTable(name="JASPER_REPORT_2_SUB_JRXML")
	@EnclosedEntity
	@ExposeToClient
	@OneToMany(cascade=CascadeType.ALL)
	private List<JasperReportJRXMLFile> subFiles = new ArrayList<JasperReportJRXMLFile>();
	
	public void setMasterFile(JasperReportJRXMLFile masterFile) {
		this.masterFile = masterFile;
	}

	public JasperReportJRXMLFile getMasterFile() {
		return masterFile;
	}

	public List<JasperReportJRXMLFile> getSubFiles() {
		return subFiles;
	}

	public void setSubFiles(List<JasperReportJRXMLFile> subFiles) {
		this.subFiles = subFiles;
	}
	
	public void addSubfile(JasperReportJRXMLFile subFile){
		this.subFiles.add(subFile);
	}

	@Override
	protected Report createVariant(Report report) {
		if(! (report instanceof JasperReport))
			throw new IllegalArgumentException("Expected JasperReport"); //$NON-NLS-1$
		
		JasperReportVariant variant = new JasperReportVariant();
		
		/* copy parameter instances */
		initVariant(variant, report);
		
		return variant;
	}
	
	@Override
	public void replaceWith(Report aReport, Injector injector){
		if(! (aReport instanceof JasperReport))
			throw new IllegalArgumentException("Expected JasperReport"); //$NON-NLS-1$
		super.replaceWith(aReport, injector);
		
		JasperReport report = (JasperReport) aReport;
		
		/* remove stuff */
		if(null != masterFile)
			jasperUtilsService.removeJRXMLFile(masterFile);
		for(JasperReportJRXMLFile file : new ArrayList<JasperReportJRXMLFile>(subFiles))
			jasperUtilsService.removeJRXMLFile(file);
		
		
		setMasterFile(report.getMasterFile());
		for(JasperReportJRXMLFile subfile : report.getSubFiles())
			addSubfile(subfile);
	}
	

}
