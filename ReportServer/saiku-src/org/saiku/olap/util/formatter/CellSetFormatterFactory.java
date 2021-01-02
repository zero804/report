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
 
 
package org.saiku.olap.util.formatter;

import org.saiku.service.util.exception.SaikuServiceException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.datenwerke.rs.saiku.service.saiku.ThinQueryServiceImpl;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class CellSetFormatterFactory {

	private static final Logger log = LoggerFactory.getLogger(ThinQueryServiceImpl.class);

	private Map<String, String> formatters = new HashMap<>();

	private String defaultFormatter = FlattenedCellSetFormatter.class.getName();

	public void setFormatters(Map<String, String> formatters) {
		this.formatters = formatters;
	}

	public void setDefaultFormatter(String clazz) {
		this.defaultFormatter = clazz;
	}

	public CellSetFormatterFactory() {
		formatters.put("flattened", FlattenedCellSetFormatter.class.getName());
		formatters.put("hierarchical", HierarchicalCellSetFormatter.class.getName());
		formatters.put("flat", CellSetFormatter.class.getName());
	}

  public ICellSetFormatter forName(String name) {
	return create(name, defaultFormatter);
  }

  private ICellSetFormatter create(String name, String defaultFormatter) {
	String clazzName = StringUtils.isBlank(name) || !formatters.containsKey(name) ? defaultFormatter : formatters.get(name);
	try {
	  @SuppressWarnings("unchecked")
	  final Class<ICellSetFormatter> clazz = (Class<ICellSetFormatter>)
		  Class.forName(clazzName);
	  final Constructor<ICellSetFormatter> ctor = clazz.getConstructor();
	  return ctor.newInstance();
	}
	catch (Exception e) {
	  log.error("Error creating CellSetFormatter \"" + clazzName + "\"", e);
	  throw new SaikuServiceException("Error creating cellsetformatter for class: " + clazzName, e);
	}
  }


}
