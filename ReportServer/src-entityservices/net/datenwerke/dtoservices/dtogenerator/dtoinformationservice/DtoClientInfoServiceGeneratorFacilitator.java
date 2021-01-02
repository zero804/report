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
 
 
package net.datenwerke.dtoservices.dtogenerator.dtoinformationservice;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;
import java.util.TreeSet;

import net.datenwerke.annotationprocessing.utils.SourceFileGenerator;
import net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor;
import net.datenwerke.dtoservices.dtogenerator.analizer.PosoAnalizer;
import net.datenwerke.dtoservices.dtogenerator.analizer.comparators.PosoAnalyzerComparator;
import net.datenwerke.dtoservices.dtogenerator.dtoinformationservice.service.InfoServiceImplementationGenerator;
import net.datenwerke.dtoservices.dtogenerator.dtoinformationservice.service.InfoServiceModuleGenerator;
import net.datenwerke.dtoservices.dtogenerator.dtoinformationservice.service.InfoServiceStartupGenerator;

public class DtoClientInfoServiceGeneratorFacilitator {
	
	public static final String MODULE_NAME = "ClientDtoInfoModule";
	public static final String SERVICE_IMPLEMENTATION_NAME = "ClientDtoInfoServiceImpl";
	public static final String STARTUP_SERVICE_NAME = "ClientDtoInfoStartup";
	
	private DtoAnnotationProcessor dtoAnnotationProcessor;
	
	private Set<PosoAnalizer> posos = new TreeSet<PosoAnalizer>(new PosoAnalyzerComparator());
	
	public DtoClientInfoServiceGeneratorFacilitator(DtoAnnotationProcessor dtoAnnotationProcessor) {
		this.dtoAnnotationProcessor = dtoAnnotationProcessor;
	}

	public void addPoso(PosoAnalizer poso){
		posos.add(poso);
	}

	public void createService() {
		try {
			createImplementation();
			createModule();
			if(! dtoAnnotationProcessor.isDtoMainserviceOption())
				createStartup();
		} catch (IOException e) {
			dtoAnnotationProcessor.error("Could not create source file: " + e.getMessage());
			
			RuntimeException re = new RuntimeException();
			re.initCause(e);
			throw re;
		}
		
	}

	private void createStartup() throws IOException {
		InfoServiceStartupGenerator generator = new InfoServiceStartupGenerator(dtoAnnotationProcessor);
		generator.generateSource();
		
		createSourceFile(generator);
	}

	private void createModule() throws IOException {
		SourceFileGenerator generator = new InfoServiceModuleGenerator(dtoAnnotationProcessor);
		generator.generateSource();
		
		createSourceFile(generator);
	}

	private void createImplementation() throws IOException {
		SourceFileGenerator generator = new InfoServiceImplementationGenerator(dtoAnnotationProcessor, posos);
		generator.generateSource();
		
		createSourceFile(generator);
	}

	private void createSourceFile(SourceFileGenerator generator) throws IOException{
		/* create source file */
		Writer writer = dtoAnnotationProcessor.createSourceFile(generator.getFullyQualifiedClassName()).openWriter();
		
		/* write file and close it */
		writer.write(generator.getSource());
		writer.close();
	}
}
