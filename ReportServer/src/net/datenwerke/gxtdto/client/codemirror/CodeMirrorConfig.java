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
 
 
package net.datenwerke.gxtdto.client.codemirror;

public class CodeMirrorConfig {
	
	private String mode = "javascript";
	private boolean lineNumbersVisible;
	private boolean matchBrackets;
	
	public CodeMirrorConfig() {

	}

	public CodeMirrorConfig(String mode){
		this(mode, false);
	}

	public CodeMirrorConfig(String mode, boolean lineNumbersVisible) {
		this.mode = mode;
		this.lineNumbersVisible = lineNumbersVisible;
		init(mode);
	}

	protected void init(String mode) {
		if("text/x-groovy".equals(mode) ||
		   "text/x-sql".equals(mode)){
			setMatchBrackets(true);
		}
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public boolean isLineNumbersVisible() {
		return lineNumbersVisible;
	}
	
	public void setLineNumbersVisible(boolean lineNumbersVisible) {
		this.lineNumbersVisible = lineNumbersVisible;
	}
	
	public boolean isMatchBrackets() {
		return matchBrackets;
	}
	
	public void setMatchBrackets(boolean matchBrackets) {
		this.matchBrackets = matchBrackets;
	}
	
}
