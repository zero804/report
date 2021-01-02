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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities.format;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.enums.NumberType;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;

import org.hibernate.envers.Audited;

@Entity
@Table(name="COLUMN_FORMAT_NUMBER")
@Inheritance(strategy=InheritanceType.JOINED)
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.reportengines.table.dto",
	createDecorator=true
)
public class ColumnFormatNumber extends ColumnFormat {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3270301667758472127L;

	@ExposeToClient
	private NumberType type = NumberType.DEFAULT;
	
	@ExposeToClient
	private boolean thousandSeparator = false;
	
	@ExposeToClient
	private int numberOfDecimalPlaces = 2;

	public NumberType getType() {
		return type;
	}

	public void setType(NumberType type) {
		this.type = type;
	}

	public boolean isThousandSeparator() {
		return thousandSeparator;
	}

	public void setThousandSeparator(boolean thousandSeparator) {
		this.thousandSeparator = thousandSeparator;
	}

	public int getNumberOfDecimalPlaces() {
		return numberOfDecimalPlaces;
	}

	public void setNumberOfDecimalPlaces(int numberOfDecimalPlaces) {
		this.numberOfDecimalPlaces = numberOfDecimalPlaces;
	}
	
	@Override
	public String format(Object value) {
		if(null == value)
			return null;

		try{
			NumberFormat format = getNumberFormat();
			return format.format(value);
		} catch(Exception e){
			return String.valueOf(value);
		}
	}

	public NumberFormat getNumberFormat() {
		NumberFormat format = DecimalFormat.getInstance(LocalizationServiceImpl.getLocale());
		String pattern = getPattern();
		if(format instanceof DecimalFormat)
			((DecimalFormat)format).applyPattern(pattern);
		return format;
	}
	
	public String getPattern() {
		switch(type){
		case PERCENT:
			return getDecimalPattern() + "%";
		case SCIENTIFIC:
			return getDecimalPattern() + "E0";
		default:
			return getDecimalPattern();
		}
	}
	
	public String getPatternForExcel() {
		String pattern = getPattern();
		
		return pattern.replace("E", "E-");
	}
	
	public String getDecimalPattern() {
		StringBuilder pattern = new StringBuilder();
		if(isThousandSeparator())
			pattern.append("#,##0");
		else
			pattern.append("0");
		
		if(0 < getNumberOfDecimalPlaces())
			pattern.append(".");
		
		for(int i = Math.max(0,getNumberOfDecimalPlaces()); i > 0; i--)
			pattern.append(0);
		
		return pattern.toString();
	}
	
}
