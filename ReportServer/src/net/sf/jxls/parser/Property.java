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

import net.sf.jxls.transformer.Configuration;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Map;

/**
 * Represents a property in excel template
 * @author Leonid Vysochyn
 */
public class Property {
    protected static final Log log = LogFactory.getLog(Property.class);

    private Object bean;
    private String collectionName;
    private Collection collection;

    private String property;
    private Object propertyValue;

    Configuration config;

    public Property(String value) {
        propertyValue = value;
    }

    public Property(String property, Map beans, Configuration config) {
        this.property = property;
        this.config = config;
        propertyValue = getPropertyValue(beans);
    }

    public boolean isConstant() {
        return property == null;
    }

    public Object getPropertyValue(Map beans) {
        JexlContext context = new MapContext(beans);
        ExpressionCollectionParser parser = new ExpressionCollectionParser(context, this.property + ";", config.isJexlInnerCollectionsAccess());
        if (parser.getCollection() == null) {
            propertyValue = null;
        } else {
            collectionName = parser.getCollectionExpression();
            collection = parser.getCollection();
            bean = null;
        }

        return propertyValue;
    }

    public boolean isCollection() {
        return collectionName != null;
    }

    public boolean isNull() {
        return getPropertyValue() == null;
    }

    public String getBeanName() {
        return null;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getProperty() {
        return property;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public String getFullCollectionName() {
        return collectionName;
    }

    public String getPropertyNameAfterLastDot() {
        String propertyName = null;
        if (property != null) {
            int dotIndex = property.lastIndexOf(".");
            if (dotIndex >= 0) {
                propertyName = property.substring(dotIndex + 1);
            } else {
                propertyName = property;
            }
        }
        return propertyName;
    }

    public String getPropertyNameAfterFirstDot() {
        String propertyName = null;
        if (property != null) {
            int dotIndex = property.indexOf(".");
            if (dotIndex >= 0) {
                propertyName = property.substring(dotIndex + 1);
            } else {
                propertyName = property;
            }
        }
        return propertyName;
    }

    public String toString() {
        return "Property{" + "property='" + property + "'}";
    }

    public Object getPropertyValue() {
        if (bean instanceof String) {
            return bean;
        }
        return propertyValue;
    }

    public void setPropertyValue(Object propertyValue) {
        this.propertyValue = propertyValue;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

}
