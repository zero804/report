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
// Copyright (C) 2003-2005 Julian Hyde
// Copyright (C) 2005-2009 Pentaho
// Copyright (C) 2004-2005 TONBELLER AG
// All Rights Reserved.
*/
package mondrian3.rolap;

import mondrian3.olap.*;

import java.util.EventObject;

/**
 * A factory for {@link mondrian3.olap.NativeEvaluator}.
 * If the instance returns null,
 * then the interpreter must compute the result itself.
 * If it returns a NativeEvaluator
 * the interpreter may choose to let the NativeEvaluator compute the result.
 *
 * <p>There exist multiple RolapNative implementations, e.g. for CrossJoin,
 * TopCount, Filter etc. If the arguments of these functions are simple enough
 * to be evaluated in SQL then a NativeEvaluator will be returned that performs
 * the computations in SQL. Otherwise null will be returned.
 */
public abstract class RolapNative {

    private boolean enabled;

    static class NativeEvent extends EventObject {
        private final NativeEvaluator neval;
        public NativeEvent(Object source, NativeEvaluator neval) {
            super(source);
            this.neval = neval;
        }
        NativeEvaluator getNativeEvaluator() {
            return neval;
        }
    }

    static class TupleEvent extends EventObject {
        private final TupleReader tupleReader;

        public TupleEvent(Object source, TupleReader tupleReader) {
            super(source);
            this.tupleReader = tupleReader;
        }

        TupleReader getTupleReader() {
            return tupleReader;
        }
    }

    interface Listener {
        void foundEvaluator(NativeEvent e);
        void foundInCache(TupleEvent e);
        void executingSql(TupleEvent e);
    }

    protected Listener listener;

    /**
     * If function can be implemented in SQL, returns a NativeEvaluator that
     * computes the result; otherwise returns null.
     */
    abstract NativeEvaluator createEvaluator(
        RolapEvaluator evaluator,
        FunDef fun,
        Exp[] args);

    /**
     * if enabled == false, then createEvaluator will always return null
     */
    boolean isEnabled() {
        return enabled;
    }

    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    Listener getListener() {
        return listener;
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Sets whether to use hard caching for testing.
     * When using soft references, we can not test caching
     * because things may be garbage collected during the tests.
     */
    abstract void useHardCache(boolean hard);
}

// End RolapNative.java
