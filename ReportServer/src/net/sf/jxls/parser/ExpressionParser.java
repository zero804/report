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
 
 
package net.sf.jxls.parser;

import java.util.Map;

import net.sf.jxls.transformer.Configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Expression parser class
 * @author Leonid Vysochyn
 */
public class ExpressionParser {
    protected static final Log log = LogFactory.getLog(ExpressionParser.class);
    String expression;
    Map beans;
    Configuration configuration;


    public ExpressionParser(String expression, Map beans, Configuration configuration) {
        this.expression = expression;
        this.beans = beans;
        this.configuration = configuration;
    }

    public Expression parse(){
        Expression expr = null;
        if( expression!=null ){
            expression = expression.trim();
            if( expression.startsWith(configuration.getStartExpressionToken()) && expression.endsWith( configuration.getEndExpressionToken() )){
                try {
                    expr = new Expression( expression.substring(2, expression.length() - 1), beans, configuration);
                } catch (Exception e) {
                    log.error("Can't parse expression " + expression);
                }
            }else{
                log.warn("Expression should start with " + configuration.getStartExpressionToken() + " and end with " + configuration.getEndExpressionToken()
                + " but was " + expression);
            }
        }
        return expr;
    }

}
