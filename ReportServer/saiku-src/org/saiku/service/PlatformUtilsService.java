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
 
 
package org.saiku.service;

import org.saiku.service.util.dto.Plugin;
import java.util.ArrayList;

/**
 * Created by bugg on 30/04/14.
 */
public class PlatformUtilsService {

  private String filePath;

  public void setPath(String path) {
    this.filePath = path;
  }

  public String getPath(){
    return filePath;
  }


  public ArrayList<Plugin> getAvailablePlugins(){
    ArrayList l = new ArrayList<>();
//    File f = new File(filePath);
//
//    String[] directories = f.list(new FilenameFilter() {
//      public boolean accept(File current, String name) {
//        return new File(current, name).isDirectory();
//      }
//    });
//
//    if(directories != null && directories.length>0) {
//      for ( String d : directories ) {
//        File subdir = new File( filePath+"/"+d );
//        File[] subfiles = subdir.listFiles();
//
//        /**
//         * TODO use a metadata.js file for alternative details.
//         */
//        if ( subfiles != null ) {
//          for ( File s : subfiles ) {
//            if ( s.getName().equals( "plugin.js" ) ) {
//              Plugin p = new Plugin( s.getParentFile().getName(), "", "js/saiku/plugins/" + s.getParentFile().getName() + "/plugin.js" );
//              l.add( p );
//            }
//          }
//        }
//      }
//    }
    
	  l.add( new Plugin( "Chart", "", "js/saiku/plugins/CCC_Chart/plugin.js" ) );
	  l.add( new Plugin( "Statistics", "", "js/saiku/plugins/Statistics/plugin.js" ) );
	  l.add( new Plugin( "ReportServer", "", "js/saiku/plugins/ReportServer/plugin.js" ) );

    
    return l;
  }
}
