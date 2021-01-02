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
 
 
package net.datenwerke.scheduler.service.scheduler.triggers.complex.config.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.Exception;
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
import net.datenwerke.rs.utils.entitycloner.annotation.TransientID;
import net.datenwerke.rs.utils.reflection.ReflectionService;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.DailyRepeatTypeDto;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.DateTriggerConfigDto;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.EndTypesDto;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.TimeDto;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.TimeUnitsDto;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.DateTriggerConfig;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.Time;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.dtogen.Dto2DateTriggerConfigGenerator;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.DailyRepeatType;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.EndTypes;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.TimeUnits;

/**
 * Dto2PosoGenerator for DateTriggerConfig
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2DateTriggerConfigGenerator implements Dto2PosoGenerator<DateTriggerConfigDto,DateTriggerConfig> {

	private final Provider<DtoService> dtoServiceProvider;

	private final Provider<EntityManager> entityManagerProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	private final ReflectionService reflectionService;
	@Inject
	public Dto2DateTriggerConfigGenerator(
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

	public DateTriggerConfig loadPoso(DateTriggerConfigDto dto)  {
		if(null == dto)
			return null;

		/* get id */
		Object id = dto.getId();
		if(null == id)
			return null;

		/* load poso from db */
		EntityManager entityManager = entityManagerProvider.get();
		DateTriggerConfig poso = entityManager.find(DateTriggerConfig.class, id);
		return poso;
	}

	public DateTriggerConfig instantiatePoso()  {
		DateTriggerConfig poso = new DateTriggerConfig();
		return poso;
	}

	public DateTriggerConfig createPoso(DateTriggerConfigDto dto)  throws ExpectedException {
		DateTriggerConfig poso = new DateTriggerConfig();

		/* merge data */
		mergePoso(dto, poso);
		return poso;
	}

	public DateTriggerConfig createUnmanagedPoso(DateTriggerConfigDto dto)  throws ExpectedException {
		DateTriggerConfig poso = new DateTriggerConfig();

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

	public void mergePoso(DateTriggerConfigDto dto, final DateTriggerConfig poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2Poso(dto, poso);
		else
			mergePlainDto2Poso(dto, poso);
	}

	protected void mergePlainDto2Poso(DateTriggerConfigDto dto, final DateTriggerConfig poso)  throws ExpectedException {
		/*  set atTime */
		TimeDto tmpDto_atTime = dto.getAtTime();
		poso.setAtTime((Time)dtoServiceProvider.get().createPoso(tmpDto_atTime));

		/*  set dailyRepeatType */
		DailyRepeatTypeDto tmpDto_dailyRepeatType = dto.getDailyRepeatType();
		poso.setDailyRepeatType((DailyRepeatType)dtoServiceProvider.get().createPoso(tmpDto_dailyRepeatType));

		/*  set endType */
		EndTypesDto tmpDto_endType = dto.getEndType();
		poso.setEndType((EndTypes)dtoServiceProvider.get().createPoso(tmpDto_endType));

		/*  set firstExecution */
		poso.setFirstExecution(dto.getFirstExecution() );

		/*  set lastExecution */
		poso.setLastExecution(dto.getLastExecution() );

		/*  set numberOfExecutions */
		poso.setNumberOfExecutions(dto.getNumberOfExecutions() );

		/*  set timeRangeEnd */
		TimeDto tmpDto_timeRangeEnd = dto.getTimeRangeEnd();
		poso.setTimeRangeEnd((Time)dtoServiceProvider.get().createPoso(tmpDto_timeRangeEnd));

		/*  set timeRangeInterval */
		poso.setTimeRangeInterval(dto.getTimeRangeInterval() );

		/*  set timeRangeStart */
		TimeDto tmpDto_timeRangeStart = dto.getTimeRangeStart();
		poso.setTimeRangeStart((Time)dtoServiceProvider.get().createPoso(tmpDto_timeRangeStart));

		/*  set timeRangeUnit */
		TimeUnitsDto tmpDto_timeRangeUnit = dto.getTimeRangeUnit();
		poso.setTimeRangeUnit((TimeUnits)dtoServiceProvider.get().createPoso(tmpDto_timeRangeUnit));

	}

	protected void mergeProxy2Poso(DateTriggerConfigDto dto, final DateTriggerConfig poso)  throws ExpectedException {
		/*  set atTime */
		if(dto.isAtTimeModified()){
			TimeDto tmpDto_atTime = dto.getAtTime();
			poso.setAtTime((Time)dtoServiceProvider.get().createPoso(tmpDto_atTime));
		}

		/*  set dailyRepeatType */
		if(dto.isDailyRepeatTypeModified()){
			DailyRepeatTypeDto tmpDto_dailyRepeatType = dto.getDailyRepeatType();
			poso.setDailyRepeatType((DailyRepeatType)dtoServiceProvider.get().createPoso(tmpDto_dailyRepeatType));
		}

		/*  set endType */
		if(dto.isEndTypeModified()){
			EndTypesDto tmpDto_endType = dto.getEndType();
			poso.setEndType((EndTypes)dtoServiceProvider.get().createPoso(tmpDto_endType));
		}

		/*  set firstExecution */
		if(dto.isFirstExecutionModified()){
			poso.setFirstExecution(dto.getFirstExecution() );
		}

		/*  set lastExecution */
		if(dto.isLastExecutionModified()){
			poso.setLastExecution(dto.getLastExecution() );
		}

		/*  set numberOfExecutions */
		if(dto.isNumberOfExecutionsModified()){
			poso.setNumberOfExecutions(dto.getNumberOfExecutions() );
		}

		/*  set timeRangeEnd */
		if(dto.isTimeRangeEndModified()){
			TimeDto tmpDto_timeRangeEnd = dto.getTimeRangeEnd();
			poso.setTimeRangeEnd((Time)dtoServiceProvider.get().createPoso(tmpDto_timeRangeEnd));
		}

		/*  set timeRangeInterval */
		if(dto.isTimeRangeIntervalModified()){
			poso.setTimeRangeInterval(dto.getTimeRangeInterval() );
		}

		/*  set timeRangeStart */
		if(dto.isTimeRangeStartModified()){
			TimeDto tmpDto_timeRangeStart = dto.getTimeRangeStart();
			poso.setTimeRangeStart((Time)dtoServiceProvider.get().createPoso(tmpDto_timeRangeStart));
		}

		/*  set timeRangeUnit */
		if(dto.isTimeRangeUnitModified()){
			TimeUnitsDto tmpDto_timeRangeUnit = dto.getTimeRangeUnit();
			poso.setTimeRangeUnit((TimeUnits)dtoServiceProvider.get().createPoso(tmpDto_timeRangeUnit));
		}

	}

	public void mergeUnmanagedPoso(DateTriggerConfigDto dto, final DateTriggerConfig poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2UnmanagedPoso(dto, poso);
		else
			mergePlainDto2UnmanagedPoso(dto, poso);
	}

	protected void mergePlainDto2UnmanagedPoso(DateTriggerConfigDto dto, final DateTriggerConfig poso)  throws ExpectedException {
		/*  set atTime */
		TimeDto tmpDto_atTime = dto.getAtTime();
		poso.setAtTime((Time)dtoServiceProvider.get().createPoso(tmpDto_atTime));

		/*  set dailyRepeatType */
		DailyRepeatTypeDto tmpDto_dailyRepeatType = dto.getDailyRepeatType();
		poso.setDailyRepeatType((DailyRepeatType)dtoServiceProvider.get().createPoso(tmpDto_dailyRepeatType));

		/*  set endType */
		EndTypesDto tmpDto_endType = dto.getEndType();
		poso.setEndType((EndTypes)dtoServiceProvider.get().createPoso(tmpDto_endType));

		/*  set firstExecution */
		poso.setFirstExecution(dto.getFirstExecution() );

		/*  set lastExecution */
		poso.setLastExecution(dto.getLastExecution() );

		/*  set numberOfExecutions */
		poso.setNumberOfExecutions(dto.getNumberOfExecutions() );

		/*  set timeRangeEnd */
		TimeDto tmpDto_timeRangeEnd = dto.getTimeRangeEnd();
		poso.setTimeRangeEnd((Time)dtoServiceProvider.get().createPoso(tmpDto_timeRangeEnd));

		/*  set timeRangeInterval */
		poso.setTimeRangeInterval(dto.getTimeRangeInterval() );

		/*  set timeRangeStart */
		TimeDto tmpDto_timeRangeStart = dto.getTimeRangeStart();
		poso.setTimeRangeStart((Time)dtoServiceProvider.get().createPoso(tmpDto_timeRangeStart));

		/*  set timeRangeUnit */
		TimeUnitsDto tmpDto_timeRangeUnit = dto.getTimeRangeUnit();
		poso.setTimeRangeUnit((TimeUnits)dtoServiceProvider.get().createPoso(tmpDto_timeRangeUnit));

	}

	protected void mergeProxy2UnmanagedPoso(DateTriggerConfigDto dto, final DateTriggerConfig poso)  throws ExpectedException {
		/*  set atTime */
		if(dto.isAtTimeModified()){
			TimeDto tmpDto_atTime = dto.getAtTime();
			poso.setAtTime((Time)dtoServiceProvider.get().createPoso(tmpDto_atTime));
		}

		/*  set dailyRepeatType */
		if(dto.isDailyRepeatTypeModified()){
			DailyRepeatTypeDto tmpDto_dailyRepeatType = dto.getDailyRepeatType();
			poso.setDailyRepeatType((DailyRepeatType)dtoServiceProvider.get().createPoso(tmpDto_dailyRepeatType));
		}

		/*  set endType */
		if(dto.isEndTypeModified()){
			EndTypesDto tmpDto_endType = dto.getEndType();
			poso.setEndType((EndTypes)dtoServiceProvider.get().createPoso(tmpDto_endType));
		}

		/*  set firstExecution */
		if(dto.isFirstExecutionModified()){
			poso.setFirstExecution(dto.getFirstExecution() );
		}

		/*  set lastExecution */
		if(dto.isLastExecutionModified()){
			poso.setLastExecution(dto.getLastExecution() );
		}

		/*  set numberOfExecutions */
		if(dto.isNumberOfExecutionsModified()){
			poso.setNumberOfExecutions(dto.getNumberOfExecutions() );
		}

		/*  set timeRangeEnd */
		if(dto.isTimeRangeEndModified()){
			TimeDto tmpDto_timeRangeEnd = dto.getTimeRangeEnd();
			poso.setTimeRangeEnd((Time)dtoServiceProvider.get().createPoso(tmpDto_timeRangeEnd));
		}

		/*  set timeRangeInterval */
		if(dto.isTimeRangeIntervalModified()){
			poso.setTimeRangeInterval(dto.getTimeRangeInterval() );
		}

		/*  set timeRangeStart */
		if(dto.isTimeRangeStartModified()){
			TimeDto tmpDto_timeRangeStart = dto.getTimeRangeStart();
			poso.setTimeRangeStart((Time)dtoServiceProvider.get().createPoso(tmpDto_timeRangeStart));
		}

		/*  set timeRangeUnit */
		if(dto.isTimeRangeUnitModified()){
			TimeUnitsDto tmpDto_timeRangeUnit = dto.getTimeRangeUnit();
			poso.setTimeRangeUnit((TimeUnits)dtoServiceProvider.get().createPoso(tmpDto_timeRangeUnit));
		}

	}

	public DateTriggerConfig loadAndMergePoso(DateTriggerConfigDto dto)  throws ExpectedException {
		DateTriggerConfig poso = loadPoso(dto);
		if(null != poso){
			mergePoso(dto, poso);
			return poso;
		}
		return createPoso(dto);
	}

	public void postProcessCreate(DateTriggerConfigDto dto, DateTriggerConfig poso)  {
	}


	public void postProcessCreateUnmanaged(DateTriggerConfigDto dto, DateTriggerConfig poso)  {
	}


	public void postProcessLoad(DateTriggerConfigDto dto, DateTriggerConfig poso)  {
	}


	public void postProcessMerge(DateTriggerConfigDto dto, DateTriggerConfig poso)  {
	}


	public void postProcessInstantiate(DateTriggerConfig poso)  {
	}



}
