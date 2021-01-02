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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.DtoContainer;
import net.datenwerke.gxtdto.client.dtomanager.DtoRegistrar;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;

/**
 * 
 *
 */
public class TsDiskItemList implements Serializable, DtoContainer {

	private List<AbstractTsDiskNodeDto> items = new ArrayList<AbstractTsDiskNodeDto>();
	
	private List<AbstractTsDiskNodeDto> path = new ArrayList<AbstractTsDiskNodeDto>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8645371464170972130L;

	@Override
	public void registerDtos(DtoRegistrar registrar) {
		for(AbstractTsDiskNodeDto node : path)
			registrar.registerDto(node);
		for(AbstractTsDiskNodeDto node : items)
			registrar.registerDto(node);
	}

	public void setPath(List<AbstractTsDiskNodeDto> path) {
		this.path = path;
	}

	public List<AbstractTsDiskNodeDto> getPath() {
		return new ArrayList<AbstractTsDiskNodeDto>(path);
	}

	public void setItems(List<AbstractTsDiskNodeDto> items) {
		this.items = items;
	}

	public List<AbstractTsDiskNodeDto> getItems() {
		return new ArrayList<AbstractTsDiskNodeDto>(items);
	}

	public AbstractTsDiskNodeDto getItem(Long id) {
		if(null == id)
			return null;
		
		for(AbstractTsDiskNodeDto item : getItems())
			if(id.equals(item.getId()))
				return item;
		
		return null;
	}

}
