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
 
 
package net.datenwerke.dtoservices.dtogenerator.dtoservicegenerator.service;

import java.util.Collection;
import java.util.Collections;

import net.datenwerke.annotationprocessing.utils.SourceFileGeneratorImpl;
import net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor;
import net.datenwerke.dtoservices.dtogenerator.dtoservicegenerator.DTOServiceGeneratorFacilitator;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;

public class ModuleGenerator extends SourceFileGeneratorImpl {

	private DtoAnnotationProcessor dtoAnnotationProcessor;
	
	public ModuleGenerator(DtoAnnotationProcessor dtoAnnotationProcessor) {
		super(dtoAnnotationProcessor);
		this.dtoAnnotationProcessor = dtoAnnotationProcessor;
	}

	@Override
	protected void addClassBody(StringBuilder sourceBuilder) {
		/* add main method */
		sourceBuilder.append("\t@Override\n")
		 .append("\tprotected void configure() {\n");
		
		if(dtoAnnotationProcessor.isDtoMainserviceOption()){
			sourceBuilder.append("\t\tbind(").append(
							 (dtoAnnotationProcessor.isDtoMainserviceOption() ? "" : dtoAnnotationProcessor.getDtoServiceBaseName() )
									 + DTOServiceGeneratorFacilitator.SERVICE_INTERFACE_NAME)
						 .append(".class).to(").append(
							 (dtoAnnotationProcessor.isDtoMainserviceOption() ? "" : dtoAnnotationProcessor.getDtoServiceBaseName() )
							  + DTOServiceGeneratorFacilitator.SERVICE_IMPLEMENTATION_NAME)
						  .append(".class).in(Scopes.SINGLETON);\n");
		} else {
			sourceBuilder.append("\t\tbind(").append(
					 dtoAnnotationProcessor.getDtoServiceBaseName() + DTOServiceGeneratorFacilitator.SUB_MODULE_STARTUP)
				  .append(".class).asEagerSingleton();\n");
		}
		
		sourceBuilder.append("\t}");
	}

	@Override
	protected String getExtendedClass() {
		return "AbstractModule";
	}

	@Override
	protected Collection<String> getImplementedInterfaces() {
		return Collections.emptySet();
	}

	@Override
	protected Collection<String> getReferencedClasses() {
		Collection<String> references = super.getReferencedClasses();
		
		references.add("com.google.inject.AbstractModule");
		references.add("com.google.inject.Scopes");
		references.add(DtoService.class.getName());
		
		return references;
	}

	@Override
	protected boolean isAbstract() {
		return false;
	}

	public String getClassName() {
		if(dtoAnnotationProcessor.isDtoMainserviceOption())
			return DTOServiceGeneratorFacilitator.MODULE_NAME;
		return dtoAnnotationProcessor.getDtoServiceBaseName() + DTOServiceGeneratorFacilitator.MODULE_NAME;
	}

	public String getPackageName() {
		return dtoAnnotationProcessor.getOptionDtoServicePackage();	
	}

}
