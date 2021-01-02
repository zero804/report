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

import org.apache.commons.vfs.FileName;
import org.apache.commons.vfs.FileType;
import org.apache.commons.vfs.provider.AbstractFileName;

public class RepositoryFileName
        extends AbstractFileName
{
    public RepositoryFileName(String fileRef, FileType fileType)
    {
    	super("repo", fileRef, fileType);
    }

    public FileName createName(String s, FileType fileType)
    {
        return new RepositoryFileName(s, fileType);
    }

    protected void appendRootUri(StringBuffer stringBuffer, boolean b) {}

	protected void appendRootUri(StringBuffer arg0) {
		
	}

	protected FileName createName(String arg0) {
		return null;
	}
}