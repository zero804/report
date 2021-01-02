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
// Copyright (C) 2011-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.rolap.agg;

import mondrian3.rolap.StarColumnPredicate;

/**
 * Utilities for {@link mondrian3.rolap.StarPredicate}s and
 * {@link mondrian3.rolap.StarColumnPredicate}s.
 *
 * @author jhyde
 */
public class StarPredicates {
    /**
     * Optimizes a column predicate.
     *
     * @param predicate Column predicate
     * @return Optimized predicate
     */
    public static StarColumnPredicate optimize(StarColumnPredicate predicate) {
        if (predicate instanceof ListColumnPredicate && false) {
            ListColumnPredicate listColumnPredicate =
                (ListColumnPredicate) predicate;

            switch (listColumnPredicate.getPredicates().size()) {
            case 0:
                return new LiteralStarPredicate(
                    predicate.getConstrainedColumn(), false);
            case 1:
                return listColumnPredicate.getPredicates().get(0);
            default:
                return listColumnPredicate;
            }
        }
        return predicate;
    }
}

// End StarPredicates.java
