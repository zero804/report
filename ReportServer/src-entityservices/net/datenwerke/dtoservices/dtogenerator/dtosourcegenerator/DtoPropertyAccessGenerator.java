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

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor;
import net.datenwerke.dtoservices.dtogenerator.analizer.DtoField;
import net.datenwerke.dtoservices.dtogenerator.analizer.PosoAnalizer;
import net.datenwerke.dtoservices.dtogenerator.analizer.PosoFieldDescriptor;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.dtoservices.dtogenerator.util.SourceFileGenerationUtils;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public class DtoPropertyAccessGenerator extends DtoCreator {

	public DtoPropertyAccessGenerator(PosoAnalizer posoAnalizer, DtoAnnotationProcessor processor) {
		super(posoAnalizer, processor);
		
		this.posoAnalizer = posoAnalizer;
		this.dtoAnnotationProcessor = processor;
	}

	@Override
	public String getPackageName() {
		return posoAnalizer.getDtoInformation().getDto2PropertyAccessPackageName();
	}

	@Override
	public String getClassName() {
		return posoAnalizer.getDtoInformation().getDto2PropertyAccessClassName();
	}
	
	@Override
	protected boolean isInterface() {
		return true;
	}

	@Override
	protected void addClassBody(StringBuilder sourceBuilder) {
		sourceBuilder.append("\n");

		String dtoClass = posoAnalizer.getDtoInformation().getClassName();

		sourceBuilder.append("\tpublic static final " + getClassName() + " INSTANCE = GWT.create(" + getClassName() + ".class);\n\n");
		
		/* id field */
		PosoFieldDescriptor idField = posoAnalizer.getIdField();
		if(! (posoAnalizer.hasIdFieldInHeritage() || null == idField)){
			sourceBuilder.append("\t@Path(\"" + ID_FIELD_NAME + "\")\n");
			sourceBuilder.append("\tpublic ModelKeyProvider<" + posoAnalizer.getDtoInformation().getClassName() + "> " + ID_FIELD_NAME + "();\n");
		}
		
		/* properties */
		sourceBuilder.append("\n\t/* Properties */\n");
		
		for(DtoField field : posoAnalizer.getDtoFieldObejcts())
			sourceBuilder.append("\tpublic ValueProvider<" + dtoClass + "," + SourceFileGenerationUtils.unproxySimpleType(field.getKnownDtoType(dtoAnnotationProcessor, referenceAccu)) + "> " + field.getName() + "();\n");
	
		sourceBuilder.append("\n");
	}
	
	@Override
	protected void addAnnotations(StringBuilder sourceBuilder) {
		super.addAnnotations(sourceBuilder);
		if(addGeneratedAnnotation())
			sourceBuilder.append("@").append(CorrespondingPoso.class.getSimpleName()).append("(").append(posoAnalizer.getFullyQualifiedClassName()).append(".class)\n");
		
	}

	@Override
	protected Collection<String> getImplementedInterfaces() {
		Collection<String> ifaces =  super.getImplementedInterfaces();

		/* test for default */
		if(! posoAnalizer.extendsPoso()){
			ifaces.add(PropertyAccess.class.getSimpleName() + "<" + posoAnalizer.getDtoInformation().getClassName() + ">");
		} else {
			/* use poso */
			PosoAnalizer parentAnalizer = dtoAnnotationProcessor.getPosoAnalizerFor(posoAnalizer.getParentPoso());
			
			/* decorator */
			referenceAccu.add(parentAnalizer.getDtoInformation().getFullyQualifiedPropertyAccesClassName());
			ifaces.add(parentAnalizer.getDtoInformation().getDto2PropertyAccessClassName());
		}
	
		return ifaces;
	}
	
	@Override
	protected Collection<String> getReferencedClasses() {
		Collection<String> references = super.getReferencedClasses();
		
		if(posoAnalizer.getDtoInformation().hasAdditionalImports())
			for(DeclaredType type : posoAnalizer.getDtoInformation().getAdditionalImports())
				references.add(((TypeElement)type.asElement()).getQualifiedName().toString());
		
		references.add(PropertyAccess.class.getName());
		references.add(posoAnalizer.getDtoInformation().getFullyQualifiedClassName());
		if(posoAnalizer.getDtoInformation().hasDecorator())
			references.add(posoAnalizer.getDtoInformation().getFullyQualifiedClassNameForDecorator());
		references.add(ModelKeyProvider.class.getName());
		references.add(ValueProvider.class.getName());
		references.add(CorrespondingPoso.class.getName());
		references.add("com.google.gwt.editor.client.Editor.Path");
		references.add(GWT.class.getName());
		
		references.addAll(referenceAccu);
		
		return references;
	}

}
