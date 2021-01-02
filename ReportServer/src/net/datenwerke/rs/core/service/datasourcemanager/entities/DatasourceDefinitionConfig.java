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
 
 
package net.datenwerke.rs.core.service.datasourcemanager.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.envers.Audited;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.utils.entitycloner.annotation.TransientID;

/**
 * 
 *
 */
@Entity
@Audited
@Table(name="DATASOURCE_DEF_CONFIG")
@Inheritance(strategy=InheritanceType.JOINED)
@GenerateDto(
	dtoPackage="net.datenwerke.rs.core.client.datasourcemanager.dto"
)
public class DatasourceDefinitionConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2818989299688753011L;

	@Version
	private Long version;
	
	@ExposeToClient(id=true)
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Transient @TransientID
	private Long oldTransientId;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
    public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	/* a second implementation of equals that should not go via the id, but the actual content */
	public boolean contentEquals(DatasourceDefinition definition, DatasourceDefinitionConfig config) {
		return equals(config);
	}
    
	
	/**
	 * Tests on equality of id field.
	 */
	@Override
    public boolean equals(Object obj) {
    	/* returns true if objects have the same id */
    	if(! (obj instanceof DatasourceDefinitionConfig))
    		return false;
    	
    	/* cast object */
    	DatasourceDefinitionConfig config = (DatasourceDefinitionConfig) obj;
    	
    	/* test id */
    	if(null == getId() && null != config.getId())
    		return false;
    	if(null != getId() && ! getId().equals(config.getId()))
    		return false;
    	
    	return true;
    }
    
    @Override
    public int hashCode() {
    	if(null != getId())
    		return getId().hashCode();
    	
    	return super.hashCode();
    }

	public void setOldTransientId(Long oldTransientId) {
		this.oldTransientId = oldTransientId;
	}

	public Long getOldTransientId() {
		return oldTransientId;
	}

}
