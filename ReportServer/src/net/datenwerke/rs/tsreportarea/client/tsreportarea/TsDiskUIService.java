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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea;

import java.util.List;

import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.data.shared.IconProvider;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

/**
 * 
 *
 */
public interface TsDiskUIService {

	abstract <A extends AbstractTsDiskNodeDto> List<ColumnConfig<A, ?>> createGridColumnConfig(Class<A> type);

	abstract <A extends AbstractTsDiskNodeDto> List<ColumnConfig<A, ?>> createGridColumnConfig(Class<A> type, boolean idColumn);

	abstract ImageResource getIconFor(AbstractTsDiskNodeDto node, boolean largeIcons);

	IconProvider<AbstractNodeDto> getIconProvider();
	
}
