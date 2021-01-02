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
 
 
package net.datenwerke.security.client.security.dto;

import com.google.gwt.core.client.GWT;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.gf.base.client.dtogenerator.RsDto;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.client.dtomanager.PropertyAccessor;
import net.datenwerke.gxtdto.client.dtomanager.redoundo.ChangeTracker;
import net.datenwerke.security.client.security.dto.RightDto;
import net.datenwerke.security.client.security.dto.pa.ExecuteDtoPA;
import net.datenwerke.security.client.security.dto.posomap.ExecuteDto2PosoMap;
import net.datenwerke.security.service.security.rights.Execute;

/**
 * Dto for {@link Execute}
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class ExecuteDto extends RsDto implements RightDto {


	private static final long serialVersionUID = 1;


	/* Fields */
	private String abbreviation;
	private  boolean abbreviation_m;
	public static final String PROPERTY_ABBREVIATION = "dpi-execute-abbreviation";

	private transient static PropertyAccessor<ExecuteDto, String> abbreviation_pa = new PropertyAccessor<ExecuteDto, String>() {
		@Override
		public void setValue(ExecuteDto container, String object) {
			container.setAbbreviation(object);
		}

		@Override
		public String getValue(ExecuteDto container) {
			return container.getAbbreviation();
		}

		@Override
		public Class<?> getType() {
			return String.class;
		}

		@Override
		public String getPath() {
			return "abbreviation";
		}

		@Override
		public void setModified(ExecuteDto container, boolean modified) {
			container.abbreviation_m = modified;
		}

		@Override
		public boolean isModified(ExecuteDto container) {
			return container.isAbbreviationModified();
		}
	};

	private long bitField;
	private  boolean bitField_m;
	public static final String PROPERTY_BIT_FIELD = "dpi-execute-bitfield";

	private transient static PropertyAccessor<ExecuteDto, Long> bitField_pa = new PropertyAccessor<ExecuteDto, Long>() {
		@Override
		public void setValue(ExecuteDto container, Long object) {
			container.setBitField(object);
		}

		@Override
		public Long getValue(ExecuteDto container) {
			return container.getBitField();
		}

		@Override
		public Class<?> getType() {
			return Long.class;
		}

		@Override
		public String getPath() {
			return "bitField";
		}

		@Override
		public void setModified(ExecuteDto container, boolean modified) {
			container.bitField_m = modified;
		}

		@Override
		public boolean isModified(ExecuteDto container) {
			return container.isBitFieldModified();
		}
	};

	private String description;
	private  boolean description_m;
	public static final String PROPERTY_DESCRIPTION = "dpi-execute-description";

	private transient static PropertyAccessor<ExecuteDto, String> description_pa = new PropertyAccessor<ExecuteDto, String>() {
		@Override
		public void setValue(ExecuteDto container, String object) {
			container.setDescription(object);
		}

		@Override
		public String getValue(ExecuteDto container) {
			return container.getDescription();
		}

		@Override
		public Class<?> getType() {
			return String.class;
		}

		@Override
		public String getPath() {
			return "description";
		}

		@Override
		public void setModified(ExecuteDto container, boolean modified) {
			container.description_m = modified;
		}

		@Override
		public boolean isModified(ExecuteDto container) {
			return container.isDescriptionModified();
		}
	};


	public ExecuteDto() {
		super();
	}

	public void setAbbreviation(String abbreviation)  {
		/* old value */
		String oldValue = null;
		if(GWT.isClient())
			oldValue = getAbbreviation();

		/* set new value */
		this.abbreviation = abbreviation;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(abbreviation_pa, oldValue, abbreviation, this.abbreviation_m));

		/* set indicator */
		this.abbreviation_m = true;

		this.fireObjectChangedEvent(ExecuteDtoPA.INSTANCE.abbreviation(), oldValue);
	}


	public String getAbbreviation()  {
		if(! isDtoProxy()){
			return this.abbreviation;
		}

		if(isAbbreviationModified())
			return this.abbreviation;

		if(! GWT.isClient())
			return null;

		String _value = dtoManager.getProperty(this, instantiatePropertyAccess().abbreviation());

		return _value;
	}


	public boolean isAbbreviationModified()  {
		return abbreviation_m;
	}


	public static PropertyAccessor<ExecuteDto, String> getAbbreviationPropertyAccessor()  {
		return abbreviation_pa;
	}


	public void setBitField(long bitField)  {
		/* old value */
		long oldValue = 0;
		if(GWT.isClient())
			oldValue = getBitField();

		/* set new value */
		this.bitField = bitField;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(bitField_pa, oldValue, bitField, this.bitField_m));

		/* set indicator */
		this.bitField_m = true;

		this.fireObjectChangedEvent(ExecuteDtoPA.INSTANCE.bitField(), oldValue);
	}


	public long getBitField()  {
		if(! isDtoProxy()){
			return this.bitField;
		}

		if(isBitFieldModified())
			return this.bitField;

		if(! GWT.isClient())
			return 0;

		long _value = dtoManager.getProperty(this, instantiatePropertyAccess().bitField());

		return _value;
	}


	public boolean isBitFieldModified()  {
		return bitField_m;
	}


	public static PropertyAccessor<ExecuteDto, Long> getBitFieldPropertyAccessor()  {
		return bitField_pa;
	}


	public void setDescription(String description)  {
		/* old value */
		String oldValue = null;
		if(GWT.isClient())
			oldValue = getDescription();

		/* set new value */
		this.description = description;

		if(! GWT.isClient())
			return;

		if(isTrackChanges())
			addChange(new ChangeTracker(description_pa, oldValue, description, this.description_m));

		/* set indicator */
		this.description_m = true;

		this.fireObjectChangedEvent(ExecuteDtoPA.INSTANCE.description(), oldValue);
	}


	public String getDescription()  {
		if(! isDtoProxy()){
			return this.description;
		}

		if(isDescriptionModified())
			return this.description;

		if(! GWT.isClient())
			return null;

		String _value = dtoManager.getProperty(this, instantiatePropertyAccess().description());

		return _value;
	}


	public boolean isDescriptionModified()  {
		return description_m;
	}


	public static PropertyAccessor<ExecuteDto, String> getDescriptionPropertyAccessor()  {
		return description_pa;
	}


	@Override
	public String toString()  {
		return super.toString();
	}

	public static Dto2PosoMapper newPosoMapper()  {
		return new ExecuteDto2PosoMap();
	}

	public ExecuteDtoPA instantiatePropertyAccess()  {
		return GWT.create(ExecuteDtoPA.class);
	}

	public void clearModified()  {
		this.abbreviation = null;
		this.abbreviation_m = false;
		this.bitField = 0;
		this.bitField_m = false;
		this.description = null;
		this.description_m = false;
	}


	public boolean isModified()  {
		if(super.isModified())
			return true;
		if(abbreviation_m)
			return true;
		if(bitField_m)
			return true;
		if(description_m)
			return true;
		return false;
	}


	public List<PropertyAccessor> getPropertyAccessors()  {
		List<PropertyAccessor> list = super.getPropertyAccessors();
		list.add(abbreviation_pa);
		list.add(bitField_pa);
		list.add(description_pa);
		return list;
	}


	public List<PropertyAccessor> getModifiedPropertyAccessors()  {
		List<PropertyAccessor> list = super.getModifiedPropertyAccessors();
		if(abbreviation_m)
			list.add(abbreviation_pa);
		if(bitField_m)
			list.add(bitField_pa);
		if(description_m)
			list.add(description_pa);
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsByView(net.datenwerke.gxtdto.client.dtomanager.DtoView view)  {
		List<PropertyAccessor> list = super.getPropertyAccessorsByView(view);
		if(view.compareTo(DtoView.NORMAL) >= 0){
			list.add(abbreviation_pa);
			list.add(bitField_pa);
			list.add(description_pa);
		}
		return list;
	}


	public List<PropertyAccessor> getPropertyAccessorsForDtos()  {
		List<PropertyAccessor> list = super.getPropertyAccessorsForDtos();
		return list;
	}




}
