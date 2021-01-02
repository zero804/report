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
 
 
package net.datenwerke.rs.base.client;

public enum AvailableReportProperties  {
	PROPERTY_OUTPUT_FORMAT_AUTH("output_format_auth"),
	PROPERTY_OUTPUT_FORMAT_DEFAULT("output_format_default"),
	PROPERTY_JXLS_STREAM("jxls_stream"),
	PROPERTY_JXLS_STREAM_ROW_ACCESS_WINDOW_SIZE("jxls_stream_row_access_window_size"),
	PROPERTY_JXLS_STREAM_COMPRESS_TMP_FILES("jxls_stream_compress_tmp_files"),
	PROPERTY_JXLS_STREAM_USE_SHARED_STRINGS_TABLE("jxls_stream_use_shared_strings_table"),
	PROPERTY_OUTPUT_PARAMETERS("output_parameters", true),
	PROPERTY_OUTPUT_FILTERS("output_filters", true),
	PROPERTY_OUTPUT_INCLUDE_PARAMETERS("output_include_hidden_parameters", true),
	PROPERTY_OUTPUT_COMPLETE_CONFIGURATION("output_complete_configuration", true),
	PROPERTY_DL_FILTER_SHOW_CONSISTENCY("ui:filter:consistency:show", true),
	PROPERTY_DL_FILTER_DEFAULT_CONSISTENCY("ui:filter:consistency:default", true),
	PROPERTY_DL_FILTER_COUNT_DEFAULT("ui:filter:count:default", true),		
	PROPERTY_DL_PREVIEW_COUNT_DEFAULT("ui:preview:count:default", true);
	
	private String value;
	private boolean dynamicListSpecificProperty;
	
	AvailableReportProperties(String value) {
		this.value = value;
	}
	
	AvailableReportProperties(String param, boolean isOutputProperty) {
		this(param);
		this.dynamicListSpecificProperty = isOutputProperty;
	}
	
    public String getValue() {
        return value;
    }

    public boolean isDynamicListSpecificProperty() {
    	return dynamicListSpecificProperty;
    }
};