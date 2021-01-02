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
 
 
package org.saiku.service.util.security.authentication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileBackedPasswordProvider implements PasswordProvider
{
    private final String password;

    public FileBackedPasswordProvider(File passwordPropertyFile, String passwordKey)
    {
        this.password = loadPasswordFromFile(passwordPropertyFile, passwordKey);
    }

    public String getPassword()
    {
        return password;
    }

    private String loadPasswordFromFile(File passwordPropertyFile, String passwordKey)
    {
        final Properties properties = new Properties();
        InputStream inputStream = null;

        try
        {
            inputStream = new FileInputStream(passwordPropertyFile);
            properties.load(inputStream);

            String password = properties.getProperty(passwordKey);

            if(password == null)
            {
                throw new RuntimeException("Did not find password key '" + passwordKey + "' in file " + passwordPropertyFile.getAbsolutePath());
            }

            return password;
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to load password from file: " + passwordPropertyFile.getAbsolutePath(), e);
        }
        finally
        {
            closeInputStream(inputStream);
        }
    }

    private void closeInputStream(InputStream is)
    {
        try
        {
            if(is != null)
            {
                is.close();
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to close input stream", e);
        }
    }
}
