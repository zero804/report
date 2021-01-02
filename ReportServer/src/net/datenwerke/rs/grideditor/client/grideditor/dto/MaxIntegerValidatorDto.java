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
 
 
package net.datenwerke.rs.grideditor.client.grideditor.dto;

import com.google.gwt.core.client.GWT;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.client.dtomanager.PropertyAccessor;
import net.datenwerke.gxtdto.client.dtomanager.redoundo.ChangeTracker;
import net.datenwerke.rs.grideditor.client.grideditor.dto.decorator.ValidatorDtoDec;
import net.datenwerke.rs.grideditor.client.grideditor.dto.pa.MaxIntegerValidatorDtoPA;
import net.datenwerke.rs.grideditor.client.grideditor.dto.posomap.MaxIntegerValidatorDto2PosoMap;
import net.datenwerke.rs.grideditor.service.grideditor.definition.validator.MaxIntegerValidator;

/**
 * Dto for {@link MaxIntegerValidator}
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
abstract public class MaxIntegerValidatorDto extends ValidatorDtoDec {


	private static final long serialVersionUID = 1;


	/* Fields */
	private Integer number;
	private  boolean number_m;
	public static final String PROPERTY_NUMBER = "dpi-maxintegervalidator-number";

	private transient static PropertyAccessor<MaxIntegerValidatorDto, Integer> number_pa = new PropertyAccessor<MaxIntegerValidatorDto, Integer>() {
		@Override
		public void setValue(MaxIntegerValidatorDto container, Integer object) {
			container.setNumber(object);
		}

		@Override
		public Integer getValue(MaxIntegerValidatorDto container) {
			return container.getNumber();
		}

		@Override
		public Class<?> getType() {
			return Integer.class;
		}

		@Override
		public String getPath() {
			return "number";
		}

		@Override
		public void setModified(MaxIntegerValidatorDto container, boolean modified) {
			container.number_m = modified;
		}

		@Override
		public boolean isModified(MaxIntegerValidatorDto container) {
			return container.isNumberModified();
		}
	};


	public MaxIntegerValidatorDto() {
		super();
	}

	public Integer getNumber()  {
		if(! isDtoProxy()){
			return this.number;
		}

		if(isNumberModified())
			return this.number;

		if(! GWT.isClient())
			return null;

		Integer _value = dtoManager.getProperty(this, instantiatePropertyAccess().number());

		return _value;
	}


	public void setNumber(Integer number)  {
		/* old value */
		Integer oldValue = null;
		if(GWT.isClient())
			oldValue = getNumber();

		/* set new value */
		this.number = number;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(number_pa, oldValue, number, this.number_m));

		/* set indicator */
		this.number_m = true;

		this.fireObjectChangedEvent(MaxIntegerValidatorDtoPA.INSTANCE.number(), oldValue);
	}


	public boolean isNumberModified()  {
		return number_m;
	}


	public static PropertyAccessor<MaxIntegerValidatorDto, Integer> getNumberPropertyAccessor()  {
		return number_pa;
	}


	@Override
	public String toString()  {
		return super.toString();
	}

	public static Dto2PosoMapper newPosoMapper()  {
		return new MaxIntegerValidatorDto2PosoMap();
	}

	public MaxIntegerValidatorDtoPA instantiatePropertyAccess()  {
		return GWT.create(MaxIntegerValidatorDtoPA.class);
	}

	public void clearModified()  {
		this.number = null;
		this.number_m = false;
	}


	public boolean isModified()  {
		if(super.isModified())
			return true;
		if(number_m)
			return true;
		return false;
	}


	public List<PropertyAccessor> getPropertyAccessors()  {
		List<PropertyAccessor> list = super.getPropertyAccessors();
		list.add(number_pa);
		return list;
	}


	public List<PropertyAccessor> getModifiedPropertyAccessors()  {
		List<PropertyAccessor> list = super.getModifiedPropertyAccessors();
		if(number_m)
			list.add(number_pa);
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsByView(net.datenwerke.gxtdto.client.dtomanager.DtoView view)  {
		List<PropertyAccessor> list = super.getPropertyAccessorsByView(view);
		if(view.compareTo(DtoView.NORMAL) >= 0){
			list.add(number_pa);
		}
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsForDtos()  {
		List<PropertyAccessor> list = super.getPropertyAccessorsForDtos();
		return list;
	}




}
