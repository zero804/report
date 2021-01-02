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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import net.datenwerke.annotationprocessing.utils.SourceFileGeneratorImpl;
import net.datenwerke.entityservices.metadatagenerator.interfaces.EntityMetadataProvider;

public class FileGenerator extends SourceFileGeneratorImpl {

	private EntityMetadataProcessor processor;
	private Element element;

	public FileGenerator(EntityMetadataProcessor processor, Element element){
		super(processor);
		this.processor = processor;
		this.element = element;
	}
	
	@Override
	public String getPackageName() {
		Elements utils = processor.getProcessingEnvironment().getElementUtils();
		return utils.getPackageOf(element).toString(); 
	}

	@Override
	public String getClassName() {
		return element.getSimpleName().toString() + "__";
	}

	@Override
	protected void addClassBody(StringBuilder sourceBuilder) {
		List<VariableElement> fieldsIn = new ArrayList<VariableElement>(ElementFilter.fieldsIn(element.getEnclosedElements()));
		Collections.sort(fieldsIn, new Comparator<VariableElement>() {
			@Override
			public int compare(VariableElement o1, VariableElement o2) {
				return o1.getSimpleName().toString().compareTo(o2.getSimpleName().toString());
			}
		});
		
		for(VariableElement field : fieldsIn){
			if(null == field.getAnnotation(Transient.class))
				sourceBuilder.append("\tpublic static final String ").append(field.getSimpleName().toString()).append(" = \"").append(field.getSimpleName().toString()).append("\";\n");
		}
	}

	@Override
	protected String getExtendedClass() {
		Element superclass = ((DeclaredType)((TypeElement)element).getSuperclass()).asElement();
		if(null == superclass.getAnnotation(Entity.class) && null == superclass.getAnnotation(MappedSuperclass.class))
			return super.getExtendedClass();

		DeclaredType typeDecl = ((DeclaredType)((TypeElement)element).getSuperclass());
		String name = typeDecl.toString();

		/* do we have type parameters */
		if(typeDecl.getTypeArguments().isEmpty())
			return name + "__";
		
		return name.substring(0, name.indexOf("<")) + "__";
	}
	
	@Override
	protected Collection<String> getImplementedInterfaces() {
		Collection<String> ifaces = super.getImplementedInterfaces();
		ifaces.add(EntityMetadataProvider.class.getName());
		return ifaces;
	}
}
