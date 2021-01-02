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
 
 
package net.datenwerke.rs.base.service.parameters.separator.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.ArrayList;
import java.util.List;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.client.parameters.locale.RsMessages;
import net.datenwerke.rs.base.client.parameters.separator.dto.SeparatorParameterDefinitionDto;
import net.datenwerke.rs.base.service.parameters.separator.SeparatorParameterDefinition;
import net.datenwerke.rs.base.service.parameters.separator.dtogen.SeparatorParameterDefinition2DtoGenerator;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for SeparatorParameterDefinition
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class SeparatorParameterDefinition2DtoGenerator implements Poso2DtoGenerator<SeparatorParameterDefinition,SeparatorParameterDefinitionDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public SeparatorParameterDefinition2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public SeparatorParameterDefinitionDto instantiateDto(SeparatorParameterDefinition poso)  {
		SeparatorParameterDefinitionDto dto = new SeparatorParameterDefinitionDto();
		return dto;
	}

	public SeparatorParameterDefinitionDto createDto(SeparatorParameterDefinition poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final SeparatorParameterDefinitionDto dto = new SeparatorParameterDefinitionDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.MINIMAL) >= 0){
			/*  set description */
			dto.setDescription(StringEscapeUtils.escapeXml(StringUtils.left(poso.getDescription(),8192)));

			/*  set id */
			dto.setId(poso.getId() );

			/*  set key */
			dto.setKey(StringEscapeUtils.escapeXml(StringUtils.left(poso.getKey(),8192)));

			/*  set name */
			dto.setName(StringEscapeUtils.escapeXml(StringUtils.left(poso.getName(),8192)));

		}
		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set dependsOn */
			final List<ParameterDefinitionDto> col_dependsOn = new ArrayList<ParameterDefinitionDto>();
			if( null != poso.getDependsOn()){
				for(ParameterDefinition refPoso : poso.getDependsOn()){
					final Object tmpDtoParameterDefinitionDtogetDependsOn = dtoServiceProvider.get().createDto(refPoso, referenced, referenced);
					col_dependsOn.add((ParameterDefinitionDto) tmpDtoParameterDefinitionDtogetDependsOn);
					/* ask for dto with higher view if generated */
					((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoParameterDefinitionDtogetDependsOn, refPoso, new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
						public void callback(Object dto){
							if(null == dto)
								throw new IllegalArgumentException("expected to get dto object (dependsOn)");
							int tmp_index = col_dependsOn.indexOf(tmpDtoParameterDefinitionDtogetDependsOn);
							col_dependsOn.set(tmp_index,(ParameterDefinitionDto) dto);
						}
					});
				}
				dto.setDependsOn(col_dependsOn);
			}

			/*  set displayInline */
			dto.setDisplayInline(poso.isDisplayInline() );

			/*  set editable */
			dto.setEditable(poso.isEditable() );

			/*  set hidden */
			dto.setHidden(poso.isHidden() );

			/*  set labelWidth */
			dto.setLabelWidth(poso.getLabelWidth() );

			/*  set mandatory */
			dto.setMandatory(poso.isMandatory() );

			/*  set n */
			dto.setN(poso.getN() );

		}

		return dto;
	}


}
