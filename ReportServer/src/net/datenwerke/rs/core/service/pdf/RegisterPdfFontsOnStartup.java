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
 
 
package net.datenwerke.rs.core.service.pdf;

import com.google.inject.Inject;

import net.datenwerke.gf.service.lateinit.LateInitHook;
import net.datenwerke.rs.birt.service.reportengine.BirtReportService;
import net.datenwerke.rs.utils.misc.PdfUtils;

public class RegisterPdfFontsOnStartup implements LateInitHook {

	private final PdfUtils pdfUtils;
	private final BirtReportService birtService;

	@Inject
	public RegisterPdfFontsOnStartup(PdfUtils pdfUtils, BirtReportService birtService) {
		this.pdfUtils = pdfUtils;
		this.birtService = birtService;
	}
	
	@Override
	public void initialize() {
		pdfUtils.registerPdfFonts();
		birtService.clearFontCache();
	}

}
