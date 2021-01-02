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
 
 
package net.datenwerke.rs.saiku.service.saiku.reportengine.output.generator;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.olap4j.CellSet;
import org.saiku.olap.dto.resultset.CellDataSet;
import org.saiku.olap.query2.ThinHierarchy;
import org.saiku.olap.util.formatter.ICellSetFormatter;
import org.saiku.web.export.JSConverter;

import com.google.inject.Provider;

import net.datenwerke.gf.service.theme.ThemeService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.service.parameterreplacements.provider.ReportForJuel;
import net.datenwerke.rs.base.service.reportengines.locale.ReportEnginesMessages;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column.ColumnFormatCellFormatter;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.ColumnFormatNumber;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.license.service.LicenseService;
import net.datenwerke.rs.saiku.server.rest.objects.resultset.QueryResult;
import net.datenwerke.rs.saiku.server.rest.util.RestUtil;
import net.datenwerke.rs.saiku.service.saiku.reportengine.output.object.CompiledHTMLSaikuReport;
import net.datenwerke.rs.saiku.service.saiku.reportengine.output.object.CompiledRSSaikuReport;
import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.rs.utils.juel.SimpleJuel;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;

public class SaikuHTMLOutputGenerator extends SaikuOutputGeneratorImpl {

	protected static final String CONFIG_FILE = "reportengines/saiku.htmlexport.cf";

	@Inject
	protected Provider<ThemeService> themeServiceProvider;
	
	@Inject
	protected Provider<ConfigService> configServiceProvider; 

	@Inject
	protected Provider<LocalizationServiceImpl> localizationServiceProvider;
	
	@Inject
	protected Provider<LicenseService> licenseServiceProvider;

	@Inject
	protected Provider<SimpleJuel> juelProvider;
	
	protected static final String STYLE_PROPERTY = "htmlexport.style";
	protected static final String PRE_PROPERTY = "htmlexport.pre";
	protected static final String POST_PROPERTY = "htmlexport.post";
	protected static final String SCRIPT_PROPERTY = "htmlexport.script";
	protected static final String HEAD_PROPERTY = "htmlexport.head";
	protected static final String TITLE_PROPERTY = "htmlexport.title";
	
	@Inject
	public SaikuHTMLOutputGenerator(HookHandlerService hookHandler) {
		super(hookHandler);
	}

	@Override
	public CompiledRSSaikuReport exportReport(CellDataSet cellDataSet,
			CellSet cellset, List<ThinHierarchy> filters, ICellSetFormatter formatter,
			String outputFormat, ReportExecutionConfig... configs)
					throws ReportExecutorException {

		Configuration configFile;
		if(licenseServiceProvider.get().isEnterprise())
			configFile = configServiceProvider.get().getConfigFailsafe(getConfigFileLocation());
		else
			configFile = new BaseConfiguration();

		QueryResult qr = RestUtil.convert(cellDataSet);
		try{
			String content = JSConverter.convertToHtml(qr, true);
			StringBuilder sb = new StringBuilder("<!DOCTYPE html><html><head>");
			doAddHtmlHeader(sb, configFile);
			sb.append("</head>\n<body class='" + getBodyClass() + "'><div class='olap-report'>\n");
			
			doAddPreTable(sb, configFile);
			sb.append(content);
			doAddPostTable(sb, configFile);
			
			sb.append("\n</div></body></html>");

			return new CompiledHTMLSaikuReport(sb.toString());
		} catch(Exception e){
			throw new ReportExecutorException(e);
		}
	}
	
	protected String getBodyClass() {
		return "rs-reportexport-dl-html";
	}

	protected String getConfigFileLocation() {
		return CONFIG_FILE;
	}

