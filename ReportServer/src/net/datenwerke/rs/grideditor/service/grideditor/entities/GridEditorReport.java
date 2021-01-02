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
 
 
package net.datenwerke.rs.grideditor.service.grideditor.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gf.base.service.annotations.Indexed;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.grideditor.service.grideditor.locale.GridEditorMessages;
import net.datenwerke.rs.utils.instancedescription.annotations.InstanceDescription;
import net.datenwerke.treedb.service.treedb.annotation.TreeDBAllowedChildren;

import org.hibernate.envers.Audited;

import com.google.inject.Injector;

@Entity
@Table(name="GRID_EDT_REPORT")
@Audited
@Indexed
@TreeDBAllowedChildren({
	GridEditorReportVariant.class
})
@GenerateDto(
	dtoPackage="net.datenwerke.rs.grideditor.client.grideditor.dto",
	createDecorator=true,
	typeDescriptionMsg=net.datenwerke.rs.grideditor.client.grideditor.locale.GridEditorMessages.class,
	typeDescriptionKey="reportTypeName"
)
@InstanceDescription(
	msgLocation=GridEditorMessages.class,
	objNameKey="reportTypeName",
	icon = "file"
)
public class GridEditorReport extends Report {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -791479978558860812L;
	
	@ExposeToClient
	@ManyToOne
	private FileServerFile script;
	
	@ExposeToClient
	private String arguments;
	
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	public String getArguments() {
		return arguments;
	}

	public void setScript(FileServerFile script) {
		this.script = script;
	}

	public FileServerFile getScript() {
		return script;
	}

	@Override
	protected Report createVariant(Report report) {
		if(! (report instanceof GridEditorReport))
			throw new IllegalArgumentException("Expected GridEditorReport");
		
		GridEditorReportVariant variant = new GridEditorReportVariant();
		
		/* copy parameter instances */
		initVariant(variant, report);
		
		return variant;
		
	}

	@Override
	public void replaceWith(Report aReport, Injector injector) {
		if(! (aReport instanceof GridEditorReport))
			throw new IllegalArgumentException("Expected GridEditorReport");
		
		super.replaceWith(aReport, injector);
		
		GridEditorReport report = (GridEditorReport) aReport;
		
		/* set any fields from this report */
	}

}
