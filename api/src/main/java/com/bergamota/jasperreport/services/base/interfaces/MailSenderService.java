package com.bergamota.jasperreport.services.base.interfaces;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

public interface MailSenderService {
	void sendHtmlEmail(MimeMessage msg);
	void sendEmail(SimpleMailMessage msg);
}
