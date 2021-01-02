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
 
 
package org.saiku.service.olap.totals;

import org.olap4j.Cell;
import org.olap4j.metadata.Measure;
import org.saiku.service.olap.totals.aggregators.TotalAggregator;


public class TotalNode {
  private final String[] captions;
  private final TotalAggregator[][] totals;
  private final boolean showsTotals;
  private final int cellsAdded;
  private int span;
  private int width;

  public TotalNode( String[] captions, Measure[] measures, TotalAggregator aggregatorTemplate, FormatList formatList,
                    int count ) {
    this.captions = captions;
    showsTotals = aggregatorTemplate != null;

    if ( showsTotals ) {
      cellsAdded = captions != null ? captions.length : 1;
      totals = new TotalAggregator[ cellsAdded ][ count ];

      if ( aggregatorTemplate != null ) {
        for ( int i = 0; i < totals.length; i++ ) {
          for ( int j = 0; j < totals[ 0 ].length; j++ ) {
            totals[ i ][ j ] = aggregatorTemplate.newInstance( formatList.getValueFormat( j, i ), measures[ i ] );
          }
        }
      }
    } else {
      totals = new TotalAggregator[ 0 ][ count ];
      cellsAdded = 0;
    }
  }

  public void addData( int member, int index, Cell cell ) {
    totals[ member ][ index ].addData( cell );
  }

  public void setFormattedValue( int member, int index, String value ) {
    totals[ member ][ index ].setFormattedValue( value );
  }

  public int getSpan() {
    return span;
  }

  public void setSpan( int span ) {
    this.span = span;
  }

  private void appendSpan(int append) {
    this.span += append;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth( int width ) {
    this.width = width;
  }

  private void appendWidth(int append) {
    this.width += append;
  }

  public void appendChild( TotalNode child ) {
    appendSpan( child.getRenderedCount() );
    appendWidth( child.width );
  }

  public String[] getMemberCaptions() {
    return captions;
  }

  public TotalAggregator[][] getTotalGroups() {
    return totals;
  }

  private int getRenderedCount() {
    return span + cellsAdded;
  }
}