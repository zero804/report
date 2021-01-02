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
import net.datenwerke.dtoservices.dtogenerator.dtoservicegenerator.service.MainInterfaceGenerator;
import net.datenwerke.gxtdto.client.dtoinfo.DtoInformationService;
import net.datenwerke.gxtdto.client.dtoinfo.DtoMainInformationService;

import com.google.inject.Inject;

public class InfoServiceStartupGenerator extends SourceFileGeneratorImpl {
	
	private DtoAnnotationProcessor dtoAnnotationProcessor;
	
	public InfoServiceStartupGenerator(DtoAnnotationProcessor dtoAnnotationProcessor) {
		super(dtoAnnotationProcessor);
		this.dtoAnnotationProcessor = dtoAnnotationProcessor;
	}

	@Override
	protected void addClassBody(StringBuilder sourceBuilder) {
		sourceBuilder.append("@Inject\n");
		sourceBuilder.append("\tpublic " + dtoAnnotationProcessor.getDtoServiceBaseName() + DtoClientInfoServiceGeneratorFacilitator.STARTUP_SERVICE_NAME + "(\n");
		
		sourceBuilder.append("\t\t" + DtoInformationService.class.getSimpleName() + " mainService,\n");
		sourceBuilder.append("\t\t" + dtoAnnotationProcessor.getDtoServiceBaseName() + DtoClientInfoServiceGeneratorFacilitator.SERVICE_IMPLEMENTATION_NAME + " subService\n");
		
		sourceBuilder.append("\t){\n");

		sourceBuilder.append("\t\t((" + DtoMainInformationService.class.getSimpleName() + ")mainService)." + MainInterfaceGenerator.ADD_SUB_MODULE_METHOD + "(subService);\n");
		
		sourceBuilder.append("\t}\n");
	}

	@Override
	protected String getExtendedClass() {
		return null;
	}

	@Override
	protected Collection<String> getImplementedInterfaces() {
		return Collections.emptySet();
	}

	@Override
	protected Collection<String> getReferencedClasses() {
		Collection<String> references = super.getReferencedClasses();
		
		references.add(Inject.class.getName());
		references.add(DtoInformationService.class.getName());
		references.add(DtoMainInformationService.class.getName());
		
		return references;
	}

	@Override
	protected boolean isAbstract() {
		return false;
	}

	public String getClassName() {
		return dtoAnnotationProcessor.getDtoServiceBaseName() + DtoClientInfoServiceGeneratorFacilitator.STARTUP_SERVICE_NAME;
	}

	public String getPackageName() {
		return dtoAnnotationProcessor.getOptionDtoInfoServicePackage();
	}
}
