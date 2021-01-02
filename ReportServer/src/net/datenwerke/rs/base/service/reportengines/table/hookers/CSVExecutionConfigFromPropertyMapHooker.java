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
 
 
package net.datenwerke.rs.base.service.reportengines.table.hookers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.datenwerke.rs.core.service.reportmanager.engine.config.RECCsv;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.engine.hooks.ReportExecutionConfigFromPropertyMapHook;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;

public class CSVExecutionConfigFromPropertyMapHooker implements
		ReportExecutionConfigFromPropertyMapHook {

	public static final String PROPERTY_PRINT_HEADER = "csv_ph";
	public static final String PROPERTY_SEPARATOR = "csv_sep";
	public static final String PROPERTY_QUOTE = "csv_q";
	
	@Override
	public Collection<ReportExecutionConfig> parse(
			Report report, HttpServletRequest request, 
			Map<String, String[]> properties) {
		List<ReportExecutionConfig> configs = new ArrayList<ReportExecutionConfig>();
		
		if(properties.containsKey(PROPERTY_PRINT_HEADER) || 
		   properties.containsKey(PROPERTY_SEPARATOR) ||
		   properties.containsKey(PROPERTY_QUOTE)){
			RECCsv config = new RECCsv();
			configs.add(config);
			
			if(properties.containsKey(PROPERTY_PRINT_HEADER)){
				String[] phInfo = (String[]) properties.get(PROPERTY_PRINT_HEADER);
				config.setPrintHeader("true".equals(phInfo[0]));
			}
			
			if(properties.containsKey(PROPERTY_SEPARATOR)){
				String[] sepInfo = (String[]) properties.get(PROPERTY_SEPARATOR);
				config.setSeparator(sepInfo[0]);
			}
			
			if(properties.containsKey(PROPERTY_QUOTE)){
				String[] quoteInfo = (String[]) properties.get(PROPERTY_QUOTE);
				config.setQuote(quoteInfo[0]);
			}
		}
		
		return configs;
	}

}
