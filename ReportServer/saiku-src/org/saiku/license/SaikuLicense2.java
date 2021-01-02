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
 
 
package org.saiku.license;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by bugg on 18/03/14.
 */
public class SaikuLicense2 extends AbstractLicense {

  private int memoryLimit;
  private String hostname;

  public int getUserLimit() {
    return userLimit;
  }

  public void setUserLimit(int userLimit) {
    this.userLimit = userLimit;
  }

  private int userLimit;

  private int getMemoryLimit() {
    return memoryLimit;
  }

  public void setMemoryLimit(int memoryLimit) {
    this.memoryLimit = memoryLimit;
  }


  private void validateMemory(int mem) throws LicenseException {
    if(!this.getLicenseType().equals("trial")) {
      MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
      long max = memoryBean.getHeapMemoryUsage().getMax();

      long conv = max / 1073741824;

      if (conv > mem) {
        throw new LicenseException(
            "Too much memory allocated, decrease your XMX Limit");
      }
    }
  }

  private void validateHostname(String hostname) throws LicenseException {
    try {
      String inetAddr = InetAddress.getLocalHost().getHostName();
      if(!hostname.equals(inetAddr)){
        throw new LicenseException("Hostname does not match issued license. Expected: "+InetAddress.getLocalHost()
                                                                                                   .getHostName());
      }
    } catch (UnknownHostException e) {
      e.printStackTrace();
      throw new LicenseException("Could not resolve hostname");
    }



  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  private String getHostname() {
    return hostname;
  }

  @Override
  public void validate(Date currentDate, String currentVersion)
      throws LicenseException {

    v(currentDate, currentVersion);


  }

  private void v(Date currentDate, String currentVersion) throws LicenseException{
    validateExpiration(new Date());

    validateVersion(currentVersion);

    validateMemory(getMemoryLimit());

    if(!this.getLicenseType().equals("CANONICAL_DEMO")) {
      validateHostname(getHostname());
    }
  }
}
