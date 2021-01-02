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
 
 
package net.datenwerke.rs.core.service.mail;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import net.datenwerke.rs.core.service.mail.annotations.MailModuleDefaultFrom;
import net.datenwerke.rs.core.service.mail.annotations.MailModuleDefaultFromName;
import net.datenwerke.rs.core.service.mail.interfaces.SessionProvider;
import net.datenwerke.rs.utils.misc.Nullable;

/**
 * 
 *
 */
public class SimpleMail extends MimeMessage implements SessionProvider {
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	protected static final String MIME_SUBTYPE_HTML = "html";
	protected static final String MIME_SUBTYPE_PLAIN = "plain";
	protected static final String CHARSET_UTF8 = "UTF-8";

	@Inject
	public SimpleMail(
		Session session,
		@Nullable @MailModuleDefaultFrom String from, @Nullable @MailModuleDefaultFromName String fromName) {
		super(session);
		
		if(null != from)
			setFrom(from, fromName);
	}
	
	public void setFrom(String from, String fromName) {
		try {
			if (null == fromName)
				super.setFrom(new InternetAddress(from));
			else
				super.setFrom(new InternetAddress(from, fromName));
		} catch (Exception e) {
			logger.warn( e.getMessage(), e);
		}
	}
	
	public void setFrom(Address from, boolean setSender) {
		if(setSender){
			try {
				if(null != super.getFrom() || super.getFrom().length > 0){
					if (super.getFrom()[0] instanceof InternetAddress) {
						InternetAddress internetAddress = (InternetAddress) super.getFrom()[0];
						setSender(internetAddress);
						super.setHeader("Return-path", internetAddress.getAddress());
					}
				}
			} catch (MessagingException e) {
				logger.warn( e.getMessage(), e);
			}
		}
		
		try {
			setFrom(from);
		} catch (MessagingException e) {
			logger.warn( e.getMessage(), e);
		}
	}



	@Override
	public void setText(String text){
		try {
			super.setText(text, CHARSET_UTF8, MIME_SUBTYPE_PLAIN);
		} catch (MessagingException e) {
			logger.warn( e.getMessage(), e);
		}
	}
	
	public void setContents(String text){
		setText(text);
	}
	
	public void setHtml(String html){
		try {
			super.setText(html, CHARSET_UTF8, MIME_SUBTYPE_HTML);
		} catch (MessagingException e) {
			logger.warn( e.getMessage(), e);
		}
	}
	
	public void setHtml(String html, SimpleAttachement... attachements){
		/* create multipart */
		MimeMultipart multipart = new MimeMultipart();
		
		try {
			/* create text */
			MimeBodyPart textMBP = new MimeBodyPart();
			textMBP.setText(html, CHARSET_UTF8, MIME_SUBTYPE_HTML);
			multipart.addBodyPart(textMBP);
			
			/* create attachements */
			for(SimpleAttachement att : attachements){
				MimeBodyPart mbp = new MimeBodyPart();
				mbp.setContent(att.getAttachement(), att.getMimeType());
				mbp.setFileName(MimeUtility.encodeText(att.getFileName(), CHARSET_UTF8, null));
				multipart.addBodyPart(mbp);
			}
			
			/* set contents */
			setContent(multipart);
		} catch (Exception e) {
			logger.warn( e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	
	public void setContent(String text, SimpleAttachement... attachements){
		/* create multipart */
		MimeMultipart multipart = new MimeMultipart();
		
		try {
			/* create text */
			MimeBodyPart textMBP = new MimeBodyPart();
			textMBP.setText(text, CHARSET_UTF8, MIME_SUBTYPE_PLAIN);
			multipart.addBodyPart(textMBP);
			
			/* create attachements */
			for(SimpleAttachement att : attachements){
				multipart.addBodyPart(createAttachmentPart(att));
			}
			
			/* set contents */
			setContent(multipart);
		} catch (Exception e) {
			logger.warn( e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	protected MimeBodyPart createAttachmentPart(SimpleAttachement att) throws UnsupportedEncodingException, MessagingException{
		MimeBodyPart mbp = new MimeBodyPart();
		mbp.setFileName(MimeUtility.encodeText(att.getFileName(), CHARSET_UTF8, null));
		
		Object data = att.getAttachement();
		if(data instanceof String) {
			ContentType ct = new ContentType(att.getMimeType());
			mbp.setText((String)data, CHARSET_UTF8, ct.getSubType());
		}else if(data instanceof byte[]){
			DataSource ds = new ByteArrayDataSource((byte[])data, att.getMimeType());
			mbp.setDataHandler(new DataHandler(ds ));
		}else{
			mbp.setContent(data, att.getMimeType());
		}
		
		return mbp;
	}

	public void setToRecipients(List<String> recipients){
		setXXRecipients(RecipientType.TO, recipients);
	}
	
	public void setToRecipients(String... recipients){
		setXXRecipients(RecipientType.TO, recipients);
	}
	
	public void setCCRecipients(String... recipients){
		setXXRecipients(RecipientType.CC, recipients);
	}
	
	public void setBccRecipients(String... recipients){
		setXXRecipients(RecipientType.BCC, recipients);
	}
	
	protected void setXXRecipients(javax.mail.Message.RecipientType type, List<String> recipients){
		try{
			InternetAddress[] addresses = new InternetAddress[recipients.size()];
			for(int i = 0; i < recipients.size(); i++)
				addresses[i] = new InternetAddress(recipients.get(i));
			
			setRecipients(type, addresses);
		} catch(AddressException e){
			// TODO error handling
			logger.warn( e.getMessage(), e);
		} catch (MessagingException e) {
			// TODO error handling
			logger.warn( e.getMessage(), e);
		}
	}
	
	
	protected void setXXRecipients(javax.mail.Message.RecipientType type, String... recipients){
		try{
			InternetAddress[] addresses = new InternetAddress[recipients.length];
			for(int i = 0; i < recipients.length; i++)
				addresses[i] = new InternetAddress(recipients[i]);
			
			setRecipients(type, addresses);
		} catch(AddressException e){
			// TODO error handling
			logger.warn( e.getMessage(), e);
		} catch (MessagingException e) {
			// TODO error handling
			logger.warn( e.getMessage(), e);
		}
	}
	
	@Override
	public void setSubject(String subject){
		try {
			super.setSubject(subject,CHARSET_UTF8);
		} catch (MessagingException e) {
			// TODO error handling
			logger.warn( e.getMessage(), e);
		}
	}

	@Override
	public Session getSession() {
		return session;
	}
}
