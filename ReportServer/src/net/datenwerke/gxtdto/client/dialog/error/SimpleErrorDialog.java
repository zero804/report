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
 
 
package net.datenwerke.gxtdto.client.dialog.error;

import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.layout.DwHorizontalFlowLayoutContainer;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.user.client.ui.HTML;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class SimpleErrorDialog extends DwWindow {
	
	private final SafeHtml msg;
	private final SafeHtml title;

	private boolean isDuringRender;
	
	public SimpleErrorDialog(String msg){
		this(BaseMessages.INSTANCE.error(), msg);
	}
	
	public SimpleErrorDialog(String title, String msg){
		super();
		
		if(title.isEmpty())
			title = BaseMessages.INSTANCE.error();
		
		this.title = SimpleHtmlSanitizer.sanitizeHtml(title);
		this.msg = new SafeHtmlBuilder().appendEscapedLines(msg).toSafeHtml();
		
		setModal(true);
		
		initializeUi();
	}

	protected void initializeUi() {
		/* create window */
		setOnEsc(false);
		setHeading(title);

		/* error message */
		DwHorizontalFlowLayoutContainer blc = new DwHorizontalFlowLayoutContainer();
		add(blc);
		
		HTML image = new HTML(BaseIcon.ERROR.toSafeHtml(1));
		blc.add(image, new MarginData(10,0,10,10));
		image.getElement().getStyle().setFloat(Float.LEFT);
		
		
		HTML textMsg = new HTML(msg);
		textMsg.getElement().getStyle().setFloat(Float.LEFT);
		textMsg.setWidth("80%");
		blc.add(textMsg, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(10)));
		
		
		if(msg.asString().length() > 150)
			setWidth(500);
				
		/* buttons */
		DwTextButton okButton = new DwTextButton(BaseMessages.INSTANCE.ok());
		okButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				submitButtonPressed();
				SimpleErrorDialog.super.hide();
			}
		});
		
		addButton(okButton);
	}
	
	@Override
	public void hide() {
		if(!isDuringRender)
			directlyClosed();
		super.hide();
	}

	@Override
	protected void onAfterFirstAttach() {
		isDuringRender = true;
		super.onAfterFirstAttach();
		isDuringRender = false;
	}
	
	protected void directlyClosed() {
		onAnyClose();
	}

	protected void submitButtonPressed() {
		onAnyClose();
	}

	protected void onAnyClose() {
		
	}
}
