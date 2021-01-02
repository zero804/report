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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;

import org.hibernate.envers.Audited;

@Entity
@Table(name="BINARY_COLUMN_FILTER")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.reportengines.table.dto",
	createDecorator = true
)
public class BinaryColumnFilter extends FilterSpec {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2692299428473434186L;

	@ExposeToClient
	private BinaryOperator operator;
	
	@EnclosedEntity
	@ExposeToClient
	@OneToOne(cascade=CascadeType.ALL)
	private Column columnA;
	
	@EnclosedEntity
	@ExposeToClient
	@OneToOne(cascade=CascadeType.ALL)
	private Column columnB;

	public void setColumnA(Column columnA) {
		this.columnA = columnA;
	}

	public Column getColumnA() {
		return columnA;
	}

	public void setColumnB(Column columnB) {
		this.columnB = columnB;
	}

	public Column getColumnB() {
		return columnB;
	}

	public void setOperator(BinaryOperator operator) {
		this.operator = operator;
	}

	public BinaryOperator getOperator() {
		return operator;
	}
	
	@Override
	public Collection<Column> getColumns() {
		List<Column> l = new ArrayList<Column>();
		if(null != columnA)
			l.add(columnA);
		if(null != columnB)
			l.add(columnB);
		return l;
	}
	
}
