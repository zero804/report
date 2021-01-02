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
 
 
package net.datenwerke.rs.jxlsreport.service.jxlsreport.entities.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;
import java.lang.RuntimeException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoGenerator;
import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.validator.DtoPropertyValidator;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ValidationFailedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.dto.JxlsReportDto;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.dto.JxlsReportFileDto;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.entities.JxlsReport;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.entities.JxlsReportFile;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.entities.dtogen.Dto2JxlsReportGenerator;
import net.datenwerke.rs.utils.entitycloner.annotation.TransientID;
import net.datenwerke.rs.utils.reflection.ReflectionService;

/**
 * Dto2PosoGenerator for JxlsReport
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2JxlsReportGenerator implements Dto2PosoGenerator<JxlsReportDto,JxlsReport> {

	private final Provider<DtoService> dtoServiceProvider;

	private final Provider<EntityManager> entityManagerProvider;

	private final net.datenwerke.rs.core.service.reportmanager.entities.reports.post.Dto2ReportPostProcessor postProcessor_1;

	private final net.datenwerke.rs.core.service.reportmanager.entities.reports.supervisor.Dto2ReportSupervisor dto2PosoSupervisor;

	private final ReflectionService reflectionService;
	@Inject
	public Dto2JxlsReportGenerator(
		net.datenwerke.rs.core.service.reportmanager.entities.reports.supervisor.Dto2ReportSupervisor dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider,
		Provider<EntityManager> entityManagerProvider,
		net.datenwerke.rs.core.service.reportmanager.entities.reports.post.Dto2ReportPostProcessor postProcessor_1,
		ReflectionService reflectionService
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
		this.entityManagerProvider = entityManagerProvider;
		this.postProcessor_1 = postProcessor_1;
		this.reflectionService = reflectionService;
	}

	public JxlsReport loadPoso(JxlsReportDto dto)  {
		if(null == dto)
			return null;

		/* get id */
		Object id = dto.getId();
		if(null == id)
			return null;

		/* load poso from db */
		EntityManager entityManager = entityManagerProvider.get();
		JxlsReport poso = entityManager.find(JxlsReport.class, id);
		return poso;
	}

	public JxlsReport instantiatePoso()  {
		JxlsReport poso = new JxlsReport();
		return poso;
	}

	public JxlsReport createPoso(JxlsReportDto dto)  throws ExpectedException {
		JxlsReport poso = new JxlsReport();

		/* merge data */
		mergePoso(dto, poso);
		return poso;
	}

	public JxlsReport createUnmanagedPoso(JxlsReportDto dto)  throws ExpectedException {
		JxlsReport poso = new JxlsReport();

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

	public void mergePoso(JxlsReportDto dto, final JxlsReport poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2Poso(dto, poso);
		else
			mergePlainDto2Poso(dto, poso);
	}

	protected void mergePlainDto2Poso(JxlsReportDto dto, final JxlsReport poso)  throws ExpectedException {
		/*  set datasourceContainer */
		DatasourceContainerDto tmpDto_datasourceContainer = dto.getDatasourceContainer();
		if(null != tmpDto_datasourceContainer && null != tmpDto_datasourceContainer.getId()){
			if(null != poso.getDatasourceContainer() && null != poso.getDatasourceContainer().getId() && poso.getDatasourceContainer().getId().equals(tmpDto_datasourceContainer.getId()))
				poso.setDatasourceContainer((DatasourceContainer)dtoServiceProvider.get().loadAndMergePoso(tmpDto_datasourceContainer));
			else
				throw new IllegalArgumentException("enclosed dto should not have non matching id (datasourceContainer)");
		} else if(null != poso.getDatasourceContainer()){
			DatasourceContainer newPropertyValue = (DatasourceContainer)dtoServiceProvider.get().createPoso(tmpDto_datasourceContainer);
			dto2PosoSupervisor.enclosedObjectRemoved(dto, poso, poso.getDatasourceContainer(), newPropertyValue, "datasourceContainer");
			poso.setDatasourceContainer(newPropertyValue);
		} else {
			poso.setDatasourceContainer((DatasourceContainer)dtoServiceProvider.get().createPoso(tmpDto_datasourceContainer));
		}

		/*  set description */
		poso.setDescription(dto.getDescription() );

		/*  set flags */
		try{
			poso.setFlags(dto.getFlags() );
		} catch(NullPointerException e){
		}

		/*  set jxlsOne */
		try{
			poso.setJxlsOne(dto.isJxlsOne() );
		} catch(NullPointerException e){
		}

		/*  set key */
		if(validateKeyProperty(dto, poso)){
			poso.setKey(dto.getKey() );
		}

		/*  set name */
		poso.setName(dto.getName() );

		/*  set parameterDefinitions */
		final List<ParameterDefinition> col_parameterDefinitions = new ArrayList<ParameterDefinition>();
		/* load new data from dto */
		Collection<ParameterDefinitionDto> tmpCol_parameterDefinitions = dto.getParameterDefinitions();

		/* load current data from poso */
		if(null != poso.getParameterDefinitions())
			col_parameterDefinitions.addAll(poso.getParameterDefinitions());

		/* remove non existing data */
		List<ParameterDefinition> remDto_parameterDefinitions = new ArrayList<ParameterDefinition>();
		for(ParameterDefinition ref : col_parameterDefinitions){
			boolean found = false;
			for(ParameterDefinitionDto refDto : tmpCol_parameterDefinitions){
				if(null != refDto && null != refDto.getId() && refDto.getId().equals(ref.getId())){
					found = true;
					break;
				}
			}
			if(! found)
				remDto_parameterDefinitions.add(ref);
		}
		for(ParameterDefinition ref : remDto_parameterDefinitions)
			col_parameterDefinitions.remove(ref);
		dto2PosoSupervisor.enclosedObjectsRemovedFromCollection(dto, poso, remDto_parameterDefinitions, "parameterDefinitions");

		/* merge or add data */
		List<ParameterDefinition> new_col_parameterDefinitions = new ArrayList<ParameterDefinition>();
		for(ParameterDefinitionDto refDto : tmpCol_parameterDefinitions){
			boolean found = false;
			for(ParameterDefinition ref : col_parameterDefinitions){
				if(null != refDto && null != refDto.getId() && refDto.getId().equals(ref.getId())){
					new_col_parameterDefinitions.add((ParameterDefinition) dtoServiceProvider.get().loadAndMergePoso(refDto));
					found = true;
					break;
				}
			}
			if(! found && null != refDto && null == refDto.getId() )
				new_col_parameterDefinitions.add((ParameterDefinition) dtoServiceProvider.get().createPoso(refDto));
			else if(! found && null != refDto && null != refDto.getId() )
				throw new IllegalArgumentException("dto should not have an id. property(parameterDefinitions) ");
		}
		poso.setParameterDefinitions(new_col_parameterDefinitions);

		/*  set parameterInstances */
		final Set<ParameterInstance> col_parameterInstances = new HashSet<ParameterInstance>();
		/* load new data from dto */
		Collection<ParameterInstanceDto> tmpCol_parameterInstances = dto.getParameterInstances();

		/* load current data from poso */
		if(null != poso.getParameterInstances())
			col_parameterInstances.addAll(poso.getParameterInstances());

		/* remove non existing data */
		Set<ParameterInstance> remDto_parameterInstances = new HashSet<ParameterInstance>();
		for(ParameterInstance ref : col_parameterInstances){
			boolean found = false;
			for(ParameterInstanceDto refDto : tmpCol_parameterInstances){
				if(null != refDto && null != refDto.getId() && refDto.getId().equals(ref.getId())){
					found = true;
					break;
				}
			}
			if(! found)
				remDto_parameterInstances.add(ref);
		}
		for(ParameterInstance ref : remDto_parameterInstances)
			col_parameterInstances.remove(ref);
		dto2PosoSupervisor.enclosedObjectsRemovedFromCollection(dto, poso, remDto_parameterInstances, "parameterInstances");

		/* merge or add data */
		Set<ParameterInstance> new_col_parameterInstances = new HashSet<ParameterInstance>();
		for(ParameterInstanceDto refDto : tmpCol_parameterInstances){
			boolean found = false;
			for(ParameterInstance ref : col_parameterInstances){
				if(null != refDto && null != refDto.getId() && refDto.getId().equals(ref.getId())){
					new_col_parameterInstances.add((ParameterInstance) dtoServiceProvider.get().loadAndMergePoso(refDto));
					found = true;
					break;
				}
			}
			if(! found && null != refDto && null == refDto.getId() )
				new_col_parameterInstances.add((ParameterInstance) dtoServiceProvider.get().createPoso(refDto));
			else if(! found && null != refDto && null != refDto.getId() )
				throw new IllegalArgumentException("dto should not have an id. property(parameterInstances) ");
		}
		poso.setParameterInstances(new_col_parameterInstances);

		/*  set reportFile */
		JxlsReportFileDto tmpDto_reportFile = dto.getReportFile();
		if(null != tmpDto_reportFile && null != tmpDto_reportFile.getId()){
			if(null != poso.getReportFile() && null != poso.getReportFile().getId() && poso.getReportFile().getId().equals(tmpDto_reportFile.getId()))
				poso.setReportFile((JxlsReportFile)dtoServiceProvider.get().loadAndMergePoso(tmpDto_reportFile));
			else
				throw new IllegalArgumentException("enclosed dto should not have non matching id (reportFile)");
		} else if(null != poso.getReportFile()){
			JxlsReportFile newPropertyValue = (JxlsReportFile)dtoServiceProvider.get().createPoso(tmpDto_reportFile);
			dto2PosoSupervisor.enclosedObjectRemoved(dto, poso, poso.getReportFile(), newPropertyValue, "reportFile");
			poso.setReportFile(newPropertyValue);
		} else {
			poso.setReportFile((JxlsReportFile)dtoServiceProvider.get().createPoso(tmpDto_reportFile));
		}

	}

	protected void mergeProxy2Poso(JxlsReportDto dto, final JxlsReport poso)  throws ExpectedException {
		/*  set datasourceContainer */
		if(dto.isDatasourceContainerModified()){
			DatasourceContainerDto tmpDto_datasourceContainer = dto.getDatasourceContainer();
			if(null != tmpDto_datasourceContainer && null != tmpDto_datasourceContainer.getId()){
				if(null != poso.getDatasourceContainer() && null != poso.getDatasourceContainer().getId() && poso.getDatasourceContainer().getId().equals(tmpDto_datasourceContainer.getId()))
					poso.setDatasourceContainer((DatasourceContainer)dtoServiceProvider.get().loadAndMergePoso(tmpDto_datasourceContainer));
				else
					throw new IllegalArgumentException("enclosed dto should not have non matching id (datasourceContainer)");
			} else if(null != poso.getDatasourceContainer()){
				DatasourceContainer newPropertyValue = (DatasourceContainer)dtoServiceProvider.get().createPoso(tmpDto_datasourceContainer);
				dto2PosoSupervisor.enclosedObjectRemoved(dto, poso, poso.getDatasourceContainer(), newPropertyValue, "datasourceContainer");
				poso.setDatasourceContainer(newPropertyValue);
			} else {
				poso.setDatasourceContainer((DatasourceContainer)dtoServiceProvider.get().createPoso(tmpDto_datasourceContainer));
			}
		}

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

		/*  set jxlsOne */
		if(dto.isJxlsOneModified()){
			try{
				poso.setJxlsOne(dto.isJxlsOne() );
			} catch(NullPointerException e){
			}
		}

		/*  set key */
		if(dto.isKeyModified()){
			if(validateKeyProperty(dto, poso)){
				poso.setKey(dto.getKey() );
			}
		}

		/*  set name */
		if(dto.isNameModified()){
			poso.setName(dto.getName() );
		}

		/*  set parameterDefinitions */
		if(dto.isParameterDefinitionsModified()){
			final List<ParameterDefinition> col_parameterDefinitions = new ArrayList<ParameterDefinition>();
			/* load new data from dto */
			Collection<ParameterDefinitionDto> tmpCol_parameterDefinitions = null;
			tmpCol_parameterDefinitions = dto.getParameterDefinitions();

			/* load current data from poso */
			if(null != poso.getParameterDefinitions())
				col_parameterDefinitions.addAll(poso.getParameterDefinitions());

			/* remove non existing data */
			List<ParameterDefinition> remDto_parameterDefinitions = new ArrayList<ParameterDefinition>();
			for(ParameterDefinition ref : col_parameterDefinitions){
				boolean found = false;
				for(ParameterDefinitionDto refDto : tmpCol_parameterDefinitions){
					if(null != refDto && null != refDto.getId() && refDto.getId().equals(ref.getId())){
						found = true;
						break;
					}
				}
				if(! found)
					remDto_parameterDefinitions.add(ref);
			}
			for(ParameterDefinition ref : remDto_parameterDefinitions)
				col_parameterDefinitions.remove(ref);
			dto2PosoSupervisor.enclosedObjectsRemovedFromCollection(dto, poso, remDto_parameterDefinitions, "parameterDefinitions");

			/* merge or add data */
			List<ParameterDefinition> new_col_parameterDefinitions = new ArrayList<ParameterDefinition>();
			for(ParameterDefinitionDto refDto : tmpCol_parameterDefinitions){
				boolean found = false;
				for(ParameterDefinition ref : col_parameterDefinitions){
					if(null != refDto && null != refDto.getId() && refDto.getId().equals(ref.getId())){
						new_col_parameterDefinitions.add((ParameterDefinition) dtoServiceProvider.get().loadAndMergePoso(refDto));
						found = true;
						break;
					}
				}
				if(! found && null != refDto && null == refDto.getId() )
					new_col_parameterDefinitions.add((ParameterDefinition) dtoServiceProvider.get().createPoso(refDto));
				else if(! found && null != refDto && null != refDto.getId() )
					throw new IllegalArgumentException("dto should not have an id. property(parameterDefinitions) ");
			}
			poso.setParameterDefinitions(new_col_parameterDefinitions);
		}

		/*  set parameterInstances */
		if(dto.isParameterInstancesModified()){
			final Set<ParameterInstance> col_parameterInstances = new HashSet<ParameterInstance>();
			/* load new data from dto */
			Collection<ParameterInstanceDto> tmpCol_parameterInstances = null;
			tmpCol_parameterInstances = dto.getParameterInstances();

			/* load current data from poso */
			if(null != poso.getParameterInstances())
				col_parameterInstances.addAll(poso.getParameterInstances());

			/* remove non existing data */
			Set<ParameterInstance> remDto_parameterInstances = new HashSet<ParameterInstance>();
			for(ParameterInstance ref : col_parameterInstances){
				boolean found = false;
				for(ParameterInstanceDto refDto : tmpCol_parameterInstances){
					if(null != refDto && null != refDto.getId() && refDto.getId().equals(ref.getId())){
						found = true;
						break;
					}
				}
				if(! found)
					remDto_parameterInstances.add(ref);
			}
			for(ParameterInstance ref : remDto_parameterInstances)
				col_parameterInstances.remove(ref);
			dto2PosoSupervisor.enclosedObjectsRemovedFromCollection(dto, poso, remDto_parameterInstances, "parameterInstances");

			/* merge or add data */
			Set<ParameterInstance> new_col_parameterInstances = new HashSet<ParameterInstance>();
			for(ParameterInstanceDto refDto : tmpCol_parameterInstances){
				boolean found = false;
				for(ParameterInstance ref : col_parameterInstances){
					if(null != refDto && null != refDto.getId() && refDto.getId().equals(ref.getId())){
						new_col_parameterInstances.add((ParameterInstance) dtoServiceProvider.get().loadAndMergePoso(refDto));
						found = true;
						break;
					}
				}
				if(! found && null != refDto && null == refDto.getId() )
					new_col_parameterInstances.add((ParameterInstance) dtoServiceProvider.get().createPoso(refDto));
				else if(! found && null != refDto && null != refDto.getId() )
					throw new IllegalArgumentException("dto should not have an id. property(parameterInstances) ");
			}
			poso.setParameterInstances(new_col_parameterInstances);
		}

		/*  set reportFile */
		if(dto.isReportFileModified()){
			JxlsReportFileDto tmpDto_reportFile = dto.getReportFile();
			if(null != tmpDto_reportFile && null != tmpDto_reportFile.getId()){
				if(null != poso.getReportFile() && null != poso.getReportFile().getId() && poso.getReportFile().getId().equals(tmpDto_reportFile.getId()))
					poso.setReportFile((JxlsReportFile)dtoServiceProvider.get().loadAndMergePoso(tmpDto_reportFile));
				else
					throw new IllegalArgumentException("enclosed dto should not have non matching id (reportFile)");
			} else if(null != poso.getReportFile()){
				JxlsReportFile newPropertyValue = (JxlsReportFile)dtoServiceProvider.get().createPoso(tmpDto_reportFile);
				dto2PosoSupervisor.enclosedObjectRemoved(dto, poso, poso.getReportFile(), newPropertyValue, "reportFile");
				poso.setReportFile(newPropertyValue);
			} else {
				poso.setReportFile((JxlsReportFile)dtoServiceProvider.get().createPoso(tmpDto_reportFile));
			}
		}

	}

	public void mergeUnmanagedPoso(JxlsReportDto dto, final JxlsReport poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2UnmanagedPoso(dto, poso);
		else
			mergePlainDto2UnmanagedPoso(dto, poso);
	}

	protected void mergePlainDto2UnmanagedPoso(JxlsReportDto dto, final JxlsReport poso)  throws ExpectedException {
		/*  set datasourceContainer */
		DatasourceContainerDto tmpDto_datasourceContainer = dto.getDatasourceContainer();
		poso.setDatasourceContainer((DatasourceContainer)dtoServiceProvider.get().createUnmanagedPoso(tmpDto_datasourceContainer));

		/*  set description */
		poso.setDescription(dto.getDescription() );

		/*  set flags */
		try{
			poso.setFlags(dto.getFlags() );
		} catch(NullPointerException e){
		}

		/*  set jxlsOne */
		try{
			poso.setJxlsOne(dto.isJxlsOne() );
		} catch(NullPointerException e){
		}

		/*  set key */
		if(validateKeyProperty(dto, poso)){
			poso.setKey(dto.getKey() );
		}

		/*  set name */
		poso.setName(dto.getName() );

		/*  set parameterDefinitions */
		final List<ParameterDefinition> col_parameterDefinitions = new ArrayList<ParameterDefinition>();
		/* load new data from dto */
		Collection<ParameterDefinitionDto> tmpCol_parameterDefinitions = dto.getParameterDefinitions();

		/* merge or add data */
		for(ParameterDefinitionDto refDto : tmpCol_parameterDefinitions){
			col_parameterDefinitions.add((ParameterDefinition) dtoServiceProvider.get().createUnmanagedPoso(refDto));
		}
		poso.setParameterDefinitions(col_parameterDefinitions);

		/*  set parameterInstances */
		final Set<ParameterInstance> col_parameterInstances = new HashSet<ParameterInstance>();
		/* load new data from dto */
		Collection<ParameterInstanceDto> tmpCol_parameterInstances = dto.getParameterInstances();

		/* merge or add data */
		for(ParameterInstanceDto refDto : tmpCol_parameterInstances){
			col_parameterInstances.add((ParameterInstance) dtoServiceProvider.get().createUnmanagedPoso(refDto));
		}
		poso.setParameterInstances(col_parameterInstances);

		/*  set reportFile */
		JxlsReportFileDto tmpDto_reportFile = dto.getReportFile();
		poso.setReportFile((JxlsReportFile)dtoServiceProvider.get().createUnmanagedPoso(tmpDto_reportFile));

	}

	protected void mergeProxy2UnmanagedPoso(JxlsReportDto dto, final JxlsReport poso)  throws ExpectedException {
		/*  set datasourceContainer */
		if(dto.isDatasourceContainerModified()){
			DatasourceContainerDto tmpDto_datasourceContainer = dto.getDatasourceContainer();
			poso.setDatasourceContainer((DatasourceContainer)dtoServiceProvider.get().createUnmanagedPoso(tmpDto_datasourceContainer));
		}

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

		/*  set jxlsOne */
		if(dto.isJxlsOneModified()){
			try{
				poso.setJxlsOne(dto.isJxlsOne() );
			} catch(NullPointerException e){
			}
		}

		/*  set key */
		if(dto.isKeyModified()){
			if(validateKeyProperty(dto, poso)){
				poso.setKey(dto.getKey() );
			}
		}

		/*  set name */
		if(dto.isNameModified()){
			poso.setName(dto.getName() );
		}

		/*  set parameterDefinitions */
		if(dto.isParameterDefinitionsModified()){
			final List<ParameterDefinition> col_parameterDefinitions = new ArrayList<ParameterDefinition>();
			/* load new data from dto */
			Collection<ParameterDefinitionDto> tmpCol_parameterDefinitions = null;
			tmpCol_parameterDefinitions = dto.getParameterDefinitions();

			/* merge or add data */
			for(ParameterDefinitionDto refDto : tmpCol_parameterDefinitions){
				col_parameterDefinitions.add((ParameterDefinition) dtoServiceProvider.get().createUnmanagedPoso(refDto));
			}
			poso.setParameterDefinitions(col_parameterDefinitions);
		}

		/*  set parameterInstances */
		if(dto.isParameterInstancesModified()){
			final Set<ParameterInstance> col_parameterInstances = new HashSet<ParameterInstance>();
			/* load new data from dto */
			Collection<ParameterInstanceDto> tmpCol_parameterInstances = null;
			tmpCol_parameterInstances = dto.getParameterInstances();

			/* merge or add data */
			for(ParameterInstanceDto refDto : tmpCol_parameterInstances){
				col_parameterInstances.add((ParameterInstance) dtoServiceProvider.get().createUnmanagedPoso(refDto));
			}
			poso.setParameterInstances(col_parameterInstances);
		}

		/*  set reportFile */
		if(dto.isReportFileModified()){
			JxlsReportFileDto tmpDto_reportFile = dto.getReportFile();
			poso.setReportFile((JxlsReportFile)dtoServiceProvider.get().createUnmanagedPoso(tmpDto_reportFile));
		}

	}

	public JxlsReport loadAndMergePoso(JxlsReportDto dto)  throws ExpectedException {
		JxlsReport poso = loadPoso(dto);
		if(null != poso){
			mergePoso(dto, poso);
			return poso;
		}
		return createPoso(dto);
	}

	public void postProcessCreate(JxlsReportDto dto, JxlsReport poso)  {

		this.postProcessor_1.posoCreated(dto, poso);
	}


	public void postProcessCreateUnmanaged(JxlsReportDto dto, JxlsReport poso)  {

		this.postProcessor_1.posoCreatedUnmanaged(dto, poso);
	}


	public void postProcessLoad(JxlsReportDto dto, JxlsReport poso)  {

		this.postProcessor_1.posoLoaded(dto, poso);
	}


	public void postProcessMerge(JxlsReportDto dto, JxlsReport poso)  {

		this.postProcessor_1.posoMerged(dto, poso);
	}


	public void postProcessInstantiate(JxlsReport poso)  {

		this.postProcessor_1.posoInstantiated(poso);
	}


	public boolean validateKeyProperty(JxlsReportDto dto, JxlsReport poso)  throws ExpectedException {
		Object propertyValue = dto.getKey();

		/* allow null */
		if(null == propertyValue)
			return true;

		/* make sure property is string */
		if(! java.lang.String.class.isAssignableFrom(propertyValue.getClass()))
			throw new ValidationFailedException("String validation failed for key", "expected a String");

		if(! ((String)propertyValue).matches("^[a-zA-Z0-9_\\-]*$"))
			throw new ValidationFailedException("String validation failed for key", " Regex test failed.");

		/* all went well */
		return true;
	}


}
