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
 
 
package org.saiku.repository;

import org.saiku.repository.AclMethod;

import java.util.List;



public class RepositoryFileObject implements IRepositoryObject {

	private final Type type;
	private final String name;
	private final String id;
	private final String filetype;
	private final String path;
	private final List<AclMethod> acl;

	public RepositoryFileObject(String filename, String id, String filetype, String path, List<AclMethod> acl) {
		this.type = Type.FILE;
		this.name = filename;
		this.id = id;
		this.filetype = filetype;
		this.path = path;
		this.acl = acl;
		
	}
	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getFileType() {
		return filetype;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getId() {
		return id;
	}
	public List<AclMethod> getAcl() {
		return acl;
	}
}
