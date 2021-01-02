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
// Copyright (C) 2004-2005 TONBELLER AG
// Copyright (C) 2006-2009 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.rolap;

import mondrian3.olap.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Composite of {@link RolapNative}s. Uses chain of responsibility
 * to select the appropriate {@link RolapNative} evaluator.
 */
public class RolapNativeRegistry extends RolapNative {

    private Map<String, RolapNative> nativeEvaluatorMap =
        new HashMap<String, RolapNative>();

    public RolapNativeRegistry() {
        super.setEnabled(true);

        /*
         * Mondrian functions which might be evaluated natively.
         */
        register("NonEmptyCrossJoin".toUpperCase(), new RolapNativeCrossJoin());
        register("CrossJoin".toUpperCase(), new RolapNativeCrossJoin());
        register("TopCount".toUpperCase(), new RolapNativeTopCount());
        register("Filter".toUpperCase(), new RolapNativeFilter());
    }

    /**
     * Returns the matching NativeEvaluator or null if <code>fun</code> can not
     * be executed in SQL for the given context and arguments.
     */
    public NativeEvaluator createEvaluator(
        RolapEvaluator evaluator, FunDef fun, Exp[] args)
    {
        if (!isEnabled()) {
            return null;
        }

        RolapNative rn = nativeEvaluatorMap.get(fun.getName().toUpperCase());

        if (rn == null) {
            return null;
        }

        NativeEvaluator ne = rn.createEvaluator(evaluator, fun, args);

        if (ne != null) {
            if (listener != null) {
                NativeEvent e = new NativeEvent(this, ne);
                listener.foundEvaluator(e);
            }
        }
        return ne;
    }

    public void register(String funName, RolapNative rn) {
        nativeEvaluatorMap.put(funName, rn);
    }

    /** for testing */
    void setListener(Listener listener) {
        super.setListener(listener);
        for (RolapNative rn : nativeEvaluatorMap.values()) {
            rn.setListener(listener);
        }
    }

    /** for testing */
    void useHardCache(boolean hard) {
        for (RolapNative rn : nativeEvaluatorMap.values()) {
            rn.useHardCache(hard);
        }
    }
}

// End RolapNativeRegistry.java
