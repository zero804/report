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
 
 
package net.datenwerke.gxtdto.client.dtoinfo;

import net.datenwerke.gf.base.client.dtogenerator.RsDto;
import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;

/**
 * 
 *
 */
public interface DtoInformationService {

	public Object getDtoId(Dto dto);
	
	public boolean isProxyableDto(Dto dto);
	
	public <X extends Dto> X createInstance(Class<X> dtoClass);
	
	public <X extends Dto> X createInstance(String dtoClassName);
	
	public Class<? extends Dto2PosoMapper> lookupPosoMapper(Class<? extends RsDto> dtoType);
	
	public boolean isAuthorityForClass(Class<?> clazz);

	public boolean isAuthorityFor(Object object);

	public boolean isAuthorityForClassName(String dtoClassName);
}
