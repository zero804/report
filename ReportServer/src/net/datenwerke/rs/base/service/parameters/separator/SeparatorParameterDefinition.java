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
 
 
package net.datenwerke.rs.base.service.parameters.separator;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.base.client.parameters.locale.RsMessages;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;

import org.hibernate.envers.Audited;

/**
 * 
 *
 */
@Entity
@Table(name="SEP_PARAM_DEF")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.parameters.separator.dto",
	displayTitle="RsMessages.INSTANCE.separatorParameterText()",
	additionalImports=RsMessages.class
)
public class SeparatorParameterDefinition extends ParameterDefinition<SeparatorParameterInstance> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4747333715325728172L;

	@Override
	protected SeparatorParameterInstance doCreateParameterInstance() {
		return new SeparatorParameterInstance();
	}
	
	@Override
    public boolean isSeparator() {
        return true;
    }

}
