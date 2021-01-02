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
 
 
package net.datenwerke.rs.base.service.parameters.string.post;

import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoPostProcessor;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoPostProcessor;
import net.datenwerke.rs.base.client.parameters.string.dto.TextParameterDefinitionDto;
import net.datenwerke.rs.base.service.parameters.string.TextParameterDefinition;
import net.datenwerke.rs.core.service.i18ntools.I18nToolsService;

import com.google.inject.Inject;

public class TextParameterDefinitionPost implements Dto2PosoPostProcessor<TextParameterDefinitionDto, TextParameterDefinition>, Poso2DtoPostProcessor<TextParameterDefinition, TextParameterDefinitionDto> {

	private final I18nToolsService i18nTools;

	@Inject
	public TextParameterDefinitionPost(I18nToolsService i18nTools) {
		this.i18nTools = i18nTools;
	}

	@Override
	public void dtoCreated(TextParameterDefinition poso, TextParameterDefinitionDto dto) {
		system2User(dto, poso);
	}

	@Override
	public void dtoInstantiated(TextParameterDefinition poso,
			TextParameterDefinitionDto dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void posoCreated(TextParameterDefinitionDto dto,
			TextParameterDefinition poso) {
		user2System(dto, poso);
	}

	@Override
	public void posoCreatedUnmanaged(TextParameterDefinitionDto dto,
			TextParameterDefinition poso) {
		user2System(dto, poso);
	}

	@Override
	public void posoInstantiated(TextParameterDefinition poso) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void posoLoaded(TextParameterDefinitionDto dto,
			TextParameterDefinition poso) {
		user2System(dto, poso);
	}

	@Override
	public void posoMerged(TextParameterDefinitionDto dto,
			TextParameterDefinition poso) {
		user2System(dto, poso);
	}

	private void user2System(TextParameterDefinitionDto dto,
			TextParameterDefinition poso) {
		if(null != poso.getDefaultValue() &&  null != poso.getReturnType()){
			switch(poso.getReturnType()){
			case Integer:
			case Long:
			case BigDecimal:
			case Float:
			case Double:
				String number = i18nTools.translateNumberFromUserToSystem(poso.getDefaultValue());
				i18nTools.validateSystemNumber(number);
				poso.setDefaultValue(number);
				break;
			}
		}
	}
	
	private void system2User(TextParameterDefinitionDto dto,
			TextParameterDefinition poso) {
		if(null != dto.getDefaultValue() && null != poso.getReturnType()){
			switch(poso.getReturnType()){
			case Integer:
			case Long:
			case BigDecimal:
			case Float:
			case Double:
				dto.setDefaultValue(i18nTools.translateNumberFromSystemToUser(dto.getDefaultValue()));
				break;
			}
		}
	}

}
