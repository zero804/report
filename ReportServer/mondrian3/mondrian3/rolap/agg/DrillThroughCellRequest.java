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
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2012-2012 Pentaho
// All Rights Reserved.
*/
package mondrian3.rolap.agg;

import mondrian3.rolap.RolapStar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Subclass of {@link CellRequest} that allows to specify
 * which columns and measures to return as part of the ResultSet
 * which we return to the client.
 */
public class DrillThroughCellRequest extends CellRequest {

    private final List<RolapStar.Column> drillThroughColumns =
        new ArrayList<RolapStar.Column>();

    private final List<RolapStar.Measure> drillThroughMeasures =
        new ArrayList<RolapStar.Measure>();

    public DrillThroughCellRequest(
        RolapStar.Measure measure,
        boolean extendedContext)
    {
        super(measure, extendedContext, true);
    }

    public void addDrillThroughColumn(RolapStar.Column column) {
        this.drillThroughColumns.add(column);
    }

    public boolean includeInSelect(RolapStar.Column column) {
        if (drillThroughColumns.size() == 0
            && drillThroughMeasures.size() == 0)
        {
            return true;
        }
        return drillThroughColumns.contains(column);
    }

    public void addDrillThroughMeasure(RolapStar.Measure measure) {
        this.drillThroughMeasures.add(measure);
    }

    public boolean includeInSelect(RolapStar.Measure measure) {
        if (drillThroughColumns.size() == 0
            && drillThroughMeasures.size() == 0)
        {
            return true;
        }
        return drillThroughMeasures.contains(measure);
    }

    public List<RolapStar.Measure> getDrillThroughMeasures() {
        return Collections.unmodifiableList(drillThroughMeasures);
    }
}

// End DrillThroughCellRequest.java