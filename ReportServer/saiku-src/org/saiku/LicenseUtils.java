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
 
 
/* Copyright (C) OSBI Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by OSBI LTD, 2014
 */

package org.saiku;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.saiku.license.SaikuLicense;
import org.saiku.license.UserQuota;

/**
 * Created by bugg on 01/07/14.
 */
public class LicenseUtils {
  public void init() {

  }

  public LicenseUtils() {

  }

  public void setLicense(SaikuLicense lic) {

    //Stub for EE

  }

  public void setLicense(String lic) {
    //Stub for EE
  }

  public Object getLicense()
      throws IOException, ClassNotFoundException {
//
//    SaikuLicense2 sl = new SaikuLicense2();
//
//    sl.setLicenseType("Open Source License");
//    sl.setUserLimit(10000000);
//    sl.setHostname(InetAddress.getLocalHost().getHostName());
//    sl.setEmail("info@meteorite.bi");
//    String string = "January 1, 2500";
//    DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
//    Date date = null;
//    try {
//      date = format.parse(string);
//    } catch (ParseException e) {
//      e.printStackTrace();
//    }
//    sl.setExpiration(date);
//    sl.setName("Meteorite BI");
//    return sl;
	  throw new RuntimeException("Not implemented");
    }

  public void validateLicense()
      throws Exception {
   
  }

  private String getVersion() {
    Properties prop = new Properties();
    InputStream input = null;
    String version = "";
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    InputStream is = classloader.getResourceAsStream(
        "org/saiku/web/rest/resources/version.properties");
    try {

      //input = new FileInputStream("version.properties");

      // load a properties file
      prop.load(is);

      // get the property value and print it out
      version = prop.getProperty("VERSION");
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return version;
  }

  public List<UserQuota> getQuota() {
    List<UserQuota> list = new ArrayList<>();
    return list;
  }
}
