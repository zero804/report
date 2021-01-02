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

import java.util.Set;
import java.util.TreeSet;

import net.datenwerke.annotationprocessing.utils.SourceFileGeneratorImpl;
import net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor;
import net.datenwerke.dtoservices.dtogenerator.analizer.PosoAnalizer;

public abstract class DtoCreator extends SourceFileGeneratorImpl {

	public static final String ID_FIELD_NAME = "dtoId";
	public static final String PROPERTY_ACCESS_METHOD = "instantiatePropertyAccess";
	
	protected DtoAnnotationProcessor dtoAnnotationProcessor;
	protected PosoAnalizer posoAnalizer;
	protected Set<String> referenceAccu = new TreeSet<String>();

	public DtoCreator(PosoAnalizer posoAnalizer, DtoAnnotationProcessor dtoAnnotationProcessor) {
		super(dtoAnnotationProcessor);
		this.posoAnalizer = posoAnalizer;
		this.dtoAnnotationProcessor = dtoAnnotationProcessor;
	}

}