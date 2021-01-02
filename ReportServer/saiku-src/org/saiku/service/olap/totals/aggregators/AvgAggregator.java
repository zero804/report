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
import org.olap4j.Cell;
import org.olap4j.OlapException;
import org.olap4j.metadata.Datatype;
import org.olap4j.metadata.Measure;
import org.olap4j.metadata.Property;

import java.util.Set;

public class AvgAggregator extends TotalAggregator {
  private static final Property DRILLTHROUGH_COUNT = new Property() {

    public String getCaption() {
      return null;
    }

    public String getDescription() {
      return null;
    }

    public String getName() {
      return "DRILLTHROUGH_COUNT";
    }

    public String getUniqueName() {
      return null;
    }

    public boolean isVisible() {
      return false;
    }

    public ContentType getContentType() {
      return null;
    }

    public Datatype getDatatype() {
      return null;
    }

    public Set<TypeFlag> getType() {
      return null;
    }

  };

  AvgAggregator(Format format) {
    super( format );
  }

  private double accumulator = 0.0;
  private long count = 0;

  public void addData( Cell cell ) {
    Object value = cell.getValue();
    if ( value instanceof Number ) {
    	
    	/* RS-2662: we don't use this property since it is either null for saiku reports
    	 * or incorrect for pivot reports.
    	 */
    	
//      Integer count = (Integer) cell.getPropertyValue( DRILLTHROUGH_COUNT );
//      double doubleVal;
//      try {
//        doubleVal = cell.getDoubleValue();
//      } catch ( OlapException e ) {
//        throw new RuntimeException( e );
//      }
//      if ( count.longValue() > -1 ) {
//        this.count += count.longValue();
//        accumulator += doubleVal * count.doubleValue();
//      } else {
//        this.count++;
//        accumulator += doubleVal;
//      }

    	double doubleVal;
    	try {
	        doubleVal = cell.getDoubleValue();
	      } catch ( OlapException e ) {
	        throw new RuntimeException( e );
	      }	      
    	this.count++;
    	accumulator += doubleVal;
    }
  }

  @Override
  public void addData( double data ) {
  }


  @Override
  public Double getValue() {
    if ( count > 0 ) {
      return accumulator / count;
    }
    return null;
  }

  @Override
  public TotalAggregator newInstance( Format format, Measure measure ) {
    return new AvgAggregator( format );
  }

}