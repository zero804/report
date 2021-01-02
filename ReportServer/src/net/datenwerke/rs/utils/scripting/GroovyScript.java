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
 
 
package net.datenwerke.rs.utils.scripting;

import groovy.lang.GroovyObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class GroovyScript {

	private Class<?> script;
	
	private Map<String, Object> binding = new HashMap<String, Object>();
	
	public GroovyScript(Class<?> script) {
		this.script = script;
	}
	
	Class<?> getScript() {
		return script;
	}
	
	public void newBinding(){
		binding = new HashMap<String, Object>();
	}
	
	public void setVariable(String key, Object value){
		binding.put(key, value);
	}
	
	public Map<String, Object> getBinding() {
		return binding;
	}

	public Object run() {
		try {
			GroovyObject groovy = (GroovyObject) script.newInstance();
			for(Entry<String, Object> e : binding.entrySet())
				groovy.setProperty(e.getKey(), e.getValue());
			return groovy.invokeMethod("run", null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
