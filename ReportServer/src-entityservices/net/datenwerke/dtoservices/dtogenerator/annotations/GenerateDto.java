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
 
 
package net.datenwerke.dtoservices.dtogenerator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoPostProcessor;
import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisor;
import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoPostProcessor;
import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.client.locale.BaseMessages;

import com.google.gwt.i18n.client.Messages;

/**
 * 
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GenerateDto {

	boolean proxyableDto() default true;
	
	Class<? extends Poso2DtoPostProcessor>[] poso2DtoPostProcessors() default {};

	Class<? extends Dto2PosoPostProcessor>[] dto2PosoPostProcessors() default {};
	
	Class<? extends Dto2PosoSupervisor> dto2PosoSupervisor() default Dto2PosoSupervisorDefaultImpl.class;
	
	/**
	 * Defines additional interfaces the dto should implement
	 * @return
	 */
	Class<?>[] dtoImplementInterfaces() default {};
	
	String dtoPackage();
	boolean abstractDto() default false;
	boolean generateDto() default true;
	
	Class<?> dtoExtends() default Dto.class;
	
	String poso2DtoGeneratorPackage() default "dtogen";
	boolean generatePoso2Dto() default true;
	
	String dto2posoGeneratorPackage() default "dtogen";
	boolean generateDto2Poso() default true;;
	
	/**
	 * SerialVersionUID for dto class
	 * @return
	 */
	long dtoSerialVersionUID() default 0;

	boolean createDecorator() default false;

	String icon() default "file";
	
	/**
	 * Can be used to define a more complex display title
	 * @return
	 */
	String displayTitle() default "";
	
	Class<?>[] additionalImports() default {};
	
	Class<?>[] whitelist() default {};
	
	AdditionalField[] additionalFields() default {};
	
	Class<? extends Messages> typeDescriptionMsg() default BaseMessages.class;
	String typeDescriptionKey() default "unknown";
	
    
}
