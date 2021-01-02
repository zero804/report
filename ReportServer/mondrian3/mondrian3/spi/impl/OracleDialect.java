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
// Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
*/
package mondrian3.spi.impl;

import mondrian3.rolap.SqlStatement;

import java.sql.*;
import java.util.List;
import java.util.regex.*;

/**
 * Implementation of {@link mondrian3.spi.Dialect} for the Oracle database.
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
public class OracleDialect extends JdbcDialectImpl {

    private final String flagsRegexp = "^(\\(\\?([a-zA-Z])\\)).*$";
    private final Pattern flagsPattern = Pattern.compile(flagsRegexp);
    private final String escapeRegexp = "(\\\\Q([^\\\\Q]+)\\\\E)";
    private final Pattern escapePattern = Pattern.compile(escapeRegexp);

    public static final JdbcDialectFactory FACTORY =
        new JdbcDialectFactory(
            OracleDialect.class,
            DatabaseProduct.ORACLE);

    /**
     * Creates an OracleDialect.
     *
     * @param connection Connection
     */
    public OracleDialect(Connection connection) throws SQLException {
        super(connection);
    }

    public OracleDialect() {
    }

    public boolean allowsAs() {
        return false;
    }

    public String generateInline(
        List<String> columnNames,
        List<String> columnTypes,
        List<String[]> valueList)
    {
        return generateInlineGeneric(
            columnNames, columnTypes, valueList,
            " from dual", false);
    }

    public boolean supportsGroupingSets() {
        return true;
    }

    @Override
    public String generateOrderByNulls(
        String expr,
        boolean ascending,
        boolean collateNullsLast)
    {
        return generateOrderByNullsAnsi(expr, ascending, collateNullsLast);
    }

    @Override
    public boolean allowsJoinOn() {
        return false;
    }

    @Override
    public boolean allowsRegularExpressionInWhereClause() {
        return true;
    }

    @Override
    public String generateRegularExpression(
        String source,
        String javaRegex)
    {
        try {
            Pattern.compile(javaRegex);
        } catch (PatternSyntaxException e) {
            // Not a valid Java regex. Too risky to continue.
            return null;
        }
        final Matcher flagsMatcher = flagsPattern.matcher(javaRegex);
        final String suffix;
        if (flagsMatcher.matches()) {
            // We need to convert leading flags into oracle
            // specific flags
            final StringBuilder suffixSb = new StringBuilder();
            final String flags = flagsMatcher.group(2);
            if (flags.contains("i")) {
                suffixSb.append("i");
            }
            if (flags.contains("c")) {
                suffixSb.append("c");
            }
            if (flags.contains("m")) {
                suffixSb.append("m");
            }
            suffix = suffixSb.toString();
            javaRegex =
                javaRegex.substring(0, flagsMatcher.start(1))
                + javaRegex.substring(flagsMatcher.end(1));
        } else {
            suffix = "";
        }
        final Matcher escapeMatcher = escapePattern.matcher(javaRegex);
        while (escapeMatcher.find()) {
            javaRegex =
                javaRegex.replace(
                    escapeMatcher.group(1),
                    escapeMatcher.group(2));
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("REGEXP_LIKE(");
        sb.append(source);
        sb.append(", ");
        quoteStringLiteral(sb, javaRegex);
        sb.append(", ");
        quoteStringLiteral(sb, suffix);
        sb.append(")");
        return sb.toString();
    }

    public void quoteDateLiteral(StringBuilder buf, String value) {
        Date date;
        // the ansi spec for <date string> ::=
        // <years value> <minus sign> <months value> <minus sign> <days value>
        final String ansiDateLiteralFormat = "\\d{2,4}-\\d{1,2}-\\d{1,2}";
        try {
              // The format of the 'value' parameter is not certain.
              // Some JDBC drivers will return a timestamp even though
              // we ask for a date (oracle is one of them). We must try to
              // convert both formats.
            if (Pattern.matches(ansiDateLiteralFormat, value)) {
                date = Date.valueOf(value);
            } else {
                date = new Date(Timestamp.valueOf(value).getTime());
            }
        } catch (IllegalArgumentException ex) {
            throw new NumberFormatException(
                "Illegal DATE literal:  " + value);
        }
        // Date.toString formats date in the date escape
        // format yyyy-mm-dd, which is consistent with the
        // ansi DATE literal spec.
        assert Pattern.matches(ansiDateLiteralFormat, date.toString());
        quoteDateLiteral(buf, date.toString(), date);
    }

    /**
     * Chooses the most appropriate type for accessing the values of a
     * column in a result set.
     *
     * The OracleDialect implementation handles some of the specific
     * quirks of Oracle:  e.g. scale = -127 has special meaning with
     * NUMERIC types and may indicate a FLOAT value if precision is non-zero.
     *
     * @param metaData  Resultset metadata
     * @param columnIndex  index of the column in the result set
     * @return  For Types.NUMERIC and Types.DECIMAL, getType()
     * will return a Type.INT, Type.DOUBLE, or Type.OBJECT based on
     * scale, precision, and column name.
     *
     * @throws SQLException
     */
    @Override
    public SqlStatement.Type getType(
        ResultSetMetaData metaData, int columnIndex)
        throws SQLException
    {
        final int columnType = metaData.getColumnType(columnIndex + 1);
        final int precision = metaData.getPrecision(columnIndex + 1);
        final int scale = metaData.getScale(columnIndex + 1);
        final String columnName = metaData.getColumnName(columnIndex + 1);
        SqlStatement.Type type;

        if (columnType == Types.NUMERIC || columnType == Types.DECIMAL) {
            if (scale == -127 && precision != 0) {
                // non zero precision w/ -127 scale means float in Oracle.
                type = SqlStatement.Type.DOUBLE;
            } else if (columnType == Types.NUMERIC
                && (scale == 0 || scale == -127)
                && precision == 0 && columnName.startsWith("m"))
            {
                // In GROUPING SETS queries, Oracle
                // loosens the type of columns compared to mere GROUP BY
                // queries. We need integer GROUP BY columns to remain integers,
                // otherwise the segments won't be found; but if we convert
                // measure (whose column names are like "m0", "m1") to integers,
                // data loss will occur.
                type = SqlStatement.Type.OBJECT;
            } else if (scale == -127 && precision ==0) {
                type = SqlStatement.Type.INT;
            } else if (scale == 0 && (precision == 38 || precision == 0)) {
                // NUMBER(38, 0) is conventionally used in
                // Oracle for integers of unspecified precision, so let's be
                // bold and assume that they can fit into an int.
                type = SqlStatement.Type.INT;
            } else if (scale == 0 && precision <= 9) {
                // An int (up to 2^31 = 2.1B) can hold any NUMBER(10, 0) value
                // (up to 10^9 = 1B).
                type = SqlStatement.Type.INT;
            } else {
                type = SqlStatement.Type.DOUBLE;
            }

        } else {
            type = super.getType(metaData, columnIndex);
        }
        logTypeInfo(metaData, columnIndex, type);
        return type;
    }
}

// End OracleDialect.java
