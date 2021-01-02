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
 
 
/*  
 *   Copyright 2012 OSBI Ltd
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.legacysaiku.olap.dto;

import java.util.List;

public class SaikuAxis extends AbstractSaikuObject {

	private List<SaikuDimensionSelection> dimensionSelections;
	private int ordinal;
	private String sortOrder;
	private String sortLiteral;
	private String limitFunction;
	private String limitFunctionN;
	private String limitFunctionSortLiteral;
	private String filterCondition;

	public SaikuAxis() {		
		super(null,null);
		throw new RuntimeException("Unsupported Constructor. Serialization only");
	}
	
	public SaikuAxis(String name, int ordinal, String uniqueName, List<SaikuDimensionSelection> dimsel, String sortOrder, String sortLiteral) {
		super(uniqueName,name);
		this.dimensionSelections = dimsel;
		this.ordinal = ordinal;
		this.sortOrder = sortOrder;
		this.sortLiteral = sortLiteral;
	}
	
	public List<SaikuDimensionSelection> getDimensionSelections() {
		return dimensionSelections;
	}
	
	public int getOrdinal() {
		return ordinal;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public String getSortLiteral() {
		return sortLiteral;
	}

	/**
	 * @return the limitFunction
	 */
	public String getLimitFunction() {
		return limitFunction;
	}

	/**
	 * @param limitFunction the limitFunction to set
	 */
	public void setLimitFunction(String limitFunction) {
		this.limitFunction = limitFunction;
	}

	/**
	 * @return the limitFunctionN
	 */
	public String getLimitFunctionN() {
		return limitFunctionN;
	}

	/**
	 * @param limitFunctionN the limitFunctionN to set
	 */
	public void setLimitFunctionN(String limitFunctionN) {
		this.limitFunctionN = limitFunctionN;
	}

	/**
	 * @return the limitFunctionSortLiteral
	 */
	public String getLimitFunctionSortLiteral() {
		return limitFunctionSortLiteral;
	}

	/**
	 * @param limitFunctionSortLiteral the limitFunctionSortLiteral to set
	 */
	public void setLimitFunctionSortLiteral(String limitFunctionSortLiteral) {
		this.limitFunctionSortLiteral = limitFunctionSortLiteral;
	}

	/**
	 * @return the filterCondition
	 */
	public String getFilterCondition() {
		return filterCondition;
	}

	/**
	 * @param filterCondition the filterCondition to set
	 */
	public void setFilterCondition(String filterCondition) {
		this.filterCondition = filterCondition;
	}


}
