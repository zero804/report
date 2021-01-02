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
 
 
package org.saiku.service.util.export;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Class to log the load of converting queries to PDF's.
 */
public class PdfPerformanceLogger{
    private static final Logger log = LoggerFactory.getLogger(PdfPerformanceLogger.class);

    private final long start;
    private long queryToHtmlStart;
    private long queryToHtmlEnd;
    private long renderStart;
    private long renderEnd;

    public PdfPerformanceLogger() {
        this.start = (new Date()).getTime();
    }

    public void queryToHtmlStart(){
        queryToHtmlStart = getCurrentTime();
    }

    public void setQueryToHtmlStop(){
        queryToHtmlEnd = getCurrentTime();
    }

    public void renderStart(){
        renderStart = getCurrentTime();
    }

    public void renderStop(){
        renderEnd = getCurrentTime();
    }

    public void logResults(){
        log.debug("PDF Output - JSConverter: " + (queryToHtmlEnd - queryToHtmlStart) + "ms PDF Render: " + (renderEnd - renderStart) + "ms");
    }

    private long getCurrentTime(){
        return (new Date()).getTime();
    }
}