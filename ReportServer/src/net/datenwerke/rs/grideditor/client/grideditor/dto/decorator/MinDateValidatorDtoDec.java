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
 
 
package net.datenwerke.rs.grideditor.client.grideditor.dto.decorator;

import net.datenwerke.rs.grideditor.client.grideditor.dto.MinDateValidatorDto;

import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.validator.MinDateValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinDateValidator.MinDateMessages;

/**
 * Dto Decorator for {@link MinDateValidatorDto}
 *
 */
public class MinDateValidatorDtoDec extends MinDateValidatorDto {


	private static final long serialVersionUID = 1L;

	public MinDateValidatorDtoDec() {
		super();
	}

	@Override
	public Validator<?> getValidator() {
		MinDateValidator minDateValidator = new MinDateValidator(getMinDate());
		minDateValidator.setMessages(new MinDateMessages() {
			@Override
			public String dateMinText(String max) {
				return getErrorMsg();
			}
		});
		return minDateValidator;
	}

}
