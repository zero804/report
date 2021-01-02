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
 
 
package net.datenwerke.rs.core.service.i18ntools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.inject.Inject;

import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;

public class FormatHelper {

	private String shortDatePattern;
	private String longDatePattern;
	private String shortTimePattern;
	private String longTimePattern;
	private String shortDateTimePattern;
	private String longDateTimePattern;

	private final I18nToolsService i18nToolsService;

	@Inject
	public FormatHelper(I18nToolsService i18nToolsService) {
		this.i18nToolsService = i18nToolsService;
		initPatterns();
	}

	private void initPatterns() {
		FormatPatterns patterns = i18nToolsService.getFormatPatterns();
		shortDatePattern = patterns.getShortDatePattern();
		longDatePattern = patterns.getLongDatePattern();
		shortTimePattern = patterns.getShortTimePattern();
		longTimePattern = patterns.getLongTimePattern();
		shortDateTimePattern = patterns.getShortDateTimePattern();
		longDateTimePattern = patterns.getLongDateTimePattern();
	}

	public String formatShortDate(Date date) {
		SimpleDateFormat format = null;
		if (null != shortDatePattern) {
			format = new SimpleDateFormat(shortDatePattern);
			return format.format(date);
		} else {
			DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, LocalizationServiceImpl.getLocale());
			return df.format(date);
		}
	}

	public String formatLongDate(Date date) {
		SimpleDateFormat format = null;
		if (null != longDatePattern) {
			format = new SimpleDateFormat(longDatePattern);
			return format.format(date);
		} else {
			DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, LocalizationServiceImpl.getLocale());
			return df.format(date);
		}
	}

	public String formatShortTime(Date date) {
		SimpleDateFormat format = null;
		if (null != shortTimePattern) {
			format = new SimpleDateFormat(shortTimePattern);
			return format.format(date);
		} else {
			DateFormat df = DateFormat.getTimeInstance(DateFormat.DEFAULT, LocalizationServiceImpl.getLocale());
			return df.format(date);
		}
	}

	public String formatLongTime(Date date) {
		SimpleDateFormat format = null;
		if (null != longTimePattern) {
			format = new SimpleDateFormat(longTimePattern);
			return format.format(date);
		} else {
			DateFormat df = DateFormat.getTimeInstance(DateFormat.DEFAULT, LocalizationServiceImpl.getLocale());
			return df.format(date);
		}
	}

	public String formatShortDateTime(Date date) {
		SimpleDateFormat format = null;
		if (null != shortDateTimePattern) {
			format = new SimpleDateFormat(shortDateTimePattern);
			return format.format(date);
		} else {
			DateFormat df = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT,
					LocalizationServiceImpl.getLocale());
			return df.format(date);
		}
	}

	public String formatLongDateTime(Date date) {
		SimpleDateFormat format = null;
		if (null != longDateTimePattern) {
			format = new SimpleDateFormat(longDateTimePattern);
			return format.format(date);
		} else {
			DateFormat df = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT,
					LocalizationServiceImpl.getLocale());
			return df.format(date);
		}
	}

}
