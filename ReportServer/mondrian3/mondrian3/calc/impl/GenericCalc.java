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
package mondrian3.calc.impl;

import mondrian3.calc.*;
import mondrian3.olap.*;
import mondrian3.olap.fun.FunUtil;

import java.util.Date;

/**
 * Adapter which computes a scalar or tuple expression and converts it to any
 * required type.
 *
 * @see mondrian3.calc.impl.GenericIterCalc
 *
 * @author jhyde
 * @since Sep 26, 2005
 */
public abstract class GenericCalc
    extends AbstractCalc
    implements TupleCalc,
    StringCalc, IntegerCalc, DoubleCalc, BooleanCalc, DateTimeCalc,
    VoidCalc, MemberCalc, LevelCalc, HierarchyCalc, DimensionCalc
{
    /**
     * Creates a GenericCalc without specifying child calculated expressions.
     *
     * <p>Subclass should override {@link #getCalcs()}.
     *
     * @param exp Source expression
     */
    protected GenericCalc(Exp exp) {
        super(exp, null);
    }

    /**
     * Creates an GenericCalc.
     *
     * @param exp Source expression
     * @param calcs Child compiled expressions
     */
    protected GenericCalc(Exp exp, Calc[] calcs) {
        super(exp, calcs);
    }

    public Member[] evaluateTuple(Evaluator evaluator) {
        return (Member[]) evaluate(evaluator);
    }

    private String msg(TypeEnum expectedType, Object o) {
        final TypeEnum actualType = actualType(o);
        return "Expected value of type " + expectedType + "; got value '" + o
           + "' (" + (actualType == null ? o.getClass() : actualType) + ")";
    }

    private static TypeEnum actualType(Object o) {
        if (o == null) {
            return TypeEnum.NULL;
        } else if (o instanceof String) {
            return TypeEnum.STRING;
        } else if (o instanceof Boolean) {
            return TypeEnum.BOOLEAN;
        } else if (o instanceof Number) {
            return TypeEnum.NUMERIC;
        } else if (o instanceof Date) {
            return TypeEnum.DATETIME;
        } else if (o instanceof Member) {
            return TypeEnum.MEMBER;
        } else if (o instanceof Level) {
            return TypeEnum.LEVEL;
        } else if (o instanceof Hierarchy) {
            return TypeEnum.HIERARCHY;
        } else if (o instanceof Dimension) {
            return TypeEnum.DIMENSION;
        } else {
            return null;
        }
    }

    public String evaluateString(Evaluator evaluator) {
        final Object o = evaluate(evaluator);
        try {
            return (String) o;
        } catch (ClassCastException e) {
            throw evaluator.newEvalException(null, msg(TypeEnum.STRING, o));
        }
    }

    public int evaluateInteger(Evaluator evaluator) {
        Object o = evaluate(evaluator);
        try {
            final Number number = (Number) o;
            return number == null
                ? FunUtil.IntegerNull
                : number.intValue();
        } catch (ClassCastException e) {
            throw evaluator.newEvalException(null, msg(TypeEnum.NUMERIC, o));
        }
    }

    public double evaluateDouble(Evaluator evaluator) {
        final Object o = evaluate(evaluator);
        try {
            final Number number = (Number) o;
            return numberToDouble(number);
        } catch (ClassCastException e) {
            throw evaluator.newEvalException(null, msg(TypeEnum.NUMERIC, o));
        }
    }

    public static double numberToDouble(Number number) {
        return number == null
            ? FunUtil.DoubleNull
            : number.doubleValue();
    }

    public boolean evaluateBoolean(Evaluator evaluator) {
        final Object o = evaluate(evaluator);
        try {
            return (Boolean) o;
        } catch (ClassCastException e) {
            throw evaluator.newEvalException(null, msg(TypeEnum.BOOLEAN, o));
        }
    }

    public Date evaluateDateTime(Evaluator evaluator) {
        final Object o = evaluate(evaluator);
        try {
            return (Date) o;
        } catch (ClassCastException e) {
            throw evaluator.newEvalException(null, msg(TypeEnum.DATETIME, o));
        }
    }

    public void evaluateVoid(Evaluator evaluator) {
        final Object result = evaluate(evaluator);
        assert result == null;
    }

    public Member evaluateMember(Evaluator evaluator) {
        final Object o = evaluate(evaluator);
        try {
            return (Member) o;
        } catch (ClassCastException e) {
            throw evaluator.newEvalException(null, msg(TypeEnum.MEMBER, o));
        }
    }

    public Level evaluateLevel(Evaluator evaluator) {
        final Object o = evaluate(evaluator);
        try {
            return (Level) o;
        } catch (ClassCastException e) {
            throw evaluator.newEvalException(null, msg(TypeEnum.LEVEL, o));
        }
    }

    public Hierarchy evaluateHierarchy(Evaluator evaluator) {
        final Object o = evaluate(evaluator);
        try {
            return (Hierarchy) o;
        } catch (ClassCastException e) {
            throw evaluator.newEvalException(null, msg(TypeEnum.HIERARCHY, o));
        }
    }

    public Dimension evaluateDimension(Evaluator evaluator) {
        final Object o = evaluate(evaluator);
        try {
            return (Dimension) o;
        } catch (ClassCastException e) {
            throw evaluator.newEvalException(null, msg(TypeEnum.DIMENSION, o));
        }
    }

    private enum TypeEnum {
        NULL,
        BOOLEAN, STRING, NUMERIC, DATETIME,
        MEMBER, LEVEL, HIERARCHY, DIMENSION
    }
}

// End GenericCalc.java
