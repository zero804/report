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
 
 
package net.datenwerke.dtoservices.dtogenerator.dtoservicegenerator.service;


public class InterfaceGenerator {
	
	public static final String LOAD_POSO_METHOD = "loadPoso";
	public static final String CREATE_POSO_METHOD = "createPoso";
	public static final String CREATE_UNMANAGED_POSO_METHOD = "createUnmanagedPoso";
	public static final String INSTANTIATE_POSO_METHOD = "instantiatePoso";
	public static final String MERGE_POSO_METHOD = "mergePoso";
	public static final String MERGE_UNMANAGED_POSO_METHOD = "mergeUnmanagedPoso";
	public static final String LOAD_AND_MERGE_POSO_METHOD = "loadAndMergePoso";
	
	public static final String CREATE_BASIC_DTO_METHOD = "createDto";
	public static final String CREATE_LIST_DTO_METHOD = "createListDto";
	public static final String CREATE_FULL_DTO_METHOD = "createDtoFullAccess";
	public static final String INSTANTIATE_DTO_METHOD = "instantiateDto";
	public static final String INSTANTIATE_DTO_FROM_CLASS_METHOD = "instantiateDto";
	public static final String IS_AUTHORITY_FOR_POSO_CLASS_METHOD = "isAuthorityForPosoClass";
	public static final String IS_AUTHORITY_FOR_DTO_CLASS_METHOD = "isAuthorityForDtoClass";
	public static final String IS_AUTHORITY_FOR_POSO_METHOD = "isAuthorityForPoso";
	public static final String IS_AUTHORITY_FOR_DTO_METHOD = "isAuthorityForDto";
	
	public static final String GET_POSO_FROM_MAPPER = "getPosoFromDtoMapper";
	
	

}
