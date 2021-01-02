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
 
 
package net.datenwerke.gxtdto.client.utils.modelkeyprovider;

import java.util.HashMap;

import com.sencha.gxt.data.shared.ModelKeyProvider;

public class BasicObjectModelKeyProvider<T> implements ModelKeyProvider<T> {

	private final HashMap<T, Integer> map = new HashMap<T, Integer>();
	private int cnt = 0;
	
	@Override
	public String getKey(T item) {
		if(! map.containsKey(item))
			map.put(item, cnt++);
		return String.valueOf(map.get(item));
	}

}
