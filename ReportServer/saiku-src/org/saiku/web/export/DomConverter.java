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
 
 
package org.saiku.web.export;

class DomConverter {
    public static org.w3c.dom.Document getDom(String html) {
//        ByteArrayInputStream input = new ByteArrayInputStream(html.getBytes());
//        final HtmlCleaner cleaner = createHtmlCleanerWithProperties();
//        DomSerializer doms = new DomSerializer(cleaner.getProperties(), false);
//        try {
//            TagNode node = cleaner.clean(input);
//            return doms.createDOM(node);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    	throw new RuntimeException("not implemented");
    }

//    private static HtmlCleaner createHtmlCleanerWithProperties() {
//        HtmlCleaner cleaner = new HtmlCleaner();
//        CleanerProperties props = cleaner.getProperties();
//        props.setAdvancedXmlEscape(true);
//        props.setRecognizeUnicodeChars(true);
//        props.setTranslateSpecialEntities(true);
//        return cleaner;
//    }
}
