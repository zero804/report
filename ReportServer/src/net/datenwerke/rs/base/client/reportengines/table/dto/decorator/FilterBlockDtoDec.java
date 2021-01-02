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
 
 
package net.datenwerke.rs.base.client.reportengines.table.dto.decorator;

import java.util.HashSet;
import java.util.Set;

import net.datenwerke.gxtdto.client.dtomanager.IdedDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.BlockTypeDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.FilterBlockDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.FilterSpecDto;

/**
 * Dto Decorator for {@link FilterBlockDto}
 *
 */
public class FilterBlockDtoDec extends FilterBlockDto implements IdedDto {

	private static final long serialVersionUID = 1L;
	
	transient private BlockTypeDto blockType;  

	public FilterBlockDtoDec() {
		super();
	}

	public void removeChildBlock(FilterBlockDto toBeRemoved) {
		Set<FilterBlockDto> childBlocks = getChildBlocks();
		childBlocks.remove(toBeRemoved);
		setChildBlocks(childBlocks);
	}

	public void removeChildFilter(FilterSpecDto toBeRemoved) {
		Set<FilterSpecDto> filters = getFilters();
		filters.remove(toBeRemoved);
		setFilters(filters);
	}

	public void clearBlock() {
		Set<FilterBlockDto> childBlocks = getChildBlocks();
		childBlocks.clear();
		setChildBlocks(childBlocks);
		
		Set<FilterSpecDto> filters = getFilters();
		filters.clear();
		setFilters(filters);
	}

	public void addFilter(FilterSpecDto added) {
		Set<FilterSpecDto> filters = getFilters();
		filters.add(added);
		setFilters(filters);
	}

	public void addChildBlock(FilterBlockDto added) {
		Set<FilterBlockDto> childBlocks = getChildBlocks();
		childBlocks.add(added);
		setChildBlocks(childBlocks);
	}

	public FilterBlockDto cloneFilterBlock(){
		FilterBlockDtoDec clone = new FilterBlockDtoDec();
		
		if(null != getBlockType())
			clone.setBlockType(getBlockType());
		if(null != getFilters()){
			Set<FilterSpecDto> filters = new HashSet<FilterSpecDto>();
			for(FilterSpecDto filter : getFilters())
				filters.add(((FilterSpecDtoDec)filter).cloneFilter());
			clone.setFilters(filters);
		}
		if(null != getChildBlocks()){
			Set<FilterBlockDto> childBlocks = new HashSet<FilterBlockDto>();
			for(FilterBlockDto childBlock : getChildBlocks())
				childBlocks.add(((FilterBlockDtoDec)childBlock).cloneFilterBlock());
			clone.setChildBlocks(childBlocks);
		}
		
		return clone;
	}
	
	public String toDisplayTitle(){
		return "Block";
	}

	public void initBlockTypes(BlockTypeDto blockType) {
		this.blockType = blockType;
		
		BlockTypeDto cblock = blockType == BlockTypeDto.AND ? BlockTypeDto.OR : BlockTypeDto.AND;
		for(FilterBlockDto c : getChildBlocks())
			((FilterBlockDtoDec)c).initBlockTypes(cblock);
	}
	
	public BlockTypeDto getBlockType() {
		if(null == blockType)
			throw new IllegalStateException("BlockTypes have not been initialized");
		return blockType;
	}
	
	public void setBlockType(BlockTypeDto blockType) {
		if(null == blockType)
			blockType = BlockTypeDto.OR;
		this.blockType = blockType;
	}

}
