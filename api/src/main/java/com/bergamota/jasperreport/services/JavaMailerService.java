package com.bergamota.jasperreport.services;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.bergamota.jasperreport.entities.EmailConfig;

public class JavaMailerService {

	private JavaMailSender javaMailSender;
	private EmailConfig emailConfig;
	private static JavaMailerService instance;
	

	private JavaMailerService() {}

	public static JavaMailerService getInstance() {
		if(instance == null)
			instance = new JavaMailerService();

		return instance;
	}

	public void setSender(JavaMailSender sender) {
		this.javaMailSender = sender;
	}

	public JavaMailSender getSender() {
		return javaMailSender;
	}
	
	public EmailConfig getEmailConfig() {
		return emailConfig;
	}

	public void setEmailConfig(EmailConfig emailConfig) {		

		this.emailConfig = emailConfig;
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		//mailSender.setHost("smtp.mailtrap.io");
		mailSender.setHost(emailConfig.getHost());
		//mailSender.setPort(2525);
		mailSender.setPort(emailConfig.getPort());

		//mailSender.setUsername("8f7dbb3cf08298");
		mailSender.setUsername(emailConfig.getUser());
		//mailSender.setPassword("1cb54b175f2f8a");
		mailSender.setPassword(emailConfig.getPassword());

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", emailConfig.isAuth());
		props.put("mail.smtp.starttls.enable", emailConfig.isTtls());
		props.put("mail.debug", "false");
		
		setSender(mailSender);
	}



}
