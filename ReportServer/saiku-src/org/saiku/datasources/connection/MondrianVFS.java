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

import java.util.Collection;
import org.apache.commons.vfs.FileName;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemConfigBuilder;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.impl.DefaultFileSystemManager;
import org.apache.commons.vfs.provider.FileProvider;
import org.saiku.service.datasource.IDatasourceManager;

public class MondrianVFS
        implements FileProvider
{
    private IDatasourceManager datasourceManager;

    public void setDatasourceManager(IDatasourceManager dms)
    {
        this.datasourceManager = dms;
    }

    public void init()
    {
        try
        {
            DefaultFileSystemManager dfsm = (DefaultFileSystemManager)VFS.getManager();
            if (!dfsm.hasProvider("mondrian")) {
                dfsm.addProvider("mondrian", this);
            }
        }
        catch (FileSystemException e) {}
    }

    public FileObject findFile(FileObject fileObject, String catalog, FileSystemOptions fileSystemOptions)
            throws FileSystemException
    {
        return new RepositoryVfsFileObject(catalog, this.datasourceManager);
    }

    public FileObject createFileSystem(String s, FileObject fileObject, FileSystemOptions fileSystemOptions)
            throws FileSystemException
    {
        return null;
    }

    public FileSystemConfigBuilder getConfigBuilder()
    {
        return null;
    }

    public Collection getCapabilities()
    {
        return null;
    }

    public FileName parseUri(FileName fileName, String s)
            throws FileSystemException
    {
        return null;
    }
}