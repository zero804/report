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
 
 
package net.datenwerke.dtoservices.dtogenerator.analizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;

import net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;

/**
 * Provides information on a dto generator for a given poso.
 * 
 *
 */
public class Poso2DtoInformation {

	private PosoAnalizer posoAnalizer;
	private DtoAnnotationProcessor dtoAnnotationProcessor;
	
	public Poso2DtoInformation(PosoAnalizer posoAnalizer,
			DtoAnnotationProcessor dtoAnnotationProcessor) {
		super();
		this.posoAnalizer = posoAnalizer;
		this.dtoAnnotationProcessor = dtoAnnotationProcessor;
	}
	
	public String getSubPackageFor(Element element){
		/* get properties from annotation new file */
		GenerateDto geAnno = element.getAnnotation(GenerateDto.class);
		if(null == geAnno)
			throw new IllegalArgumentException(element.getSimpleName() + " is not annotated with " + GenerateDto.class.getSimpleName());
		
		return geAnno.poso2DtoGeneratorPackage();
	}
	
	public String getClassName(){
		return posoAnalizer.getSimpleName() + "2DtoGenerator";
	}
	
	public String getPackage(){
		String subsubpackage = posoAnalizer.getPackage();
		String subpackage = posoAnalizer.getGenerateDtoAnnotation().poso2DtoGeneratorPackage();
		
		return "".equals(subpackage) ? subsubpackage : subsubpackage + "." + subpackage;
	}

	public String getFullyQualifiedClassName() {
		return getPackage() + "." + getClassName();
	}

	public boolean generateGenerator() {
		return (posoAnalizer.isPosoClass() || posoAnalizer.isPosoEnum()) &&
			   ! posoAnalizer.getDtoInformation().isAbstractDto() && 
				posoAnalizer.getGenerateDtoAnnotation().generatePoso2Dto();
	}
	
	public boolean hasPostProcessors() {
		AnnotationMirror gdAnno = posoAnalizer.getGenerateDtoAnnotationMirror();
		for(ExecutableElement key : gdAnno.getElementValues().keySet())
			if(key.toString().equals("poso2DtoPostProcessors()"))
				return true;
		
		if(null != posoAnalizer.getParentPoso())
			return dtoAnnotationProcessor.getPosoAnalizerFor(posoAnalizer.getParentPoso()).getPoso2DtoInformation().hasPostProcessors();
		
		return false;
	}

	public List<DeclaredType> getPostProcessors() {
		List<DeclaredType> processors = new ArrayList<DeclaredType>();
		
		/* get parent processors */
		if(null != posoAnalizer.getParentPoso()){
			PosoAnalizer parent = dtoAnnotationProcessor.getPosoAnalizerFor(posoAnalizer.getParentPoso());
			processors.addAll(parent.getPoso2DtoInformation().getPostProcessors());
		}
			
		AnnotationMirror gdAnno = posoAnalizer.getGenerateDtoAnnotationMirror();
		for(ExecutableElement key : gdAnno.getElementValues().keySet()){
			if(key.toString().equals("poso2DtoPostProcessors()")){
				AnnotationValue value = gdAnno.getElementValues().get(key);
				Object postProcessors = value.getValue();
				
				if(postProcessors instanceof Collection){
					for(AnnotationValue attr : (Collection<AnnotationValue>) postProcessors){
						DeclaredType type = (DeclaredType) attr.getValue();
						processors.add(type);
					}
				} 
				break;
			}
		}
		
		return processors;
	}
	
}
