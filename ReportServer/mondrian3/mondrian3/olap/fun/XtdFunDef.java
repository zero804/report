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
// Copyright (C) 2005-2005 Julian Hyde
// Copyright (C) 2005-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.olap.fun;

import mondrian3.calc.*;
import mondrian3.calc.impl.AbstractListCalc;
import mondrian3.calc.impl.UnaryTupleList;
import mondrian3.mdx.ResolvedFunCall;
import mondrian3.olap.DimensionType;
import mondrian3.olap.*;
import mondrian3.olap.LevelType;
import mondrian3.olap.type.*;
import mondrian3.resource.MondrianResource;
import mondrian3.rolap.RolapCube;
import mondrian3.rolap.RolapHierarchy;

/**
 * Definition of <code>Ytd</code>, <code>Qtd</code>, <code>Mtd</code>,
 * and <code>Wtd</code> MDX builtin functions.
 *
 * @author jhyde
 * @since Mar 23, 2006
 */
class XtdFunDef extends FunDefBase {
    private final LevelType levelType;

    static final ResolverImpl MtdResolver =
        new ResolverImpl(
            "Mtd",
            "Mtd([<Member>])",
            "A shortcut function for the PeriodsToDate function that specifies the level to be Month.",
            new String[]{"fx", "fxm"},
            LevelType.TimeMonths);

    static final ResolverImpl QtdResolver =
        new ResolverImpl(
            "Qtd",
            "Qtd([<Member>])",
            "A shortcut function for the PeriodsToDate function that specifies the level to be Quarter.",
            new String[]{"fx", "fxm"},
            LevelType.TimeQuarters);

    static final ResolverImpl WtdResolver =
        new ResolverImpl(
            "Wtd",
            "Wtd([<Member>])",
            "A shortcut function for the PeriodsToDate function that specifies the level to be Week.",
            new String[]{"fx", "fxm"},
            LevelType.TimeWeeks);

    static final ResolverImpl YtdResolver =
        new ResolverImpl(
            "Ytd",
            "Ytd([<Member>])",
            "A shortcut function for the PeriodsToDate function that specifies the level to be Year.",
            new String[]{"fx", "fxm"},
            LevelType.TimeYears);

    public XtdFunDef(FunDef dummyFunDef, LevelType levelType) {
        super(dummyFunDef);
        this.levelType = levelType;
    }

    public Type getResultType(Validator validator, Exp[] args) {
        if (args.length == 0) {
            // With no args, the default implementation cannot
            // guess the hierarchy.
            RolapHierarchy defaultTimeHierarchy =
                ((RolapCube) validator.getQuery().getCube()).getTimeHierarchy(
                    getName());
            return new SetType(MemberType.forHierarchy(defaultTimeHierarchy));
        }
        final Type type = args[0].getType();
        if (type.getDimension().getDimensionType()
            != DimensionType.TimeDimension)
        {
            throw MondrianResource.instance().TimeArgNeeded.ex(getName());
        }
        return super.getResultType(validator, args);
    }

    private Level getLevel(Evaluator evaluator) {
        switch (levelType) {
        case TimeYears:
            return evaluator.getCube().getYearLevel();
        case TimeQuarters:
            return evaluator.getCube().getQuarterLevel();
        case TimeMonths:
            return evaluator.getCube().getMonthLevel();
        case TimeWeeks:
            return evaluator.getCube().getWeekLevel();
        case TimeDays:
            return evaluator.getCube().getWeekLevel();
        default:
            throw Util.badValue(levelType);
        }
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final Level level = getLevel(compiler.getEvaluator());
        switch (call.getArgCount()) {
        case 0:
            return new AbstractListCalc(call, new Calc[0]) {
                public TupleList evaluateList(Evaluator evaluator) {
                    return new UnaryTupleList(
                        periodsToDate(evaluator, level, null));
                }

                public boolean dependsOn(Hierarchy hierarchy) {
                    return hierarchy.getDimension().getDimensionType()
                        == mondrian3.olap.DimensionType.TimeDimension;
                }
            };
        default:
            final MemberCalc memberCalc =
                compiler.compileMember(call.getArg(0));
            return new AbstractListCalc(call, new Calc[] {memberCalc}) {
                public TupleList evaluateList(Evaluator evaluator) {
                    return new UnaryTupleList(
                        periodsToDate(
                            evaluator,
                            level,
                            memberCalc.evaluateMember(evaluator)));
                }
            };
        }
    }

    private static class ResolverImpl extends MultiResolver {
        private final LevelType levelType;

        public ResolverImpl(
            String name,
            String signature,
            String description,
            String[] signatures,
            LevelType levelType)
        {
            super(name, signature, description, signatures);
            this.levelType = levelType;
        }

        protected FunDef createFunDef(Exp[] args, FunDef dummyFunDef) {
            return new XtdFunDef(dummyFunDef, levelType);
        }
    }
}

// End XtdFunDef.java