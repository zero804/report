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
 
 
package net.datenwerke.rs.base.service.reportengines.locale;

import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.rs.utils.localization.Messages;

public interface ReportEnginesMessages extends Messages{

	public final static ReportEnginesMessages INSTANCE = LocalizationServiceImpl.getMessages(ReportEnginesMessages.class);
	
	String xlsOutputGeneratorSheetName();

	String rsTableToXLSNullValue();

	String rsTableToXLSBinaryData();
	
	String dateFormat();
	String timeFormat();

	String exceptionNoColumnsSelected();
	String exceptionOutputFormatNotSupportsDuplicateNames(String columnName);
	
	String jasperReportTypeName();
	String jasperReportVariantTypeName();
	String tableReportTypeName();
	String tableReportVariantTypeName();

	String detailRowsInGroup(int rowsInGroup);

	String exceptionNeedAggregateAndGroupForSubtotals();
	
	String page();
	String of();

	String exceptionNoFilterOnNonAggregateNonGroupingForSubtotals();
	
	String parameter();
	String value();
	String configurationParameter();
	
	String outputNameDynamicList();
	String outputNameDynamicListParameter();
	String outputNameDynamicListConfiguration();
	
	String filters();
	String columns();
	String type();
	String excludeTitle();
	String includeTitle();
	String includeRangesTitle();
	String excludeRangeTitle();
	String nullHandlingLabel();
	String nullInclude();
	String nullExclude();
	String caseSensitiveLabel();
	String yes();
	String no();

	String prefilter();
	String caseSensitiveFilter();
	String emptyCells();
	String excluded();
	String included();
	String configuration();
	String parameters();
	String noparameters();
	String nofilters();
	String noprefilter();
}

