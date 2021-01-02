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
 
 
package net.datenwerke.dtoservices.dtogenerator.dtoinformationservice.service;

import java.util.Collection;
import java.util.Collections;

import net.datenwerke.annotationprocessing.utils.SourceFileGeneratorImpl;
import net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor;
import net.datenwerke.dtoservices.dtogenerator.dtoinformationservice.DtoClientInfoServiceGeneratorFacilitator;
import net.datenwerke.gxtdto.client.dtoinfo.DtoInformationService;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class InfoServiceModuleGenerator extends SourceFileGeneratorImpl {
	
	private DtoAnnotationProcessor dtoAnnotationProcessor;
	
	public InfoServiceModuleGenerator(DtoAnnotationProcessor dtoAnnotationProcessor) {
		super(dtoAnnotationProcessor);
		this.dtoAnnotationProcessor = dtoAnnotationProcessor;
	}

	@Override
	protected void addClassBody(StringBuilder sourceBuilder) {
		/* add main method */
		sourceBuilder.append("\t@Override\n")
					 .append("\tprotected void configure() {\n");
		
		if(dtoAnnotationProcessor.isDtoMainserviceOption()){
			sourceBuilder.append("\t\tbind(").append(DtoInformationService.class.getSimpleName()).append(".class).to(").append(DtoClientInfoServiceGeneratorFacilitator.SERVICE_IMPLEMENTATION_NAME).append(".class).in(Singleton.class);\n");
		} else {
			sourceBuilder.append("\t\tbind(").append(dtoAnnotationProcessor.getDtoServiceBaseName() + DtoClientInfoServiceGeneratorFacilitator.STARTUP_SERVICE_NAME + ".class).asEagerSingleton();\n");
		}
		sourceBuilder.append("\t}");
	}

	@Override
	protected String getExtendedClass() {
		return "AbstractGinModule";
	}

	@Override
	protected Collection<String> getImplementedInterfaces() {
		return Collections.emptySet();
	}

	@Override
	protected Collection<String> getReferencedClasses() {
		Collection<String> references = super.getReferencedClasses();
		
		references.add(AbstractGinModule.class.getName());
		references.add(Singleton.class.getName());
		references.add(DtoInformationService.class.getName());
		
		return references;
	}

	@Override
	protected boolean isAbstract() {
		return false;
	}

	public String getClassName() {
		if(dtoAnnotationProcessor.isDtoMainserviceOption())
			return DtoClientInfoServiceGeneratorFacilitator.MODULE_NAME;
		return dtoAnnotationProcessor.getDtoServiceBaseName() + DtoClientInfoServiceGeneratorFacilitator.MODULE_NAME;
	}

	public String getPackageName() {
		return dtoAnnotationProcessor.getOptionDtoInfoServicePackage();
	}
}
