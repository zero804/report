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
 
 
/*  
 *   Copyright 2012 OSBI Ltd
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package net.datenwerke.rs.legacysaiku.server.rest.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import net.datenwerke.rs.legacysaiku.server.rest.objects.resultset.Cell;
import net.datenwerke.rs.legacysaiku.server.rest.objects.resultset.QueryResult;

import org.legacysaiku.olap.dto.resultset.AbstractBaseCell;
import org.legacysaiku.olap.dto.resultset.CellDataSet;
import org.legacysaiku.olap.dto.resultset.DataCell;
import org.legacysaiku.olap.dto.resultset.MemberCell;

public class RestUtil {
	
	public static QueryResult convert(ResultSet rs) {
		return convert(rs, 0);
	}

	public static QueryResult convert(ResultSet rs, int limit) {

		Integer width = 0;
        Integer height = 0;
        
        Cell[] header = null;
        ArrayList<Cell[]> rows = new ArrayList<Cell[]>();
        
        // System.out.println("DATASET");
        try {
			while (rs.next() && (limit == 0 || height < limit)) {
			    if (height == 0) {
			        width = rs.getMetaData().getColumnCount();
			        header = new Cell[width];
			        for (int s = 0; s < width; s++) {
			            header[s] = new Cell(rs.getMetaData().getColumnName(s + 1),Cell.Type.COLUMN_HEADER);
			        }
			        if (width > 0) {
			            rows.add(header);
			            // System.out.println(" |");
			        }
			    }
			    Cell[] row = new Cell[width];
			    for (int i = 0; i < width; i++) {
			    	String content = rs.getString(i + 1);
			        
			        if (content == null)
			            content = "";
			        row[i] = new Cell(content, Cell.Type.DATA_CELL);
			    }
			    rows.add(row);
			    height++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new QueryResult(rows,0,width,height);
	}
	
	public static QueryResult convert(CellDataSet cellSet) {
		return convert(cellSet, 0);
	}
	
	public static QueryResult convert(CellDataSet cellSet, int limit) {
		ArrayList<Cell[]> rows = new ArrayList<Cell[]>();
		if (cellSet == null || cellSet.getCellSetBody() == null || cellSet.getCellSetHeaders() == null) {
			return null;
		}
		AbstractBaseCell[][] body = cellSet.getCellSetBody();
		AbstractBaseCell[][] headers = cellSet.getCellSetHeaders();
		
		
		
		for (AbstractBaseCell header[] : headers) {
			rows.add(convert(header, Cell.Type.COLUMN_HEADER));
		}
		for (int i = 0; i < body.length && (limit == 0 || i < limit) ; i++) {
			AbstractBaseCell[] row = body[i];
			rows.add(convert(row, Cell.Type.ROW_HEADER));
		}
		
		QueryResult qr = new QueryResult(rows, cellSet.getRuntime(), cellSet.getWidth(), cellSet.getHeight());
		return qr;
		
	}
	
	public static Cell[] convert(AbstractBaseCell[] acells, Cell.Type headertype) {
		Cell[]  cells = new Cell[acells.length];
		for (int i = 0; i < acells.length; i++) {
			cells[i] = convert(acells[i], headertype);
		}
		return cells;
	}
	
	public static Cell convert(AbstractBaseCell acell, Cell.Type headertype) {
		if (acell != null) {
			if (acell instanceof DataCell) {
				DataCell dcell = (DataCell) acell;
				Properties metaprops = new Properties();
				// metaprops.put("color", "" + dcell.getColorValue());
				String position = null;
				for (Integer number : dcell.getCoordinates()) {
					if (position != null) {
						position += ":" + number.toString();
					}
					else {
						position = number.toString();
					}
				}
				if (position != null) {
					metaprops.put("position", position);
				}
				
				if (dcell != null && dcell.getRawNumber() != null) {
					metaprops.put("raw", "" + dcell.getRawNumber());
				}
				
				
				metaprops.putAll(dcell.getProperties());
				
				// TODO no properties  (NULL) for now - 
				return new Cell(dcell.getFormattedValue(), metaprops, Cell.Type.DATA_CELL);
			}
			if (acell instanceof MemberCell) {
				MemberCell mcell = (MemberCell) acell;
//				Properties metaprops = new Properties();
//				metaprops.put("children", "" + mcell.getChildMemberCount());
//				metaprops.put("uniqueName", "" + mcell.getUniqueName());

				Properties props = new Properties();
				if ( mcell != null) {
					if (mcell.getParentDimension() != null) {
						props.put("dimension", mcell.getParentDimension());
					}
					if (mcell.getUniqueName() != null) {
						props.put("uniquename", mcell.getUniqueName());
					}
					if (mcell.getHierarchy() != null) {
						props.put("hierarchy", mcell.getHierarchy());
					}
					if (mcell.getLevel() != null) {
						props.put("level", mcell.getLevel());
					}
				}
//				props.putAll(mcell.getProperties());

				// TODO no properties  (NULL) for now - 
				if ("row_header_header".equals(mcell.getProperty("__headertype"))) {
					headertype = Cell.Type.ROW_HEADER_HEADER;
				}
				return new Cell("" + mcell.getFormattedValue(), props, headertype);
			}

		}
		return null;
	}

}
