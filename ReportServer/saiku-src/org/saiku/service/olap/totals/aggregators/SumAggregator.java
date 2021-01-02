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

public class SumAggregator extends TotalAggregator {

  SumAggregator(Format format) {
    super( format );
  }

  private double sum = 0.0;

  @Override
  public void addData( double data ) {
    sum += data;
  }

  @Override
  public Double getValue() {
    return sum;
  }

  @Override
  public TotalAggregator newInstance() {
    return new SumAggregator( format );
  }

  public TotalAggregator newInstance( Format format, Measure measure ) {
    return new SumAggregator( format );
  }

}