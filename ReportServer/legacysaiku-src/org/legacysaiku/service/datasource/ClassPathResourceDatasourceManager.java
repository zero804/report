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
package org.legacysaiku.service.datasource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;
import org.legacysaiku.datasources.datasource.SaikuDatasource;
import org.legacysaiku.datasources.datasource.SaikuDatasource.Type;
import org.legacysaiku.service.util.exception.SaikuServiceException;

public class ClassPathResourceDatasourceManager implements IDatasourceManager {

	private URL repoURL;

	private Map<String,SaikuDatasource> datasources = Collections.synchronizedMap(new HashMap<String,SaikuDatasource>());

	public ClassPathResourceDatasourceManager() {

	}


	public ClassPathResourceDatasourceManager(String path) {
		try {
			setPath(path);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPath(String path) {

		FileSystemManager fileSystemManager;
		try {
			fileSystemManager = VFS.getManager();

			FileObject fileObject;
			fileObject = fileSystemManager.resolveFile(path);
			if (fileObject == null) {
				throw new IOException("File cannot be resolved: " + path);
			}
			if(!fileObject.exists()) {
				throw new IOException("File does not exist: " + path);
			}
			repoURL = fileObject.getURL();
			if (repoURL == null) {
				throw new Exception("Cannot load connection repository from path: "+path);
			} else {
				load();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void load() {
		datasources.clear();
		try {
			if (repoURL != null) {
				File[] files =  new File(repoURL.getFile()).listFiles();

				for (File file : files) {
					if (!file.isHidden()) {
						Properties props = new Properties();
						props.load(new FileInputStream(file));
						String name = props.getProperty("name");
						String type = props.getProperty("type");
						if (name != null && type != null) {
							Type t = SaikuDatasource.Type.valueOf(type.toUpperCase());
							SaikuDatasource ds = new SaikuDatasource(name,t,props);
							datasources.put(name, ds);
						}
					}
				}
			}
			else {
				throw new Exception("repo URL is null");
			}
		} catch (Exception e) {
			throw new SaikuServiceException(e.getMessage(),e);
		}		
	}

	public SaikuDatasource addDatasource(SaikuDatasource datasource) {
		try { 
			String uri = repoURL.toURI().toString();
			if (uri != null && datasource != null) {
				uri += datasource.getName().replace(" ", "_");
				File dsFile = new File(new URI(uri));
				if (dsFile.exists()) {
					dsFile.delete();
				}
				else {
					dsFile.createNewFile();
				}
				FileWriter fw = new FileWriter(dsFile);
				Properties props = datasource.getProperties();
				props.store(fw, null);
				fw.close();
				datasources.put(datasource.getName(), datasource);
				return datasource;

			}
			else {
				throw new SaikuServiceException("Cannot save datasource because uri or datasource is null uri(" 
						+ (uri == null) + ")" );
			}
		}
		catch (Exception e) {
			throw new SaikuServiceException("Error saving datasource",e);
		}
	}

	public SaikuDatasource setDatasource(SaikuDatasource datasource) {
		return addDatasource(datasource);
	}

	public List<SaikuDatasource> addDatasources(List<SaikuDatasource> datasources) {
		for (SaikuDatasource ds : datasources) {
			addDatasource(ds);
		}
		return datasources;
	}

	public boolean removeDatasource(String datasourceName) {
		try {
			String uri = repoURL.toURI().toString();
			if (uri != null) {
				// seems like we don't have to do this anymore
				//uri.toString().endsWith(String.valueOf(File.separatorChar))) {
				uri += datasourceName;
				File dsFile = new File(new URI(uri));
				if (dsFile.delete()) {
					datasources.remove(datasourceName);
					return true;
				}
			}
			throw new Exception("Cannot delete datasource file uri:" + uri);
		}
		catch(Exception e){
			throw new SaikuServiceException("Cannot delete datasource",e);
		}
	}

	public Map<String,SaikuDatasource> getDatasources() {
		return datasources;
	}

	public SaikuDatasource getDatasource(String datasourceName) {
		return datasources.get(datasourceName);
	}
}
