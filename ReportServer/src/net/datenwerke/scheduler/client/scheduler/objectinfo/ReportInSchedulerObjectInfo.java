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
 
 
package net.datenwerke.scheduler.client.scheduler.objectinfo;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.objectinformation.hooks.ObjectInfoAdditionalInfoProvider;
import net.datenwerke.gxtdto.client.ui.helper.info.InfoWindow;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.scheduler.client.scheduler.SchedulerDao;
import net.datenwerke.rs.scheduler.client.scheduler.locale.SchedulerMessages;

/**
 * 
 *
 */
public class ReportInSchedulerObjectInfo implements ObjectInfoAdditionalInfoProvider {

	private final SchedulerDao schedulerDao;

	@Inject
	public ReportInSchedulerObjectInfo(SchedulerDao schedulerDao) {
		this.schedulerDao = schedulerDao;
	}

	@Override
	public boolean consumes(Object object) {
		return object instanceof ReportDto;
	}

	@Override
	public void addInfoFor(Object object, InfoWindow window) {
		final DwContentPanel panel = window.addDelayedSimpelInfoPanel(SchedulerMessages.INSTANCE.scheduler());

		schedulerDao.getReportJobListAsHtml((ReportDto) object, new RsAsyncCallback<SafeHtml>() {
			@Override
			public void onSuccess(SafeHtml result) {
				panel.clear();
				panel.enableScrollContainer();

				if (null == result)
					panel.add(new Label(SchedulerMessages.INSTANCE.reportNotInJobMessages()));
				else {
					SafeHtmlBuilder builder = new SafeHtmlBuilder();
					builder.appendHtmlConstant("<div class=\"rs-infopanel-reportinscheduler\">");
					builder.append(result);
					panel.add(new HTML(builder.toSafeHtml()));
				}

				panel.forceLayout();
			}
		});
	}

}
