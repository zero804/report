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
 
 
package net.datenwerke.rs.utils.mockrequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class MockServletResponse implements HttpServletResponse {

	@Override
	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetBuffer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBufferSize(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCharacterEncoding(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentLength(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentType(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLocale(Locale arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addCookie(Cookie arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDateHeader(String arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addHeader(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addIntHeader(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsHeader(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String encodeRedirectURL(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encodeRedirectUrl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encodeURL(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encodeUrl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendError(int arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendError(int arg0, String arg1) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendRedirect(String arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDateHeader(String arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHeader(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIntHeader(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatus(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatus(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getHeader(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getHeaderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getHeaders(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setContentLengthLong(long arg0) {
		// TODO Auto-generated method stub
		
	}

}
