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
// Copyright (C) 2002-2005 Julian Hyde
// Copyright (C) 2005-2009 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap.fun;

import mondrian3.olap.*;

import java.util.List;

/**
 * A <code>Resolver</code> converts a function name, invocation type, and set
 * of arguments into a {@link FunDef}.
 *
 * @author jhyde
 * @since 3 March, 2002
 */
public interface Resolver {
    /**
     * Returns the name of the function or operator.
     */
    String getName();

    /**
     * Returns the description of the function or operator.
     */
    String getDescription();

    /**
     * Returns the syntax with which the function or operator was invoked.
     */
    Syntax getSyntax();

    /**
     * Given a particular set of arguments the function is applied to, returns
     * the correct overloaded form of the function.
     *
     * <p>The method adds an item to <code>conversions</code> every
     * time it performs an implicit type-conversion. If there are several
     * candidate functions with the same signature, the validator will choose
     * the one which used the fewest implicit conversions.</p>
     *
     * @param args Expressions which this function call is applied to.
     * @param validator Validator
     * @param conversions List of implicit conversions performed (out)
     *
     * @return The function definition which matches these arguments, or null
     *   if no function definition that this resolver knows about matches.
     */
    FunDef resolve(
        Exp[] args,
        Validator validator,
        List<Conversion> conversions);

    /**
     * Returns whether a particular argument must be a scalar expression.
     * Returns <code>false</code> if any of the variants of this resolver
     * allows a set as its <code>k</code>th argument; true otherwise.
     */
    boolean requiresExpression(int k);

    /**
     * Returns an array of symbolic constants which can appear as arguments
     * to this function.
     *
     * <p>For example, the <code>DrilldownMember</code> may take the symbol
     * <code>RECURSIVE</code> as an argument. Most functions do not define
     * any symbolic constants.
     *
     * @return An array of the names of the symbolic constants
     */
    String[] getReservedWords();

    /**
     * Returns a string describing the syntax of this function, for example
     * <pre><code>StrToSet(<String Expression>)</code></pre>
     */
    String getSignature();

    /**
     * Returns a representative example of the function which this Resolver
     * can produce, for purposes of describing the function set. May return
     * null if there is no representative function, or if the Resolver has
     * a way to describe itself in more detail.
     */
    FunDef getFunDef();

    /**
     * Description of an implicit conversion that occurred while resolving an
     * operator call.
     */
    public interface Conversion {
        /**
         * Returns the cost of the conversion. If there are several matching
         * overloads, the one with the lowest overall cost will be preferred.
         *
         * @return Cost of conversion
         */
        int getCost();

        /**
         * Checks the viability of implicit conversions. Converting from a
         * dimension to a hierarchy is valid if is only one hierarchy.
         */
        void checkValid();

        /**
         * Applies this conversion to its argument, modifying the argument list
         * in place.
         *
         * @param validator Validator
         * @param args Argument list
         */
        void apply(Validator validator, List<Exp> args);
    }
}

// End Resolver.java
