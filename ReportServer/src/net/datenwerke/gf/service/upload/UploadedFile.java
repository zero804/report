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
 
 
package net.datenwerke.gf.service.upload;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UploadedFile implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String fileName;
	private String contentType;
	private long length;
	private byte[] fileBytes;
	private String handler;
	private Map<String,String> metadata = new HashMap<String,String>();
	private File tmpLocation;
	private Date uploadTime = new Date();
	
	public UploadedFile(){
	}
	
	public UploadedFile(String id) {
		this.id = id;
	}
	
	public UploadedFile(UploadedFile file){
		setId(file.id);
		setFileName(file.fileName);
		setContentType(file.contentType);
		setLength(file.length);
		setFileBytes(file.fileBytes);
		setHandler(file.handler);
		setMetadata(new HashMap<String, String>(file.metadata));
		setTmpLocation(file.tmpLocation);
		setUploadTime(file.uploadTime);
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	public byte[] getFileBytes() {
		return fileBytes;
	}
	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
		if(0 == length && null != fileBytes)
			length = fileBytes.length;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
	
	public void addMetadata(String key, String value){
		this.metadata.put(key,value);
	}

	public void setTmpLocation(File file) {
		this.tmpLocation = file;
	}
	
	public File getTmpLocation() {
		return tmpLocation;
	}

	public void clearData() {
		fileBytes = null;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	
	
}