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
 
 
package net.datenwerke.rs.utils.misc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.TrueTypeUtil;

import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;

import net.datenwerke.rs.utils.config.ConfigService;

public class PdfUtils {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	public static final String PDF_CONFIG_FILE = "exportfilemd/pdfexport.cf";
	private ConfigService configService;
	private List<FontConfig> fonts;

	@Inject
	public PdfUtils(ConfigService configService) {
		this.configService = configService;

		loadFonts();
	}

	private class FontConfig{
		String path;
		String encoding;
		boolean embed;

		public FontConfig(String path, String encoding, boolean embed) {
			this.path = path;
			this.encoding = encoding;
			this.embed = embed;
		}

		public String getPath() {
			return path;
		}

		public String getEncoding() {
			return encoding;
		}

		public boolean isEmbed() {
			return embed;
		}

		@Override
		public String toString() {
			return "FontConfig [path=" + path + ", encoding=" + encoding
					+ ", embed=" + embed + "]";
		}

	}

	private void loadFonts(){
		fonts = new ArrayList<PdfUtils.FontConfig>();

		/* load user fonts */
		Configuration cfg = configService.getConfigFailsafe(PDF_CONFIG_FILE);
		if(cfg instanceof HierarchicalConfiguration) {
			HierarchicalConfiguration hc = (HierarchicalConfiguration) cfg;
			List<HierarchicalConfiguration> cfonts = hc.configurationsAt("pdf.fonts.font");
			for(HierarchicalConfiguration fconf : cfonts){
				String path = fconf.getString("path", null);
				String encoding = fconf.getString("encoding", BaseFont.IDENTITY_H);
				if(encoding.isEmpty())
					encoding = BaseFont.IDENTITY_H;
				boolean embed = !"false".equals(fconf.getString("embed", "true"));

				if(null != path){
					fonts.add(new FontConfig(path, encoding, embed));
				}
			}
		}

		/* static default fonts */
		fonts.add(new FontConfig("org/dejavu/fonts/ttf/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
	}

	public BaseFont getBaseFont() throws DocumentException, IOException{
		FontConfig defaultFont = fonts.get(0);
		return BaseFont.createFont(defaultFont.getPath(), defaultFont.getEncoding(), defaultFont.isEmbed());
	}

	public String getBaseFontFamily() throws DocumentException, IOException{
		String[] name = TrueTypeUtil.getFamilyNames(getBaseFont());
		if(name.length > 0)
			return name[0];
		return null;
	}

	public void configureFontResolver(ITextFontResolver fontResolver) throws DocumentException, IOException{
		for(FontConfig fconf : fonts){
			fontResolver.addFont(fconf.getPath(), fconf.getEncoding(), fconf.isEmbed());
		}
	}

	public void registerPdfFonts() {
		loadFonts();
		
		Configuration cfg = configService.getConfigFailsafe(PDF_CONFIG_FILE);

		/* check if we should register standard directory */
		if(cfg instanceof HierarchicalConfiguration){ 
			boolean register = cfg.getBoolean("pdf.fontDirectories[@registerDefaultDirectories]", false);
		
			if(register) {
				try{
					FontFactory.registerDirectories();
				} catch(Exception e){
					logger.warn("Could not register default font directories.", e);
				}
			}
		}
		
		/* check if we should register further directories */
		if(cfg instanceof HierarchicalConfiguration){ 
			HierarchicalConfiguration hc = (HierarchicalConfiguration) cfg;
			List<HierarchicalConfiguration> cdirs = hc.configurationsAt("pdf.fontDirectories.dir");
			for(HierarchicalConfiguration cdir : cdirs){
				String dir = "";
				try{
					dir = cdir.getString(".");
					FontFactory.registerDirectory( dir );
				} catch(Exception e){
					logger.warn("Could not register font directory: " + dir, e);
				}
			}
		}
		
		for(FontConfig fc : fonts){
			try{
				FontFactory.register( fc.getPath() );
			} catch(Exception e){
				logger.warn("Could not register font: " + fc.getPath(), e);
			}
		}
	}

}
