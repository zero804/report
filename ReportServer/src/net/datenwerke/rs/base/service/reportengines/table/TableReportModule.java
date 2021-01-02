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
 
 
package net.datenwerke.rs.base.service.reportengines.table;

import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.Filter;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.ColumnFormatDate;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.ColumnFormatTemplate;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.enums.CurrencyType;
import net.datenwerke.rs.base.service.reportengines.table.output.generator.TableOutputGeneratorImpl;
import net.datenwerke.rs.base.service.reportengines.table.output.metadata.TableMetadataExporter;
import net.datenwerke.rs.base.service.reportengines.table.output.metadata.TablePlainExporter;
import net.datenwerke.rs.base.service.reportengines.table.utils.RSTableToXLS;
import net.datenwerke.rs.base.service.reportengines.table.utils.TableReportColumnMetadataService;
import net.datenwerke.rs.base.service.reportengines.table.utils.TableReportColumnMetadataServiceImpl;
import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;

public class TableReportModule extends AbstractReportServerModule {

	@Override
	protected void configure() {
		/* bind metadata exporter */
		Multibinder<TableMetadataExporter> metadataExporterBinder = Multibinder.newSetBinder(binder(), TableMetadataExporter.class);
		metadataExporterBinder.addBinding().to(TablePlainExporter.class);
		
		/* bind utilities */
		bind(TableReportUtils.class).to(TableReportUtilsImpl.class).in(Singleton.class);
		bind(TableReportColumnMetadataService.class).to(TableReportColumnMetadataServiceImpl.class).in(Singleton.class);
		
		/* bind startup */
		bind(TableReportStartup.class).asEagerSingleton();
		
		requestStaticInjection(
			ColumnFormatTemplate.class,
			ColumnFormatDate.class,
			TableOutputGeneratorImpl.class,
			RSTableToXLS.class,
			Filter.class,
			Column.class,
			CurrencyType.class
		);
	}

}
