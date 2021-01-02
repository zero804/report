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
 
 
package org.saiku.web.export;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.datenwerke.rs.saiku.server.rest.objects.resultset.QueryResult;

public class JSConverter {
    public static String convertToHtml(QueryResult queryResult, boolean wrapcontent) throws IOException {
        StringWriter stringWriter = new StringWriter();
        useJavascriptToConvertToHtml(queryResult, stringWriter);
        String content = stringWriter.toString();
        content = appendSaikuCommercialIfNecessary(content);
        return content;
    }

    private static void useJavascriptToConvertToHtml(QueryResult queryResult, StringWriter stringWriter) throws IOException {
        Context javascriptContext = createJavascriptContext();
        Scriptable globalScope = javascriptContext.initStandardObjects();
        loadJavascriptScripts(javascriptContext, globalScope);
        loadDataIntoJsContext(queryResult, globalScope);
        loadStringWriterIntoJsContext(stringWriter, globalScope);
        executeJavascript(javascriptContext, globalScope);
        Context.exit();
    }

    private static void executeJavascript(Context javascriptContext, Scriptable globalScope) {
        String code =
            "eval('var cellset = ' + data); \n" +
                "var renderer = new SaikuTableRenderer(); \n" +
                "var html = renderer.render(cellset, { wrapContent : " + false + " }); out.write(html);";
        javascriptContext.evaluateString(globalScope, code, "<mem>", 1, null);
    }

    private static void loadStringWriterIntoJsContext(StringWriter stringWriter, Scriptable globalScope) {
        // load StringWriter into JS environment so JS has a place to write to
        Object wrappedOut = Context.javaToJS(stringWriter, globalScope);
        ScriptableObject.putProperty(globalScope, "out", wrappedOut);
    }

    private static void loadDataIntoJsContext(QueryResult queryResult, Scriptable globalScope) throws JsonProcessingException {
        // load data of queryResult into JS environment
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(queryResult);
        Object wrappedQueryResult = Context.javaToJS(data, globalScope);
        ScriptableObject.putProperty(globalScope, "data", wrappedQueryResult);
    }

    private static Context createJavascriptContext() {
        Context javascriptContext = Context.enter();
        javascriptContext.setOptimizationLevel(-1);
        javascriptContext.setLanguageVersion(Context.VERSION_1_5);
        return javascriptContext;
    }

    private static void loadJavascriptScripts(Context javascriptContext, Scriptable globalScope) throws IOException {
        // fetch the .js files and put them on scope of the javascriptContext to be executed
        Reader underscoreReader = new InputStreamReader(JSConverter.class.getClassLoader().getResourceAsStream("resources/saiku/underscore.js"));
        javascriptContext.evaluateReader(globalScope, underscoreReader, "underscore.js", 1, null);
        Reader saikuRendererReader = new InputStreamReader(JSConverter.class.getClassLoader().getResourceAsStream("resources/saiku/SaikuRenderer.js"));
        javascriptContext.evaluateReader(globalScope, saikuRendererReader, "SaikuRenderer.js", 1, null);
        String result = IOUtils.toString(JSConverter.class.getClassLoader().getResourceAsStream("resources/saiku/SaikuTableRenderer.js"));
        javascriptContext.evaluateString(globalScope, result, "SaikuTableRenderer.js", 1, null);
    }

    private static String appendSaikuCommercialIfNecessary(String content) {
        content = content.replaceAll("&nbsp;", " ");
        content = content.replaceAll("&nbsp", " ");
        return content;
    }

    public static String convertToHtml(QueryResult qr) throws IOException {
        return convertToHtml(qr, false);
    }

}
