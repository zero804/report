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
 
 
package org.saiku.datasources.connection;

import java.util.UUID;

/**
 * Created by bugg on 23/06/14.
 */
public class RepositoryFile {

    private String path = null;
    private String fileName = null;
    private String fileId = null;
    private byte[] data;

    private RepositoryFile() {
    }

    public RepositoryFile(String fileName, RepositoryFile parent, byte[] data) {
        this(fileName, parent, data, System.currentTimeMillis());
    }

    public RepositoryFile(String fileName, RepositoryFile parent, byte[] data, String path) {
        this(fileName, parent, data, System.currentTimeMillis());
        this.path = path;
    }

    private RepositoryFile(String fileName, RepositoryFile parent, byte[] data, long lastModified) {
        this();
        this.fileId = UUID.randomUUID().toString();

        this.fileName = fileName;

        setData(data);
    }

    private void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return this.data;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return this.fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public boolean isFolder() {
        return false;
    }

    public String getPath() {
        return this.path;
    }
}
