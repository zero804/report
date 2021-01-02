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
 
 
package net.datenwerke.dtoservices.dtogenerator.dto2posogenerator;

import net.datenwerke.annotationprocessing.utils.MethodBuilder;
import net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor;
import net.datenwerke.dtoservices.dtogenerator.analizer.EnumAnalizer;
import net.datenwerke.dtoservices.dtogenerator.analizer.PosoAnalizer;

/**
 * 
 *
 */
public class Dto2EnumSourceFileGenerator extends Dto2PosoSourceFileGenerator {

	public Dto2EnumSourceFileGenerator(PosoAnalizer posoAnalizer, DtoAnnotationProcessor dtoAnnotationProcessor) {
		super(posoAnalizer, dtoAnnotationProcessor);
	}
	
	@Override
	protected void addMergePoso(StringBuilder sourceBuilder) {
		MethodBuilder method = new MethodBuilder("mergePoso", "void", getDtoType() + " dto", getPosoType() + " poso");
		
		method.addBodyComment("no merging for enums");
		
		sourceBuilder.append(method).append("\n");
	}
	
	@Override
	protected void addMergeUnmanagedPoso(StringBuilder sourceBuilder) {
		MethodBuilder method = new MethodBuilder("mergeUnmanagedPoso", "void", getDtoType() + " dto", getPosoType() + " poso");
		
		method.addBodyComment("no merging for enums");
		
		sourceBuilder.append(method).append("\n");
	}


	@Override
	protected void addloadAndMergePoso(StringBuilder sourceBuilder) {
		MethodBuilder method = new MethodBuilder("loadAndMergePoso", getPosoType(), getDtoType() +" dto");
		
		method.addBodyLine("return createPoso(dto);");
		
		sourceBuilder.append(method).append("\n");		
	}
	
	@Override
	protected void addLoadPoso(StringBuilder sourceBuilder) {
		MethodBuilder method = new MethodBuilder("loadPoso", getPosoType(), getDtoType() + " dto");
		
		method.addBodyLine("return createPoso(dto);");
		
		sourceBuilder.append(method).append("\n");
	}

	@Override
	protected void addInstantiatePoso(StringBuilder sourceBuilder) {
		MethodBuilder method = new MethodBuilder("instantiatePoso", getPosoType());

		referenceAccu.add(IllegalStateException.class.getName());
		method.addBodyLine("throw new IllegalStateException(\"Cannot instantiate enum!\");");
		
		sourceBuilder.append(method).append("\n");
	}
	
	@Override
	protected void addCreatePoso(StringBuilder sourceBuilder) {
		MethodBuilder method = new MethodBuilder("createPoso", getPosoType(), getDtoType() + " dto");
		
		method.beginOneLineBlock("if (null == dto)");
		method.addBodyLine("return null;");
		
		method.beginBodyBlock("switch(dto)");
		
		for(EnumAnalizer enumConstant : posoAnalizer.getEnumConstants()){
			method.addBodyLine("case " + enumConstant.getTheEnum().getSimpleName() + ":");
			method.addBodyLine("return " + posoAnalizer.getSimpleName() + "." + enumConstant.getTheEnum().getSimpleName() + ";",1);
		}
		
		method.endBodyBlock();
		
		referenceAccu.add(IllegalArgumentException.class.getName());
		method.addBodyLine("throw new IllegalArgumentException(\"unknown enum type for: \" + dto);");
		
		sourceBuilder.append(method).append("\n");
	}
	
	@Override
	protected void addCreateUnmanagedPoso(StringBuilder sourceBuilder) {
		MethodBuilder method = new MethodBuilder("createUnmanagedPoso", getPosoType(), getDtoType() + " dto");
		
		method.addBodyLine("return createPoso(dto);");
		
		sourceBuilder.append(method).append("\n");
	}


}
