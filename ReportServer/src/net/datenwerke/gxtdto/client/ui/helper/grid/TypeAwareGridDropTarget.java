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
 
 
package net.datenwerke.gxtdto.client.ui.helper.grid;

import java.util.ArrayList;
import java.util.List;

import com.sencha.gxt.data.shared.TreeStore.TreeNode;
import com.sencha.gxt.dnd.core.client.DndDragMoveEvent;
import com.sencha.gxt.dnd.core.client.GridDropTarget;
import com.sencha.gxt.widget.core.client.grid.Grid;

/**
 * 
 *
 */
public class TypeAwareGridDropTarget<M> extends GridDropTarget<M> {

	protected final Class<?>[] types;
	
	public TypeAwareGridDropTarget(Grid<M> grid, Class<?>... types) {
		super(grid);

		this.types = types;
	}
	
	@Override
	protected void onDragMove(DndDragMoveEvent event) {
		List list = (List) event.getData();
		if(list.size() > 0){
			Object m = list.get(0);
			
			/* search for object */
			Object data = m;
			if(m instanceof TreeNode)
				data = ((TreeNode)m).getData();
			
			boolean foundType = false;
			for(Class<?> type : types){
				if(type.equals(data.getClass())){
					foundType = true;
					break;
				}
			}
			
			if( foundType ){
				super.onDragMove(event);
			} else {
				event.setCancelled(true);
				event.getStatusProxy().setStatus(false);
				return;
			}
		}
	}
	
	@Override
	protected List<Object> prepareDropData(Object data, boolean convertTreeStoreModel) {
		List<Object> models = new ArrayList<Object>();
		
		if (data instanceof TreeNode) {
			models.add(((TreeNode)data).getData());
		} else if (data instanceof List) {
			for (Object obj : (List) data) {
				if (obj instanceof TreeNode) {
					models.add(((TreeNode)obj).getData());
				}
			}
		}
		
		return models;
	}

}
