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
package org.saiku.olap.dto.resultset;

import org.olap4j.metadata.Measure;
import org.saiku.service.olap.totals.TotalNode;

import java.io.Serializable;
import java.util.List;

public class CellDataSet implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private int width;

  private int height;

  private AbstractBaseCell[][] cellSetHeader;

  private AbstractBaseCell[][] cellSetBody;

  private Measure[] selectedMeasures;

  private List<TotalNode>[] colTotalsLists;

  private List<TotalNode>[] rowTotalsLists;

  private int offset;

  private int topOffset;

  private int leftOffset;

  private int runtime;

  public CellDataSet() {
    super();
  }

  public CellDataSet( final int width, final int height ) {
    this.width = width;
    this.height = height;
  }

  public AbstractBaseCell[][] getCellSetHeaders() {
    return cellSetHeader;
  }

  public void setCellSetHeaders( final AbstractBaseCell[][] cellSet ) {
    this.topOffset = cellSet.length;
    this.cellSetHeader = cellSet;
  }

  public AbstractBaseCell[][] getCellSetBody() {
    return cellSetBody;
  }

  public void setCellSetBody( final AbstractBaseCell[][] cellSet ) {
    this.cellSetBody = cellSet;
  }

  public void setOffset( final int offset ) {
    this.offset = offset;
  }

  public int getOffset() {
    return offset;

  }

  public int getTopOffset() {
    return topOffset;
  }

  public int getLeftOffset() {
    return leftOffset;
  }

  public void setLeftOffset( int leftOffset ) {
    this.leftOffset = leftOffset;
  }

  public Measure[] getSelectedMeasures() {
    return selectedMeasures;
  }

  public void setSelectedMeasures( Measure[] selectedMeasures ) {
    this.selectedMeasures = selectedMeasures;
  }

  public List<TotalNode>[] getRowTotalsLists() {
    return rowTotalsLists;
  }

  public void setRowTotalsLists( List<TotalNode>[] rowTotalsLists ) {
    this.rowTotalsLists = rowTotalsLists;
  }

  public List<TotalNode>[] getColTotalsLists() {
    return colTotalsLists;
  }

  public void setColTotalsLists( List<TotalNode>[] colTotalsLists ) {
    this.colTotalsLists = colTotalsLists;
  }

  /**
   * @param width the width to set
   */
  public void setWidth( final int width ) {
    this.width = width;
  }

  /**
   * @return the width
   */
  public int getWidth() {
    return width;
  }

  /**
   * @param height the height to set
   */
  public void setHeight( final int height ) {
    this.height = height;
  }

  /**
   * @return the height
   */
  public int getHeight() {
    return height;
  }

  public int getRuntime() {
    return runtime;
  }

  public void setRuntime( int runtime ) {
    this.runtime = runtime;
  }
}
