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
 
 
package net.datenwerke.dtoservices.dtogenerator.dtosourcegenerator;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import net.datenwerke.annotationprocessing.utils.SourceFileGeneratorImpl;
import net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor;
import net.datenwerke.dtoservices.dtogenerator.analizer.EnumAnalizer;
import net.datenwerke.dtoservices.dtogenerator.analizer.PosoAnalizer;
import net.datenwerke.dtoservices.dtogenerator.annotations.EnumLabel;
import net.datenwerke.dtoservices.dtogenerator.util.SourceFileGenerationUtils;

public class EnumDtoSourceFileGenerator extends SourceFileGeneratorImpl {
	
	private DtoAnnotationProcessor dtoAnnotationProcessor;
	private PosoAnalizer posoAnalizer;
	
	/* used to accumulate references */
	private Set<String> referenceAccu = new TreeSet<String>();
	
	public EnumDtoSourceFileGenerator(PosoAnalizer posoAnalizer, DtoAnnotationProcessor dtoAnnotationProcessor) {
		super(dtoAnnotationProcessor);
		this.posoAnalizer = posoAnalizer;
		this.dtoAnnotationProcessor = dtoAnnotationProcessor;
	}

	@Override
	public String getClassName() {
		return posoAnalizer.getDtoInformation().getClassName();
	}

	@Override
	public String getFullyQualifiedClassName() {
		return posoAnalizer.getDtoInformation().getFullyQualifiedClassName();
	}

	@Override
	public String getPackageName() {
		return posoAnalizer.getDtoInformation().getPackageName();
	}
	
	@Override
	protected void addClassBody(StringBuilder sourceBuilder) {
		boolean first = true;
		for(EnumAnalizer enumAnalizer : posoAnalizer.getEnumConstants()){
			if(first)
				first = false;
			else
				sourceBuilder.append(",\n");
			
			sourceBuilder.append("\t").append(enumAnalizer.getSimpleName());
			
			if(enumAnalizer.hasLabelAnnotation()){
				EnumLabel label = enumAnalizer.getLabelAnnotation();
				String iFace = SourceFileGenerationUtils.getSimpleTypeName(enumAnalizer.getLabelMsgInterface());
				
				referenceAccu.add(enumAnalizer.getLabelMsgInterface().toString());
				sourceBuilder.append(" {\n")
					.append("\t\tpublic String toString(){\n")
					.append("\t\t\treturn ").append(iFace).append(".INSTANCE.").append(label.key()).append("();\n")
					.append("\t\t}\n")
					.append("\t}");
					
			}
			
		}
		
		sourceBuilder.append("\n");
	}

	@Override
	protected ClassType getClassType() {
		return ClassType.Enum;
	}
	
	@Override
	protected Collection<String> getReferencedClasses() {
		/* references */
		Collection<String> imports = super.getReferencedClasses();
		
		imports.addAll(referenceAccu);
		
		return imports;
	}
	
}
