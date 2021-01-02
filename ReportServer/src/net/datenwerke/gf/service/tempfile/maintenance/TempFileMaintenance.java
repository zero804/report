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
 
 
package net.datenwerke.gf.service.tempfile.maintenance;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Provider;
import javax.inject.Singleton;

import net.datenwerke.gf.service.maintenance.hooks.MaintenanceTask;
import net.datenwerke.gf.service.tempfile.annotations.TempDirLocation;
import net.datenwerke.gf.service.tempfile.annotations.TempFileLifeTime;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

@Singleton
public class TempFileMaintenance implements MaintenanceTask{

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private Provider<Integer> lifetime;
	private Provider<String> tempDir;

	private boolean initial = true;
	
	@Inject
	public TempFileMaintenance(
		@TempDirLocation Provider<String> tempDir,
		@TempFileLifeTime Provider<Integer> lifeSpan
		){
		this.tempDir = tempDir;
		this.lifetime = lifeSpan;
	}
	
	@Override
	public void performMaintenance() {
		Date deadline = DateUtils.addSeconds(new Date(), - lifetime.get());
		
		File dir = new File(tempDir.get());
		if(! dir.exists() && ! dir.isDirectory()){
			logger.warn( "cannot access tempdir: " + dir.getAbsolutePath());
			return;
		}
		
		if(initial){
			clearTempdir(dir);
			initial = false;
			return;
		}
		
		for(File file : dir.listFiles()){
			if(! isTempFile(file))
				continue;
			
			Date created = getCreated(file);
			
			if(null != created && created.after(deadline))
				continue;
			
			file.delete();
		}
	}

	private Date getCreated(File file) {
		try{
			String timeInMillis = file.getName().substring(7, file.getName().lastIndexOf("-"));
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(Long.valueOf(timeInMillis));
			return cal.getTime();
		} catch(Exception e){}
		return null;
	}

	private void clearTempdir(File dir) {
		for(File file : dir.listFiles()){
			if(! isTempFile(file))
				continue;
			file.delete();
		}
	}

	private boolean isTempFile(File file) {
		return file.isFile() && file.getName().startsWith("rs-tmp-") && file.getName().endsWith(".tmp");
	}

}
