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
 
 
package net.datenwerke.rs.passwordpolicy.client;

import java.util.List;

import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import net.datenwerke.gxtdto.client.baseex.widget.mb.DwAlertMessageBox;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwMessageBox;
import net.datenwerke.gxtdto.client.dialog.error.SimpleErrorDialog;
import net.datenwerke.gxtdto.client.servercommunication.callback.NotamCallback;
import net.datenwerke.rs.authenticator.client.login.PostAuthenticateClientHook;
import net.datenwerke.security.client.login.AuthenticateResultDto;
import net.datenwerke.security.client.login.AuthenticateResultInfo;
import net.datenwerke.security.client.login.resultinfos.AccountLockedAuthenticateResultInfo;
import net.datenwerke.security.client.security.passwordpolicy.locale.PasswordPolicyMessages;
import net.datenwerke.security.ext.client.password.PasswordServiceDao;
import net.datenwerke.security.ext.client.password.ui.ChangePasswordDialog;

public class BsiPasswordPolicyPostAuthenticateClientHook implements PostAuthenticateClientHook{

	private final PasswordServiceDao passwordServiceDao;
	
	@Inject
	public BsiPasswordPolicyPostAuthenticateClientHook(
		PasswordServiceDao passwordServiceDao	
		){
		
		/* store objects */
		this.passwordServiceDao = passwordServiceDao;
	}


	@Override
	public void authenticated(final AuthenticateResultDto authRes, final List<PostAuthenticateClientHook> chain) {

		boolean autoProceed = true;

		if(null != authRes.getInfo()){
			for(final AuthenticateResultInfo info : authRes.getInfo()){

				if(info instanceof AccountLockedAuthenticateResultInfo){
					autoProceed = false;
					new SimpleErrorDialog(PasswordPolicyMessages.INSTANCE.accountLockedHeading(), PasswordPolicyMessages.INSTANCE.accountLockedMessage(((AccountLockedAuthenticateResultInfo)info).getLockedUntil())){
						@Override
						protected void onHide() {
							skipToEnd(authRes, chain);
						}
					}.show();					
				}

				if(info instanceof PasswordExpiredAuthenticationResultInfo){
					autoProceed = false;
					int expiresIn = ((PasswordExpiredAuthenticationResultInfo) info).getExpiresIn();
					if( expiresIn > 0){
						new DwMessageBox(PasswordPolicyMessages.INSTANCE.passwordExpiration(), PasswordPolicyMessages.INSTANCE.passwordExpirationWarning(expiresIn)){
							protected void onHide() {
								final ChangePasswordDialog cpd = new ChangePasswordDialog(true);

								cpd.addSubmitHandler(new SelectHandler() {
									@Override
									public void onSelect(SelectEvent event) {
										passwordServiceDao.changePassword(((PasswordExpiredAuthenticationResultInfo) info).getUsername(), cpd.getOldPassword(), cpd.getNewPassword(), new NotamCallback<Void>("password changed"){
											@Override
											public void doOnSuccess(Void result) {
												cpd.hide();
												skipToEnd(authRes, chain);
											}
										});
									}
								});

								cpd.addCancelHandler(new SelectHandler() {
									@Override
									public void onSelect(SelectEvent event) {
										cpd.hide();
										skipToEnd(authRes, chain);
									}
								});
								
								cpd.show();								
							};
						}.show();
					}else{
						new DwAlertMessageBox(PasswordPolicyMessages.INSTANCE.passwordExpiration(), PasswordPolicyMessages.INSTANCE.passwordExpired()){
							protected void onHide() {
								final ChangePasswordDialog cpd = new ChangePasswordDialog(false);

								cpd.addSubmitHandler(new SelectHandler() {
									@Override
									public void onSelect(SelectEvent event) {
										passwordServiceDao.changePassword(((PasswordExpiredAuthenticationResultInfo) info).getUsername(), cpd.getOldPassword(), cpd.getNewPassword(), new NotamCallback<Void>("password changed"){
											@Override
											public void doOnSuccess(Void result) {
												cpd.hide();
												skipToEnd(authRes, chain);
											}
										});
									}
								});
								
								cpd.show();
							};
						}.show();
					}
				}
			}

		}

		if(autoProceed)
			proceed(authRes, chain);
	}

	protected void proceed(AuthenticateResultDto authRes, List<PostAuthenticateClientHook> chain){
		if(chain.size() > 0)
			chain.remove(0).authenticated(authRes, chain);
	}
	
	protected void skipToEnd(AuthenticateResultDto authRes, List<PostAuthenticateClientHook> chain){
		if(chain.size() > 0){
			PostAuthenticateClientHook last = chain.get(chain.size()-1);
			chain.clear();
			last.authenticated(authRes, chain);
		}
	}
}
