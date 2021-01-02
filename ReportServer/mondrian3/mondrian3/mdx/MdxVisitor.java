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
// Copyright (C) 2006-2006 Pentaho
// All Rights Reserved.
*/
package mondrian3.mdx;

import mondrian3.olap.*;

/**
 * Interface for a visitor to an MDX parse tree.
 *
 * @author jhyde
 * @since Jul 21, 2006
 */
public interface MdxVisitor {
    /**
     * @return Indicates whether the visitee should call accept on it's children
     */
    boolean shouldVisitChildren();

    /**
     * Visits a Query.
     *
     * @see Query#accept(MdxVisitor)
     */
    Object visit(Query query);

    /**
     * Visits a QueryAxis.
     *
     * @see QueryAxis#accept(MdxVisitor)
     */
    Object visit(QueryAxis queryAxis);

    /**
     * Visits a Formula.
     *
     * @see Formula#accept(MdxVisitor)
     */
    Object visit(Formula formula);

    /**
     * Visits an UnresolvedFunCall.
     *
     * @see UnresolvedFunCall#accept(MdxVisitor)
     */
    Object visit(UnresolvedFunCall call);

    /**
     * Visits a ResolvedFunCall.
     *
     * @see ResolvedFunCall#accept(MdxVisitor)
     */
    Object visit(ResolvedFunCall call);

    /**
     * Visits an Id.
     *
     * @see Id#accept(MdxVisitor)
     */
    Object visit(Id id);

    /**
     * Visits a Parameter.
     *
     * @see ParameterExpr#accept(MdxVisitor)
     */
    Object visit(ParameterExpr parameterExpr);

    /**
     * Visits a DimensionExpr.
     *
     * @see DimensionExpr#accept(MdxVisitor)
     */
    Object visit(DimensionExpr dimensionExpr);

    /**
     * Visits a HierarchyExpr.
     *
     * @see HierarchyExpr#accept(MdxVisitor)
     */
    Object visit(HierarchyExpr hierarchyExpr);

    /**
     * Visits a LevelExpr.
     *
     * @see LevelExpr#accept(MdxVisitor)
     */
    Object visit(LevelExpr levelExpr);

    /**
     * Visits a MemberExpr.
     *
     * @see MemberExpr#accept(MdxVisitor)
     */
    Object visit(MemberExpr memberExpr);

    /**
     * Visits a NamedSetExpr.
     *
     * @see NamedSetExpr#accept(MdxVisitor)
     */
    Object visit(NamedSetExpr namedSetExpr);

    /**
     * Visits a Literal.
     *
     * @see Literal#accept(MdxVisitor)
     */
    Object visit(Literal literal);
}

// End MdxVisitor.java
