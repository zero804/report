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
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2006-2009 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.xmla;

import mondrian3.olap.MondrianException;

/**
 * An exception thrown while processing an XMLA request. The faultcode
 * corresponds to the SOAP Fault faultcode and the faultstring
 * to the SOAP Fault faultstring.
 *
 * @author <a>Richard M. Emberson</a>
 */
public class XmlaException extends MondrianException {

    public static String formatFaultCode(XmlaException xex) {
        return formatFaultCode(xex.getFaultCode(), xex.getCode());
    }
    public static String formatFaultCode(String faultCode, String code) {
        return formatFaultCode(
            XmlaConstants.SOAP_PREFIX,
            faultCode, code);
    }

    public static String formatFaultCode(
        String nsPrefix,
        String faultCode, String code)
    {
        return nsPrefix
            + ':'
            + faultCode
            + '.'
            + code;
    }
    public static String formatDetail(String msg) {
        return XmlaConstants.FAULT_FS_PREFIX + msg;
    }

    public static Throwable getRootCause(Throwable throwable) {
        Throwable t = throwable;
        while (t.getCause() != null) {
            t = t.getCause();
        }
        return t;
    }

    private final String faultCode;
    private final String code;
    private final String faultString;

    public XmlaException(
        String faultCode,
        String code,
        String faultString,
        Throwable cause)
    {
        super(faultString, cause);
        this.faultCode = faultCode;
        this.code = code;
        this.faultString = faultString;
    }

    public String getFaultCode() {
        return faultCode;
    }
    public String getCode() {
        return code;
    }
    public String getFaultString() {
        return faultString;
    }
    public String getDetail() {
        Throwable t = getCause();
        t = getRootCause(t);
        String detail = t.getMessage();
        return (detail != null)
            ? detail
            : t.getClass().getName();
    }
}

// End XmlaException.java

