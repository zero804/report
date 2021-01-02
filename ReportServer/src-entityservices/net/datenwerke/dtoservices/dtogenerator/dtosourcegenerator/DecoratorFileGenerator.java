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

import net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor;
import net.datenwerke.dtoservices.dtogenerator.analizer.PosoAnalizer;

public class DecoratorFileGenerator extends DtoClassFileGenerator {

	public DecoratorFileGenerator(PosoAnalizer posoAnalizer, DtoAnnotationProcessor dtoAnnotationProcessor) {
		super(posoAnalizer, dtoAnnotationProcessor);
	}

	@Override
	public String getPackageName() {
		return posoAnalizer.getDtoInformation().getPackageNameForDecorator();
	}

	@Override
	public String getClassName() {
		return posoAnalizer.getDtoInformation().getClassNameForDecorator();
	}
	
	@Override
	public String getFullyQualifiedClassName() {
		return posoAnalizer.getDtoInformation().getFullyQualifiedClassNameForDecorator();
	}
	
	@Override
	protected boolean addGeneratedAnnotation() {
		return false;
	}
	
	@Override
	protected void addClassComment(StringBuilder sourceBuilder) {
		referenceAccu.add(posoAnalizer.getFullyQualifiedClassName());
		
		sourceBuilder.append("\n/**\n")
					.append(" * Dto Decorator for {@link ").append(posoAnalizer.getDtoInformation().getClassName()).append("}\n")
					.append(" *\n")
					.append(" */\n");
	}

	
	@Override
	protected void addClassBody(StringBuilder sourceBuilder) {
		sourceBuilder.append("\n\tprivate static final long serialVersionUID = 1L;\n");
		
		addConstructors(sourceBuilder);
	}

	@Override
	protected String getExtendedClass() {
		referenceAccu.add(posoAnalizer.getDtoInformation().getFullyQualifiedClassName());
		return posoAnalizer.getDtoInformation().getClassName();
	}
	
	@Override
	protected boolean isAbstract() {
		return posoAnalizer.getDtoInformation().isAbstractDto();
	}
	
}
