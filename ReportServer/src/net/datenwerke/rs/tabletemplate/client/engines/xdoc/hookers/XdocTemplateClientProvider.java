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
 
 
package net.datenwerke.rs.tabletemplate.client.engines.xdoc.hookers;

import com.google.gwt.resources.client.ImageResource;

import net.datenwerke.rs.tabletemplate.client.engines.xdoc.XdocTemplateUIModule;
import net.datenwerke.rs.tabletemplate.client.engines.xdoc.locale.XdocTemplateMessages;
import net.datenwerke.rs.tabletemplate.client.tabletemplate.hooks.TableTemplateClientProviderHook;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

public class XdocTemplateClientProvider implements
		TableTemplateClientProviderHook {

	@Override
	public String getType() {
		return XdocTemplateUIModule.TEMPLATE_TYPE;
	}

	@Override
	public String getName() {
		return "XDOC";
	}

	@Override
	public String getDescription() {
		return XdocTemplateMessages.INSTANCE.templateTypeDescription();
	}

	@Override
	public ImageResource getIconLarge() {
		return BaseIcon.fromFileExtension("doc").toImageResource(1);
	}

	@Override
	public ImageResource getIconSmall() {
		return BaseIcon.fromFileExtension("doc").toImageResource();
	}
	
	@Override
	public ContentTypeConfig getContentTypeConfig() {
		return new ContentTypeConfig(){
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
