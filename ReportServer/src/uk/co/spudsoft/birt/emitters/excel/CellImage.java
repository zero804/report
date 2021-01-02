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
 
 
/*************************************************************************************
 * Copyright (c) 2011, 2012, 2013 James Talbut.
 *  jim-emitters@spudsoft.co.uk
 *  
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     James Talbut - Initial implementation.
 ************************************************************************************/


package uk.co.spudsoft.birt.emitters.excel;

import org.eclipse.birt.report.engine.content.IImageContent;

/**
 * <p>
 * CellImage is used to cache all the required data for inserting images so that they can be
 * processed after all other spreadsheet contents has been inserted.
 * </p><p>
 * Processing images after all other spreadsheet contents means that the images will be unaffected
 * by any column resizing that may be required.
 * Images usually cause row resizing (the emitter never allows an image to spread onto multiple rows),
 * but never cause column resizing.
 * </p>
 * 
 * @author Jim Talbut
 *
 */
public class CellImage {
	public Coordinate location;
	public int imageIdx;
	public IImageContent image;
	public boolean spanColumns;
	public CellImage(Coordinate location, int imageIdx, IImageContent image, boolean spanColumns) {
		this.location = location;
		this.imageIdx = imageIdx;
		this.image = image;
		this.spanColumns = spanColumns;
	}
}

