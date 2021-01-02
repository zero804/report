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
 
 
package net.sf.jxls.tag;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines mapping between java class files and jx tag names
 * @author Leonid Vysochyn
 */
public class JxTaglib implements TagLib {
    static String[] tagName = new String[]{ "forEach", "if", "outline", "out"};
    static String[] tagClassName = new String[]{ "net.sf.jxls.tag.ForEachTag", "net.sf.jxls.tag.IfTag", "net.sf.jxls.tag.OutlineTag", "net.sf.jxls.tag.OutTag" };

    static Map tags = new HashMap();

    static{
        for (int i = 0; i < tagName.length; i++) {
            String key = tagName[i];
            String value = tagClassName[i];
            tags.put( key, value );
        }
    }

    public Map getTags(){
        return tags;
    }
}