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
 
 
package net.datenwerke.security.service.usermanager.hooks;

import java.util.HashMap;

import org.apache.commons.configuration.Configuration;

import com.google.inject.Inject;

import net.datenwerke.gf.service.localization.RemoteMessageService;
import net.datenwerke.rs.base.service.parameterreplacements.provider.UserForJuel;
import net.datenwerke.rs.core.service.mail.MailService;
import net.datenwerke.rs.core.service.mail.MailTemplate;
import net.datenwerke.rs.core.service.mail.SimpleMail;
import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;
import net.datenwerke.security.ext.server.locale.DwSecurityMessages;
import net.datenwerke.security.service.usermanager.entities.User;

public class NotificationEmailChangedPasswordHook implements PasswordManualSetHook {

	private final MailService mailService;
	private final ConfigService configService;
	private final RemoteMessageService remoteMessageService;

	private final static String PROPERTY_USER = "user";

	public static final String CONFIG_FILE = "security/notifications.cf";

	@Inject
	public NotificationEmailChangedPasswordHook(RemoteMessageService remoteMessageService, MailService mailService,
			ConfigService configService) {
		this.remoteMessageService = remoteMessageService;
		this.mailService = mailService;
		this.configService = configService;
	}

	@Override
	public void passwordWasManuallySet(User user, boolean createdPassword) {

		if (null == user.getEmail())
			return;

		Configuration config = configService.getConfigFailsafe(CONFIG_FILE);
		String function = null;
		String content = null;
		String subject = null;
		/* For Email notification */
		if (createdPassword) {
			function = "createdpassword";
			subject = DwSecurityMessages.INSTANCE.createdPasswordSubject();
			content = DwSecurityMessages.INSTANCE.createdPasswordIntro() + "\n\n" + user.getUsername() + "\n\n"
					+ DwSecurityMessages.INSTANCE.createdPasswordEnd();
		} else {
			function = "changedpassword";
			subject = DwSecurityMessages.INSTANCE.changedPasswordSubject();
			content = DwSecurityMessages.INSTANCE.changedPasswordIntro() + "\n\n" + user.getUsername() + "\n\n"
					+ DwSecurityMessages.INSTANCE.changedPasswordEnd();
		}

		boolean disabled = config.getBoolean(function + "[@disabled]", false);
		if (disabled)
			return;

		/* Get the notification file */
		String mailTemplate = config.getString(function + ".email.text", content);
		String mailSubject = config.getString(function + ".email.subject", subject);

		/* prepare value map for template */
		HashMap<String, Object> replacements = new HashMap<String, Object>();
		replacements.put(PROPERTY_USER, UserForJuel.createInstance(user));

		String currentLanguage = LocalizationServiceImpl.getLocale().getLanguage();
		replacements.put("msgs", remoteMessageService.getMessages(currentLanguage));

		/* fill email template */
		MailTemplate template = new MailTemplate();
		template.setMessageTemplate(mailTemplate);
		template.setSubjectTemplate(mailSubject);
		template.setDataMap(replacements);

		SimpleMail mail = mailService.newTemplateMail(template);
		mail.setToRecipients(user.getEmail());

		/* Sending the email */
		mailService.sendMailSync(mail);
	}

}