	@Override
	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_HTML};
	}

	@Override
	public CompiledReport getFormatInfo() {
		return new CompiledHTMLSaikuReport();
	}

	protected void doAddPreTable(StringBuilder writer, Configuration configFile) throws IOException {
		String pre = configFile.getString(PRE_PROPERTY, "");
		if(null != pre && ! "".equals(pre.trim())){
			String html = parseTemplate(pre);
			writer.append(html);
		} else {
			writer.append("<div class='wrap'>");
			writer.append("<div class='header'>");
			writer.append("<span class='logo'><img src=\"data:image/png;base64,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\" alt=\"logo\"/></span>");
			writer.append("<div class='reportdata'>");
			writer.append("<span class='name'>" + org.apache.commons.lang.StringEscapeUtils.escapeHtml(report.getName()) + "</span>");
			writer.append("<span class='date'>" + getNowString() + "</span>");
			writer.append("</div>");
			writer.append("<div class='clear'></div>");
			writer.append("</div>");
		}
	}

	protected String parseTemplate(String template) {
		SimpleJuel juel = juelProvider.get();

		juel.addReplacement("report", new ReportForJuel(report));
		juel.addReplacement("now", getNowString());
		
		juel.addReplacement("page", ReportEnginesMessages.INSTANCE.page());
		juel.addReplacement("of", ReportEnginesMessages.INSTANCE.of());
		
		juel.addReplacement("user", user);
		
		try{
			return juel.parse(template);
		} catch(Exception e){
			return "ERROR";
		}
	}


	protected void doAddPostTable(StringBuilder writer, Configuration configFile) throws IOException {
		String post = configFile.getString(POST_PROPERTY, "");
		if(null != post && ! "".equals(post.trim())){
			String html = parseTemplate(post);
			writer.append(html);
		}else 
			writer.append("</div>"); //wrap
	}

	protected void doAddHtmlHeader(StringBuilder writer, Configuration configFile) throws IOException {
		writer.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
		writer.append("<style type=\"text/css\"><!--");
		doAddCss(writer, configFile);
		writer.append("\n-->\n</style>\n");
		writer.append("<script>/* <![CDATA[ */");
		doAddScript(writer, configFile);
		writer.append("\n/* ]]> */</script>\n");
		
		String head = configFile.getString(HEAD_PROPERTY, "");
		if(null != head && ! "".equals(head.trim())){
			String html = parseTemplate(head);
			writer.append(html);
		}
		
		String title = configFile.getString(TITLE_PROPERTY, "");
		if(null != title && ! "".equals(title.trim())){
			String html = parseTemplate(title);
			writer.append("<title>").append(html).append("</title>\n");
		} else 
			writer.append("<title>").append(report.getName()).append("</title>\n");
	}

	private void doAddScript(StringBuilder writer, Configuration configFile) throws IOException {
		String script = configFile.getString(SCRIPT_PROPERTY, "");
		if(null != script && ! "".equals(script.trim())){
			String html = parseTemplate(script);
			writer.append(html);
		}
	}


	protected void doAddCss(StringBuilder writer, Configuration configFile) throws IOException {
		writer.append(themeServiceProvider.get().getTheme());
		
		String style = configFile.getString(STYLE_PROPERTY, "");
		if(null != style && ! "".equals(style.trim())){
			String html = parseTemplate(style);
			writer.append(html);
		} else {
			writer.append("@page {size: A4 portrait;margin-top:1.5cm;");
			writer.append("@top-left { content: \"" + org.apache.commons.lang.StringEscapeUtils.unescapeHtml(report.getName()) + "\"; font-family: DejaVu Sans, Sans-Serif; font-size: 8pt; }");
			writer.append("@top-right {content: \"" + getNowString() + "\"; font-family: DejaVu Sans, Sans-Serif; font-size: 8pt; }");
			writer.append("@bottom-right { content: \"" + ReportEnginesMessages.INSTANCE.page()+ " \" counter(page) \" " + ReportEnginesMessages.INSTANCE.of() + " \" counter(pages); font-family: DejaVu Sans, Sans-Serif; font-size: 8pt; }");
			writer.append("}");
		}
	}
	
	protected String getNowString() {
		return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, localizationServiceProvider.get().getLocale()).format(new Date());
	}
}
