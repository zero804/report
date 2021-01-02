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
 
 
package net.datenwerke.rs.condition.client.condition.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.datenwerke.rs.condition.client.condition.Condition;

public class SimpleCondition implements Serializable, Condition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String key;
	private boolean hasExpression;

	private String name;
	private String description;
	private List<String> replacements = new ArrayList<>();

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean hasExpression() {
		return hasExpression;
	}

	public void setHasExpression(boolean hasExpression) {
		this.hasExpression = hasExpression;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getReplacements() {
		return replacements;
	}

	public void setReplacements(List<String> replacements) {
		this.replacements = replacements;
	}
	
	public void addReplacement(String replacement){
		if(null == replacements)
			replacements = new ArrayList<>();
		replacements.add(replacement);
	}
	
	
}
