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
 
 
package net.datenwerke.rs.scheduleasfile.service.scheduleasfile.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.AdditionalField;
import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.compiledreportstore.entities.PersistentCompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.scheduleasfile.service.scheduleasfile.entities.post.ExecuteReportFileReference2DtoPost;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskGeneralReference;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import net.datenwerke.gf.base.service.annotations.Indexed;

@Entity
@Table(name="EXEC_REPORT_AS_FILE_REF")
@Audited
@Indexed
@GenerateDto(
	dtoPackage="net.datenwerke.rs.scheduleasfile.client.scheduleasfile.dto",
	createDecorator=true,
	poso2DtoPostProcessors=ExecuteReportFileReference2DtoPost.class,
	additionalFields ={
		@AdditionalField(name="iconStr", type=String.class),
		@AdditionalField(name="typeStr", type=String.class),
	}
)
public class ExecutedReportFileReference extends TsDiskGeneralReference {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7405420338212859521L;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@EnclosedEntity
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private PersistentCompiledReport compiledReport;
	
	@ExposeToClient
	private String outputFormat;

	public void setCompiledReport(PersistentCompiledReport compiledReport) {
		this.compiledReport = compiledReport;
	}

	public PersistentCompiledReport getCompiledReport() {
		return compiledReport;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	@Override
	public Date getReferenceLastUpdated() {
		return getLastUpdated();
	}

	@Override
	public boolean hasData() {
		return null != compiledReport;
	}
	
	@Override
	public byte[] getData() {
		CompiledReport report = compiledReport.getCompiledReport();
		if(null == report)
			return null;
		
		if(report.isStringReport())
			return ((String)report.getReport()).getBytes();
		Object data = report.getReport();
		if(data instanceof byte[])
			return (byte[]) data;
		return compiledReport.getSerializedReport();
	}
	
	@Override
	public long getSize() {
		CompiledReport report = compiledReport.getCompiledReport();
		if(null == report)
			return 0;
		
		if(report.isStringReport())
			return ((String)report.getReport()).getBytes().length;
		Object data = report.getReport();
		if(data instanceof byte[])
			return ((byte[])data).length;
		return compiledReport.getSerializedReport().length;
	}
	
	@Override
	public String getDataContentType() {
		CompiledReport report = compiledReport.getCompiledReport();
		if(null == report)
			return null;
		return report.getMimeType();
	}
}
