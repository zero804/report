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
 
 
package net.datenwerke.rs.tabletemplate.client.engines.jxls2.hookers;

import com.google.gwt.resources.client.ImageResource;

import net.datenwerke.rs.tabletemplate.client.engines.jxls2.JXls2TemplateUIModule;
import net.datenwerke.rs.tabletemplate.client.engines.jxls2.locale.JXls2TemplateMessages;
import net.datenwerke.rs.tabletemplate.client.tabletemplate.hooks.TableTemplateClientProviderHook;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

public class JXls2TemplateClientProvider implements TableTemplateClientProviderHook {

    @Override
    public String getType() {
        return JXls2TemplateUIModule.TEMPLATE_TYPE;
    }

    @Override
    public String getName() {
        return "JXLS";
    }

    @Override
    public String getDescription() {
        return JXls2TemplateMessages.INSTANCE.templateTypeDescription();
    }

    @Override
    public ImageResource getIconLarge() {
        return BaseIcon.fromFileExtension("xls").toImageResource(1);
    }

    @Override
    public ImageResource getIconSmall() {
        return BaseIcon.fromFileExtension("xls").toImageResource();
    }

    @Override
    public ContentTypeConfig getContentTypeConfig() {
        return new ContentTypeConfig() {
            @Override
            public boolean isDisplay() {
                return false;
            }

            @Override
            public String getDefaultContentType() {
                return null;
            }

            @Override
            public String getDefaultFileExtension() {
                return null;
            }
        };
    }

}
