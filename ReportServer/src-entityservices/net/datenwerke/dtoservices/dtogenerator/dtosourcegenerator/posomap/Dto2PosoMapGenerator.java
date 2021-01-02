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
 
 
package net.datenwerke.dtoservices.dtogenerator.dtosourcegenerator.posomap;

import java.util.Collection;

import javax.annotation.processing.AbstractProcessor;

import net.datenwerke.annotationprocessing.utils.SourceFileGeneratorImpl;
import net.datenwerke.dtoservices.dtogenerator.analizer.PosoAnalizer;
import net.datenwerke.dtoservices.dtogenerator.annotations.CorrespondingPoso;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;

public class Dto2PosoMapGenerator extends SourceFileGeneratorImpl {

	private final PosoAnalizer posoAnalizer;
	
	public Dto2PosoMapGenerator(PosoAnalizer posoAnalizer, AbstractProcessor processor) {
		super(processor);
		
		this.posoAnalizer = posoAnalizer;
	}

	@Override
	public String getPackageName() {
		return posoAnalizer.getDtoInformation().getDto2PosoMapPackageName();
	}

	@Override
	public String getClassName() {
		return posoAnalizer.getDtoInformation().getDto2PosoMapClassName();
	}

	@Override
	protected void addClassBody(StringBuilder sourceBuilder) {

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
		
		ifaces.add(Dto2PosoMapper.class.getSimpleName());
		
		return ifaces;
	}
	
	@Override
	protected Collection<String> getReferencedClasses() {
		Collection<String> references = super.getReferencedClasses();
		
		references.add(CorrespondingPoso.class.getName());
		references.add(Dto2PosoMapper.class.getName());
		
		return references;
	}

}
