package com.bergamota.jasperreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.bergamota.jasperreport.entities.EmailTaskDefinition;
import com.bergamota.jasperreport.services.JasperReportGeneratorService;
import com.bergamota.jasperreport.services.ReportService;
import com.bergamota.jasperreport.services.tasks.ReportSenderEmailServiceTask;
import com.bergamota.jasperreport.services.tasks.ScheduleTaskService;

@SpringBootApplication
public class JasperReportGeneratorApplication implements CommandLineRunner {

	@Autowired
	private JasperReportGeneratorService reportService;
	
	@Autowired
	private ReportService service;
	
	@Autowired
	private ScheduleTaskService scheduleTaskService;
	
	@Autowired
	private ApplicationContext context;
		
	
	public static void main(String[] args) {
		SpringApplication.run(JasperReportGeneratorApplication.class, args);
		
		
	}

	@Override
	public void run(String... args) throws Exception {
		var taskDefinitin = EmailTaskDefinition.builder().build();
		taskDefinitin.setCronExpression("0 * * ? * *");
		taskDefinitin.setReferenceId(20L);
		taskDefinitin.setRecipients(new String[] {"jetersonsi@gmail.com"});
		taskDefinitin.setName("send-email-report");
		taskDefinitin.setSubject("Relatorio");
		taskDefinitin.setBody("");
		
		scheduleTaskService.addTaskToScheduler(1, new ReportSenderEmailServiceTask(taskDefinitin, context));
		
	}

}
