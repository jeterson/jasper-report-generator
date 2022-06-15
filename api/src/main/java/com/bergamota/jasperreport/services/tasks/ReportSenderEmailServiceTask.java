package com.bergamota.jasperreport.services.tasks;

import javax.mail.internet.MimeMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;

import com.bergamota.jasperreport.entities.EmailTaskDefinition;

public class ReportSenderEmailServiceTask extends AbstractReportEmailServiceTask {

	
	public ReportSenderEmailServiceTask(EmailTaskDefinition taskDefinition, ApplicationContext context) {
		super(taskDefinition, context);		
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {		
		javaMailSender.send(msg);
	}

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		javaMailSender.send(msg);
		
	}
		

}
