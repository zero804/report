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
 
 
/*  
 *   Copyright 2012 OSBI Ltd
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.saiku.olap.dto;

public class SaikuCube extends AbstractSaikuObject {


	private String connection;
	private String catalog;
	private String schema;
	private String caption;
	private boolean visible;


  public SaikuCube() {
  }

	public SaikuCube(String connectionName, String uniqueCubeName, String name, String caption, String catalog, String schema) {
		this(connectionName, uniqueCubeName, name, caption, catalog, schema, true);
	}
	
	public SaikuCube(String connectionName, String uniqueCubeName, String name, String caption, String catalog, String schema, boolean visible) {
		super(uniqueCubeName,name);		
		this.connection = connectionName;
		this.catalog = catalog;
		this.schema = schema;
		this.caption = caption;
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}

	@Override
	public String getUniqueName() {
		String uniqueName = "[" + connection + "].[" + catalog + "]";
		uniqueName += ".[" + schema + "].[" + getName() + "]";
		return uniqueName;
	}

	@Override
	public String getName() {
		return super.getName();
	}
	
	public String getCaption() {
		return caption;
	}
	
	public String getCatalog() {
		return catalog;
	}
	public String getConnection() {
		return connection;
	}

	public String getSchema() {
		return schema;
	}

}

