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
 
 
package net.datenwerke.rs.fileserver.service.fileserver.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.client.dtomanager.FolderDto;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.fileserver.service.fileserver.locale.FileserverMessages;
import net.datenwerke.rs.utils.instancedescription.annotations.Description;
import net.datenwerke.rs.utils.instancedescription.annotations.InstanceDescription;
import net.datenwerke.rs.utils.instancedescription.annotations.Title;
import net.datenwerke.treedb.service.treedb.annotation.TreeDBAllowedChildren;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import net.datenwerke.gf.base.service.annotations.Field;
import net.datenwerke.gf.base.service.annotations.Indexed;

@Entity
@Table(name="FILE_SERVER_FOLDER")
@Audited
@Indexed
@TreeDBAllowedChildren({
	FileServerFolder.class,
	FileServerFile.class
})
@GenerateDto(
	dtoPackage="net.datenwerke.rs.fileserver.client.fileserver.dto",
	dtoImplementInterfaces=FolderDto.class,	
	typeDescriptionMsg=BaseMessages.class, typeDescriptionKey="folder",
	icon="folder"
)
@InstanceDescription(
	msgLocation=FileserverMessages.class,
	objNameKey="folderTypeName",
	icon = "folder"
)
public class FileServerFolder extends AbstractFileServerNode {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5246206383961083936L;

	@ExposeToClient(
		view=DtoView.MINIMAL,
		displayTitle=true
	)
	@Column(length = 128)
	@Field
	@Title
	private String name;

	@ExposeToClient(view=DtoView.MINIMAL)
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	@Field
	@Description
    private String description;
	
	@ExposeToClient
	private boolean publiclyAccessible = false;
	
	public FileServerFolder() {
		super();
	}
	
    public FileServerFolder(String name) {
    	super();
    	this.name = name;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setPubliclyAccessible(boolean publiclyAccessible) {
		this.publiclyAccessible = publiclyAccessible;
	}

	public boolean isPubliclyAccessible() {
		return publiclyAccessible;
	}
	
	@Override
	public boolean isFolder() {
		return true;
	}

	public FileServerFolder getSubfolderByName(String name) {
		if(null == name)
			return null;
		
		for(FileServerFolder f : getChildrenOfType(FileServerFolder.class))
			if(name.equals(f.getName()))
				return f;
		
		return null;
	}
}
