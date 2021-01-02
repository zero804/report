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

import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.validator.DtoPropertyValidator;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PropertyValidator {

	/**
	 * The validator will not interfere if bypass is true
	 * @return
	 */
	boolean bypass() default false;
	
	/**
	 * Tells the validator to simply ignore the property on failure. 
	 * 
	 * Default is to throw a validation failed exception
	 */
	boolean ignoreOnFailure() default false;
	
	/**
	 * The expected Supertype
	 */
	Class<?> type() default Object.class;
	
	/**
	 * The expected property is a string
	 * @return
	 */
	StringValidator string() default @StringValidator(bypass=true);
	
	/**
	 * The expected property is a number. 
	 * @return
	 */
	NumberValidator number() default @NumberValidator(bypass=true);
	
	/**
	 * Returns an array containing validators for a specific property 
	 * @return
	 */
	Class<? extends DtoPropertyValidator>[] validators() default {};
}
