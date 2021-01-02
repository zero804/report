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

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;

import net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor;
import net.datenwerke.dtoservices.dtogenerator.annotations.EnumLabel;

public class EnumAnalizer {

	private DtoAnnotationProcessor dtoAnnotationProcessor;
	private Element theEnum;

	public EnumAnalizer(DtoAnnotationProcessor dtoAnnotationProcessor, Element theEnum) {
		super();
		this.dtoAnnotationProcessor = dtoAnnotationProcessor;
		this.theEnum = theEnum;
		
		/* analize and store information objects */
		analizeEnum();
	}

	private void analizeEnum() {
	}
	
	public Element getTheEnum() {
		return theEnum;
	}

	public String getSimpleName() {
		return getTheEnum().getSimpleName().toString();
	}

	public boolean hasLabelAnnotation() {
		return getLabelAnnotation() != null;
	}

	public EnumLabel getLabelAnnotation() {
		return getTheEnum().getAnnotation(EnumLabel.class);
	}
	
	public AnnotationMirror getLabelAnnotationMirror() {
		for(AnnotationMirror mirror : theEnum.getAnnotationMirrors()){
			if(mirror.getAnnotationType().toString().equals(EnumLabel.class.getName()))
				return mirror;
		}
		
		throw new IllegalStateException("Could not find annotation mirror");
	}
	
	public DeclaredType getLabelMsgInterface() {
		AnnotationMirror gdAnno = getLabelAnnotationMirror();
		for(ExecutableElement key : gdAnno.getElementValues().keySet()){
			if(key.toString().equals("msg()")){
				AnnotationValue value = gdAnno.getElementValues().get(key);
				return (DeclaredType) value.getValue(); 
			}
		}
		
		return null;
	}

}
