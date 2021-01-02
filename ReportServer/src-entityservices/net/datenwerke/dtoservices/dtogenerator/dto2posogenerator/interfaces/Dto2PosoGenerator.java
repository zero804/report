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
 
 
package net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;


/**
 * 
 *
 */
public interface Dto2PosoGenerator<D, P> {

	/**
	 * Creates a new Poso obejct with the data provided by the dto.
	 * 
	 * @param dto
	 * @return
	 */
	public P createPoso(D dto) throws ExpectedException;
	
	public P createUnmanagedPoso(D dto) throws ExpectedException;
	
	public P instantiatePoso();
	
	public void mergePoso(D dto, P poso) throws ExpectedException;

	public void mergeUnmanagedPoso(D dto, P poso) throws ExpectedException;
	
	public P loadAndMergePoso(D dto) throws ExpectedException;
	
	/**
	 * Loads the poso referenced by the dto.
	 * 
	 * @param dto
	 * @return
	 */
	public P loadPoso(D dto);
	
	void postProcessCreate(D dto, P poso);
	
	void postProcessCreateUnmanaged(D dto, P poso);
	
	void postProcessLoad(D dto, P poso);
	
	void postProcessMerge(D dto, P poso);
	
	void postProcessInstantiate(P poso);
	
}
