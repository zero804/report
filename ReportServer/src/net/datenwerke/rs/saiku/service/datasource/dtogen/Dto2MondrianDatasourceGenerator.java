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
 
 
package net.datenwerke.rs.saiku.service.datasource.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.Exception;
import java.lang.NullPointerException;
import java.lang.RuntimeException;
import java.lang.reflect.Field;
import java.util.Collection;
import javax.persistence.EntityManager;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoGenerator;
import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.validator.DtoPropertyValidator;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.saiku.client.datasource.dto.MondrianDatasourceDto;
import net.datenwerke.rs.saiku.service.datasource.MondrianDatasource;
import net.datenwerke.rs.saiku.service.datasource.dtogen.Dto2MondrianDatasourceGenerator;
import net.datenwerke.rs.utils.entitycloner.annotation.TransientID;
import net.datenwerke.rs.utils.reflection.ReflectionService;

/**
 * Dto2PosoGenerator for MondrianDatasource
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2MondrianDatasourceGenerator implements Dto2PosoGenerator<MondrianDatasourceDto,MondrianDatasource> {

	private final Provider<DtoService> dtoServiceProvider;

	private final Provider<EntityManager> entityManagerProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	private final ReflectionService reflectionService;
	@Inject
	public Dto2MondrianDatasourceGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider,
		Provider<EntityManager> entityManagerProvider,
		ReflectionService reflectionService
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
		this.entityManagerProvider = entityManagerProvider;
		this.reflectionService = reflectionService;
	}

	public MondrianDatasource loadPoso(MondrianDatasourceDto dto)  {
		if(null == dto)
			return null;

		/* get id */
		Object id = dto.getId();
		if(null == id)
			return null;

		/* load poso from db */
		EntityManager entityManager = entityManagerProvider.get();
		MondrianDatasource poso = entityManager.find(MondrianDatasource.class, id);
		return poso;
	}

	public MondrianDatasource instantiatePoso()  {
		MondrianDatasource poso = new MondrianDatasource();
		return poso;
	}

	public MondrianDatasource createPoso(MondrianDatasourceDto dto)  throws ExpectedException {
		MondrianDatasource poso = new MondrianDatasource();

		/* merge data */
		mergePoso(dto, poso);
		return poso;
	}

	public MondrianDatasource createUnmanagedPoso(MondrianDatasourceDto dto)  throws ExpectedException {
		MondrianDatasource poso = new MondrianDatasource();

		/* store old id */
		if(null != dto.getId()){
			Field transientIdField = reflectionService.getFieldByAnnotation(poso, TransientID.class);
			if(null != transientIdField){
				transientIdField.setAccessible(true);
				try{
					transientIdField.set(poso, dto.getId());
				} catch(Exception e){
				}
			}
		}

		mergePlainDto2UnmanagedPoso(dto,poso);

		return poso;
	}

	public void mergePoso(MondrianDatasourceDto dto, final MondrianDatasource poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2Poso(dto, poso);
		else
			mergePlainDto2Poso(dto, poso);
	}

	protected void mergePlainDto2Poso(MondrianDatasourceDto dto, final MondrianDatasource poso)  throws ExpectedException {
		/*  set description */
		poso.setDescription(dto.getDescription() );

		/*  set flags */
		try{
			poso.setFlags(dto.getFlags() );
		} catch(NullPointerException e){
		}

		/*  set mondrian3 */
		try{
			poso.setMondrian3(dto.isMondrian3() );
		} catch(NullPointerException e){
		}

		/*  set mondrianSchema */
		poso.setMondrianSchema(dto.getMondrianSchema() );

		/*  set name */
		poso.setName(dto.getName() );

		/*  set password */
		poso.setPassword(dto.getPassword() );

		/*  set properties */
		poso.setProperties(dto.getProperties() );

		/*  set url */
		poso.setUrl(dto.getUrl() );

		/*  set username */
		poso.setUsername(dto.getUsername() );

	}

	protected void mergeProxy2Poso(MondrianDatasourceDto dto, final MondrianDatasource poso)  throws ExpectedException {
		/*  set description */
		if(dto.isDescriptionModified()){
			poso.setDescription(dto.getDescription() );
		}

		/*  set flags */
		if(dto.isFlagsModified()){
			try{
				poso.setFlags(dto.getFlags() );
			} catch(NullPointerException e){
			}
		}

		/*  set mondrian3 */
		if(dto.isMondrian3Modified()){
			try{
				poso.setMondrian3(dto.isMondrian3() );
			} catch(NullPointerException e){
			}
		}

		/*  set mondrianSchema */
		if(dto.isMondrianSchemaModified()){
			poso.setMondrianSchema(dto.getMondrianSchema() );
		}

		/*  set name */
		if(dto.isNameModified()){
			poso.setName(dto.getName() );
		}

		/*  set password */
		if(dto.isPasswordModified()){
			poso.setPassword(dto.getPassword() );
		}

		/*  set properties */
		if(dto.isPropertiesModified()){
			poso.setProperties(dto.getProperties() );
		}

		/*  set url */
		if(dto.isUrlModified()){
			poso.setUrl(dto.getUrl() );
		}

		/*  set username */
		if(dto.isUsernameModified()){
			poso.setUsername(dto.getUsername() );
		}

	}

	public void mergeUnmanagedPoso(MondrianDatasourceDto dto, final MondrianDatasource poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2UnmanagedPoso(dto, poso);
		else
			mergePlainDto2UnmanagedPoso(dto, poso);
	}

	protected void mergePlainDto2UnmanagedPoso(MondrianDatasourceDto dto, final MondrianDatasource poso)  throws ExpectedException {
		/*  set description */
		poso.setDescription(dto.getDescription() );

		/*  set flags */
		try{
			poso.setFlags(dto.getFlags() );
		} catch(NullPointerException e){
		}

		/*  set mondrian3 */
		try{
			poso.setMondrian3(dto.isMondrian3() );
		} catch(NullPointerException e){
		}

		/*  set mondrianSchema */
		poso.setMondrianSchema(dto.getMondrianSchema() );

		/*  set name */
		poso.setName(dto.getName() );

		/*  set password */
		poso.setPassword(dto.getPassword() );

		/*  set properties */
		poso.setProperties(dto.getProperties() );

		/*  set url */
		poso.setUrl(dto.getUrl() );

		/*  set username */
		poso.setUsername(dto.getUsername() );

	}

	protected void mergeProxy2UnmanagedPoso(MondrianDatasourceDto dto, final MondrianDatasource poso)  throws ExpectedException {
		/*  set description */
		if(dto.isDescriptionModified()){
			poso.setDescription(dto.getDescription() );
		}

		/*  set flags */
		if(dto.isFlagsModified()){
			try{
				poso.setFlags(dto.getFlags() );
			} catch(NullPointerException e){
			}
		}

		/*  set mondrian3 */
		if(dto.isMondrian3Modified()){
			try{
				poso.setMondrian3(dto.isMondrian3() );
			} catch(NullPointerException e){
			}
		}

		/*  set mondrianSchema */
		if(dto.isMondrianSchemaModified()){
			poso.setMondrianSchema(dto.getMondrianSchema() );
		}

		/*  set name */
		if(dto.isNameModified()){
			poso.setName(dto.getName() );
		}

		/*  set password */
		if(dto.isPasswordModified()){
			poso.setPassword(dto.getPassword() );
		}

		/*  set properties */
		if(dto.isPropertiesModified()){
			poso.setProperties(dto.getProperties() );
		}

		/*  set url */
		if(dto.isUrlModified()){
			poso.setUrl(dto.getUrl() );
		}

		/*  set username */
		if(dto.isUsernameModified()){
			poso.setUsername(dto.getUsername() );
		}

	}

	public MondrianDatasource loadAndMergePoso(MondrianDatasourceDto dto)  throws ExpectedException {
		MondrianDatasource poso = loadPoso(dto);
		if(null != poso){
			mergePoso(dto, poso);
			return poso;
		}
		return createPoso(dto);
	}

	public void postProcessCreate(MondrianDatasourceDto dto, MondrianDatasource poso)  {
	}


	public void postProcessCreateUnmanaged(MondrianDatasourceDto dto, MondrianDatasource poso)  {
	}


	public void postProcessLoad(MondrianDatasourceDto dto, MondrianDatasource poso)  {
	}


	public void postProcessMerge(MondrianDatasourceDto dto, MondrianDatasource poso)  {
	}


	public void postProcessInstantiate(MondrianDatasource poso)  {
	}



}
