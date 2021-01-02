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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.olap4j.Cell;
import org.olap4j.metadata.Measure;
import org.saiku.olap.util.SaikuProperties;

import mondrian.util.Format;

public abstract class TotalAggregator {
  private static final Map<String, TotalAggregator> all;

  static {
    Map<String, TotalAggregator> tmp = new HashMap<>();
    tmp.put( "sum", new SumAggregator( null ) );
    tmp.put( "max", new MaxAggregator( null ) );
    tmp.put( "min", new MinAggregator( null ) );
    tmp.put( "avg", new AvgAggregator( null ) );
    tmp.put( "deep_avg", new DeepAvgAggregator( null ) );
    all = Collections.unmodifiableMap( tmp );
  }

  private String formattedValue;
  final Format format;

  TotalAggregator(Format format) {
    this.format = format;
  }

  public void addData( Cell cell ) {
    try {
      // FIXME - maybe we should try to do fetch the format here, but seems to cause some issues? infinite loop? make
      // sure we try this only once to override existing format?
      //		if (format == null) {
      //			String formatString = (String) cell.getPropertyValue(Property.StandardCellProperty.FORMAT_STRING);
      //			this.format = Format.get(formatString, SaikuProperties.locale);
      //
      //		}
      Object value = cell.getValue();
      if ( value instanceof Number ) {
        double doubleVal;

        doubleVal = cell.getDoubleValue();
        addData( doubleVal );
      }
    } catch ( Exception e ) {
      throw new RuntimeException( e );
    }

  }

  protected abstract void addData( double data );

  protected abstract Double getValue();

  public abstract TotalAggregator newInstance( Format format, Measure measure );

  public String getFormattedValue() {
    if ( formattedValue != null ) {
      return formattedValue;
    } else {
      Double value = getValue();
      if ( value != null ) {
        return format.format( value );
      }
      return "";
    }
  }

  public void setFormattedValue( String formattedValue ) {
    this.formattedValue = formattedValue;
  }

  public TotalAggregator newInstance() {
    return newInstance( "" );
  }

  private TotalAggregator newInstance(String formatString) {
    return newInstance( new Format( formatString, SaikuProperties.locale ), null );
  }

  public static TotalAggregator newInstanceByFunctionName( final String functionName ) {
    return all.get( functionName );
  }
}