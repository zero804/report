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
 
 
/*
 * Copyright 2014 OSBI Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.saiku.repository;

/**
 * Created by bugg on 10/12/14.
 */
//public class SaikuDavResourceImpl extends DefaultHandler {

public class SaikuDavResourceImpl {
// 
//  public SaikuDavResourceImpl() {
//  }
//
//  public SaikuDavResourceImpl(IOManager ioManager) {
//    super(ioManager);
//  }
//
//  public SaikuDavResourceImpl(IOManager ioManager, String collectionNodetype,
//                              String defaultNodetype, String contentNodetype) {
//    super(ioManager, collectionNodetype, defaultNodetype, contentNodetype);
//  }
//
//  @Override
//  public boolean importContent(ImportContext context, boolean isCollection) throws IOException {
//    if (!canImport(context, isCollection)) {
//      throw new IOException(getName() + ": Cannot import " + context.getSystemId());
//    }
//
//    boolean success = false;
//    try {
//
//      Node contentNode = getContentNode(context, isCollection);
//      String ext = FilenameUtils.getExtension(context.getSystemId());
//      if(ext.equals("saiku")){
//        contentNode.getParent().addMixin("nt:saikufiles");
//      }
//      else if(ext.equals("xml")){
//        contentNode.getParent().addMixin("nt:mondrianschema");
//      }
//      else if(ext.equals("sds")){
//        contentNode.getParent().addMixin("nt:olapdatasource");
//      }
//      else if(isCollection){
//        contentNode.getParent().addMixin("nt:saikufolders");
//      }
//
//      //contentNode.addNode("jcr:content", "nt:resource");
//
//      success = importData(context, isCollection, contentNode);
//      if (success) {
//        success = importProperties(context, isCollection, contentNode);
//      }
//    } catch (RepositoryException e) {
//      success = false;
//      throw new IOException(e.getMessage());
//    } finally {
//      // revert any changes made in case the import failed.
//      if (!success) {
//        try {
//          context.getImportRoot().refresh(false);
//        } catch (RepositoryException e) {
//          throw new IOException(e.getMessage());
//        }
//      }
//    }
//    return success;
//  }

}
