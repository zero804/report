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

import net.datenwerke.dtoservices.dtogenerator.Visibility;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;

/**
 * 
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExposeToClient {
	
	/**
	 * Defines if this field acts as id for this poso.
	 * @return
	 */
	boolean id() default false; 

	/**
	 * Defines if this field acts as key for this poso.
	 * @return
	 */
	boolean key() default false; 
	
	/**
	 * The minimal view this property belongs to.
	 * @return
	 */
	DtoView view() default DtoView.NORMAL;
	
	/**
	 * Defines if the server value is copied to the client on dto generation
	 * 
	 * @return
	 */
	boolean exposeValueToClient() default true;
	
	/**
	 * String values are encoded using StringEscapeUtils htmlEncode. By setting this to true, no encoding will be performed.
	 * 
	 * @return
	 */
	boolean disableHtmlEncode() default false;
	
	boolean enableSimpleHtmlPolicy() default false;
	
	/**
	 * Very large objects might compromise the application (dos). Strings are per default cut off at 8192 characters. To circumvent this, set this option.
	 * @return
	 */
	boolean allowArbitraryLobSize() default false;
	
	/**
	 * Allows to disable merging of a certain property when merging dto and poso.
	 * 
	 * @return
	 */
	boolean mergeDtoValueBack() default true;
	
	/**
	 * If true, the property's content will be used as display title.
	 * 
	 * @see GenerateDto#displayTitle()
	 */
	boolean displayTitle() default false;

	/**
	 * Returns an array containing validators for a specific property 
	 * @return
	 */
	PropertyValidator validateDtoProperty() default @PropertyValidator(bypass=true);
	
	/**
	 * Allows an existing enclosed poso to be "moved".
	 * @return
	 */
	boolean allowForeignPosoForEnclosed() default false;
	
	public Visibility visibility() default Visibility.PRIVATE; 
	
	/**
	 * Uses the "here" view for the generation of a subentity (as for EnclosedEntities)
	 * @return
	 */
	public boolean inheritDtoView() default false;
}
