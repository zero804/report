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
 
 
package net.datenwerke.gxtdto.client.servercommunication.callback;

import net.datenwerke.gxtdto.client.baseex.widget.mb.DwProgressMessageBox;
import net.datenwerke.gxtdto.client.locale.BaseMessages;

import com.google.gwt.http.client.Request;
import com.sencha.gxt.core.client.util.DelayedTask;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ProgressMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public abstract class ModalAsyncCallback<T> extends NotamCallback<T> {

	private String waitTitle = BaseMessages.INSTANCE.waitMsg();
	private String waitMessage = BaseMessages.INSTANCE.waitMsg();
	private String progressText = BaseMessages.INSTANCE.progressMsg();

	private int delay = 0;
	
	private ProgressMessageBox waitBox;
	private DelayedTask delayedTask;
	
	public ModalAsyncCallback(int delay){
		this(delay, BaseMessages.INSTANCE.waitMsg(), BaseMessages.INSTANCE.waitMsg(), BaseMessages.INSTANCE.progressMsg());
		
		showMessageBox();
	}
	
	public ModalAsyncCallback(int delay, String waitTitle, String waitMessage, String progressText){
		super(null);
		
		this.delay = delay;
		if(null != waitTitle)
			this.waitTitle =  waitTitle;
		if(null != waitMessage)
			this.waitMessage =  waitMessage;
		if(null != progressText)
			this.progressText =  progressText;
		
		showMessageBox();
	}

	public ModalAsyncCallback(String errorTitle, String errorText, String successTitle, String successText, String waitTitle, String waitMessage, String progressText) {
		super(errorTitle, errorText, successTitle, successText);
		
		if(null != waitTitle)
			this.waitTitle = waitTitle;
		if(null != waitMessage)
			this.waitMessage = waitMessage;
		if(null != progressText)
			this.progressText = progressText;
		
		showMessageBox();
	}
	
	public ModalAsyncCallback(String successText, String waitTitle, String waitMessage, String progressText) {
		super(successText);
		
		if(null != waitTitle)
			this.waitTitle = waitTitle;
		if(null != waitMessage)
			this.waitMessage = waitMessage;
		if(null != progressText)
			this.progressText = progressText;
		
		showMessageBox();
	}
	
	public ModalAsyncCallback(String successText){
		super(successText);
		
		this.waitTitle =  BaseMessages.INSTANCE.waitMsg();
		this.waitMessage =  BaseMessages.INSTANCE.waitMsg();
		this.progressText =  BaseMessages.INSTANCE.progressMsg();
		
		showMessageBox();
	}
	
	final public void showMessageBox(){
		waitBox = new DwProgressMessageBox(waitTitle, waitMessage);
		waitBox.setPredefinedButtons(PredefinedButton.CANCEL);
		waitBox.setProgressText(progressText);
		waitBox.setClosable(false);
		
		delayedTask = new DelayedTask() {
			@Override
			public void onExecute() {
				waitBox.show();
			}
		};
		
		delayedTask.delay(delay);
	}
	
	@Override
	public void setRequest(final Request request) {
		super.setRequest(request);
		if(null != waitBox){
			TextButton cancelBtn = waitBox.getButton(PredefinedButton.CANCEL);
			cancelBtn.setVisible(true);
			cancelBtn.addSelectHandler(new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					request.cancel();
					doOnCancel();
				}
			});
		}
	}

	public void cancelRequest(){
		if(null != delayedTask)
			delayedTask.cancel();
		
		if(null != waitBox){
			//TODO: CHECK GXT
			//waitBox.hide(waitBox.getButton(PredefinedButton.CANCEL));
			waitBox.hide();
		}

		if(null != getRequest())
			getRequest().cancel();
		
		doOnCancel();

	}
	
	protected void doOnCancel() {
		RuntimeException ex = new RuntimeException("The request was cancelled by the client");
		doOnFailure(ex);
	}

	final public void doOnFinish(){
		if(null != waitBox){
			waitBox.hide();
			delayedTask.cancel();
			waitBox = null;
		}
	}
}
