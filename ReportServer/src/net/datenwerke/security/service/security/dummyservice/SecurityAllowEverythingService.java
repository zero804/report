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
 
 
package net.datenwerke.security.service.security.dummyservice;

import java.util.Collection;

import net.datenwerke.security.service.security.Securee;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.SecurityTarget;
import net.datenwerke.security.service.security.SecurityTargetConfiguration;
import net.datenwerke.security.service.security.action.SecurityAction;
import net.datenwerke.security.service.security.entities.Ace;
import net.datenwerke.security.service.security.entities.Acl;
import net.datenwerke.security.service.security.entities.GenericSecurityTargetEntity;
import net.datenwerke.security.service.security.entities.HierarchicalAce;
import net.datenwerke.security.service.security.rights.Right;
import net.datenwerke.security.service.usermanager.entities.User;

public class SecurityAllowEverythingService implements SecurityService {

	@Override
	public void registerSecuree(Securee securee) {
	}

	@Override
	public Collection<Securee> getAllRegisteredSecurees() {
		return null;
	}

	@Override
	public void registerSecurityTarget(SecurityTargetConfiguration... configs) {
	}

	@Override
	public void registerSecurityTarget(Class<?> type, Securee securee) {
	}

	@Override
	public void registerSecurityTarget(Class<?>... types) {
	}

	@Override
	public Collection<Securee> getRegisteredSecureesForTarget(Class<?> target) {
		return null;
	}

	@Override
	public Collection<Class<?>> getRegisteredGenericSecurityTargets() {
		return null;
	}

	@Override
	public Collection<Class<? extends SecurityTarget>> getRegisteredSecurityTargets() {
		return null;
	}

	@Override
	public boolean checkRights(SecurityTarget target,
			Class<? extends Right>... rights) {
		return true;
	}

	@Override
	public boolean checkRights(SecurityTarget target,
			Class<? extends Securee> securee, Class<? extends Right>... rights) {
		return true;
	}

	@Override
	public boolean checkActions(SecurityTarget target,
			Class<? extends SecurityAction>... actions) {
		return true;
	}

	@Override
	public boolean checkRights(User user, SecurityTarget target,
			Class<? extends Securee> securee, Class<? extends Right>... rights) {
		return true;
	}

	@Override
	public boolean checkActions(User user, SecurityTarget target,
			Class<? extends SecurityAction>... actions) {
		return true;
	}

	@Override
	public boolean checkRights(Class<?> target,
			Class<? extends Securee> securee, Class<? extends Right>... rights) {
		return true;
	}

	@Override
	public boolean checkRights(Class<?> target,
			Class<? extends Right>... rights) {
		return true;
	}

	@Override
	public boolean checkActions(Class<?> target,
			Class<? extends SecurityAction>... actions) {
		return true;
	}

	@Override
	public boolean checkRights(User user, Class<?> target,
			Class<? extends Securee> securee, Class<? extends Right>... rights) {
		return true;
	}

	@Override
	public boolean checkActions(User user, Class<?> target,
			Class<? extends SecurityAction>... actions) {
		return true;
	}

	@Override
	public boolean isTypeSecured(Class<?> type) {
		return true;
	}

	@Override
	public boolean isGenericTarget(Class<?> type) {
		return true;
	}

	@Override
	public boolean isEntitySecurityTarget(Class<?> type) {
		return true;
	}

	@Override
	public GenericSecurityTargetEntity createGenericSecurityTargetEntity(Class<?> type) {
		return null;
	}

	@Override
	public GenericSecurityTargetEntity merge(
			GenericSecurityTargetEntity targetEntity) {
		return null;
	}

	@Override
	public GenericSecurityTargetEntity loadGenericTarget(Class<?> type) {
		return null;
	}

	@Override
	public GenericSecurityTargetEntity loadGenericTargetByIdentifier(String id) {
		return null;
	}

	@Override
	public Class<?> getGenericTargetMarkerById(String id) {
		return null;
	}

	@Override
	public void persist(Acl acl) {
	}

	@Override
	public Acl merge(Acl acl) {
		return null;
	}

	@Override
	public void remove(Acl acl) {
	}

	@Override
	public void persist(Ace ace) {
	}

	@Override
	public Ace merge(Ace ace) {
		return null;
	}

	@Override
	public void remove(Ace ace) {
		
	}

	@Override
	public Ace createACE(Class<?> target) {
		return null;
	}

	@Override
	public Ace createACE(GenericSecurityTargetEntity entity) {
		return null;
	}

	@Override
	public HierarchicalAce createHierarchicalACE(Class<?> target) {
		return null;
	}

	@Override
	public boolean checkRights(User user, SecurityTarget target,
			boolean requireInheritance, Class<? extends Securee> securee,
			Class<? extends Right>... rights) {
		return true;
	}

	@Override
	public boolean checkRights(SecurityTarget target,
			boolean requireInheritance, Class<? extends Securee> securee,
			Class<? extends Right>... rights) {
		// TODO Auto-generated method stub
		return false;
	}

}
