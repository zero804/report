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
 
 
package org.saiku.service.olap.totals.aggregators;

import mondrian.util.Format;
import org.olap4j.metadata.Measure;


public class MinAggregator extends TotalAggregator {

  MinAggregator(Format format) {
    super( format );
  }

  private Double min = null;

  @Override
  public void addData( double data ) {
    if ( min == null ) {
      min = data;
    } else if ( min > data ) {
      min = data;
    }
  }

  @Override
  public Double getValue() {
    return min;
  }

  @Override
  public TotalAggregator newInstance( Format format, Measure measure ) {
    return new MinAggregator( format );
  }

}