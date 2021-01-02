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
 
 
package org.saiku.olap.util;

import org.olap4j.mdx.SelectNode;
import org.olap4j.metadata.Level;
import org.olap4j.metadata.Member;
import org.olap4j.query.Query;
import org.olap4j.query.QueryAxis;
import org.olap4j.query.QueryDimension;
import org.olap4j.query.Selection;
import org.olap4j.query.Selection.Operator;
import org.saiku.olap.util.exception.SaikuIncompatibleException;
import org.saiku.query.QueryHierarchy;
import org.saiku.query.SortOrder;
import org.saiku.query.mdx.GenericFilter;
import org.saiku.query.mdx.IFilterFunction.MdxFunctionType;
import org.saiku.query.mdx.NFilter;

public class QueryConverter {

    public static SelectNode convert(Query query) throws Exception {
        org.saiku.query.Query sQuery = convertQuery(query);
        return sQuery.getSelect();
    }

    public static org.saiku.query.Query convertQuery(Query query) throws Exception {
        org.saiku.query.Query sQuery = new org.saiku.query.Query(query.getName(), query.getCube());

        for (QueryAxis axis : query.getAxes().values()) {
            if (axis.getLocation() != null) {
                org.saiku.query.QueryAxis sAxis = sQuery.getAxis(axis.getLocation());
                convertAxis(axis, sAxis, sQuery);
            }

        }
        return sQuery;
    }

    private static void convertAxis(QueryAxis axis, org.saiku.query.QueryAxis sAxis, org.saiku.query.Query sQuery) throws Exception {

        for (QueryDimension qD : axis.getDimensions()) {
            convertDimension(qD, sAxis, sQuery);
        }

        if (axis.getSortOrder() != null) {
            SortOrder so = SortOrder.valueOf(axis.getSortOrder().toString());
            sAxis.sort(so, axis.getSortIdentifierNodeName());
        }

        if (axis.getFilterCondition() != null) {
            sAxis.addFilter(new GenericFilter(axis.getFilterCondition()));
        }

        if (axis.getLimitFunction() != null) {
            NFilter nf = new NFilter(MdxFunctionType.valueOf(
                    axis.getLimitFunction().toString()),
                    axis.getLimitFunctionN().intValue(),
                    axis.getLimitFunctionSortLiteral());
            sAxis.addFilter(nf);
        }

        sAxis.setNonEmpty(axis.isNonEmpty());




    }

    private static void convertDimension(QueryDimension qD, org.saiku.query.QueryAxis sAxis, org.saiku.query.Query sQuery) throws Exception {
        boolean first = true;
        String hierarchyName = null;
        QueryHierarchy qh = null;
        for (Selection sel : qD.getInclusions()) {
            if (first) {
                if ((sel.getRootElement() instanceof Member)) {
                    hierarchyName = ((Member) sel.getRootElement()).getHierarchy().getUniqueName();
                } else {
                    hierarchyName = ((Level) sel.getRootElement()).getHierarchy().getUniqueName();
                }

                qh = sQuery.getHierarchy(hierarchyName);
                first = false;
            }

            if (sel.getSelectionContext() != null) {
                throw new SaikuIncompatibleException("Cannot convert queries with selection context");
            }
            if ((sel.getRootElement() instanceof Member)) {
                if (sel.getOperator().equals(Operator.MEMBER)) {
                    qh.includeMember(sel.getRootElement().getUniqueName());
                } else {
                    throw new SaikuIncompatibleException("Cannot convert member selection using operator: " + sel.getOperator());
                }
            } else {
                qh.includeLevel(sel.getRootElement().getName());
            }
        }
         sAxis.addHierarchy(qh);

    }

}
