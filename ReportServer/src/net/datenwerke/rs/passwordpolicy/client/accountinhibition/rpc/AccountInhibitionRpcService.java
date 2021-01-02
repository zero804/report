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
 
 
package net.datenwerke.rs.passwordpolicy.client.accountinhibition.rpc;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.passwordpolicy.client.accountinhibition.AccountInhibitionConfiguration;
import net.datenwerke.rs.passwordpolicy.client.accountinhibition.dto.InhibitionState;
import net.datenwerke.security.client.usermanager.dto.UserDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.inject.name.Named;

@RemoteServiceRelativePath("security_accountinhibition")
public interface AccountInhibitionRpcService extends RemoteService {
	public InhibitionState getInhibitionState(UserDto user) throws ServerCallFailedException;
	public void applyAccountInhibitionConfiguration(AccountInhibitionConfiguration accountInhibitionModel) throws ServerCallFailedException;
	public AccountInhibitionConfiguration getAccountInhibitionConfiguration(@Named("user")UserDto user) throws ServerCallFailedException;
}
