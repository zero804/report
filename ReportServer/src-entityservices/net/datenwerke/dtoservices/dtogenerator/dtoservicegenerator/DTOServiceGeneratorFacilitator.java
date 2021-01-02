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
 
 
package net.datenwerke.dtoservices.dtogenerator.dtoservicegenerator;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;
import java.util.TreeSet;

import net.datenwerke.annotationprocessing.utils.SourceFileGenerator;
import net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor;
import net.datenwerke.dtoservices.dtogenerator.analizer.PosoAnalizer;
import net.datenwerke.dtoservices.dtogenerator.analizer.comparators.PosoAnalyzerComparator;
import net.datenwerke.dtoservices.dtogenerator.dtoservicegenerator.service.ImplementationGenerator;
import net.datenwerke.dtoservices.dtogenerator.dtoservicegenerator.service.ModuleGenerator;
import net.datenwerke.dtoservices.dtogenerator.dtoservicegenerator.service.SubmoduleStartupGenerator;

/**
 * Responsible for creating the dto service and related classes.
 * 
 *
 */
public class DTOServiceGeneratorFacilitator {

	public static final String MODULE_NAME = "DtoModule";
	public static final String SERVICE_INTERFACE_NAME = "DtoService";
	public static final String SERVICE_MAIN_INTERFACE_NAME = "DtoMainService";
	public static final String SERVICE_IMPLEMENTATION_NAME = "DtoServiceImpl";
	public static final String SUB_MODULE_STARTUP = "Startup";
	
	private DtoAnnotationProcessor dtoAnnotationProcessor;
	
	private Set<PosoAnalizer> posos = new TreeSet<PosoAnalizer>(new PosoAnalyzerComparator());
	
	public DTOServiceGeneratorFacilitator(DtoAnnotationProcessor dtoAnnotationProcessor) {
		this.dtoAnnotationProcessor = dtoAnnotationProcessor;
	}

	public void addPoso(PosoAnalizer poso){
		posos.add(poso);
	}

	public void createService() {
		try {
			if(!dtoAnnotationProcessor.isDtoMainserviceOption())
				createSubModuleStartup();
			createImplementation();
			createModule();
		} catch (IOException e) {
			dtoAnnotationProcessor.error("Could not create source file: " + e.getMessage());
			
			RuntimeException re = new RuntimeException();
			re.initCause(e);
			throw re;
		}
		
	}

	private void createModule() throws IOException {
		SourceFileGenerator generator = new ModuleGenerator(dtoAnnotationProcessor);
		generator.generateSource();
		
		createSourceFile(generator);
	}

	private void createImplementation() throws IOException {
		SourceFileGenerator generator = new ImplementationGenerator(dtoAnnotationProcessor, posos);
		generator.generateSource();
		
		createSourceFile(generator);
	}

	private void createSubModuleStartup() throws IOException {
		SubmoduleStartupGenerator generator = new SubmoduleStartupGenerator(dtoAnnotationProcessor);
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
