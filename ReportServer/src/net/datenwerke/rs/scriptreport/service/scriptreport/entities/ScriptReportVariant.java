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
 
 
package net.datenwerke.rs.scriptreport.service.scriptreport.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.dtoservices.dtogenerator.annotations.IgnoreMergeBackDto;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.core.service.reportmanager.entities.AbstractReportManagerNode;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.interfaces.ReportVariant;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.utils.entitycloner.annotation.ClonePostProcessor;

import org.apache.commons.lang.NotImplementedException;
import org.hibernate.envers.Audited;
import org.hibernate.proxy.HibernateProxy;
import net.datenwerke.gf.base.service.annotations.Indexed;

@Entity
@Table(name="SCRIPT_REPORT_VARIANT")
@Audited
@Indexed
@GenerateDto(
	dtoPackage="net.datenwerke.rs.scriptreport.client.scriptreport.dto", 
	createDecorator=true
)
public class ScriptReportVariant extends ScriptReport implements ReportVariant {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8689438037587625879L;

	public ScriptReport getBaseReport() {
		AbstractReportManagerNode parent = getParent();
		if(parent instanceof HibernateProxy)
			parent = (AbstractReportManagerNode) ((HibernateProxy)parent).getHibernateLazyInitializer().getImplementation();
		return (ScriptReport) parent;
	}

	public void setBaseReport(Report baseReport) {
		throw new IllegalStateException("should not be called on server");
	}

	@Override
	public DatasourceContainer getDatasourceContainer() {
		return getBaseReport().getDatasourceContainer();
	}
	
	@IgnoreMergeBackDto
	@Override
	public void setScript(FileServerFile script) {
		throw new NotImplementedException();
	}
	
	@IgnoreMergeBackDto
	@Override
	public void setArguments(String arguments) {
		throw new NotImplementedException();
	}
	
	@IgnoreMergeBackDto
	@Override
	public void setExportFormats(List<String> exportFormats) {
		throw new NotImplementedException();
	}
	
	@Override
	public FileServerFile getScript() {
		return getBaseReport().getScript();
	}
	
	@Override
	public String getArguments() {
		return getBaseReport().getArguments();
	}
	
	@Override
	public List<String> getExportFormats() {
		return getBaseReport().getExportFormats();
	}
	
	@IgnoreMergeBackDto
	@Override
	public void setDatasourceContainer(DatasourceContainer datasource){
		throw new NotImplementedException();
	}
	
	@Override
	public List<ParameterDefinition> getParameterDefinitions() {
		return getBaseReport().getParameterDefinitions();
	}
	
	@IgnoreMergeBackDto
	@Override
	public void setParameterDefinitions( List<ParameterDefinition> parameters) {
		throw new NotImplementedException();
	}
	
	/**
	 */
	@ClonePostProcessor
	public void guideCloningProcess(Object report){
		super.setParameterDefinitions(null);
		super.setDatasourceContainer(null);
		super.setScript(null);
		super.setArguments(null);
		super.setExportFormats(null);
	}
	
	@Override
	public boolean hasChildren() {
		return false;
	}
}
