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
 
 
package net.datenwerke.gf.service.gwtstacktrace;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Modifier;

import javax.persistence.RollbackException;

import net.datenwerke.gf.service.gwtstacktrace.locale.GWTStackTraceMessages;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.NeedForcefulDeleteClientException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.NonFatalException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ViolatedSecurityExceptionDto;
import net.datenwerke.rs.utils.exception.exceptions.NeedForcefulDeleteException;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.UnexpectedException;

public class CatchStacktraceInterceptor implements MethodInterceptor{

	private final GWTStackTraceMessages messages = LocalizationServiceImpl.getMessages(GWTStackTraceMessages.class);
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		/* only public methods */
		if(! Modifier.isPublic(invocation.getMethod().getModifiers()))
			return invocation.proceed();
		
		try{
			return invocation.proceed();
		} catch(ExpectedException e){
			/* ignore and throw exception */
			throw e;
		} catch(Exception e){
			if(e instanceof UnexpectedException && e.getCause() instanceof Exception)
				e = (Exception) e.getCause();
			
			/* map exception and try to throw it*/
			e = mapException(e);

			if(e instanceof ExpectedException)
				throw e;

			if(! (e instanceof NonFatalException))
				logger.info( "Intercepted NonFatalException", e);
				
			/* any other exception is wrapped */
			ServerCallFailedException exception = null;
			if(e instanceof ServerCallFailedException){
				exception = (ServerCallFailedException) e;
				if(null == exception.getStackTraceAsString())
					exception.setStackTraceAsString(initWithStackTrace(exception));
			} else {
				exception = new ServerCallFailedException(e);
				exception.setStackTraceAsString(initWithStackTrace(e));
			}
			
			throw exception;
		}
	}

	private Exception mapException(Exception e) {
		if(e instanceof ViolatedSecurityException)
			return mapSecurityException(e);
		if(e instanceof RollbackException)
			return new ServerCallFailedException(e);
		if(e instanceof NeedForcefulDeleteException)
			return new NeedForcefulDeleteClientException(e);
		return e;
	}


	private Exception mapSecurityException(Exception e) {
		ViolatedSecurityExceptionDto securityE = new ViolatedSecurityExceptionDto(messages.securityExceptionDefaultMessage(e.getMessage()));
		securityE.initCause(e);
		return securityE;
	}

	private String initWithStackTrace(Exception e) {
		Writer result = new StringWriter();
	    PrintWriter printWriter = new PrintWriter(result);
	    e.printStackTrace(printWriter);
	    return result.toString();
	}

}
