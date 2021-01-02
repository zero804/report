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
package mondrian3.calc.impl;

import mondrian3.calc.TupleCursor;
import mondrian3.olap.Evaluator;
import mondrian3.olap.Member;

/**
 * Abstract implementation of {@link mondrian3.calc.TupleIterator}.
 *
 * <p>Derived classes need to implement only {@link #forward()}.
 *
 * @author jhyde
 */
public abstract class AbstractTupleCursor implements TupleCursor {
    protected final int arity;

    public AbstractTupleCursor(int arity) {
        super();
        this.arity = arity;
    }

    public void setContext(Evaluator evaluator) {
        evaluator.setContext(current());
    }

    public void currentToArray(Member[] members, int offset) {
        if (offset == 0) {
            current().toArray(members);
        } else {
            //noinspection SuspiciousSystemArraycopy
            System.arraycopy(current().toArray(), 0, members, offset, arity);
        }
    }

    public int getArity() {
        return arity;
    }

    public Member member(int column) {
        return current().get(column);
    }
}

// End AbstractTupleCursor.java
