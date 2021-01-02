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
 
 
package net.datenwerke.entityservices.metadatagenerator;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import net.datenwerke.annotationprocessing.utils.SourceFileGenerator;
import net.datenwerke.annotationprocessing.utils.SourceFileGeneratorImpl;

/**
 * 
 *
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"javax.persistence.Entity", "javax.persistence.MappedSuperclass"})
@SupportedOptions({
	EntityMetadataProcessor.BUILD_METADATA
})
public class EntityMetadataProcessor extends AbstractProcessor {

	public static final String BUILD_METADATA = "buildMetadata";
	public static final String name = "MetadataGeneratorProcessor";
	public static final String version = "0.1";
	
	private String buildMetadataOption;
	private boolean processed = false;
	
	
	/**
	 * Initializes the processor.
	 *  
	 */
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		
		/* options */
		Map<String, String> options = processingEnv.getOptions();
		this.buildMetadataOption = options.get(BUILD_METADATA);
		
		System.out.println("Build Metadata: " + (buildMetadataOption == null ? "false" : buildMetadataOption));
		
 	}
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		/* only do one round of processing */
		if (! roundEnv.processingOver() && ! processed && "true".equals(buildMetadataOption)){
			/* loop over posos */
			for(Element element : roundEnv.getElementsAnnotatedWith(Entity.class)){
				SourceFileGeneratorImpl generator = new FileGenerator(this, element);
				generateSourceFile(generator);
			}
			for(Element element : roundEnv.getElementsAnnotatedWith(MappedSuperclass.class)){
				SourceFileGeneratorImpl generator = new FileGenerator(this, element);
				generateSourceFile(generator);
			}
			/* we are done and we do not need another round */
			processed = true;
		}
		
		/* do not claim annotations */
		return false;
	}
	
	public ProcessingEnvironment getProcessingEnvironment(){
		return processingEnv;
	}
	
	public void generateSourceFile(SourceFileGenerator generator){
		generator.generateSource();
		
		try {
			System.out.println("metadata for: " + generator.getClassName());
			/* create source file */
			JavaFileObject jfo = processingEnv.getFiler().createSourceFile(generator.getFullyQualifiedClassName());
			Writer writer = new OutputStreamWriter(jfo.openOutputStream(), "UTF-8");
			
			/* write file and close it */
			writer.write(generator.getSource());
			writer.close();
		} catch (IOException e) {
			processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,"Could not create source file: " + e.getMessage());
			
			RuntimeException re = new RuntimeException();
			re.initCause(e);
			throw re;
		}
	}


}
