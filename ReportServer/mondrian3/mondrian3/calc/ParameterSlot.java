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
// Copyright (C) 2006-2010 Pentaho
// All Rights Reserved.
*/
package mondrian3.calc;

import mondrian3.olap.Parameter;

/**
 * Implementation of a parameter.
 *
 * @author jhyde
 * @since Jul 25, 2006
 */
public interface ParameterSlot {
    /**
     * Returns the unique index of the slot.
     */
    int getIndex();

    /**
     * Returns a compiled expression to compute the default value of the
     * parameter.
     */
    Calc getDefaultValueCalc();

    /**
     * Returns the parameter.
     */
    Parameter getParameter();

    /**
     * Sets the value of this parameter.
     *
     * <p>NOTE: This method will be removed when we store parameter values
     * in the {@link mondrian3.olap.Result} rather than in the
     * {@link mondrian3.olap.Query}.
     *
     * @param value New value
     * @param assigned Whether {@link #isParameterSet()} should return true;
     *   supply value {@code false} if this is an internal assignment, to
     *   remember the default value
     */
    void setParameterValue(Object value, boolean assigned);

    /**
     * Returns the value of this parameter.
     *
     * <p>NOTE: This method will be removed when we store parameter values
     * in the {@link mondrian3.olap.Result} rather than in the
     * {@link mondrian3.olap.Query}.
     */
    Object getParameterValue();

    /**
     * Returns whether the parameter has been assigned a value. (That value
     * may be null.)
     *
     * @return Whether parmaeter has been assigned a value.
     */
    boolean isParameterSet();

    void setCachedDefaultValue(Object value);

    Object getCachedDefaultValue();

    /**
     * Unsets the parameter value.
     */
    void unsetParameterValue();
}

// End ParameterSlot.java
