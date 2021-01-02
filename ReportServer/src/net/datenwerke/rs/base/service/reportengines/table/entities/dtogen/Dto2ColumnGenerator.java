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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities.dtogen;

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
import net.datenwerke.rs.base.client.reportengines.table.dto.AggregateFunctionDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnFormatDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.FilterDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.NullHandlingDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.OrderDto;
import net.datenwerke.rs.base.service.reportengines.table.entities.AggregateFunction;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.base.service.reportengines.table.entities.NullHandling;
import net.datenwerke.rs.base.service.reportengines.table.entities.Order;
import net.datenwerke.rs.base.service.reportengines.table.entities.dtogen.Dto2ColumnGenerator;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.Filter;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.ColumnFormat;
import net.datenwerke.rs.utils.entitycloner.annotation.TransientID;
import net.datenwerke.rs.utils.reflection.ReflectionService;

/**
 * Dto2PosoGenerator for Column
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2ColumnGenerator implements Dto2PosoGenerator<ColumnDto,Column> {

	private final Provider<DtoService> dtoServiceProvider;

	private final Provider<EntityManager> entityManagerProvider;

	private final net.datenwerke.rs.base.service.reportengines.table.entities.post.ColumnDtoPost postProcessor_1;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	private final ReflectionService reflectionService;
	@Inject
	public Dto2ColumnGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider,
		Provider<EntityManager> entityManagerProvider,
		net.datenwerke.rs.base.service.reportengines.table.entities.post.ColumnDtoPost postProcessor_1,
		ReflectionService reflectionService
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
		this.entityManagerProvider = entityManagerProvider;
		this.postProcessor_1 = postProcessor_1;
		this.reflectionService = reflectionService;
	}

	public Column loadPoso(ColumnDto dto)  {
		if(null == dto)
			return null;

		/* get id */
		Object id = dto.getId();
		if(null == id)
			return null;

		/* load poso from db */
		EntityManager entityManager = entityManagerProvider.get();
		Column poso = entityManager.find(Column.class, id);
		return poso;
	}

	public Column instantiatePoso()  {
		Column poso = new Column();
		return poso;
	}

	public Column createPoso(ColumnDto dto)  throws ExpectedException {
		Column poso = new Column();

		/* merge data */
		mergePoso(dto, poso);
		return poso;
	}

	public Column createUnmanagedPoso(ColumnDto dto)  throws ExpectedException {
		Column poso = new Column();

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

	public void mergePoso(ColumnDto dto, final Column poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2Poso(dto, poso);
		else
			mergePlainDto2Poso(dto, poso);
	}

	protected void mergePlainDto2Poso(ColumnDto dto, final Column poso)  throws ExpectedException {
		/*  set aggregateFunction */
		AggregateFunctionDto tmpDto_aggregateFunction = dto.getAggregateFunction();
		poso.setAggregateFunction((AggregateFunction)dtoServiceProvider.get().createPoso(tmpDto_aggregateFunction));

		/*  set alias */
		poso.setAlias(dto.getAlias() );

		/*  set defaultAlias */
		poso.setDefaultAlias(dto.getDefaultAlias() );

		/*  set defaultPreviewWidth */
		poso.setDefaultPreviewWidth(dto.getDefaultPreviewWidth() );

		/*  set description */
		poso.setDescription(dto.getDescription() );

		/*  set dimension */
		poso.setDimension(dto.getDimension() );

		/*  set filter */
		FilterDto tmpDto_filter = dto.getFilter();
		if(null != tmpDto_filter && null != tmpDto_filter.getId()){
			if(null != poso.getFilter() && null != poso.getFilter().getId() && poso.getFilter().getId().equals(tmpDto_filter.getId()))
				poso.setFilter((Filter)dtoServiceProvider.get().loadAndMergePoso(tmpDto_filter));
			else
				throw new IllegalArgumentException("enclosed dto should not have non matching id (filter)");
		} else if(null != poso.getFilter()){
			Filter newPropertyValue = (Filter)dtoServiceProvider.get().createPoso(tmpDto_filter);
			dto2PosoSupervisor.enclosedObjectRemoved(dto, poso, poso.getFilter(), newPropertyValue, "filter");
			poso.setFilter(newPropertyValue);
		} else {
			poso.setFilter((Filter)dtoServiceProvider.get().createPoso(tmpDto_filter));
		}

		/*  set format */
		ColumnFormatDto tmpDto_format = dto.getFormat();
		if(null != tmpDto_format && null != tmpDto_format.getId()){
			if(null != poso.getFormat() && null != poso.getFormat().getId() && poso.getFormat().getId().equals(tmpDto_format.getId()))
				poso.setFormat((ColumnFormat)dtoServiceProvider.get().loadAndMergePoso(tmpDto_format));
			else
				throw new IllegalArgumentException("enclosed dto should not have non matching id (format)");
		} else if(null != poso.getFormat()){
			ColumnFormat newPropertyValue = (ColumnFormat)dtoServiceProvider.get().createPoso(tmpDto_format);
			dto2PosoSupervisor.enclosedObjectRemoved(dto, poso, poso.getFormat(), newPropertyValue, "format");
			poso.setFormat(newPropertyValue);
		} else {
			poso.setFormat((ColumnFormat)dtoServiceProvider.get().createPoso(tmpDto_format));
		}

		/*  set hidden */
		poso.setHidden(dto.isHidden() );

		/*  set indexColumn */
		try{
			poso.setIndexColumn(dto.isIndexColumn() );
		} catch(NullPointerException e){
		}

		/*  set name */
		poso.setName(dto.getName() );

		/*  set nullHandling */
		NullHandlingDto tmpDto_nullHandling = dto.getNullHandling();
		poso.setNullHandling((NullHandling)dtoServiceProvider.get().createPoso(tmpDto_nullHandling));

		/*  set nullReplacementFormat */
		poso.setNullReplacementFormat(dto.getNullReplacementFormat() );

		/*  set order */
		OrderDto tmpDto_order = dto.getOrder();
		poso.setOrder((Order)dtoServiceProvider.get().createPoso(tmpDto_order));

		/*  set previewWidth */
		poso.setPreviewWidth(dto.getPreviewWidth() );

		/*  set semanticType */
		poso.setSemanticType(dto.getSemanticType() );

		/*  set subtotalGroup */
		poso.setSubtotalGroup(dto.isSubtotalGroup() );

		/*  set type */
		poso.setType(dto.getType() );

	}

	protected void mergeProxy2Poso(ColumnDto dto, final Column poso)  throws ExpectedException {
		/*  set aggregateFunction */
		if(dto.isAggregateFunctionModified()){
			AggregateFunctionDto tmpDto_aggregateFunction = dto.getAggregateFunction();
			poso.setAggregateFunction((AggregateFunction)dtoServiceProvider.get().createPoso(tmpDto_aggregateFunction));
		}

		/*  set alias */
		if(dto.isAliasModified()){
			poso.setAlias(dto.getAlias() );
		}

		/*  set defaultAlias */
		if(dto.isDefaultAliasModified()){
			poso.setDefaultAlias(dto.getDefaultAlias() );
		}

		/*  set defaultPreviewWidth */
		if(dto.isDefaultPreviewWidthModified()){
			poso.setDefaultPreviewWidth(dto.getDefaultPreviewWidth() );
		}

		/*  set description */
		if(dto.isDescriptionModified()){
			poso.setDescription(dto.getDescription() );
		}

		/*  set dimension */
		if(dto.isDimensionModified()){
			poso.setDimension(dto.getDimension() );
		}

		/*  set filter */
		if(dto.isFilterModified()){
			FilterDto tmpDto_filter = dto.getFilter();
			if(null != tmpDto_filter && null != tmpDto_filter.getId()){
				if(null != poso.getFilter() && null != poso.getFilter().getId() && poso.getFilter().getId().equals(tmpDto_filter.getId()))
					poso.setFilter((Filter)dtoServiceProvider.get().loadAndMergePoso(tmpDto_filter));
				else
					throw new IllegalArgumentException("enclosed dto should not have non matching id (filter)");
			} else if(null != poso.getFilter()){
				Filter newPropertyValue = (Filter)dtoServiceProvider.get().createPoso(tmpDto_filter);
				dto2PosoSupervisor.enclosedObjectRemoved(dto, poso, poso.getFilter(), newPropertyValue, "filter");
				poso.setFilter(newPropertyValue);
			} else {
				poso.setFilter((Filter)dtoServiceProvider.get().createPoso(tmpDto_filter));
			}
		}

		/*  set format */
		if(dto.isFormatModified()){
			ColumnFormatDto tmpDto_format = dto.getFormat();
			if(null != tmpDto_format && null != tmpDto_format.getId()){
				if(null != poso.getFormat() && null != poso.getFormat().getId() && poso.getFormat().getId().equals(tmpDto_format.getId()))
					poso.setFormat((ColumnFormat)dtoServiceProvider.get().loadAndMergePoso(tmpDto_format));
				else
					throw new IllegalArgumentException("enclosed dto should not have non matching id (format)");
			} else if(null != poso.getFormat()){
				ColumnFormat newPropertyValue = (ColumnFormat)dtoServiceProvider.get().createPoso(tmpDto_format);
				dto2PosoSupervisor.enclosedObjectRemoved(dto, poso, poso.getFormat(), newPropertyValue, "format");
				poso.setFormat(newPropertyValue);
			} else {
				poso.setFormat((ColumnFormat)dtoServiceProvider.get().createPoso(tmpDto_format));
			}
		}

		/*  set hidden */
		if(dto.isHiddenModified()){
			poso.setHidden(dto.isHidden() );
		}

		/*  set indexColumn */
		if(dto.isIndexColumnModified()){
			try{
				poso.setIndexColumn(dto.isIndexColumn() );
			} catch(NullPointerException e){
			}
		}

		/*  set name */
		if(dto.isNameModified()){
			poso.setName(dto.getName() );
		}

		/*  set nullHandling */
		if(dto.isNullHandlingModified()){
			NullHandlingDto tmpDto_nullHandling = dto.getNullHandling();
			poso.setNullHandling((NullHandling)dtoServiceProvider.get().createPoso(tmpDto_nullHandling));
		}

		/*  set nullReplacementFormat */
		if(dto.isNullReplacementFormatModified()){
			poso.setNullReplacementFormat(dto.getNullReplacementFormat() );
		}

		/*  set order */
		if(dto.isOrderModified()){
			OrderDto tmpDto_order = dto.getOrder();
			poso.setOrder((Order)dtoServiceProvider.get().createPoso(tmpDto_order));
		}

		/*  set previewWidth */
		if(dto.isPreviewWidthModified()){
			poso.setPreviewWidth(dto.getPreviewWidth() );
		}

		/*  set semanticType */
		if(dto.isSemanticTypeModified()){
			poso.setSemanticType(dto.getSemanticType() );
		}

		/*  set subtotalGroup */
		if(dto.isSubtotalGroupModified()){
			poso.setSubtotalGroup(dto.isSubtotalGroup() );
		}

		/*  set type */
		if(dto.isTypeModified()){
			poso.setType(dto.getType() );
		}

	}

	public void mergeUnmanagedPoso(ColumnDto dto, final Column poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2UnmanagedPoso(dto, poso);
		else
			mergePlainDto2UnmanagedPoso(dto, poso);
	}

	protected void mergePlainDto2UnmanagedPoso(ColumnDto dto, final Column poso)  throws ExpectedException {
		/*  set aggregateFunction */
		AggregateFunctionDto tmpDto_aggregateFunction = dto.getAggregateFunction();
		poso.setAggregateFunction((AggregateFunction)dtoServiceProvider.get().createPoso(tmpDto_aggregateFunction));

		/*  set alias */
		poso.setAlias(dto.getAlias() );

		/*  set defaultAlias */
		poso.setDefaultAlias(dto.getDefaultAlias() );

		/*  set defaultPreviewWidth */
		poso.setDefaultPreviewWidth(dto.getDefaultPreviewWidth() );

		/*  set description */
		poso.setDescription(dto.getDescription() );

		/*  set dimension */
		poso.setDimension(dto.getDimension() );

		/*  set filter */
		FilterDto tmpDto_filter = dto.getFilter();
		poso.setFilter((Filter)dtoServiceProvider.get().createUnmanagedPoso(tmpDto_filter));

		/*  set format */
		ColumnFormatDto tmpDto_format = dto.getFormat();
		poso.setFormat((ColumnFormat)dtoServiceProvider.get().createUnmanagedPoso(tmpDto_format));

		/*  set hidden */
		poso.setHidden(dto.isHidden() );

		/*  set indexColumn */
		try{
			poso.setIndexColumn(dto.isIndexColumn() );
		} catch(NullPointerException e){
		}

		/*  set name */
		poso.setName(dto.getName() );

		/*  set nullHandling */
		NullHandlingDto tmpDto_nullHandling = dto.getNullHandling();
		poso.setNullHandling((NullHandling)dtoServiceProvider.get().createPoso(tmpDto_nullHandling));

		/*  set nullReplacementFormat */
		poso.setNullReplacementFormat(dto.getNullReplacementFormat() );

		/*  set order */
		OrderDto tmpDto_order = dto.getOrder();
		poso.setOrder((Order)dtoServiceProvider.get().createPoso(tmpDto_order));

		/*  set previewWidth */
		poso.setPreviewWidth(dto.getPreviewWidth() );

		/*  set semanticType */
		poso.setSemanticType(dto.getSemanticType() );

		/*  set subtotalGroup */
		poso.setSubtotalGroup(dto.isSubtotalGroup() );

		/*  set type */
		poso.setType(dto.getType() );

	}

	protected void mergeProxy2UnmanagedPoso(ColumnDto dto, final Column poso)  throws ExpectedException {
		/*  set aggregateFunction */
		if(dto.isAggregateFunctionModified()){
			AggregateFunctionDto tmpDto_aggregateFunction = dto.getAggregateFunction();
			poso.setAggregateFunction((AggregateFunction)dtoServiceProvider.get().createPoso(tmpDto_aggregateFunction));
		}

		/*  set alias */
		if(dto.isAliasModified()){
			poso.setAlias(dto.getAlias() );
		}

		/*  set defaultAlias */
		if(dto.isDefaultAliasModified()){
			poso.setDefaultAlias(dto.getDefaultAlias() );
		}

		/*  set defaultPreviewWidth */
		if(dto.isDefaultPreviewWidthModified()){
			poso.setDefaultPreviewWidth(dto.getDefaultPreviewWidth() );
		}

		/*  set description */
		if(dto.isDescriptionModified()){
			poso.setDescription(dto.getDescription() );
		}

		/*  set dimension */
		if(dto.isDimensionModified()){
			poso.setDimension(dto.getDimension() );
		}

		/*  set filter */
		if(dto.isFilterModified()){
			FilterDto tmpDto_filter = dto.getFilter();
			poso.setFilter((Filter)dtoServiceProvider.get().createUnmanagedPoso(tmpDto_filter));
		}

		/*  set format */
		if(dto.isFormatModified()){
			ColumnFormatDto tmpDto_format = dto.getFormat();
			poso.setFormat((ColumnFormat)dtoServiceProvider.get().createUnmanagedPoso(tmpDto_format));
		}

		/*  set hidden */
		if(dto.isHiddenModified()){
			poso.setHidden(dto.isHidden() );
		}

		/*  set indexColumn */
		if(dto.isIndexColumnModified()){
			try{
				poso.setIndexColumn(dto.isIndexColumn() );
			} catch(NullPointerException e){
			}
		}

		/*  set name */
		if(dto.isNameModified()){
			poso.setName(dto.getName() );
		}

		/*  set nullHandling */
		if(dto.isNullHandlingModified()){
			NullHandlingDto tmpDto_nullHandling = dto.getNullHandling();
			poso.setNullHandling((NullHandling)dtoServiceProvider.get().createPoso(tmpDto_nullHandling));
		}

		/*  set nullReplacementFormat */
		if(dto.isNullReplacementFormatModified()){
			poso.setNullReplacementFormat(dto.getNullReplacementFormat() );
		}

		/*  set order */
		if(dto.isOrderModified()){
			OrderDto tmpDto_order = dto.getOrder();
			poso.setOrder((Order)dtoServiceProvider.get().createPoso(tmpDto_order));
		}

		/*  set previewWidth */
		if(dto.isPreviewWidthModified()){
			poso.setPreviewWidth(dto.getPreviewWidth() );
		}

		/*  set semanticType */
		if(dto.isSemanticTypeModified()){
			poso.setSemanticType(dto.getSemanticType() );
		}

		/*  set subtotalGroup */
		if(dto.isSubtotalGroupModified()){
			poso.setSubtotalGroup(dto.isSubtotalGroup() );
		}

		/*  set type */
		if(dto.isTypeModified()){
			poso.setType(dto.getType() );
		}

	}

	public Column loadAndMergePoso(ColumnDto dto)  throws ExpectedException {
		Column poso = loadPoso(dto);
		if(null != poso){
			mergePoso(dto, poso);
			return poso;
		}
		return createPoso(dto);
	}

	public void postProcessCreate(ColumnDto dto, Column poso)  {

		this.postProcessor_1.posoCreated(dto, poso);
	}


	public void postProcessCreateUnmanaged(ColumnDto dto, Column poso)  {

		this.postProcessor_1.posoCreatedUnmanaged(dto, poso);
	}


	public void postProcessLoad(ColumnDto dto, Column poso)  {

		this.postProcessor_1.posoLoaded(dto, poso);
	}


	public void postProcessMerge(ColumnDto dto, Column poso)  {

		this.postProcessor_1.posoMerged(dto, poso);
	}


	public void postProcessInstantiate(Column poso)  {

		this.postProcessor_1.posoInstantiated(poso);
	}



}
