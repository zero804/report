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
 
 
package net.datenwerke.rs.scheduleasfile.service.scheduleasfile;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.StorageType;
import net.datenwerke.rs.utils.config.ConfigService;

public class FtpServiceImpl implements FtpService {

	public static final String CONFIG_FILE = "exportfilemd/storage.cf";

	private static final String PROPERTY_FTP_HOST = "ftp.host";
	private static final String PROPERTY_FTP_PORT = "ftp.port";
	private static final String PROPERTY_FTP_USERNAME = "ftp.username";
	private static final String PROPERTY_FTP_PASSWORD = "ftp.password";
	private static final String PROPERTY_FTP_DEFAULT_FOLDER = "ftp.defaultFolder";
	private static final String PROPERTY_FTP_DISABLED = "ftp[@disabled]";
	private static final String PROPERTY_FTP_SCHEDULER_ENABLED = "ftp[@supportsScheduling]";

	private final ConfigService configService;

	@Inject
	public FtpServiceImpl(ConfigService configService) {
		this.configService = configService;
	}

	@Override
	public void sendToFtpServer(Object report, String filename, String folder) throws IOException, SocketException {

		String host = configService.getConfigFailsafe(CONFIG_FILE).getString(PROPERTY_FTP_HOST, null);
		Integer port = configService.getConfigFailsafe(CONFIG_FILE).getInteger(PROPERTY_FTP_PORT, 21);
		String username = configService.getConfigFailsafe(CONFIG_FILE).getString(PROPERTY_FTP_USERNAME, null);
		String password = configService.getConfigFailsafe(CONFIG_FILE).getString(PROPERTY_FTP_PASSWORD, null);

		if (null == host || host.trim().contentEquals("") || null == username || username.trim().contentEquals(""))
			throw new IllegalArgumentException("FTP server is not configured correctly");

		int reply;

		FTPClient ftpClient = new FTPClient();
		BufferedInputStream in = null;

		try {
			ftpClient.connect(host, port);

//			System.out.print(ftpClient.getReplyString());
			reply = ftpClient.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				throw new IOException("FTP server refused connection");
			}

			boolean login = ftpClient.login(username, password);
			if (!login)
				throw new IOException("Cannot login to FTP server");

			ftpClient.enterLocalPassiveMode();

			boolean fileType = ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if (!fileType)
				throw new IOException("Cannot set filetype to binary in FTP connection");

			boolean changeDir = ftpClient.changeWorkingDirectory(folder);
			if (!changeDir)
				throw new IOException("Cannot change FTP directory to " + folder);

			ByteArrayInputStream bis = null;
			if (report instanceof String) {
				bis = new ByteArrayInputStream(((String) report).getBytes(StandardCharsets.UTF_8));
			} else if (report instanceof byte[]) {
				bis = new ByteArrayInputStream(((byte[]) report));
			} else {
				throw new IllegalArgumentException("Report type not supported");
			}

			in = new BufferedInputStream(bis);
			
			boolean store = ftpClient.storeFile(filename, in);
			if (!store)
				throw new IOException("Cannot store report in FTP server");

			boolean logout = ftpClient.logout();

			if (!logout)
				throw new IOException("Cannot logout from FTP server");

		} finally {
			if (ftpClient.isConnected())
				ftpClient.disconnect();
			
			if(null != in)
				IOUtils.closeQuietly(in);
		}

	}

	@Override
	public String getFtpDefaultFolder() {
		return configService.getConfigFailsafe(CONFIG_FILE).getString(PROPERTY_FTP_DEFAULT_FOLDER,"./");
	}

	@Override
	public boolean isFtpEnabled() {
		return ! configService.getConfigFailsafe(CONFIG_FILE).getBoolean(PROPERTY_FTP_DISABLED, false);
	}
	
	@Override
	public boolean isFtpSchedulingEnabled() {
		return configService.getConfigFailsafe(CONFIG_FILE).getBoolean(PROPERTY_FTP_SCHEDULER_ENABLED, false);
	}

	@Override
	public Map<StorageType,Boolean> getEnabledConfigs() {
		Map<StorageType,Boolean> configs = new HashMap<>();
		configs.put(StorageType.FTP, ! configService.getConfigFailsafe(CONFIG_FILE).getBoolean(PROPERTY_FTP_DISABLED, false) );
		configs.put(StorageType.FTP_SCHEDULING, configService.getConfigFailsafe(CONFIG_FILE).getBoolean(PROPERTY_FTP_SCHEDULER_ENABLED, true) );
		return configs;
	}

}
