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
// Copyright (C) 2010-2011 Pentaho
// All Rights Reserved.
*/
package mondrian3.parser;

import mondrian3.olap.*;
import mondrian3.server.Statement;

/**
 * Default implementation of {@link MdxParserValidator}, using the
 * <a href="http://java.net/projects/javacc/">JavaCC</a> parser generator.
 *
 * @author jhyde
 */
public class JavaccParserValidatorImpl implements MdxParserValidator {
    private final QueryPartFactory factory;

    /**
     * Creates a JavaccParserValidatorImpl.
     */
    public JavaccParserValidatorImpl() {
        this(new Parser.FactoryImpl());
    }

    /**
     * Creates a JavaccParserValidatorImpl with an explicit factory for parse
     * tree nodes.
     *
     * @param factory Factory for parse tree nodes
     */
    public JavaccParserValidatorImpl(QueryPartFactory factory) {
        this.factory = factory;
    }

    public QueryPart parseInternal(
        Statement statement,
        String queryString,
        boolean debug,
        FunTable funTable,
        boolean strictValidation)
    {
        final MdxParserImpl mdxParser =
            new MdxParserImpl(
                factory,
                statement,
                queryString,
                debug,
                funTable,
                strictValidation);
        try {
            return mdxParser.statementEof();
        } catch (ParseException e) {
            throw convertException(queryString, e);
        }
    }

    public Exp parseExpression(
        Statement statement,
        String queryString,
        boolean debug,
        FunTable funTable)
    {
        final MdxParserImpl mdxParser =
            new MdxParserImpl(
                factory,
                statement,
                queryString,
                debug,
                funTable,
                false);
        try {
            return mdxParser.expressionEof();
        } catch (ParseException e) {
            throw convertException(queryString, e);
        }
    }

    /**
     * Converts the exception so that it looks like the exception produced by
     * JavaCUP. (Not that that format is ideal, but it minimizes test output
     * changes during the transition from JavaCUP to JavaCC.)
     *
     * @param queryString MDX query string
     * @param pe JavaCC parse exception
     * @return Wrapped exception
     */
    private RuntimeException convertException(
        String queryString,
        ParseException pe)
    {
        Exception e;
        if (pe.getMessage().startsWith("Encountered ")) {
            e = new MondrianException(
                "Syntax error at line "
                + pe.currentToken.next.beginLine
                + ", column "
                + pe.currentToken.next.beginColumn
                + ", token '"
                + pe.currentToken.next.image
                + "'");
        } else {
            e = pe;
        }
        return Util.newError(e, "While parsing " + queryString);
    }
}

// End JavaccParserValidatorImpl.java
