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
 
 
package org.saiku.repository;

/**
 * Created by bugg on 04/09/14.
 */
public final class SaikuWebdavServlet { //extends SimpleWebdavServlet {

//
//    private RepositoryDatasourceManager bean;
//  private CSRFUtil csrfUtil;
//  private UserService us;
//
//  @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//    String csrfParam = getInitParameter(INIT_PARAM_CSRF_PROTECTION);
//    csrfUtil = new CSRFUtil(csrfParam);
//        ServletContext context = getServletContext();
//
//        WebApplicationContext applicationContext =
//                WebApplicationContextUtils
//                        .getWebApplicationContext(context);
//        bean = (RepositoryDatasourceManager) applicationContext.getBean("repositoryDsManager");
//    us = (UserService) applicationContext.getBean("userServiceBean");
//    }
//
//  private boolean checkUserRole(HttpServletRequest request){
//    for(SaikuUser u: us.getUsers()) {
//      String req = request.getRemoteUser();
//      BasicCredentialsProvider b = new BasicCredentialsProvider(null);
//      SimpleCredentials creds = null;
//      try {
//        creds = (SimpleCredentials) b.getCredentials(request);
//      } catch (LoginException | ServletException e) {
//        e.printStackTrace();
//      }
//      if (u.getUsername().equals(creds.getUserID())) {
//        String[] roles = us.getRoles(u);
//        List<String> admin = us.getAdminRoles();
//
//        for (String r : roles) {
//          if (admin.contains(r)) {
//            return true;
//          }
//
//        }
//      }
//    }
//
//    return false;
//  }
//
//  private boolean checkUnsecured(HttpServletRequest request){
//    return request.getRequestURI().contains("/etc/theme");
//  }
//
//  private boolean checkSecret(HttpServletRequest request){
//    if(request.getRequestURI().contains("/datasources")){
//      return checkUserRole(request);
//    }
//    return true;
//  }
//  /**
//   * Service the given request.
//   *
//   * @param request
//   * @param response
//   * @throws ServletException
//   * @throws IOException
//   */
//  @Override
//  protected void service(HttpServletRequest request, HttpServletResponse response)
//      throws ServletException, IOException {
//
//    WebdavRequest webdavRequest = new WebdavRequestImpl(request, getLocatorFactory(), isCreateAbsoluteURI());
//    // DeltaV requires 'Cache-Control' header for all methods except 'VERSION-CONTROL' and 'REPORT'.
//    int methodCode = DavMethods.getMethodCode(request.getMethod());
//    boolean noCache = DavMethods.isDeltaVMethod(webdavRequest) && !(DavMethods.DAV_VERSION_CONTROL == methodCode || DavMethods.DAV_REPORT == methodCode);
//    WebdavResponse webdavResponse = new WebdavResponseImpl(response, noCache);
//
//    try {
//      if(checkUnsecured(request) && !getDavSessionProvider().attachSession(webdavRequest)) {
//        request.setAttribute("org.apache.jackrabbit.server.SessionProvider", new SaikuSessionProvider());
//      }
//    } catch (DavException e) {
//      if(checkUnsecured(request)) {
//        request.setAttribute("org.apache.jackrabbit.server.SessionProvider", new SaikuSessionProvider());
//      }
//    }
//
//    try {
//
//      // make sure there is a authenticated user
//      if (!getDavSessionProvider().attachSession(webdavRequest)) {
//        return;
//      }
//
//
//      if(!checkUnsecured(webdavRequest) && !checkUserRole(webdavRequest)){
//        return;
//      }
//      // perform referrer host checks if CSRF protection is enabled
//
//      if(!checkSecret(request)){
//        webdavResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
//        return;
//      }
//      if (!csrfUtil.isValidRequest(webdavRequest)) {
//        webdavResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
//        return;
//      }
//
//      // check matching if=header for lock-token relevant operations
//      DavResource resource = getResourceFactory().createResource(webdavRequest.getRequestLocator(), webdavRequest, webdavResponse);
//      if (!isPreconditionValid(webdavRequest, resource)) {
//        webdavResponse.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
//        return;
//      }
//      if (!execute(webdavRequest, webdavResponse, methodCode, resource)) {
//        super.service(request, response);
//      }
//
//    } catch (DavException e) {
//      if (e.getErrorCode() == HttpServletResponse.SC_UNAUTHORIZED) {
//        sendUnauthorized(webdavRequest, webdavResponse, e);
//      } else {
//        webdavResponse.sendError(e);
//      }
//    }
//    catch (Exception e){
//      log("Exception:", e.getCause());
//    }
//    finally {
//      getDavSessionProvider().releaseSession(webdavRequest);
//    }
//  }
//
//
//  @Override
//    public Repository getRepository() {
//
//        return (Repository) bean.getRepository();
//    }
//
//
//    @Override
//    public void doPost(WebdavRequest request,
//                       WebdavResponse response,
//                       DavResource resource)
//            throws IOException,
//            DavException{
//
////        super.doPost(request, response, resource);
//      DavResource parentResource = resource.getCollection();
//      if (parentResource == null || !parentResource.exists()) {
//        // parent does not exist
//        response.sendError(DavServletResponse.SC_CONFLICT);
//        return;
//      }
//
//      int status;
//      // test if resource already exists
//      if (resource.exists()) {
//        status = DavServletResponse.SC_NO_CONTENT;
//      } else {
//        status = DavServletResponse.SC_CREATED;
//      }
//
//      parentResource.addMember(resource, getInputContext(request, request.getInputStream()));
//      response.setStatus(status);
//
//    }
//
//    @Override
//    public void doPut(WebdavRequest request,
//                       WebdavResponse response,
//                       DavResource resource)
//            throws IOException,
//            DavException{
//
//        super.doPut(request, response, resource);
//    }
}
